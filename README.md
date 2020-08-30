### Mastercard CityRoutes REST API Version 1.0
* This is rest api to find if two cities are connected
* Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.
* Input data is provided in a file which has city pairs separated by comma
* if two cities are provided as pair in the file it means these 2 cities are connected
* City pair(one pair per line, comma separated) in the file indicates the road between 2 cities is bidirectional
	
### Technical Stack
* Java 1.8
* Spring Boot 2.3.3
* Jococo
* Swagger
* Maven	

### Input Test Data City Pairs
Boston, New York  
Philadelphia, Newark  
Newark, Boston  
Trenton, Albany  
	
* **From above example data**
  - Boston is directly connected to New York which is considered unidirectional from Boston to New York
  - Boston is in directly connected to Philadelphia (Boston -> Newark -> Philadelphia)
  - In Boston, New York city pair Boston is origin city and New York is the destination city
  - inputCityPairs Map created by code -> 
    - Boston(key), New York(value)
    - Philadelphia(key), Newark(value)
    - Newark(key), Boston(value)
    - Trenton(key), Albany(value)
  - inputCityRoads Map created by code ->
    - Boston=[New York, Newark]
    - New York=[Boston]
    - Newark=[Boston, Philadelphia]
    - Philadelphia=[Newark]
    - Trenton=[Albany]
    - Albany=[Trenton]
	
### Implementation
* CityRoutesController is the controller class which processes input requests
* With @ControllerAdvice annotation on CityRoutesExceptionHandler class allows this class to handle exceptions
* CityRoutesServiceImpl implements the logic to find if 2 cities are connected
  - The  method buildCityPairsAndRoads has a PostConstruct annotation which allows this method to be executed only once to read data from the input data file.
    * Step 1: creates a cityPairs map(refer above map in data section) with key being first city(origin) in the city pair and value being second city(destination) in the city pair
    * Step 2: loops thru the city pairs map and creates another map cityRoads(refer above map in data section) with value being a linked hash set.
  - The method isCitiesConnectedInDirectly takes origin and destination cities as input and while searching thru inputCityRoads map creates a linkedHashSet and a Queue. 
    Until the queue is empty the method takes each city from the queue and checks if the cities are connected directly by calling method isCitiesConnectedDirectly. If the           cities are not connected directly then the method gets linked hash set from the map by using the origin city as key and then iterates linked hash set to see if there is a       connection between origin and destination.

### Test Coverage
* Jococo - with maven goal covered ratio of .99 which is 99%

### Rest Api Url and other Urls
* http://localhost:8080/citiesConnected?origin=Boston&destination=Albany
* http://localhost:8080/v2/api-docs
* http://localhost:8080/swagger-ui/index.html
	
### Contributors
* Raja Goli
