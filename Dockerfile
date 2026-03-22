# Bước 1: Sử dụng hình ảnh Maven để build dự án
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Copy toàn bộ code vào trong container
COPY . .
# Chạy lệnh build để tạo file .jar (bỏ qua chạy test để nhanh hơn)
RUN mvn clean package -DskipTests

# Bước 2: Sử dụng hình ảnh JDK nhẹ để chạy ứng dụng
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy file .jar đã build từ bước 1 sang bước này
COPY --from=build /app/target/*.jar app.jar
# Mở cổng 10000 (cổng mặc định của Render)
EXPOSE 10000
# Lệnh để khởi chạy Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=10000"]