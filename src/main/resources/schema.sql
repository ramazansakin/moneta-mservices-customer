-- Table Schemas
DROP TABLE IF EXISTS customer;

CREATE TABLE IF NOT EXISTS customer(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    lastname VARCHAR(25) NOT NULL,
    tckn VARCHAR(15) NOT NULL,
    salary DOUBLE DEFAULT 0,
    phone VARCHAR(15)
);