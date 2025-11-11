CREATE TABLE patients (
    phno VARCHAR(15) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    dob DATE,
    gender VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
