# Automated Teller App
Solution for the ATM challenge.
With command line GUI for ATM Menu.

## Installation
```bash
./gradlew clean build shadowJar copyJar
```
## Database 
Default H2 in memory database config is provided in `resources/database.properties`.
```
driver=org.h2.Driver
url=jdbc:h2:mem:atm;DB_CLOSE_DELAY=-1
customer=sa
password=
hibernate.dialect=org.hibernate.dialect.H2Dialect
hibernate.show_sql=false
```
## Testing
```bash
java -jar out/atm-all.jar
```
`resources/import.sql `contains the default data that is loaded to the db at startup. Please use the data from the import for test as needed.
The default customers loaded in the file are below for testing purpose.

| Customer Number | Pin           |
| --------------- |:-------------:|
| 1234567         | 3566          | 
| 2345678         | 3577          |  
| 3456789         | 3588          | 