Avro接口定义及Class生成

# 环境搭建

* 在项目中引入依赖包
```xml
<dependency>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro</artifactId>
    <version>1.9.2</version>
</dependency>
```

```xml
<plugin>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>1.9.2</version>
    <executions>
      <execution>
        <phase>generate-sources</phase>
        <goals>
          <goal>schema</goal>
          <goal>protocol</goal>
          <goal>idl-protocol</goal>
        </goals>
        <configuration>
          <!-- 源目录，用于存放 avro的schema文件及protocol文件 ,如果没加如下配置，那么默认从/src/main/avro下面找avsc文件，生成的java文件放到target/generated-sources/avro下面-->
          <sourceDirectory>${project.basedir}/src/main/avro/</sourceDirectory>
          <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
        </configuration>
      </execution>
    </executions>
</plugin>
```

我这里把生成的java文件放到 `src/gen/avro` 目录下，再使用 build-helper-maven-plugin 插件将这个目录作为一个source文件夹来处理，这样就可以做到代码隔离了。

* 编写avdl协议，这样在生成的代码中就包含了我们需要的类
```avroidl
@namespace("com.wankun.avro")
protocol PageViewProtocol {

  record PageViewRecord {
    long viewtime = 0;
    string userid = "";
    string pageid = "";
  }

}
```
