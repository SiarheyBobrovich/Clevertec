## Test project for Clevertec
### Technologies:
- #### stack:
  - org.projectlombok:lombok:1.18.26
  - org.projectlombok:lombok:1.18.26
  - org.mapstruct:mapstruct:1.5.3.Final
  - org.mapstruct:mapstruct-processor:1.5.3.Final
  - org.springframework.boot:spring-boot-starter-web:2.7.6
  - org.springframework.boot:spring-boot-starter-validation:2.7.6
  - com.mchange:c3p0:0.9.5.5
  - org.postgresql:postgresql:42.5.1
- #### test:
  - org.junit.jupiter:junit-jupiter-params:5.9.1
  - org.junit.jupiter:junit-jupiter-engine:5.9.1
  - org.assertj:assertj-core:3.23.1
  - org.mockito:mockito-core:4.9.0
  - org.mockito:mockito-junit-jupiter:4.9.0

### Run: 
  - java -jar market-0.1.1-SNAPSHOT.jar

### Configurations
- spring:
  - product:
    - filename: """file path to load(using with database: file)"""
    - database: """optional: {memory, file, jdbc}"""
  - card:
    - filename: """file path to load(using with database: file)"""
    - database: memory #memory, file, jdbc
  - alcohol:
    - database: """optional: {memory, jdbc}"""
  - cache:
    - algorithm: lfu """optional: {lfu, lru}"""
    - size: """cache size"""

## Endpoints:
### Check
  - localhost:8080/check
    - params:
      - itemId
      - card
    - status: 200
    - response
      <pre>{
         "title":"   
                            CASH RECEIPT
                        SUPERMARKET     123
                    12, MILKYWAY GALAXY/ Earth
                 Tel : 123-456-7890\nCASHIER: №1234
                                  DATE:  08/03/2023
                                    TIME:  12:38:06
         ","body":"
               QTY  DESCRIPTION         PRICE      TOTAL
               2    Loren Ipsum         $1.55      $3.10
         ","totalBlock":"
               TAXABLE TOT_                        $2.57
               VAT17%                              $0.53
               TOTAL                               $3.10
         "
      }</pre>
### Alcohol:
  - #### GET
    - All:
      - localhost:8080/alcohol
        - status: 200
        - response: 
          <pre>[
              {
                "id": 3,
                "name": "vino",
                "country": "rus",
                "vol": 40.43,
                "price": 1,
                "quantity": 20
              },
              {
                "id": 4,
                "name": "Whisky",
                "country": "usa",
                "vol": 132.11,
                "price": 1.41,
                "quantity": 10
              }
            ]</pre>
    - By id
      - localhost:8080/alcohol/{id}
        - status 200
        - response:
          <pre>{
            "id": 4,
            "name": "Whisky",
            "country": "usa",
            "vol": 132.11,
            "price": 1.41,
            "quantity": 10
          }</pre>
  - #### POST
    - localhost:8080/alcohol
      - status: 201 
      - request:
        <pre>{
            "name": "Whisky",
            "country": "usa",
            "vol": 132.11,
            "price": 1.41,
            "quantity": 10
        }</pre>
  - #### PUT
    - localhost:8080/alcohol/{id}
      - status: 201 
      - request:
        <pre>{
            "name": "Whisky",
            "country": "usa",
            "vol": 132.11,
            "price": 1.41,
            "quantity": 10
        }</pre>
  - #### DELETE
    - localhost:8080/alcohol/{id}
      -status: 204
## Docker compose up