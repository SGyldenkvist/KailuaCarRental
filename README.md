# Afleveringens indhold: 

## Exercise 1: 


* Se ER diagrammet: "filnavn af billede"

Forklaring af diagrammet:

4 identificerede entities/tabeller; 
Car, Car_Group, Rental_Contract og Renter

Car: Har en primary key, der hedder car_id (så bilen kan identificeres og refereres til) og en UNIQUE registration_no (fordi en nummerplade er unik) og så har Car en foreign key, der hedder car_group_id (NOT NULL), fordi en Car skal tilhøre en Car_Group.

Renter: Har en PK der hedder renter_id, UNIQUE driver_licence_no

RentalContract: Hver kontrakt får sit eget id nummer, så primary key'en hedder contract_id, den har en FK car_id(NOT NULL), fordi en RentalContract skal have en car_id og en anden FK renter_id (NOT NULL), fordi en RentalContract skal have en renter_id.


Forholdet mellem entiteterne er alle non-identifying, dvs. de arver ikke en primary key, derfor er linjerne mellem dem stiplet.

Entiternes relationer: 
* En Car kan have præcis én Car_Group 
* En Car_Group kan have 0 til mange Car 
* En Car kan have 0 til mange Rental_Contract 
* En Rental_Contract kan have præcis én Car og præcis én Renter 
* En Renter kan have 0 til mange Rental_Contract

* Man kunne godt have sagt at en Renter kunne have haft flere Car registreret i 1 Rental_Contract og så ville forholdet have været mange til mange, i stedet for 1 til mange. Man vil kunne ændre det senere, hvis firmaet ønsker det. Men jeg har valgt at holde det så simpelt som muligt. 

* Min løsning lever op til 3. normalform fordi, hvert felt har sin egen værdi, der er ingen duplikater kun referencer (Foreign keys). Kundedata ligger kun i Renter, som RentalContract refererer til. Og CarGroup indeholder gruppenavn, som Car peger på/refererer til.



## Exercise 2:

SQL script ligger i "filnavn.sql"



## Exercise 3: 

Screenshots af databasen, oprettet i DataGrip ligger i "..."



## Exercise 4: 

Java programmet ligger i: "..." 


