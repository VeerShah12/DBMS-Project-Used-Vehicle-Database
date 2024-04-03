CREATE DATABASE IF NOT EXISTS GearGarage;

USE GearGarage;

CREATE TABLE IF NOT EXISTS ManufacturerDetail (
    manufacturer_id VARCHAR(20) PRIMARY KEY,
    manufacturer_name VARCHAR(50) NOT NULL,
    manufacturer_country VARCHAR(50) NOT NULL,
    service_centers INT NOT NULL
);

CREATE TABLE IF NOT EXISTS ServiceCenters (
    service_center_id VARCHAR(20) PRIMARY KEY,
    center_name VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    manufacturer_id VARCHAR(20),
    FOREIGN KEY (manufacturer_id) REFERENCES ManufacturerDetail(manufacturer_id)
);

CREATE TABLE IF NOT EXISTS VehicleCategory (
    type_id VARCHAR(20) PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL,
    vehicle_quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Vehicles (
    vehicle_id VARCHAR(20) PRIMARY KEY,
    manufacturer_id VARCHAR(20),
    type_id VARCHAR(20),
    vehicle_name VARCHAR(50) NOT NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES ManufacturerDetail(manufacturer_id),
    FOREIGN KEY (type_id) REFERENCES VehicleCategory(type_id)
);

CREATE TABLE IF NOT EXISTS VehicleDetails (
    vehicle_id VARCHAR(20) PRIMARY KEY,
    mileage DECIMAL(5,2) NOT NULL,
    make_year YEAR NOT NULL,
    body_type VARCHAR(50),
    owners INT NOT NULL,
    price BIGINT NOT NULL,
    running BIGINT NOT NULL,   
    colour VARCHAR(20),
    engine_capacity INT,
    transmission VARCHAR(20),
    fuel_type VARCHAR(20),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicles(vehicle_id)
);
CREATE TABLE IF NOT EXISTS Store (
    store_id VARCHAR(20) PRIMARY KEY,
    store_location VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS SalesPerson (
    sp_id VARCHAR(20) PRIMARY KEY,
    store_id VARCHAR(20),
    sp_fname VARCHAR(50) NOT NULL,
    sp_lname VARCHAR(50),
    units_sold INT,
    hire_date DATE NOT NULL,
    sales_figure DECIMAL (10,2),
    FOREIGN KEY (store_id) REFERENCES Store(store_id)
);
CREATE TABLE IF NOT EXISTS customerDetails (
    cust_id VARCHAR(20) PRIMARY KEY,
    cust_name VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL,
    DOB DATE NOT NULL
);
CREATE TABLE IF NOT EXISTS Sales(
    sale_id VARCHAR(20) PRIMARY KEY,
    cust_id VARCHAR(20) NOT NULL,
    store_id VARCHAR(20) NOT NULL,
    sp_id VARCHAR(20) NOT NULL,
    sale_date DATE NOT NULL,
    sales_price DECIMAL (10,2),
    vehicle_id VARCHAR(20) NOT NULL,
    FOREIGN KEY (store_id) REFERENCES Store(store_id),
    FOREIGN KEY (sp_id) REFERENCES SalesPerson(sp_id),
    FOREIGN KEY (cust_id) REFERENCES customerDetails(cust_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);
