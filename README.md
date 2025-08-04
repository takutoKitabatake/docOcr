# docOcr - Employee Information API

社員情報取得APIの実装プロジェクトです。

## 概要

このプロジェクトは以下の2つのAPIを提供します：

1. **社員情報取得API**: 社員IDと年月をもとに、その社員のその当時の情報を取得
2. **社員帳票ダウンロード履歴API**: 社員IDと年月をもとに、その月にそのユーザがダウンロードした帳票一覧を取得

## API仕様

### 1. 社員情報取得API

```
GET /api/employees/{employeeId}/info?yearMonth=YYYYMM
```

**パラメータ:**
- `employeeId` (Path): 社員ID
- `yearMonth` (Query): 年月（YYYYMM形式、例: 202412）

**レスポンス例:**
```json
{
  "employeeId": 1,
  "employeeCode": "EMP001",
  "employeeName": "山田太郎",
  "yearMonth": "2024-12",
  "company": {
    "companyId": 1,
    "companyCode": "COMP001",
    "companyName": "株式会社サンプル"
  },
  "department": {
    "departmentId": 1,
    "departmentCode": "DEPT001",
    "departmentName": "開発部"
  },
  "position": {
    "positionId": 1,
    "positionCode": "POS001",
    "positionName": "エンジニア"
  }
}
```

### 2. 社員帳票ダウンロード履歴API

```
GET /api/employees/{employeeId}/reports?yearMonth=YYYYMM
```

**パラメータ:**
- `employeeId` (Path): 社員ID
- `yearMonth` (Query): 年月（YYYYMM形式、例: 202412）

**レスポンス例:**
```json
{
  "employeeId": 1,
  "employeeCode": "EMP001",
  "employeeName": "山田太郎",
  "yearMonth": "202412",
  "reports": [
    {
      "reportId": 1,
      "reportCode": "REP001",
      "reportName": "給与明細",
      "downloadedAt": "2024-12-15 10:30:00"
    }
  ]
}
```

## エラー処理

両APIは以下のエラーを返却します：

- **404 Not Found**: 
  - 社員IDが存在しない場合: `"IDが誤っています。"`
  - 指定期間にその社員が存在しない場合: `"その期間にその社員は存在しません。"`
- **400 Bad Request**: 年月の形式が不正な場合
- **500 Internal Server Error**: システムエラー

**エラーレスポンス例:**
```json
{
  "message": "IDが誤っています。",
  "status": 404,
  "timestamp": "2024-12-15 10:30:00"
}
```

## 技術スタック

- **言語**: Java 17
- **フレームワーク**: Spring Boot 3.2.0
- **データベース**: MySQL（開発時はH2インメモリDB）
- **ビルドツール**: Maven
- **テスト**: JUnit 5, Mockito

## 実行方法

### 1. プロジェクトのビルド

```bash
mvn clean compile
```

### 2. テストの実行

```bash
mvn test
```

### 3. アプリケーションの起動

```bash
mvn spring-boot:run
```

アプリケーションは http://localhost:8080/api で起動します。

## データベース設計

本実装では、元のDDLスキーマに加えて以下のテーブルを追加しています：

### employee_report_download テーブル

社員の帳票ダウンロード履歴を管理するテーブルです。

```sql
CREATE TABLE employee_report_download (
    download_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id      BIGINT NOT NULL,
    report_id        BIGINT NOT NULL,
    downloaded_at    TIMESTAMP NOT NULL,
    year_month       CHAR(7) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Foreign Key制約とインデックスは省略
);
```

## 開発ガイドライン

- コンストラクタインジェクションを使用
- 例外処理は`@ControllerAdvice`で統一
- テストは単体テストと統合テストの両方を実装
- 日本語エラーメッセージは要件に従って実装

## 制約事項

- データベースの初期データは別途セットアップが必要
- 本実装は基本的なCRUD操作のみ提供
- 認証・認可機能は含まれていません