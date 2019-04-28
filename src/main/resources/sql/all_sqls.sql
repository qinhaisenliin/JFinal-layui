在此统一管理所有 sql，优点有：
1：避免在 MainConfig 中一个个添加 sql 模板文件
2：免除在实际的模板文件中书写 namespace，以免让 sql 定义往后缩进一层
3：在此文件中还可以通过 define 指令定义一些通用模板函数，供全局共享
   例如定义通用的 CRUD 模板函数
   
系统核心模块sql
#namespace("core")
	#include("core.sql")
#end

增删改查模板sql
#namespace("crud")
	#include("crud.sql")
#end


