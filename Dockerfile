FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the pre-built JAR file
COPY target/blogging-website-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables for database connection
ENV SPRING_DATASOURCE_URL=jdbc:mysql://trolley.proxy.rlwy.net:48756/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=BFfTASKUxTFBVSWKuKRIJWwvccdEOBVJ
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8081