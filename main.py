from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse
from typing import Optional, List
from pydantic import BaseModel
from datetime import datetime
import re

app = FastAPI(
    title="Employee Information API",
    description="社員情報取得API - Employee information retrieval API",
    version="1.0.0"
)

# Data models
class Employee(BaseModel):
    employee_id: str
    name: str
    department: str
    position: str
    start_date: str
    end_date: Optional[str] = None

class DownloadedReport(BaseModel):
    report_id: str
    report_name: str
    download_date: str
    file_type: str
    file_size: int

class EmployeeHistory(BaseModel):
    employee_id: str
    year_month: str
    employee_info: Employee
    is_active: bool

# Mock data repository
class DataRepository:
    def __init__(self):
        # Sample employee data
        self.employees = {
            "EMP001": Employee(
                employee_id="EMP001",
                name="田中太郎",
                department="営業部",
                position="主任",
                start_date="2020-01-01"
            ),
            "EMP002": Employee(
                employee_id="EMP002", 
                name="佐藤花子",
                department="技術部",
                position="エンジニア",
                start_date="2021-04-01"
            ),
            "EMP003": Employee(
                employee_id="EMP003",
                name="山田次郎",
                department="人事部", 
                position="課長",
                start_date="2019-01-01",
                end_date="2023-03-31"
            )
        }
        
        # Sample employee history (employee status for specific periods)
        self.employee_history = {
            ("EMP001", "202301"): EmployeeHistory(
                employee_id="EMP001",
                year_month="202301",
                employee_info=self.employees["EMP001"],
                is_active=True
            ),
            ("EMP001", "202302"): EmployeeHistory(
                employee_id="EMP001", 
                year_month="202302",
                employee_info=self.employees["EMP001"],
                is_active=True
            ),
            ("EMP002", "202301"): EmployeeHistory(
                employee_id="EMP002",
                year_month="202301", 
                employee_info=self.employees["EMP002"],
                is_active=True
            ),
            ("EMP003", "202301"): EmployeeHistory(
                employee_id="EMP003",
                year_month="202301",
                employee_info=self.employees["EMP003"],
                is_active=True
            ),
            ("EMP003", "202302"): EmployeeHistory(
                employee_id="EMP003",
                year_month="202302", 
                employee_info=self.employees["EMP003"],
                is_active=True
            ),
            ("EMP003", "202304"): EmployeeHistory(
                employee_id="EMP003",
                year_month="202304",
                employee_info=self.employees["EMP003"],
                is_active=False
            )
        }
        
        # Sample downloaded reports data
        self.downloaded_reports = {
            ("EMP001", "202301"): [
                DownloadedReport(
                    report_id="RPT001",
                    report_name="月次売上報告書",
                    download_date="2023-01-15",
                    file_type="PDF",
                    file_size=1024000
                ),
                DownloadedReport(
                    report_id="RPT002", 
                    report_name="顧客一覧表",
                    download_date="2023-01-20",
                    file_type="Excel",
                    file_size=512000
                )
            ],
            ("EMP001", "202302"): [
                DownloadedReport(
                    report_id="RPT003",
                    report_name="月次売上報告書", 
                    download_date="2023-02-15",
                    file_type="PDF",
                    file_size=1048000
                )
            ],
            ("EMP002", "202301"): [
                DownloadedReport(
                    report_id="RPT004",
                    report_name="システム稼働状況報告書",
                    download_date="2023-01-25",
                    file_type="PDF", 
                    file_size=2048000
                )
            ]
        }

    def employee_exists(self, employee_id: str) -> bool:
        """Check if employee ID exists"""
        return employee_id in self.employees
    
    def get_employee_info(self, employee_id: str, year_month: str) -> Optional[EmployeeHistory]:
        """Get employee information for specific period"""
        return self.employee_history.get((employee_id, year_month))
    
    def get_downloaded_reports(self, employee_id: str, year_month: str) -> List[DownloadedReport]:
        """Get downloaded reports for employee in specific month"""
        return self.downloaded_reports.get((employee_id, year_month), [])

# Initialize data repository
repo = DataRepository()

def validate_year_month(year_month: str) -> bool:
    """Validate year_month format (YYYYMM)"""
    if not re.match(r'^\d{6}$', year_month):
        return False
    
    year = int(year_month[:4])
    month = int(year_month[4:])
    
    if year < 1900 or year > 9999:
        return False
    if month < 1 or month > 12:
        return False
        
    return True

@app.get("/")
async def root():
    """Root endpoint with API information"""
    return {
        "message": "Employee Information API",
        "description": "社員情報取得API",
        "version": "1.0.0",
        "endpoints": {
            "employee_info": "/employee/{employee_id}/{year_month}",
            "employee_downloads": "/employee/{employee_id}/{year_month}/downloads"
        }
    }

@app.get("/employee/{employee_id}/{year_month}")
async def get_employee_info(employee_id: str, year_month: str):
    """
    社員ID、年月（YYYYMM）をもとに、その社員のその当時の情報を取得
    Get employee information based on employee ID and year-month
    """
    # Validate year_month format
    if not validate_year_month(year_month):
        raise HTTPException(status_code=400, detail="年月の形式が正しくありません（YYYYMM形式で入力してください）")
    
    # Check if employee exists
    if not repo.employee_exists(employee_id):
        raise HTTPException(status_code=404, detail="IDが誤っています。")
    
    # Get employee info for the specific period
    employee_history = repo.get_employee_info(employee_id, year_month)
    
    if not employee_history:
        raise HTTPException(status_code=404, detail="その期間にその社員は存在しません")
    
    return {
        "employee_id": employee_history.employee_id,
        "year_month": employee_history.year_month,
        "employee_info": employee_history.employee_info,
        "is_active": employee_history.is_active
    }

@app.get("/employee/{employee_id}/{year_month}/downloads")
async def get_employee_downloads(employee_id: str, year_month: str):
    """
    社員ID、年月（YYYYMM）をもとに、その月にそのユーザがダウンロードした帳票一覧を取得
    Get list of downloaded reports by employee in specific month
    """
    # Validate year_month format
    if not validate_year_month(year_month):
        raise HTTPException(status_code=400, detail="年月の形式が正しくありません（YYYYMM形式で入力してください）")
    
    # Check if employee exists
    if not repo.employee_exists(employee_id):
        raise HTTPException(status_code=404, detail="IDが誤っています。")
    
    # Check if employee existed in that period
    employee_history = repo.get_employee_info(employee_id, year_month)
    if not employee_history:
        raise HTTPException(status_code=404, detail="その期間にその社員は存在しません")
    
    # Get downloaded reports
    downloaded_reports = repo.get_downloaded_reports(employee_id, year_month)
    
    return {
        "employee_id": employee_id,
        "year_month": year_month,
        "downloaded_reports": downloaded_reports,
        "total_count": len(downloaded_reports)
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)