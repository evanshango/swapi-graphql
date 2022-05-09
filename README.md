### SWAPI BACKEND
This project is a backend service implemented with Spring boot and graphQL. It does consume the SWAPI with the following 
link [https://swapi.dev/ "Swapi"]

1. Clone this repository and save the project in your preferred location
2. Navigate to your location, open the project directory in terminal/console and run the command `mvn install`
3. Once the project has finished installing all the dependencies, proceed to run the command `mvn spring-boot:run`
4. The application (backend server) now runs on `http://localhost:8080`

#### Docker Option
The project has an attached docker file as part of the project files.
1. Once you have cloned the project and your docker environment is up and running, proceed to run the command 
`docker build -t swapi-graphql .` while at the current directory where the project resides having cloned it
2. After the docker build process has finished, proceed to run the following command `docker run -dp 8080:8080`
assuming that nothing is running on port 8080 on your machine. The flag `-dp` means d- for detach mode and p for port.
The port on the left side of the `:` is the one you want to expose traffic to from your machine.

### Client Installation
1. Follow the instruction attached on the repository with the following link [https://github.com/evanshango/swapi-client Swapi-Client].
to be able to test the functionality of this project