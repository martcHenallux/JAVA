CREATE TABLE ENTITY_STATUS(  
    code VARCHAR(15) NOT NULL PRIMARY KEY,
    description VARCHAR(200)
);

CREATE TABLE COUNTRY(  
    name VARCHAR(50) NOT NULL PRIMARY KEY 
);

CREATE TABLE LOCALITY(  
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
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
    serialNumber INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
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

CREATE TABLE WORKFLOW_TYPE(  
    code VARCHAR(15) NOT NULL PRIMARY KEY,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE WORKFLOW(  
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    isOver BOOLEAN NOT NULL,
    serialNumberEntity INT NOT NULL,
    type VARCHAR(15) NOT NULL,
    FOREIGN KEY (serialNumberEntity) REFERENCES BUSINESS_ENTITY (serialNumber),
    FOREIGN KEY (type) REFERENCES WORKFLOW_TYPE (code)
);

CREATE TABLE DOCUMENT_STATUS(  
    code VARCHAR(15) NOT NULL PRIMARY KEY,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE DOCUMENT_TYPE(  
    code VARCHAR(15) NOT NULL PRIMARY KEY,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE DOCUMENT(  
    type VARCHAR(15) NOT NULL,
    documentNumber INT AUTO_INCREMENT NOT NULL,
    date DATE NOT NULL,
    priceVATExcluded FLOAT NOT NULL,
    priceVATIncluded FLOAT NOT NULL,
    paymentDeadline DATE,
    workflowId INT NOT NULL,
    status VARCHAR(15),
    PRIMARY KEY (type, documentNumber),
    FOREIGN KEY (type) REFERENCES DOCUMENT_TYPE (code),
    FOREIGN KEY (workflowId) REFERENCES WORKFLOW (id),
    FOREIGN KEY (status) REFERENCES DOCUMENT_STATUS (code)
);

CREATE TABLE PRODUCT(  
    productSerialNumber INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    tag VARCHAR(200) NOT NULL,
    actualUnitPrice FLOAT NOT NULL,
    stockQuantity INT NOT NULL
);

CREATE TABLE TRANSACTION_DETAILS(  
    documentType VARCHAR(15) NOT NULL,
    documentNumber INT NOT NULL,
    code VARCHAR(15) NOT NULL,
    quantity INT NOT NULL,
    unitPrice DECIMAL(4,2) NOT NULL,
    reference INT NOT NULL,
    PRIMARY KEY (documentType, documentNumber, code),
    FOREIGN KEY (documentType, documentNumber) REFERENCES DOCUMENT (type, number),
    FOREIGN KEY (reference) REFERENCES PRODUCT (productSerialNumber)
);
