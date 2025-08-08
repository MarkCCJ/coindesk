Spring Boot Coindesk 與幣別 CRUD 專案
簡介
本專案提供以下功能：
呼叫 Coindesk API 並解析資料
針對幣別與中文名稱提供 CRUD API
進行資料轉換並提供轉換後的 API

測試項目說明
1. 針對資料轉換相關邏輯作單元測試
測試 Service 層資料從 Coindesk API 取得、解析與轉換的邏輯是否正確。
指令 : MVN test
2. 測試呼叫幣別對應表資料 CRUD API，並顯示內容
測試以下 API 是否正常運作，並能查詢、新增、修改、刪除幣別資料。
3. 測試呼叫 Coindesk API，並顯示內容
直接呼叫 Coindesk API，取得原始匯率資料。
4. 測試呼叫資料轉換的 API，並顯示內容
呼叫自訂資料轉換 API，取得包含更新時間、幣別中文名稱與匯率的結構化資料。
=========================================================================
1.API 測試方式（Postman / curl）
1.查全部（GET）
Method：GET
URL：http://localhost:8080/api/currencies
Headers：無需設定
curl http://localhost:8080/api/currencies

2.新增（POST）
Method：POST
URL：http://localhost:8080/api/currencies
Headers：
Content-Type: application/json
Body (raw JSON)：
{
  "code": "JPY",
  "nameZh": "日圓"
}
curl -X POST http://localhost:8080/api/currencies \
-H "Content-Type: application/json" \
-d '{"code":"JPY","nameZh":"日圓"}'
3.修改（PUT）
Method：PUT
URL：http://localhost:8080/api/currencies/JPY
Headers：
Content-Type: application/json
Body (raw JSON)：
{
  "code": "JPY",
  "nameZh": "日本圓(已更新)"
}
curl -X PUT http://localhost:8080/api/currencies/JPY \
-H "Content-Type: application/json" \
-d '{"code":"JPY","nameZh":"日本圓(已更新)"}'
4.刪除（DELETE）
Method：DELETE
URL：http://localhost:8080/api/currencies/JPY
Headers：無需設定
curl -X DELETE http://localhost:8080/api/currencies/JPY
5.呼叫 Coindesk 資料轉換 API（GET）
Method：GET
URL：http://localhost:8080/api/coindesk/fetch-transformed
Headers：無需設定
curl http://localhost:8080/api/coindesk/fetch-transformed

結語
以上測試均可成功呼叫並取得預期結果，確保系統功能正常。