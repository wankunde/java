1. transient 
   1. 不参与序列化
   2. 不能修饰 static，因为static 不参与序列化
2. java序列化和反序列化调用顺序

    writeObject(writeReplace())
    readResolve(readObject()) // 自动调用parse
    
[其它自定义序列化的方法（transient、writeReplace、readResolve、Externalizable）](https://blog.csdn.net/Lirx_Tech/article/details/51303966)