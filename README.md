## 写在前面


* 本地Git推送失败，报错fatal: HttpRequestException encountered
原因是Github 禁用了TLS v1.0 and v1.1，必须更新Windows的git凭证管理器
从https://github.com/Microsoft/Git-Credential-Manager-for-Windows/releases/tag/v1.14.0
下载安装GCMW-1.14.0.exe即可
* 使用自定义groovy脚本自动生成数据库表对应实体。
首先打开database工具栏，右键表选择scripts extensions然后选择进入脚本文件夹。
从resources文件加下copy出groovy脚本放到刚才的脚本文件夹，并放开注释。
再次右键表选择scripts extensions，选择对应的groovy脚本，然后选择路径即可生成。
也可自己修改调整脚本后再执行。