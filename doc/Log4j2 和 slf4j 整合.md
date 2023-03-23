# Dependency

```xml
<dependencies>
  <!-- 1. 使用slf4j API 且支持 JUL 和 JCL API 转换成 slf4j API -->
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>${slf4j.version}</version>
  </dependency>
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jul-to-slf4j</artifactId>
    <version>${slf4j.version}</version>
  </dependency>
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>${slf4j.version}</version>
  </dependency>

  <!-- 2. 使用log4j 实现 -->
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>${log4j2.version}</version>
  </dependency>
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>${log4j2.version}</version>
  </dependency>

  <!-- 3. 桥接：告诉Slf4j使用Log4j2 -->
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>${log4j2.version}</version>
  </dependency>

  <!-- 4. 支持log4j 1 -->
  <dependency>
    <!-- API bridge between log4j 1 and 2 -->
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-1.2-api</artifactId>
    <version>${log4j2.version}</version>
    <scope>${hadoop.deps.scope}</scope>
  </dependency>
</dependencies>
```

Document : https://www.cnblogs.com/dongguacai/p/6907320.html

一、日志组件概念
两种日志接口：commons-logging、slf4j

四种日志实现：logging，log4j1、log4j2、logback

1、日志接口的作用
　　目前一统江湖的就是apache的commons-logging和slf4j，他两的作用就是提供统一的接口，而具体的日志实现交给底层绑定的具体的日志实现框架。这样一来，我们的业务系统中可以灵活的更换不同的日志实现，并且可以不需要去改动代码。

对于开发者而言，每种日志都有不同的写法。如果我们以实际的日志框架来进行编写，代码就限制死了，之后就很难再更换日志系统，很难做到无缝切换。

2、jar包的对应：
logging：jdk自带的日志实现，简称jul（java-util-logging）

log4j1：log4j

log4j2：log4j-api(定义的api)、log4j-core(api的实现)

logback：logback-core（logback的核心包）、logback-classic（logback实现了slf4j的API）

commons-logging：简称jcl

         commons-logging（commons-logging的原生全部内容）

         log4j-jcl（commons-logging到log4j2的桥梁）

         jcl-over-slf4j（commons-logging到slf4j桥梁）

slf4j：这个框架比较复杂，在整个日志组件中起到了一个中转站的作用

a、使用slf4j的api编程，底层用其他具体的实现：

slf4j-jdk14：slf4j到jdk-logging的桥梁

slf4j-log4j12：slf4j到log4j1的桥梁

log4j-slf4j-impl：slf4j到log4j2的桥梁

logback-classic：slf4j到logback的桥梁

slf4j-jcl：slf4j到commons-logging的桥梁

b、如使用log4j的api编程，但最终输出通过logback来实现，这样的话就必须先将log4j转交给slf4j，再通过slf4j转交给logback

jul-to-slf4j：jdk-logging到slf4j的桥梁

log4j-over-slf4j：log4j1到slf4j的桥梁

jcl-over-slf4j：commons-logging到slf4j的桥梁