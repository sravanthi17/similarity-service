# similarity-service

# Build a docker file
docker build -t similarity-service .

# Run the docker container
docker run -p 8080:8080 similarity-service

# Configuration
You can configure what columns to be considered for similarity check in application.yml (similarity-service/src/main/resources/application.yml)
Sample configuration below explains that email and id are considered for similarity check
# Example
similarity:

  unique: email,id

# All possible fields to configure
1. id

2. firstName

3. lastName

4. company

5. email

6. address1

7. address2

8. zip

9. city

10. state

11. state_long

12. phone
  

