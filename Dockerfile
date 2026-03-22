# Bước 1: Build dự án bằng Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Bước 2: Chạy ứng dụng bằng JRE của Eclipse Temurin
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy file .jar từ bước build sang
COPY --from=build /app/target/*.jar app.jar
# Mở cổng 10000
EXPOSE 10000
# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=10000"]