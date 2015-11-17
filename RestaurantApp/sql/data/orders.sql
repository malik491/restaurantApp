/*order 1*/
	INSERT INTO orders (order_status, order_type, order_total, order_confirmation, address_id) 
	VALUES ('SUBMITTED', 'PICKUP', 8.49, 'conf#100', NULL);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (1, 1, 1);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (1, 2, 1);
/*order 2*/
	INSERT INTO orders (order_status, order_type, order_total, order_confirmation, address_id) 
	VALUES ('PREPARED', 'DELIVERY', 10.99, 'conf#200', 5);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (2, 5, 1);
/*order 3*/
	INSERT INTO orders (order_status, order_type, order_total, order_confirmation, address_id) 
	VALUES ('CANCELED', 'PICKUP', 4.00, 'conf#300', NULL);
	INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (3, 6, 2);