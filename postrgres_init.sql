CREATE TABLE IF NOT EXISTS products (
    code INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    category VARCHAR(50),
    price FLOAT,
    stock INT
);

INSERT INTO products VALUES (100,'Azúcar','Alimentación',1.10,20);
INSERT INTO products VALUES (101,'Leche','Alimentación',1.20,15);
INSERT INTO products VALUES (102,'Jabón','Limpieza',0.89,30);
INSERT INTO products VALUES (103,'Mesa','Hogar',125,4);
INSERT INTO products VALUES (104,'Televisión','Hogar',650,10);
INSERT INTO products VALUES (105,'Huevos','Alimentación',2.20,30);
INSERT INTO products VALUES (106,'Fregona','Limpieza',3.40,6);
INSERT INTO products VALUES (107,'Detergente','Limpieza',8.7,12);