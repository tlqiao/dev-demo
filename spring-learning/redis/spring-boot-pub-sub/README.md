## Simple Redis Pub sub using spring boot and reactor

##### Prerequisite
    redis local installed 

##### How to use?
- clone the program
- run `mvn springboot:run`
- using postman, etc execute:
`curl -X POST \
   http://localhost:8080/api/news/_publish \
   -H 'Content-Type: application/json' \
   -d '{
 	"text" : "{your message here}"
 }'`
