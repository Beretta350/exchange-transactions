CREATE TABLE IF NOT EXISTS transaction_retrieve_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id UUID NOT NULL,
    exchange_rate DOUBLE NOT NULL,
    converted_amount DOUBLE NOT NULL,
    retrieve_timestamp TIMESTAMP,
    transaction_id_reference UUID,
    FOREIGN KEY (transaction_id_reference) REFERENCES transaction(id)
);