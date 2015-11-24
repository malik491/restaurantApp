DROP DATABASE IF EXISTS se491;
CREATE DATABASE se491;
USE se491;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu_items;

DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS addresses;


CREATE TABLE addresses (
	address_id			BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	
	line1				VARCHAR(100) NOT NULL,
	line2				VARCHAR(100) DEFAULT '',
	city				VARCHAR(30) NOT NULL,
	state				ENUM ('AL','AK', 'AZ', 'AR', 'CA', 'CO', 'CT', 'DE', 'FL', 'GA', 'HI', 'ID',
							  'IL', 'IN', 'IA', 'KS', 'KY', 'LA', 'ME', 'MD', 'MA', 'MI', 'MN', 'MS',
							  'MO', 'MT', 'NE', 'NV', 'NH', 'NJ', 'NM', 'NY', 'NC', 'ND', 'OH', 'OK',
							  'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WA', 'WV', 
							  'WI', 'WY') NOT NULL,
	zipcode				VARCHAR(10) NOT NULL,

	PRIMARY KEY (address_id)
);

CREATE TABLE orders (
	order_id       		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	
	order_status		ENUM ('SUBMITTED', 'PREPARED', 'CANCELED') NOT NULL,
	order_type			ENUM ('PICKUP', 'DELIVERY') NOT NULL,
	order_timestamp		TIMESTAMP NOT NULL,
	order_total			DECIMAL(7, 2) NOT NULL,
	order_confirmation  VARCHAR(50) NOT NULL,
	
	address_id    		BIGINT UNSIGNED DEFAULT NULL,
	
	
	PRIMARY KEY (order_id),
	FOREIGN KEY (address_id) REFERENCES addresses (address_id)
);

CREATE TABLE menu_items (
	menu_item_id		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	
	item_name			VARCHAR(100) NOT NULL,
	item_description	VARCHAR(300) DEFAULT '',
	item_price			DECIMAL(5, 2)DEFAULT 0,
	
	PRIMARY KEY (menu_item_id)
);

CREATE TABLE order_items (
	order_id            BIGINT UNSIGNED NOT NULL,
	menu_item_id		BIGINT UNSIGNED NOT NULL,
	
	quantity			SMALLINT unsigned NOT NULL,
	
	PRIMARY KEY (order_id, menu_item_id),
	FOREIGN KEY (order_id) REFERENCES orders (order_id) on delete cascade,
	FOREIGN KEY (menu_item_id) REFERENCES menu_items (menu_item_id)
);

CREATE TABLE users (
	user_email			VARCHAR(30) NOT NULL,	
	
	first_name			VARCHAR(20) NOT NULL,
	last_name			VARCHAR(20) NOT NULL,
	phone				VARCHAR(15) DEFAULT '',
	address_id			BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (user_email),
	FOREIGN KEY (address_id) REFERENCES addresses (address_id)
);

CREATE TABLE accounts (
	username			VARCHAR(30) NOT NULL,
	password			VARCHAR(60) NOT NULL,
	account_role		enum ('ADMIN', 'MANAGER', 'EMPLOYEE', 'CUSTOMER') NOT NULL,		
	
	user_email			VARCHAR(30) NOT NULL,
	
	PRIMARY KEY (username),
	FOREIGN KEY (user_email) REFERENCES users (user_email)
);


/*address 1 for admin 1*/
	INSERT INTO addresses (line1, line2, city, state, zipcode) VALUES ('100 main St', 'Apt# 1', 'Chicago', 'IL', '60604');
/*address 2 for manager 1*/
	INSERT INTO addresses (line1, line2, city, state, zipcode) VALUES ('100 main St', 'Apt# 2', 'Chicago', 'IL', '60604');
/*address 3 for employee 1*/
	INSERT INTO addresses (line1, line2, city, state, zipcode) VALUES ('200 N Clark St', 'unit A', 'Chicago', 'IL', '60611');
