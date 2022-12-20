## Test project for Clevertec
## stack:
- run:
  - org.springframework.boot version 2.7.6
  - spring-boot-starter-web version 2.7.6
  - postgresql version 42.5.1
- tests
  - junit-jupiter version 5.9.1
  - mockito version 4.9.0

run: java -cp market/build/classes/java/main by.bobrovich.market.MarketApplication
### db - inMemory or file or change it to postgres in MarketApplication
- main: MarketApplication
  - You can see all id in resources
    - args: 1-4 8-2 5-5 
    - args: 1-4 8-2 5-5 card-1234
    - args: products-filepath cards-filepath 1-4 2-5
    - args: products-filepath cards-filepath 1-4 2-5 card-1234

## Spring boot application use: java -jar market-0.0.1-SNAPSHOT.jar
Set the application properties to local db url
### db - postgresql
#### Endpoints:
- localhost:8080/check?itemId=1&itemId=1&itemId=2&card=1234

## Docker compose up
### using postgresql with test products
See more in postgresql/ddl/ and postgresql/dml/