-- Table Schemas
CREATE TABLE IF NOT EXISTS customer(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    lastname VARCHAR(25) NOT NULL,
    tckn VARCHAR(15) NOT NULL,
    salary REAL DEFAULT 0,
    phone VARCHAR(15)
);