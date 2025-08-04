from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Optional
from datetime import datetime
import re

app = FastAPI(
    title="Employee Information API",
    description="社員情報取得関連API",
    version="1.0.0"
)

# Data models
class Employee(BaseModel):
    employee_id: str
    name: str
    department: str
    position: str
    start_date: str  # YYYYMM format
    end_date: Optional[str] = None  # YYYYMM format, None if still active

class EmployeeInfo(BaseModel):
    employee_id: str
    name: str
    department: str
    position: str
    period: str  # YYYYMM

class DownloadedDocument(BaseModel):
    document_id: str
    document_name: str
    download_date: str
    employee_id: str
    period: str  # YYYYMM

class ErrorResponse(BaseModel):
    error: str

# Sample data - In a real application, this would be a database
employees_data = [
    Employee(
        employee_id="EMP001",
        name="田中太郎",
        department="営業部",
        position="主任",
        start_date="202301"
    ),
    Employee(
        employee_id="EMP002",
        name="佐藤花子",
        department="人事部",
        position="課長",
        start_date="202201"
    ),
    Employee(
        employee_id="EMP003",
        name="鈴木次郎",
        department="技術部",
        position="エンジニア",
        start_date="202303",
        end_date="202310"
    )
]

documents_data = [
    DownloadedDocument(
        document_id="DOC001",
        document_name="給与明細_202311.pdf",
        download_date="2023-11-15",
        employee_id="EMP001",
        period="202311"
    ),
    DownloadedDocument(
        document_id="DOC002",
        document_name="年末調整書類_202312.pdf",
        download_date="2023-12-01",
        employee_id="EMP001",
        period="202312"
    ),
    DownloadedDocument(
        document_id="DOC003",
        document_name="給与明細_202311.pdf",
        download_date="2023-11-16",
        employee_id="EMP002",
        period="202311"
    )
]

def validate_period_format(period: str) -> bool:
    """Validate YYYYMM format"""
    pattern = r'^\d{6}$'
    if not re.match(pattern, period):
        return False
    
    year = int(period[:4])
    month = int(period[4:])
    
    return 1900 <= year <= 2100 and 1 <= month <= 12

def employee_exists(employee_id: str) -> bool:
    """Check if employee ID exists"""
    return any(emp.employee_id == employee_id for emp in employees_data)

def employee_active_in_period(employee_id: str, period: str) -> bool:
    """Check if employee was active in the given period"""
    for emp in employees_data:
        if emp.employee_id == employee_id:
            start_period = emp.start_date
            end_period = emp.end_date
            
            if start_period <= period:
                if end_period is None or period <= end_period:
                    return True
    return False

@app.get("/")
async def root():
    return {"message": "Employee Information API"}

@app.get("/employee/{employee_id}/info/{period}")
async def get_employee_info(employee_id: str, period: str):
    """
    社員ID、年月（YYYYMM）をもとに、その社員のその当時の情報を取得
    """
    # Validate period format
    if not validate_period_format(period):
        raise HTTPException(status_code=400, detail="期間の形式が正しくありません（YYYYMM形式で入力してください）")
    
    # Check if employee ID exists
    if not employee_exists(employee_id):
        raise HTTPException(status_code=404, detail="「ID」が誤っています。")
    
    # Check if employee was active in the given period
    if not employee_active_in_period(employee_id, period):
        raise HTTPException(status_code=404, detail="その期間にその社員は存在しません")
    
    # Get employee information
    for emp in employees_data:
        if emp.employee_id == employee_id:
            return EmployeeInfo(
                employee_id=emp.employee_id,
                name=emp.name,
                department=emp.department,
                position=emp.position,
                period=period
            )

@app.get("/employee/{employee_id}/documents/{period}")
async def get_employee_documents(employee_id: str, period: str):
    """
    社員ID、年月（YYYYMM）をもとに、その月にそのユーザがダウンロードした帳票一覧を取得
    """
    # Validate period format
    if not validate_period_format(period):
        raise HTTPException(status_code=400, detail="期間の形式が正しくありません（YYYYMM形式で入力してください）")
    
    # Check if employee ID exists
    if not employee_exists(employee_id):
        raise HTTPException(status_code=404, detail="「ID」が誤っています。")
    
    # Check if employee was active in the given period
    if not employee_active_in_period(employee_id, period):
        raise HTTPException(status_code=404, detail="その期間にその社員は存在しません")
    
    # Get documents for the employee in the given period
    employee_documents = [
        doc for doc in documents_data 
        if doc.employee_id == employee_id and doc.period == period
    ]
    
    return {
        "employee_id": employee_id,
        "period": period,
        "documents": employee_documents
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)