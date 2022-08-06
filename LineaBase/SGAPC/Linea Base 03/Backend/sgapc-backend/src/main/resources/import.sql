/**/
/* Creamos algunos usuarios con sus roles */
INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('admin','$2a$10$Atqx.BJ5NnHik1q.VeuAEeBQZ5RJDhm9kxLGp/2.CTouHaPXe9oGC',1,'Joe', 'Doe', 'joe.doe@gmail.com');
INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('jhonatan','$2a$10$ShaNfBMSZ5bw1TktyqCCUOpuADSvUHaw1b2z12QmwNL4pN2IVnOY2',1,'Jhonatan','Vasquez','jvasquezenrique@gmail.com');
INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('jorgeelcurioso','$2a$10$Atqx.BJ5NnHik1q.VeuAEeBQZ5RJDhm9kxLGp/2.CTouHaPXe9oGC',1,'Jorge', 'Curioso', 'jorge.elcurioso@gmail.com');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_GROCER');
INSERT INTO roles (name) VALUES ('ROLE_LOGISTIC');

INSERT INTO users_roles (user_id, role_id) VALUES (1,1);
INSERT INTO users_roles (user_id, role_id) VALUES (2,2);
INSERT INTO users_roles (user_id, role_id) VALUES (3,3);

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
INSERT INTO categories (name) VALUES ('Mouse');
INSERT INTO categories (name) VALUES ('Teclado');
INSERT INTO categories (name) VALUES ('Memoria RAM');
INSERT INTO categories (name) VALUES ('Disco Duro (HDD)');
INSERT INTO categories (name) VALUES ('Memoria SSD');
INSERT INTO categories (name) VALUES ('Audifonos');
INSERT INTO categories (name) VALUES ('Audifonos Gamer');
INSERT INTO categories (name) VALUES ('Microfono');
INSERT INTO categories (name) VALUES ('Pantalla');
INSERT INTO categories (name) VALUES ('Camara Web');
INSERT INTO categories (name) VALUES ('Notebook');

/*Tabla de categorias*/
INSERT INTO brands (name, photo) VALUES ('Asus', 'https://dlcdnimgs.asus.com/websites/global/Sno/79183.jpg');
INSERT INTO brands (name, photo) VALUES ('MSI', 'https://img1.freepng.es/20180402/jfq/kisspng-laptop-graphics-cards-video-adapters-computer-ca-accessory-5ac29bf99708d5.0747053115227033536186.jpg');
INSERT INTO brands (name, photo) VALUES ('GIGABYTE', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Gigabyte_Technology_logo_20080107.svg/2560px-Gigabyte_Technology_logo_20080107.svg.png');
INSERT INTO brands (name, photo) VALUES ('Apple','http://assets.stickpng.com/images/580b57fcd9996e24bc43c516.png');
INSERT INTO brands (name, photo) VALUES ('Lenovo', 'https://logodownload.org/wp-content/uploads/2014/09/lenovo-logo-1-1.png');
INSERT INTO brands (name, photo) VALUES ('Intel', 'http://assets.stickpng.com/images/58568d224f6ae202fedf2720.png');
INSERT INTO brands (name, photo) VALUES ('Razer', 'https://upload.wikimedia.org/wikipedia/commons/7/79/Logo_Razer_2017.png');
INSERT INTO brands (name, photo) VALUES ('Sony', 'http://assets.stickpng.com/images/5848242ecef1014c0b5e49c8.png');
INSERT INTO brands (name, photo) VALUES ('Samsung', 'https://upload.wikimedia.org/wikipedia/commons/f/f1/Samsung_logo_blue.png');
INSERT INTO brands (name, photo) VALUES ('Red Dragon', 'https://cdn.worldvectorlogo.com/logos/redragon.svg');
INSERT INTO brands (name, photo) VALUES ('Micronics', 'http://www.micronics.pe/wp-content/uploads/2017/07/LOGO-final.jpg');
INSERT INTO brands (name, photo) VALUES ('Sony', 'http://assets.stickpng.com/images/5848242ecef1014c0b5e49c8.png');
INSERT INTO brands (name, photo) VALUES ('Panasonic', 'https://logodownload.org/wp-content/uploads/2017/05/panasonic-logo.png');

/* tabla productos */
INSERT INTO products (name, category_id,brand_id,image) VALUES('Panasonic Pantalla LCD',9,13,'https://www.panasonic.com/content/dam/pim/pe/es/TC/TC-32E/TC-32ES600L/TC-32ES600L-Variation_Image_for_See_All_1Global-1_pe_es.png');
INSERT INTO products (name, category_id,brand_id,image) VALUES('Sony Camara web digital DSC-W320B',10,12,'https://http2.mlstatic.com/D_NQ_NP_795588-MPE42434437322_072020-V.jpg');
INSERT INTO products (name, category_id,brand_id,image) VALUES('Apple Audifono PC shuffle',6,4,'https://http2.mlstatic.com/D_NQ_NP_781435-MLV48925462732_012022-O.jpg');
INSERT INTO products (name, category_id,brand_id,image) VALUES('Sony Notebook Z110',11,12,'https://www.muycomputer.com/wp-content/uploads/2012/08/Sony-Vaio-T-ultrabook-1-630x332.jpg');
INSERT INTO products (name, category_id,brand_id,image) VALUES('Teclado Razer RT-C001',2,7,'https://images-na.ssl-images-amazon.com/images/I/41SbkLoyMmL.jpg');
INSERT INTO products (name, category_id,brand_id,image) VALUES('Memoria HDD GIGABYTE',4,3,'https://www.profesionalreview.com/wp-content/uploads/2018/05/mejores-discos-duros.jpg');
INSERT INTO products (name, category_id,brand_id,image) VALUES('Memoria SSD GIGABYTE',5,3,'https://datalockperu.com/wp-content/uploads/2020/04/SSD-Gigabyte.png');


INSERT INTO entries(supplier_id, type_receipt,date_entry,user_id, igv, num_receipt,total) VALUES (1, 'Boleta', NOW(),2,2432.7, 'RC-001',13515.00)

INSERT INTO entries_detail (product_id,purchase_price,quantity_entry,entry_id) VALUES (6, 550.50,15,1);
INSERT INTO entries_detail (product_id,purchase_price,quantity_entry,entry_id) VALUES (7, 350.50,15,1);


INSERT INTO entries_detail (product_id,purchase_price,quantity_entry) VALUES (1, 1200.50,15);
INSERT INTO entries_detail (product_id,purchase_price,quantity_entry) VALUES (4, 800.50,15);
INSERT INTO entries_detail (product_id,purchase_price,quantity_entry) VALUES (5, 40.50,15);
INSERT INTO entries_detail (product_id,purchase_price,quantity_entry) VALUES (3, 100.50,15);
INSERT INTO entries_detail (product_id,purchase_price,quantity_entry) VALUES (2, 900.50,15);


