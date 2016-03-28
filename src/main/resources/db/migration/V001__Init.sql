CREATE TABLE account_entity (
    id bigint NOT NULL,
    account_name character varying(255),
    balance bigint,
    currency character varying(255),
    owner_id character varying(255)
);

CREATE SEQUENCE account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE transaction_entity (
    id bigint NOT NULL,
    amount bigint NOT NULL,
    date timestamp NOT NULL,
    destination_account_id bigint NOT NULL,
    source_account_id bigint NOT NULL
);

ALTER TABLE account_entity
    ADD CONSTRAINT account_entity_pkey PRIMARY KEY (id);

ALTER TABLE account_entity
    ADD CONSTRAINT accountnamesareuniqueperuser UNIQUE (owner_id, account_name);

ALTER TABLE transaction_entity
    ADD CONSTRAINT transaction_entity_pkey PRIMARY KEY (id);

ALTER TABLE transaction_entity
    ADD CONSTRAINT fk_5yibsb6ck6sbjyghnngakmpf3 FOREIGN KEY (destination_account_id) REFERENCES account_entity(id);

ALTER TABLE transaction_entity
    ADD CONSTRAINT fk_dxq0j4vuo12pv6lt8j2hx9yeg FOREIGN KEY (source_account_id) REFERENCES account_entity(id);
