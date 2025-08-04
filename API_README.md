# Employee Information API

社員情報取得API - Employee Information Retrieval API

## 概要 (Overview)

このAPIは社員の情報と帳票ダウンロード履歴を取得するためのRESTful APIです。

This API provides endpoints to retrieve employee information and report download history.

## 機能 (Features)

1. **社員情報取得**: 社員ID、年月（YYYYMM）をもとに、その社員のその当時の情報を取得
2. **帳票ダウンロード履歴取得**: 社員ID、年月（YYYYMM）をもとに、その月にそのユーザがダウンロードした帳票一覧を取得

## API エンドポイント (API Endpoints)

### 1. 社員情報取得 (Get Employee Information)

```
GET /employee/{employee_id}/{year_month}
```

**パラメータ (Parameters):**
- `employee_id`: 社員ID (例: EMP001)
- `year_month`: 年月 YYYYMM形式 (例: 202301)

**レスポンス例 (Response Example):**
```json
{
  "employee_id": "EMP001",
  "year_month": "202301", 
  "employee_info": {
    "employee_id": "EMP001",
    "name": "田中太郎",
    "department": "営業部",
    "position": "主任",
    "start_date": "2020-01-01",
    "end_date": null
  },
  "is_active": true
}
```

### 2. 帳票ダウンロード履歴取得 (Get Downloaded Reports)

```
GET /employee/{employee_id}/{year_month}/downloads
```

**パラメータ (Parameters):**
- `employee_id`: 社員ID (例: EMP001)
- `year_month`: 年月 YYYYMM形式 (例: 202301)

**レスポンス例 (Response Example):**
```json
{
  "employee_id": "EMP001",
  "year_month": "202301",
  "downloaded_reports": [
    {
      "report_id": "RPT001",
      "report_name": "月次売上報告書",
      "download_date": "2023-01-15",
      "file_type": "PDF",
      "file_size": 1024000
    }
  ],
  "total_count": 1
}
```

## エラーレスポンス (Error Responses)

### 404 - 社員IDが存在しない場合
```json
{
  "detail": "IDが誤っています。"
}
```

### 404 - その期間にデータが存在しない場合
```json
{
  "detail": "その期間にその社員は存在しません"
}
```

### 400 - 年月の形式が正しくない場合
```json
{
  "detail": "年月の形式が正しくありません（YYYYMM形式で入力してください）"
}
```

## セットアップ (Setup)

### 1. 依存関係のインストール (Install Dependencies)
```bash
pip install -r requirements.txt
```

### 2. サーバー起動 (Start Server)
```bash
python main.py
```

サーバーは http://localhost:8000 で起動します。

### 3. API ドキュメント (API Documentation)
ブラウザで http://localhost:8000/docs にアクセスすると、自動生成されたAPI ドキュメントが確認できます。

## テスト (Testing)

### 手動テスト (Manual Testing)
```bash
python test_api.py
```

### cURL例 (cURL Examples)
```bash
# 社員情報取得
curl http://localhost:8000/employee/EMP001/202301

# 帳票ダウンロード履歴取得
curl http://localhost:8000/employee/EMP001/202301/downloads
```

## サンプルデータ (Sample Data)

テスト用のサンプルデータが含まれています：

- **社員**: EMP001 (田中太郎), EMP002 (佐藤花子), EMP003 (山田次郎)
- **期間**: 202301, 202302, 202304
- **帳票**: 月次売上報告書、顧客一覧表、システム稼働状況報告書

## 技術仕様 (Technical Specifications)

- **フレームワーク**: FastAPI
- **Python バージョン**: 3.12+
- **レスポンス形式**: JSON
- **文字エンコーディング**: UTF-8