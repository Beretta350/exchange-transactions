CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "purchase_transaction" (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    amount NUMERIC(15, 2) NOT NULL,
    description VARCHAR(50) NOT NULL,
    transaction_date DATE NOT NULL,
    transaction_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);