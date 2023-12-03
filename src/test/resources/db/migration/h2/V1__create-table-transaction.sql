CREATE TABLE IF NOT EXISTS purchase_transaction (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    amount NUMERIC(19, 2) NOT NULL,
    description VARCHAR(50) NOT NULL,
    transaction_date DATE NOT NULL,
    transaction_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);