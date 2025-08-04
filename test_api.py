#!/usr/bin/env python3
"""
Manual test script for Employee Information API
This script tests all the API endpoints to verify they work correctly.
"""

import requests
import json
import sys
import time
import subprocess
import os

def start_server():
    """Start the FastAPI server"""
    return subprocess.Popen([sys.executable, "main.py"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)

def test_endpoint(url, expected_status=200, description=""):
    """Test a single endpoint and return the result"""
    try:
        response = requests.get(url, timeout=5)
        success = response.status_code == expected_status
        print(f"{'✓' if success else '✗'} {description}")
        print(f"  URL: {url}")
        print(f"  Status: {response.status_code} (expected {expected_status})")
        if response.status_code in [200, 400, 404]:
            try:
                data = response.json()
                print(f"  Response: {json.dumps(data, ensure_ascii=False, indent=2)}")
            except:
                print(f"  Response: {response.text}")
        print()
        return success
    except Exception as e:
        print(f"✗ {description}")
        print(f"  Error: {e}")
        print()
        return False

def main():
    """Run all tests"""
    base_url = "http://localhost:8000"
    
    # Start server
    print("Starting FastAPI server...")
    server_process = start_server()
    time.sleep(3)  # Wait for server to start
    
    try:
        test_results = []
        
        # Test cases
        test_cases = [
            # Root endpoint
            (f"{base_url}/", 200, "Root endpoint"),
            
            # Employee info tests
            (f"{base_url}/employee/EMP001/202301", 200, "Get employee info - success case"),
            (f"{base_url}/employee/INVALID/202301", 404, "Get employee info - invalid ID"),
            (f"{base_url}/employee/EMP001/202312", 404, "Get employee info - no data for period"),
            (f"{base_url}/employee/EMP001/2023-01", 400, "Get employee info - invalid date format"),
            (f"{base_url}/employee/EMP001/202313", 400, "Get employee info - invalid month"),
            
            # Employee downloads tests
            (f"{base_url}/employee/EMP001/202301/downloads", 200, "Get employee downloads - success case"),
            (f"{base_url}/employee/INVALID/202301/downloads", 404, "Get employee downloads - invalid ID"),
            (f"{base_url}/employee/EMP001/202312/downloads", 404, "Get employee downloads - no data for period"),
            (f"{base_url}/employee/EMP001/2023-01/downloads", 400, "Get employee downloads - invalid date format"),
            
            # Edge cases
            (f"{base_url}/employee/EMP003/202301", 200, "Get employee info - employee exists in period"),
            (f"{base_url}/employee/EMP003/202304", 200, "Get employee info - employee not active in period"),
        ]
        
        print("Running API tests...\n")
        
        for url, expected_status, description in test_cases:
            result = test_endpoint(url, expected_status, description)
            test_results.append(result)
        
        # Summary
        total_tests = len(test_results)
        passed_tests = sum(test_results)
        failed_tests = total_tests - passed_tests
        
        print("="*60)
        print("TEST SUMMARY")
        print("="*60)
        print(f"Total tests: {total_tests}")
        print(f"Passed: {passed_tests}")
        print(f"Failed: {failed_tests}")
        print(f"Success rate: {passed_tests/total_tests*100:.1f}%")
        
        if failed_tests == 0:
            print("\n🎉 All tests passed!")
            return 0
        else:
            print(f"\n❌ {failed_tests} test(s) failed")
            return 1
            
    finally:
        # Clean up
        print("\nStopping server...")
        server_process.terminate()
        server_process.wait()

if __name__ == "__main__":
    exit_code = main()
    sys.exit(exit_code)