CREATE DATABASE IF NOT EXISTS banking_system;
USE banking_system;

CREATE TABLE IF NOT EXISTS accounts (
    account_number BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    balance DECIMAL(10,2),
    security_pin CHAR(4),
    PRIMARY KEY (account_number)
);

CREATE TABLE IF NOT EXISTS user (
    full_name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    PRIMARY KEY (email)
);