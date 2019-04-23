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
id
firstName
lastName
company
email
address1
address2
zip
city
state
state_long
phone
  

