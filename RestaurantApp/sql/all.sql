/**
 * the order for dropping table matters (because of foreign keys)
 */

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
	address_id    		BIGINT UNSIGNED DEFAULT NULL,
	
	order_status		ENUM ('SUBMITTED', 'PREPARED', 'CANCELED') NOT NULL,
	order_type			ENUM ('PICKUP', 'DELIVERY') NOT NULL,
	order_date			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	order_total			DECIMAL(5, 2) NOT NULL,
	order_confirmation  VARCHAR(50) NOT NULL,
	
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
	
	quantity			TINYINT unsigned NOT NULL,
	
	PRIMARY KEY (order_id, menu_item_id),
	FOREIGN KEY (order_id) REFERENCES orders (order_id),
	FOREIGN KEY (menu_item_id) REFERENCES menu_items (menu_item_id)
);

CREATE TABLE users (
	user_id				BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	address_id			BIGINT UNSIGNED NOT NULL,
	
	first_name			VARCHAR(20) NOT NULL,
	last_name			VARCHAR(20) NOT NULL,
	email				VARCHAR(30) DEFAULT '',
	phone				VARCHAR(15) DEFAULT '',
	
	PRIMARY KEY (user_id),
	FOREIGN KEY (address_id) REFERENCES addresses (address_id)
);

CREATE TABLE accounts (
	account_id			BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	user_id             BIGINT UNSIGNED NOT NULL,
	
	account_role		enum ('ADMIN', 'MANAGER', 'EMPLOYEE', 'CUSTOMER') NOT NULL,	
	username			VARCHAR(30)  NOT NULL,
	password			VARCHAR(60) NOT NULL,
	
	PRIMARY KEY (account_id),
	FOREIGN KEY (user_id) REFERENCES users (user_id),
	UNIQUE(username)
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

	
/*order 1*/
	INSERT INTO orders (order_status, order_type, order_total, order_confirmation) VALUES ('SUBMITTED', 'PICKUP', 8.49, 'abc123');
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (1, 1, 1);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (1, 2, 1);
/*order 2*/
	INSERT INTO orders (address_id, order_status, order_type, order_total, order_confirmation) VALUES (5, 'PREPARED', 'DELIVERY', 10.99, 'abc456');
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (2, 5, 1);
/*order 3*/
	INSERT INTO orders (order_status, order_type, order_total, order_confirmation) VALUES ('CANCELED', 'PICKUP', 4.00, 'abc789');
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (3, 6, 2);
	
			
/* user 1: admin*/
	INSERT INTO users (address_id, first_name, last_name, email, phone) VALUES (1, 'admin', 'lastName', 'admin.lastName@example.com', '1234567890');
/* user 2: manager*/
	INSERT INTO users (address_id, first_name, last_name, email, phone) VALUES (2, 'manager', 'lastName', 'manager.lastName@example.com', '1234567890');
/* user 3: employee 1*/
	INSERT INTO users (address_id, first_name, last_name, email, phone) VALUES (3, 'employee1', 'lastName', 'employee1@example.com', '1234567890');
/* user 4: employee 2*/
	INSERT INTO users (address_id, first_name, last_name, email, phone)	VALUES (4, 'employee2', 'lastName', 'employee2@example.com', '1234567890');

	
/* account 1: admin 1*/
	INSERT INTO accounts (user_id, account_role, username, password) VALUES (1, 'ADMIN', 'admin1', '1');
/* account 2: manager 1*/
	INSERT INTO accounts (user_id, account_role, username, password) VALUES (2, 'MANAGER', 'manager1', '1');
/* account 3: employee 1*/
	INSERT INTO accounts (user_id, account_role, username, password) VALUES (3, 'EMPLOYEE', 'employee1', '1');
/* account 4: employee 2*/
	INSERT INTO accounts (user_id, account_role, username, password) VALUES (4, 'EMPLOYEE', 'employee2', '1');