/*address 4 for employee 2*/
	INSERT INTO addresses (line1, line2, city, state, zipcode) VALUES ('300 W Madison Ave', 'suit B', 'Chicago', 'IL', '60606');
/*address 5 (for delivery)*/	
	INSERT INTO addresses (line1, line2, city, state, zipcode) VALUES ('400 S Broadway Ave', 'Apt# 1', 'Chicago', 'IL', '60613');
    

/*menu item 1*/	
	INSERT INTO menu_items (item_name, item_description, item_price) VALUES ('Soda', 'cold and refreshing soda', 1.99);	
/*menu item 2*/	
	INSERT INTO menu_items (item_name, item_description, item_price) VALUES ('Chesse Burger', 'speciality cheese burger with our special sauce', 6.50);
/*menu item 3*/
	INSERT INTO menu_items (item_name, item_description, item_price) VALUES ('Salad', 'fresh green leaves, walnuts, and grape tomatos mixed with your choice of salad dressing', 3.99);
/*menu item 4*/	
	INSERT INTO menu_items (item_name, item_description, item_price) VALUES ('Small Pizza', 'a slice of pizza of your choice', 3.00);
/*menu item 5*/	
	INSERT INTO menu_items (item_name, item_description, item_price) VALUES ('Pizza', '10 inch pizza of your choice', 10.99);
/*menu item 6*/	
	INSERT INTO menu_items (item_name, item_description, item_price) VALUES ('Apple Pie', 'something sweet', 2.00);


/* user 1: admin*/
	INSERT INTO users (user_email, first_name, last_name, phone, address_id) 
	VALUES ('admin1@email.com', 'adminFirst', 'adminLast', '1234567890', 1);
/* user 2: manager*/
	INSERT INTO users (user_email, first_name, last_name, phone, address_id) 
	VALUES ('manager1@email.com', 'managerFirst', 'managerLast', '1234567890', 2);

/* user 3: employee 1*/
	INSERT INTO users (user_email, first_name, last_name, phone, address_id) 
	VALUES ('employee1@email.com', 'employee1First', 'employee1Last', '1234567890', 3);

/* user 4: employee 2*/
	INSERT INTO users (user_email, first_name, last_name, phone, address_id) 
	VALUES ('employee2@email.com', 'employee2First', 'employee2Last', '1234567890', 4);

/*order 1*/
	INSERT INTO orders (order_status, order_type, order_timestamp, order_total, order_confirmation, address_id) 
	VALUES ('SUBMITTED', 'PICKUP', current_timestamp(), 8.49, 'conf#100', NULL);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (1, 1, 1);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (1, 2, 1);
/*order 2*/
	INSERT INTO orders (order_status, order_type, order_timestamp, order_total, order_confirmation, address_id) 
	VALUES ('SUBMITTED', 'DELIVERY', current_timestamp(), 10.99, 'conf#200', 5);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (2, 5, 1);
/*order 3*/
	INSERT INTO orders (order_status, order_type, order_timestamp, order_total, order_confirmation, address_id) 
	VALUES ('SUBMITTED', 'PICKUP', current_timestamp(), 4.00, 'conf#300', NULL);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (3, 6, 2);


/* account 1: admin 1*/
	INSERT INTO accounts (username, password, account_role, user_email) 
	VALUES ('admin1', 'admin1', 'ADMIN', 'admin1@email.com');
/* account 2: manager 1*/
	INSERT INTO accounts (username, password, account_role, user_email) 
	VALUES ('manager1', 'manager1', 'MANAGER', 'manager1@email.com');
/* account 3: employee 1*/
	INSERT INTO accounts (username, password, account_role, user_email)
	VALUES ('employee1', 'employee1', 'EMPLOYEE', 'employee1@email.com');
/* account 4: employee 2*/
	INSERT INTO accounts (username, password, account_role, user_email)
	VALUES ('employee2', 'employee2', 'EMPLOYEE', 'employee2@email.com');