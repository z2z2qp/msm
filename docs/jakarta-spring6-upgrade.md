# Jakarta / Spring 6 升级说明

## 运行环境

- JDK 25
- Kotlin 2.4.10
- Spring Framework 6.2
- Fastjson2 Spring 6 extension 2.0.61
- Jakarta Servlet 6 容器，例如 Tomcat 10.1 或更高兼容版本

项目生成传统 WAR 包，不包含内嵌 Servlet 容器。

## 主要变化

- Java 和 Kotlin 字节码目标统一为 Java 25。
- Servlet、注解和 Validation API 从 `javax.*` 迁移到 `jakarta.*`。
- MyBatis-Spring 升级到兼容 Spring 6 的 3.0 系列。
- Web 描述文件升级到 Jakarta Servlet 6。
- Spring 拦截器改为直接实现 `HandlerInterceptor`。
- 文件上传改用 Servlet 原生的 `StandardServletMultipartResolver`。
- JSON 转换器改用 Fastjson2 的 Spring 6 扩展。

## 构建

```shell
java -version
mvn clean test
mvn clean package
```

构建成功后生成 `target/msm.war`。

## 部署

将 `target/msm.war` 部署到 Jakarta Servlet 6 容器。旧版 Tomcat 9 及其他基于
`javax.servlet` 的容器不能运行此版本。
