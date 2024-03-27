FROM amazoncorretto:17.0.7-alpine

# Set environment variables
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=UTC

# Set the working directory in the container
#RUN rm -r app/
# remove cache
RUN rm -rf /var/cache/apk/*
RUN rm -rf /app/*

WORKDIR /app


# copy pom.xml and src folders to the container using workdir
COPY pom.xml /app/pom.xml
COPY src /app/src



# Install Maven
RUN apk --no-cache add maven


RUN mvn clean install

# Expose the port your application runs on
#EXPOSE 8080

# Run the application when the container starts

CMD ["java", "-jar", "target/reservas-0.0.1-SNAPSHOT.jar"]
