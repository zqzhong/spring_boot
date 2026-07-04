FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 复制本地打包好的 jar 文件
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]