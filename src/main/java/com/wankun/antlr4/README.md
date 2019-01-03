# Antlr Demo

* 下载并安装Antlr4
* Idea 安装Antlr 插件
* 引入依赖包和插件
* 编写语法文件(*.g4)
* 测试语法文件，右键选择 Test Rule r
* gen java文件,
  * 通过antlr4的maven插件生成代码
  * 在语法文件上右键`Configure ANTLR`中配置好输出文件夹并勾选下面选项，然后 `Generate ANTLR recognizer`
* Java 处理流程
  * 构造词法分析器lexer，进行记号
  * 产生记号流 tokens
  * 构造语法分析器 parser
 
