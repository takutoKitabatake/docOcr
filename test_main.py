import pytest
from fastapi.testclient import TestClient
from main import app

client = TestClient(app)

class TestEmployeeInfoAPI:
    """Test cases for employee information API"""
    
    def test_get_employee_info_success(self):
        """Test successful retrieval of employee information"""
        response = client.get("/employee/EMP001/info/202311")
        assert response.status_code == 200
        data = response.json()
        assert data["employee_id"] == "EMP001"
        assert data["name"] == "田中太郎"
        assert data["department"] == "営業部"
        assert data["position"] == "主任"
        assert data["period"] == "202311"
    
    def test_get_employee_info_invalid_id(self):
        """Test error when employee ID doesn't exist"""
        response = client.get("/employee/INVALID/info/202311")
        assert response.status_code == 404
        assert response.json()["detail"] == "「ID」が誤っています。"
    
    def test_get_employee_info_period_not_exists(self):
        """Test error when employee wasn't active in the period"""
        # EMP003 ended in 202310, so 202311 should not exist
        response = client.get("/employee/EMP003/info/202311")
        assert response.status_code == 404
        assert response.json()["detail"] == "その期間にその社員は存在しません"
    
    def test_get_employee_info_invalid_period_format(self):
        """Test error when period format is invalid"""
        response = client.get("/employee/EMP001/info/2023")
        assert response.status_code == 400
        assert "期間の形式が正しくありません" in response.json()["detail"]

class TestEmployeeDocumentsAPI:
    """Test cases for employee documents API"""
    
    def test_get_employee_documents_success(self):
        """Test successful retrieval of employee documents"""
        response = client.get("/employee/EMP001/documents/202311")
        assert response.status_code == 200
        data = response.json()
        assert data["employee_id"] == "EMP001"
        assert data["period"] == "202311"
        assert len(data["documents"]) == 1
        assert data["documents"][0]["document_name"] == "給与明細_202311.pdf"
    
    def test_get_employee_documents_no_documents(self):
        """Test when employee has no documents in the period"""
        response = client.get("/employee/EMP002/documents/202312")
        assert response.status_code == 200
        data = response.json()
        assert data["employee_id"] == "EMP002"
        assert data["period"] == "202312"
        assert len(data["documents"]) == 0
    
    def test_get_employee_documents_invalid_id(self):
        """Test error when employee ID doesn't exist"""
        response = client.get("/employee/INVALID/documents/202311")
        assert response.status_code == 404
        assert response.json()["detail"] == "「ID」が誤っています。"
    
    def test_get_employee_documents_period_not_exists(self):
        """Test error when employee wasn't active in the period"""
        # EMP003 ended in 202310, so 202311 should not exist
        response = client.get("/employee/EMP003/documents/202311")
        assert response.status_code == 404
        assert response.json()["detail"] == "その期間にその社員は存在しません"
    
    def test_get_employee_documents_invalid_period_format(self):
        """Test error when period format is invalid"""
        response = client.get("/employee/EMP001/documents/invalid")
        assert response.status_code == 400
        assert "期間の形式が正しくありません" in response.json()["detail"]

class TestUtilityFunctions:
    """Test utility functions"""
    
    def test_validate_period_format(self):
        """Test period format validation"""
        from main import validate_period_format
        
        # Valid formats
        assert validate_period_format("202311") == True
        assert validate_period_format("202401") == True
        assert validate_period_format("202312") == True
        
        # Invalid formats
        assert validate_period_format("2023") == False
        assert validate_period_format("20231") == False
        assert validate_period_format("2023111") == False
        assert validate_period_format("202313") == False  # Invalid month
        assert validate_period_format("202300") == False  # Invalid month
        assert validate_period_format("abc123") == False
    
    def test_employee_exists(self):
        """Test employee existence check"""
        from main import employee_exists
        
        assert employee_exists("EMP001") == True
        assert employee_exists("EMP002") == True
        assert employee_exists("EMP003") == True
        assert employee_exists("INVALID") == False
    
    def test_employee_active_in_period(self):
        """Test employee activity in period"""
        from main import employee_active_in_period
        
        # Active employee
        assert employee_active_in_period("EMP001", "202311") == True
        assert employee_active_in_period("EMP002", "202311") == True
        
        # Employee who ended before the period
        assert employee_active_in_period("EMP003", "202311") == False
        assert employee_active_in_period("EMP003", "202310") == True
        
        # Employee who started after the period
        assert employee_active_in_period("EMP003", "202301") == False
        assert employee_active_in_period("EMP003", "202303") == True