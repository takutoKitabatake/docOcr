# docOcr

## 社員情報取得API (Employee Information API)

このプロジェクトは社員情報と帳票ダウンロード履歴を取得するためのRESTful APIを提供します。

### 機能

1. **社員情報取得API**: 社員ID、年月（YYYYMM）をもとに、その社員のその当時の情報を取得
2. **帳票ダウンロード履歴API**: 社員ID、年月（YYYYMM）をもとに、その月にそのユーザがダウンロードした帳票一覧を取得

### クイックスタート

```bash
# 依存関係をインストール
pip install -r requirements.txt

# サーバーを起動
python main.py

# テストを実行
python test_api.py
```

詳細なAPI仕様については [API_README.md](API_README.md) をご確認ください。