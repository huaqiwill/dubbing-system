# 使用 Java 17 作为基础镜像
FROM openjdk:17-jdk-slim

# 复制应用程序的 JAR 文件到容器中
COPY moyin-admin.jar /moyin-admin.jar

# 指定容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "/moyin-admin.jar"]
