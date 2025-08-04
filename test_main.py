import pytest
from fastapi.testclient import TestClient
from main import app

client = TestClient(app)

class TestEmployeeInfoAPI:
    """Test cases for employee information API"""
    
    def test_root_endpoint(self):
        """Test root endpoint returns API information"""
        response = client.get("/")
        assert response.status_code == 200
        data = response.json()
        assert "message" in data
        assert "Employee Information API" in data["message"]
    
    def test_get_employee_info_success(self):
        """Test successful employee info retrieval"""
        response = client.get("/employee/EMP001/202301")
        assert response.status_code == 200
        data = response.json()
        assert data["employee_id"] == "EMP001"
        assert data["year_month"] == "202301"
        assert data["employee_info"]["name"] == "田中太郎"
        assert data["is_active"] == True
    
    def test_get_employee_info_invalid_employee_id(self):
        """Test employee info with invalid employee ID"""
        response = client.get("/employee/INVALID/202301")
        assert response.status_code == 404
        data = response.json()
        assert data["detail"] == "IDが誤っています。"
    
    def test_get_employee_info_no_data_for_period(self):
        """Test employee info when no data exists for the period"""
        response = client.get("/employee/EMP001/202312")
        assert response.status_code == 404
        data = response.json()
        assert data["detail"] == "その期間にその社員は存在しません"
    
    def test_get_employee_info_invalid_year_month_format(self):
        """Test employee info with invalid year-month format"""
        response = client.get("/employee/EMP001/2023-01")
        assert response.status_code == 400
        data = response.json()
        assert "年月の形式が正しくありません" in data["detail"]
    
    def test_get_employee_info_invalid_month(self):
        """Test employee info with invalid month"""
        response = client.get("/employee/EMP001/202313")
        assert response.status_code == 400
        data = response.json()
        assert "年月の形式が正しくありません" in data["detail"]

class TestEmployeeDownloadsAPI:
    """Test cases for employee downloads API"""
    
    def test_get_employee_downloads_success(self):
        """Test successful employee downloads retrieval"""
        response = client.get("/employee/EMP001/202301/downloads")
        assert response.status_code == 200
        data = response.json()
        assert data["employee_id"] == "EMP001"
        assert data["year_month"] == "202301"
        assert data["total_count"] == 2
        assert len(data["downloaded_reports"]) == 2
        assert data["downloaded_reports"][0]["report_name"] == "月次売上報告書"
    
    def test_get_employee_downloads_no_downloads(self):
        """Test employee downloads when no downloads exist"""
        response = client.get("/employee/EMP002/202302/downloads")
        assert response.status_code == 404
        data = response.json()
        assert data["detail"] == "その期間にその社員は存在しません"
    
    def test_get_employee_downloads_invalid_employee_id(self):
        """Test employee downloads with invalid employee ID"""
        response = client.get("/employee/INVALID/202301/downloads")
        assert response.status_code == 404
        data = response.json()
        assert data["detail"] == "IDが誤っています。"
    
    def test_get_employee_downloads_no_data_for_period(self):
        """Test employee downloads when no data exists for the period"""
        response = client.get("/employee/EMP001/202312/downloads")
        assert response.status_code == 404
        data = response.json()
        assert data["detail"] == "その期間にその社員は存在しません"
    
    def test_get_employee_downloads_invalid_year_month_format(self):
        """Test employee downloads with invalid year-month format"""
        response = client.get("/employee/EMP001/2023-01/downloads")
        assert response.status_code == 400
        data = response.json()
        assert "年月の形式が正しくありません" in data["detail"]

class TestValidation:
    """Test cases for validation functions"""
    
    def test_employee_exists_in_different_periods(self):
        """Test employee existence across different periods"""
        # EMP003 exists in 202301, 202302 but not active in 202304
        response1 = client.get("/employee/EMP003/202301")
        assert response1.status_code == 200
        data1 = response1.json()
        assert data1["is_active"] == True
        
        response2 = client.get("/employee/EMP003/202304")
        assert response2.status_code == 200
        data2 = response2.json()
        assert data2["is_active"] == False
    
    def test_year_month_edge_cases(self):
        """Test edge cases for year-month validation"""
        # Test various invalid formats
        invalid_formats = ["20231", "2023", "202300", "202313", "abcdef", ""]
        
        for invalid_format in invalid_formats:
            response = client.get(f"/employee/EMP001/{invalid_format}")
            assert response.status_code == 400