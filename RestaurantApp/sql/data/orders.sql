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