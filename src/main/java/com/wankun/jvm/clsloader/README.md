# [深入分析Java ClassLoader原理][1]
  
## Classloader 分类

* BootStrap ClassLoader
* Extension ClassLoader
* App ClassLoader

## ClassLoader加载类的原理

类加载和类查找**使用双亲委托模型**
![classloader][2]

优点： 
    
    * 避免重复加载
    * Class的加载更安全，例如String 类的记载，由于classloader的加载顺序固定，我们就不能加载到用户自定义的String class对象
    
class 标识符: class name,class loader

## user-defined class loader


[1]: http://www.importnew.com/15362.html 
[2]: /doc/pic/clsloader1.png "class loader"