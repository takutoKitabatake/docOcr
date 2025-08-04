# 📦 Javaプロジェクト構成ドキュメント（機能別配置）

本プロジェクトは **従業員管理機能** を中心としたSpring Bootアプリケーション構成で、各機能モジュールが明確にディレクトリで分離されています。主な配置先の概要は以下の通りです。

---

## 🧩 機能別ディレクトリ構成と配置

### 1. `common` パッケージ
| 機能 | 配置 | 説明 |
|------|------|------|
| ヘルスチェック | `common.application.controller.HealthCheckController` | サービスの死活監視用API |
| JSONハンドリング | `common.typehandler.EmployeeInfoJsonTypeHandler` | MyBatisのTypeHandler定義 |

---

### 2. `employee` 機能群（従業員/部署/役職の操作）

#### 📍 配備先: `employee`

| レイヤー | 配置先パス | 内容 |
|---------|------------|------|
| **Controller** | `employee.application.controller` | REST API群（従業員のCRUD、部署・役職取得など） |
| **Resource** | `employee.application.resource` | 入出力データモデル（Request/Response DTO） |
| **Service** | `employee.domain.service` | ビジネスロジック層 |
| **DAO** | `employee.entity.dao` + `resources/jp/co/demo/employee/entity/dao/*.xml` | MyBatisベースのDBアクセス層（Mapper + XML） |
| **定数定義** | `employee.constants.EmployeeConstant` | 機能固有の定数定義 |

---

### 3. `tenant.common`

| レイヤー | 配置先パス | 内容 |
|---------|------------|------|
| AOP | `aop.ComCheckAspect` | 共通Aspect定義 |
| DAO | `entity.dao.ComCheckSiteBaseDao` + `resources/.../ComCheckSiteBaseDao.xml` | テナント共通のDBアクセス |
| 定数定義 | `constants.ComConstant` | 共通定数群 |
| リソース設定 | `resource.ComExternalApiConfigResource` | 外部API設定 |
| ユーティリティ | `util.ComVersatileItemsUtil` | 汎用関数集 |

---

### 4. Mapper（Entity）

#### 📍 配備先: `entity.table`

| テーブルクラス名 | 内容 |
|----------------|------|
| `Company`, `Department`, `Position` | 会社・部署・役職の基本マスタ |
| `Employee`, `OrganizationStructure` | 社員情報・月次体制管理用エンティティ |
| `ReportMaster`, `ReportProcessing` | 帳票マスタ・OCR処理履歴管理用エンティティ |

---

### 6. 環境設定ファイル群

#### 📍 配備先: `src/main/resources`

| ファイル名 | 内容 |
|-----------|------|
| `application.yml` + 環境別（`-dev`, `-stg`, `-pro`） | Spring Boot設定 |
| `log4j2-*.yml` | ログ設定 |

---

### 7. テストコード

#### 📍 配備先: `src/test/java/...`

| テスト種別 | 配置パス | 内容 |
|-----------|----------|------|
| Controller単体 | `employee.application.controller.*Test` | 各Controllerの振る舞いテスト |
| Service層 | `employee.domain.service.EmployeeServiceTest` | ドメインロジックの単体テスト |
| AOP/環境 | `common.AopListTest` / `common.extension.*` | AOPとテスト拡張機能 |
| CloudFront署名 | `tenant.account.application.aop.AccAuthorizeAspectTest` | アクセス制御Aspectテスト |

---

## ⚙️ その他ファイル

| ファイル名 | 内容 |
|-----------|------|
| `pom.xml` | Mavenプロジェクト設定 |
| `buildspec.yml` | CodeBuild向けビルド設定 |
| `.gitignore` | Git無視ファイル定義 |

---

## 🧠 AIエージェント向け補足

- 各機能群はレイヤー（Controller / Resource / Service / Dao / Entity）ごとに整理されており、**役割が明確に分離**されています。
- `employee`, `tenant.account`, `tenant.common` などは機能別モジュール。
- `resources` 以下に **MyBatis XML**, **環境設定**, **秘密鍵**, **メッセージ定義** 等がまとまっています。
- DAOクラスに対応するXMLは `resources/jp/co/demoapp/.../dao/` に存在。
- テストコードも実装と同様のディレクトリ構造を保持し、粒度の高いテスト設計がされています。
- テストコードも実装と同様のディレクトリ構造を保持し、粒度の高いテスト設計がされています。
