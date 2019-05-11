Using Spring Boot, Apache CXF and RFC-7807 to report HTTP APIs errors
==============

- Build and run

      mvn clean package 
      java -jar target/jax-rs-2.1-problem-0.0.1-SNAPSHOT.jar
      
- Play with API using `Swagger UI`

      http://localhost:21020/api/api-docs

- Play with APU using `curl`

      curl -X POST "http://localhost:21020/api/people" -H  "Accept: */*" -H "Content-Type: application/json" -d '{"email":"john@smith.com", "firstName":"John", "lastName": "Smith"}' -i

      curl -X POST "http://localhost:21020/api/people" -H  "Accept: */*" -H "Content-Type: application/json" -d '{"email":"john.smith", "firstName":"John"}' -i

      curl "http://localhost:21020/api/people/1" -H  "Accept: */*" -i

- Problem Details for HTTP APIs (RFC-7807)

      https://tools.ietf.org/html/rfc7807
