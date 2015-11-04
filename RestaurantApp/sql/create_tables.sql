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