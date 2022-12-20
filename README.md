## Test project for Clevertec
- run: java -cp market/build/classes/java/main by.bobrovich.market.MarketApplication
### db - inMemory or file
- main: MarketApplication
  - You can see all id in resources
    - args: 1-4 8-2 5-5 
    - args: 1-4 8-2 5-5 card-1234
    - args: products-filepath cards-filepath 1-4 2-5
    - args: products-filepath cards-filepath 1-4 2-5 card-1234

## Spring boot application use: java -jar market-0.0.1-SNAPSHOT.jar
### db - postgresql
#### Endpoints:
- localhost:8080/check?itemId=1&itemId=1&itemId=2&card=1234

## Docker is not realized