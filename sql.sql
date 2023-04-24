CREATE TABLE ENTITY_STATUS(  
    code VARCHAR(15) NOT NULL PRIMARY KEY,
    description VARCHAR(200)
);

CREATE TABLE COUNTRY(  
    name VARCHAR(50) NOT NULL PRIMARY KEY 
);

CREATE TABLE LOCALITY(  
    id int NOT NULL PRIMARY KEY,
    country VARCHAR(50) NOT NULL,
    postalCode VARCHAR(10) NOT NULL,
    name VARCHAR(60) NOT NULL,
    FOREIGN KEY (country) REFERENCES COUNTRY (name)
);

CREATE TABLE ADDRESS(  
    id INT NOT NULL PRIMARY KEY,
    idLocality INT NOT NULL,
    street VARCHAR(100) NOT NULL,
    number INT NOT NULL,
    FOREIGN KEY (idLocality) REFERENCES LOCALITY (id)
);

CREATE TABLE BUSINESS_ENTITY(  
    serialNumber int NOT NULL PRIMARY KEY,
    lastName VARCHAR(50) NOT NULL,
    firstName VARCHAR(50),
    birthDate DATETIME,
    isCustomer BOOLEAN NOT NULL,
    isSupplier BOOLEAN NOT NULL,
    creditLimit FLOAT,
    codeStatus VARCHAR(25),
    idAddress INT,
    hashPassword VARCHAR(100) NOT NULL,
    salt VARCHAR(20) NOT NULL,
    FOREIGN KEY (codeStatus) REFERENCES ENTITY_STATUS (code),
    FOREIGN KEY (idAddress) REFERENCES ADDRESS (id)
);

CREATE TABLE PERSON_OF_CONTACT(  
    serialNumberCompany INT NOT NULL,
    serialNumberPerson INT NOT NULL,
    PRIMARY key (serialNumberCompany, serialNumberPerson),
    FOREIGN KEY (serialNumberCompany) REFERENCES BUSINESS_ENTITY (serialNumber),
    FOREIGN KEY (serialNumberPerson) REFERENCES BUSINESS_ENTITY (serialNumber)
);

CREATE TABLE BANK_INFORMATIONS(  
    accountUser INT NOT NULL,
    IBAN VARCHAR(20) NOT NULL,
    BICCode VARCHAR(15),
    PRIMARY KEY (accountUser, IBAN),
    FOREIGN KEY (accountUser) REFERENCES BUSINESS_ENTITY (serialNumber) 
);

CREATE TABLE CONTACT_TYPE(
    name VARCHAR(15) NOT NULL PRIMARY KEY
);

CREATE TABLE CONTACT(  
    entity INT NOT NULL,
    typeName VARCHAR(15) NOT NULL,
    data VARCHAR(50) NOT NULL,
    PRIMARY KEY (entity, typeName),
    FOREIGN KEY (entity) REFERENCES BUSINESS_ENTITY (serialNumber),
    FOREIGN KEY (typeName) REFERENCES CONTACT_TYPE (name)
);
