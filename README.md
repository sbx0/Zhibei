```text
git clone https://github.com/sbx0/Zhibei.git
```
复制 application-no-secret.yml 并将其改为 application.yml

将其中的数据库连接配置改成你自己的

点击运行，打开 http://localhost:8085/

## 状态码 status
|代码|描述|
|----|----|
|-1|操作失败|
|0|操作成功|
|1|空值错误|
|2|无效数据|
|3|重复操作|
|4|密码错误|
|5|暂未登录|
|6|结果为空|
|7|暂无权限|
|8|发生异常|
|9|超出限制|