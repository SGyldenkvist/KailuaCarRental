CREATE DATABASE IF NOT EXISTS kailua_car_rental;
USE kailua_car_rental;

CREATE TABLE IF NOT EXISTS car_group(
car_group_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,

name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS car(
car_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,

registration_no VARCHAR(50) NOT NULL UNIQUE,
brand VARCHAR(100) NOT NULL,
model VARCHAR(100) NOT NULL,
fuel_type VARCHAR(50) NOT NULL,
first_reg_year SMALLINT(4) NOT NULL,
first_reg_month TINYINT(2) NOT NULL,
odometer_km INT(10) NOT NULL DEFAULT 0,

car_group_id INT NOT NULL,

FOREIGN KEY (car_group_id) REFERENCES car_group (car_group_id)
);

CREATE TABLE IF NOT EXISTS renter(
renter_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,

name VARCHAR(255) NOT NULL,
address VARCHAR(255) NOT NULL,
zip VARCHAR(10) NOT NULL,
city VARCHAR(255) NOT NULL,
mobile_phone VARCHAR(20),
phone VARCHAR(20),
email VARCHAR(250) NOT NULL UNIQUE,
driver_licence_no VARCHAR(100) NOT NULL UNIQUE,
driver_since_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS rental_contract(
rental_contract_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,

car_id INT NOT NULL,
renter_id INT NOT NULL,

from_date TIMESTAMP NOT NULL,
to_date TIMESTAMP NOT NULL,

max_km INT NOT NULL DEFAULT 0,
odometer_start_km INT NOT NULL,

FOREIGN KEY (car_id) REFERENCES car (car_id),
FOREIGN KEY (renter_id) REFERENCES renter (renter_id)
);

INSERT INTO car_group (name) VALUES
('Luxury'),
('Family'),
('Sport');

SELECT* FROM car_group;


INSERT INTO renter(name, address, zip, city, mobile_phone, phone, email, driver_licence_no, driver_since_date)
VALUES
('Anna Jensen', 'Søndergade 12', 2100, 'København', '+45 11111111', NULL,'anna@example.com', 'DK-1234567', '2016-05-10'),
('Mads Sørensen','Parkvej 3','8000','Aarhus',NULL,'+45 22222222','mads@example.com','DK-7654321','2012-09-01'),
('Liva Holm','Skovkanten 8','5000','Odense','+45 33333333',NULL,'liva@example.com','DK-5556667','2018-01-20');

SELECT* FROM renter;


INSERT INTO car (registration_no, brand, model, fuel_type, first_reg_year, first_reg_month, odometer_km, car_group_id)
VALUES
('AB12345', 'Toyota', 'Avensis', 'petrol', 2018,5,82000,2),
('CD23456', 'BMW',    '530i',    'petrol', 2020, 3,  41000, 1),
('EF34567', 'VW',     'Sharan',  'diesel', 2017, 9, 120000, 3);

SELECT* FROM car;


INSERT INTO rental_contract (car_id, renter_id, from_date, to_date, max_km, odometer_start_km)
VALUES
(1,1, '2025-09-17 09:00:00', '2025-12-17 10:00:00', 1000,82000),
(2,2, '2025-09-29 12:00:00', '2025-10-29 12:00:00', 600, 41000),
(3,3,'2025-10-01 08:00:00', '2026-02-01 12:00:00', 1500, 120000);

SELECT*FROM rental_contract;
