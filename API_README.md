# Employee Information API

社員情報取得関連APIの実装

## 概要

このAPIは社員情報と社員がダウンロードした帳票情報を取得するためのRESTful APIです。

## 実装されたAPI

### 1. 社員情報取得API

**エンドポイント**: `GET /employee/{employee_id}/info/{period}`

**説明**: 社員ID、年月（YYYYMM）をもとに、その社員のその当時の情報を取得

**パラメータ**:
- `employee_id`: 社員ID（文字列）
- `period`: 年月（YYYYMM形式、例：202311）

**レスポンス例**:
```json
{
    "employee_id": "EMP001",
    "name": "田中太郎",
    "department": "営業部",
    "position": "主任",
    "period": "202311"
}
```

**エラーレスポンス**:
- 社員IDが存在しない場合: `{"detail": "「ID」が誤っています。"}`
- 社員IDと期間でデータが存在しない場合: `{"detail": "その期間にその社員は存在しません"}`

### 2. 社員ダウンロード帳票一覧取得API

**エンドポイント**: `GET /employee/{employee_id}/documents/{period}`

**説明**: 社員ID、年月（YYYYMM）をもとに、その月にそのユーザがダウンロードした帳票一覧を取得

**パラメータ**:
- `employee_id`: 社員ID（文字列）
- `period`: 年月（YYYYMM形式、例：202311）

**レスポンス例**:
```json
{
    "employee_id": "EMP001",
    "period": "202311",
    "documents": [
        {
            "document_id": "DOC001",
            "document_name": "給与明細_202311.pdf",
            "download_date": "2023-11-15",
            "employee_id": "EMP001",
            "period": "202311"
        }
    ]
}
```

**エラーレスポンス**:
- 社員IDが存在しない場合: `{"detail": "「ID」が誤っています。"}`
- 社員IDと期間でデータが存在しない場合: `{"detail": "その期間にその社員は存在しません"}`

## セットアップと実行

### 依存関係のインストール

```bash
pip install -r requirements.txt
```

### サーバーの起動

```bash
python main.py
```

サーバーは `http://localhost:8000` で起動します。

### API ドキュメント

ブラウザで `http://localhost:8000/docs` にアクセスするとSwagger UIが表示されます。

### テストの実行

```bash
pytest test_main.py -v
```

## サンプルデータ

現在の実装では以下のサンプルデータを使用しています：

### 社員データ
- EMP001: 田中太郎（営業部・主任）- 202301〜現在
- EMP002: 佐藤花子（人事部・課長）- 202201〜現在  
- EMP003: 鈴木次郎（技術部・エンジニア）- 202303〜202310

### 帳票データ
- EMP001: 給与明細_202311.pdf（2023-11-15ダウンロード）
- EMP001: 年末調整書類_202312.pdf（2023-12-01ダウンロード）
- EMP002: 給与明細_202311.pdf（2023-11-16ダウンロード）

## 使用例

```bash
# 社員情報取得
curl "http://localhost:8000/employee/EMP001/info/202311"

# 社員ダウンロード帳票一覧取得
curl "http://localhost:8000/employee/EMP001/documents/202311"

# エラーケース: 存在しない社員ID
curl "http://localhost:8000/employee/INVALID/info/202311"

# エラーケース: 該当期間に存在しない社員
curl "http://localhost:8000/employee/EMP003/info/202311"
```

## 技術仕様

- **フレームワーク**: FastAPI 0.104.1
- **Pythonバージョン**: 3.12+
- **バリデーション**: Pydantic 2.5.0
- **テストフレームワーク**: pytest 7.4.3

## 今後の拡張予定

- データベース連携（現在はメモリ上のサンプルデータ）
- 認証・認可機能の追加
- ログ機能の追加
- 期間範囲検索の対応