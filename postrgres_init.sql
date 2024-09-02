CREATE TABLE IF NOT EXISTS products (
    code INTEGER NOT NULL,
    name VARCHAR(50),
    category VARCHAR(50),
    price FLOAT,
    stock INT,
    PRIMARY KEY (code)
);

INSERT INTO products VALUES (100,'Azúcar','Alimentación',1.10,20);
INSERT INTO products VALUES (101,'Leche','Alimentación',1.20,15);
INSERT INTO products VALUES (102,'Jabón','Limpieza',0.89,30);
INSERT INTO products VALUES (103,'Mesa','Hogar',125,4);
INSERT INTO products VALUES (104,'Televisión','Hogar',650,10);
INSERT INTO products VALUES (105,'Huevos','Alimentación',2.20,30);
INSERT INTO products VALUES (106,'Fregona','Limpieza',3.40,6);
INSERT INTO products VALUES (107,'Detergente','Limpieza',8.7,12);

CREATE TABLE IF NOT EXISTS shipping (
    id_shipping UUID DEFAULT gen_random_uuid(),
    product VARCHAR(50),
    date_shipping TIMESTAMP,
    address VARCHAR(200),
    status VARCHAR(50),
    PRIMARY KEY (id_shipping)
);