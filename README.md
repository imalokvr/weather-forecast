# Weather Forecast

This project provides weather metrics for a specific city

## Features 
- It exposes "/data/{cityName}" endpoint retrives weather data for next three days
- Weather data metrics :
 	- Average temperature (in Celsius) of the next 3 days from todays date for Daytime (06:00 – 18:00) and Night time (18:00 – 06:00).
 	- Average of pressure for the next 3 days from todays date.
	
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 8 and Meven 

### Installing

- Clone the repositoy
- Go to project root folder and run mvn clean install

### How to run ?

- Go to project root directory and execute : mvn spring-boot:run

### How to test?

- Hit http endpoint using any tool such as postman or curl 
	- URL : http://localhost:8080/data/mumbai 
	- Method : GET

## Built With

* [Spring Boot](http://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [OpenWeatherMap](https://openweathermap.org/forecast5) - Used for waether data

## Enhancements and Improvements
* Implemeting caching for performance enhancement
* Better input validation for city name
* Additional test cases for edge scenario
* Capability to return metrics according to city's timezone

## Authors

* **Alok Ruplag**



