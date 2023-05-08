CREATE TABLE IF NOT EXISTS owners
(
    owner_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name  VARCHAR(255)                         NOT NULL,
    middle_name  VARCHAR(255)                        NOT NULL,
    last_name  VARCHAR(255)                          NOT NULL,
    passport VARCHAR(32)                             NOT NULL,
    drivers_license VARCHAR(8)                       NOT NULL,
    date_of_birth VARCHAR(16)                        NOT NULL,
    experience int,
    car_id bigint,
    CONSTRAINT pk_owner PRIMARY KEY (owner_id),
    CONSTRAINT UQ_PASSPORT UNIQUE (passport)
    );

CREATE TABLE IF NOT EXISTS accounts
(
    account_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    green_balance float,
    red_balance float,
    blue_balance float,
    owner_id bigint,
    CONSTRAINT pk_account PRIMARY KEY (account_id),
    CONSTRAINT FK_accounts_owners FOREIGN KEY (owner_id) REFERENCES owners (owner_id) ON DELETE CASCADE
    );







