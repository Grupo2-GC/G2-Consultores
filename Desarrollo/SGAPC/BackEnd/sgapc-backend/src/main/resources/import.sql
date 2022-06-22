/**/
/* Creamos algunos usuarios con sus roles */
INSERT INTO `users` (username, password, enabled, firstname, lastname, email) VALUES ('jhonatan','$2a$10$ShaNfBMSZ5bw1TktyqCCUOpuADSvUHaw1b2z12QmwNL4pN2IVnOY2',1,'Jhonatan','Vasquez','jvasquezenrique@gmail.com');
INSERT INTO `users` (username, password, enabled, firstname, lastname, email) VALUES ('admin','$2a$10$Atqx.BJ5NnHik1q.VeuAEeBQZ5RJDhm9kxLGp/2.CTouHaPXe9oGC',1,'Joe', 'Doe', 'joe.doe@gmail.com');
INSERT INTO `users` (username, password, enabled, firstname, lastname, email) VALUES ('jorgeelcurioso','$2a$10$Atqx.BJ5NnHik1q.VeuAEeBQZ5RJDhm9kxLGp/2.CTouHaPXe9oGC',1,'Jorge', 'Curioso', 'jorge.elcurioso@gmail.com');

INSERT INTO `roles` (name) VALUES ('ROLE_GROCER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (name) VALUES ('ROLE_LOGISTIC');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2,2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2,3);
INSERT INTO `users_roles` (user_id, role_id) VALUES (3,3);

/* Populate tabla clientes */
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01','2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05', '2018-01-01');
INSERT INTO customers (firstname, lastname, email, create_at, update_at) VALUES('Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06', '2018-01-01');


/* Populate tabla clientes */
INSERT INTO suppliers (businessname, ruc, email, create_at, update_at) VALUES('ABC S.A.C', '11111111111', 'abc@abc.com', '2018-01-01','2018-01-01');
INSERT INTO suppliers (businessname, ruc, email, create_at, update_at) VALUES('MNZ S.A.C', '22222222222', 'mnz@gmail.com', '2018-01-02', '2018-01-01');
INSERT INTO suppliers (businessname, ruc, email, create_at, update_at) VALUES('XYZ S.A.C', '33333333333', 'xyz@gmail.com', '2018-01-03', '2018-01-01');
INSERT INTO suppliers (businessname, ruc, email, create_at, update_at) VALUES('PQR S.A.C', '44444444444', 'pqr@gmail.com', '2018-01-04', '2018-01-01');
INSERT INTO suppliers (businessname, ruc, email, create_at, update_at) VALUES('TPZ S.A.C', '55555555555', 'tpz@gmail.com', '2018-02-01', '2018-01-01');
INSERT INTO suppliers (businessname, ruc, email, create_at, update_at) VALUES('RXZ S.A.C', '66666666666', 'rxz@gmail.com', '2018-02-10', '2018-01-01');



/*Tabla de categorias*/
INSERT INTO categories (id, name) VALUES (1, 'Mouse');
INSERT INTO categories (id, name) VALUES (2, 'Teclado');
INSERT INTO categories (id, name) VALUES (3, 'Memoria RAM');
INSERT INTO categories (id, name) VALUES (4, 'Disco Duro (HDD)');
INSERT INTO categories (id, name) VALUES (5, 'Memoria SSD');
INSERT INTO categories (id, name) VALUES (6, 'Audifonos');
INSERT INTO categories (id, name) VALUES (7, 'Audifonos Gamer');
INSERT INTO categories (id, name) VALUES (8, 'Microfono');
INSERT INTO categories (id, name) VALUES (9, 'Pantalla');
INSERT INTO categories (id, name) VALUES (10, 'Camara Web');
INSERT INTO categories (id, name) VALUES (11, 'Notebook');

/*Tabla de categorias*/
INSERT INTO brands (id, name ,create_at) VALUES (1, 'Asus', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (2, 'MSI', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (3, 'GIGABYTE', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (4, 'Apple', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (5, 'Lenovo', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (6, 'Intel', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (7, 'Razer', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (8, 'Sony', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (9, 'Samsung', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (10, 'Red Dragon', NOW());
INSERT INTO brands (id, name ,create_at) VALUES (11, 'Micronics', NOW());

/* tabla productos */
INSERT INTO products (name, category_id) VALUES('Panasonic Pantalla LCD',9);
INSERT INTO products (name, category_id) VALUES('Sony Camara web digital DSC-W320B',10);
INSERT INTO products (name, category_id) VALUES('Apple Audifono PC shuffle',6);
INSERT INTO products (name, category_id) VALUES('Sony Notebook Z110',11);
INSERT INTO products (name, category_id) VALUES('Teclado Razer RT-C001',2);
INSERT INTO products (name, category_id) VALUES('Memoria HDD GIGABYTE',4);
INSERT INTO products (name, category_id) VALUES('Memoria SSD GIGABYTE',5);
