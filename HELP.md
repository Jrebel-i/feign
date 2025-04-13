# 生成压缩后的数据
echo '[{"id":"1","message":"huchao"},{"id":"2","message":"zhangsan"}]' | gzip > data.json.gz

# 发送压缩请求
curl -X POST --location "http://localhost:8080/message/process" \
-H "Accept: application/json; charset=UTF-8" \
-H "Content-Encoding: gzip" \
-H "Content-Type: application/json" \
--data-binary @data.json.gz
