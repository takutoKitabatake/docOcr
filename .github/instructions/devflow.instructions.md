---
applyTo: "**"
description: "Java + Spring Boot 開発向け Copilot Agent 用の共通ルール"
---

# 開発フロー共通ルール (Java + Spring Boot)

## 言語 / フレームワーク
- 言語: Java 21+
- フレームワーク: Spring Boot 3.x
- ビルド: Maven
- テスト: JUnit5 (Mockito など既存のテストライブラリを使用)

## 実装ルール
1. コードスタイルはプロジェクトの `checkstyle` / `formatter` 設定に従う
2. 依存注入は **コンストラクタインジェクション** を原則とする
3. 新規クラスには Javadoc または JavaDoc形式コメントを記載
4. 例外処理は `@ControllerAdvice` を活用し、業務例外とシステム例外を区別
5. セキュリティ・パフォーマンスに配慮（SQLインジェクション対策、N+1クエリ回避など）

## テストルール
- **JUnit5** で必ずテストを追加
- 既存テストを壊さないこと
- CI/CD 環境で成功することを確認

## PR作成ルール
- Draft PR を作成
- PR本文には必ず以下を記載:
  - `Close #<ISSUE番号>`
  - 変更内容の概要
  - 追加したテスト
  - 確認してほしい点・懸念点

## 自動チェック
- PR作成後は `./mvnw clean verify` または `./gradlew build` を実行
- Lint/Checkstyleエラーやテスト失敗があれば修正して再実行

## 質問・確認
- 不明点や仕様の曖昧さがあれば、必ずコメントで確認してから進める
