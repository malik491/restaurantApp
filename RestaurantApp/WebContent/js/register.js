/**
 * javascript for create orders from a register
 */

// list of order items for an order
var registerOrder = [];


function getMenuItem(mItemId) {
	var result = null;
	if (registerMenuItemList) {
		for (i=0; i < registerMenuItemList.length; i++) {
			var mItem = registerMenuItemList[i];
			if (mItem.id === mItemId) {
				result = mItem;
				break;
			}
		}
	}
	return result;
}

function getNewOrderItem(mItemId) {
	var menuItem = getMenuItem(mItemId);
	var result = null;
	if (menuItem) {
		var orderItem = {"menuItem": menuItem, "quantity": 1};
		result = orderItem;
	}
	return result;
}

function addItem(mItemId) {
	var added = false;
	for (i = 0; i < registerOrder.length; i++) {
		var oldItem = registerOrder[i];
		if (oldItem.menuItem.id === mItemId) {
			oldItem.quantity += 1;
			added = true;
			break;
		}
	}

	if (added === false) {
		var newItem = getNewOrderItem(mItemId);
		if (newItem) {
			registerOrder.push(newItem);				
		}
	}
	
	updateView();
}

function updateItem(id) {
	var qtyInputElem = document.getElementById("inputId-" + id);
	var newQty = qtyInputElem.value;
	if (newQty < 0) {
		// reset to old quantity
		for (i=0; i < registerOrder.length; i++) {
			var oldItem = registerOrder[i];
			if (oldItem.menuItem.id !== id) {
				qtyInputElem.value = oldItem.quantity;
				break;
			}
		}
	}
	if (newQty == 0) {
		// remove order item from the array (order)
		// blame it on stupid JS for not having a method
		// to remove an element by index from an array
		var newOrder = [];
		
		for (i=0; i < registerOrder.length; i++) {
			var oldItem = registerOrder[i];
			if (oldItem.menuItem.id !== id) {
				newOrder.push(oldItem);
			}
		}
		// order item is removed, else is unchanged
		registerOrder = newOrder;
		// delete row
		var row = document.getElementById("rowId-" + id);
		row.parentNode.removeChild(row);
		updateTotal();
	} else {
		// update quantity
		for (i = 0; i < registerOrder.length; i++) {
			var oldItem = registerOrder[i];
			if (oldItem.menuItem.id === id) {
				oldItem.quantity = newQty;
				break;
			}
		}
		updateTotal();
	}
}

function updateTotal() {
	var total = 0.00;
	for (i = 0; i < registerOrder.length; i++) {
		var orderItem = registerOrder[i];
		var price = orderItem.menuItem.price;
		var quantity = orderItem.quantity;
		total = toFixed(total + (price *  quantity), 2);
	}
	document.getElementById("orderTotal").innerHTML = total;
}

function updateView() {
	for (i = 0; i < registerOrder.length; i++) {
		var orderItem = registerOrder[i];
		var mItemId = orderItem.menuItem.id;
		var quantity = orderItem.quantity;
		
		var qtyInputElem = document.getElementById("inputId-"+ mItemId);
		if (qtyInputElem) {
			// update old row
			qtyInputElem.value = quantity;			
		} else {
			var itemName  = orderItem.menuItem.name;
			var price = orderItem.menuItem.price;
			
			// create new one
			var qtyInputElem = getNewQtyInput(mItemId);
			qtyInputElem.value = quantity;
			
			var table = document.getElementById("orderItemsTable");
			var tableRows = table.getElementsByTagName("tr");
			var rowCount = tableRows.length;
			
			var newRow = table.insertRow(rowCount);
			newRow.setAttribute("id", "rowId-" + mItemId);
			
			newRow.insertCell(0).innerHTML = itemName;
			newRow.insertCell(1).innerHTML = price;
			newRow.insertCell(2).appendChild(qtyInputElem);
		}
	}
	updateTotal();
}

function clearOrder() {
	registerOrder = [];
	
	// remove table rows except the table header
	var table = document.getElementById("orderItemsTable");
	var tableRows = table.getElementsByTagName("tr");
	var rowCount = tableRows.length;
	for (i= rowCount - 1; i > 0; i--) {
		var row = tableRows[i];
		row.parentNode.removeChild(row);
	}
	document.getElementById("orderTotal").innerHTML = 0;
}

function submitOrder() {
	if (registerOrder.length > 0) {
		  var xhttp = new XMLHttpRequest();
		  
		  // set the callback function for this object
		  xhttp.onreadystatechange = function() {
		    if (xhttp.readyState == 4 && xhttp.status == 200) {
		    	var respJSON = JSON.parse(xhttp.responseText);
		    	var added = respJSON.added;
		    	var msg = respJSON.message;
		    	if (added) {
		    		clearOrder();
		    		alert("succesfully added a new order");		
		    	} else {
		    		alert(msg);	
		    	}
		    }
		  }
		  
		  //sync
		  var jsonData = JSON.stringify(registerOrder);
		  console.log(jsonData);
		  var encodedData = "requestType=ajax&orderItemsList=" + encodeURIComponent(jsonData);
		  console.log(encodedData);
		  
		  // for GET,use the url for both (1) servlet name and (2) data:
		  // format [servletName]?[paramName=]encoded-data 
		  //xhttp.open("GET", "addOrderJSON?" + encodedData, true);
		  //xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  //xhttp.send();
		  
		  // for POST, must set the request header and then encoded data is added using send(string)
		  // manually type the app name for now
		  xhttp.open("POST", "/RestaurantApp/order/create", true);
		  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  xhttp.send(encodedData);
	}
}


function getNewQtyInput(id) {
	var qtyInputElem = document.createElement("input");
	qtyInputElem.setAttribute("id", "inputId-" + id);
	qtyInputElem.setAttribute("type", "number");
	qtyInputElem.setAttribute("min", "0");
	qtyInputElem.setAttribute("max", "1000");
	qtyInputElem.setAttribute("onclick", "updateItem(" + id + ")");
	qtyInputElem.setAttribute("onkeyup", "updateItem(" + id + ")");
	return qtyInputElem;
}


function toFixed(value, precision) {
    var power = Math.pow(10, precision || 0);
    return Math.round(value * power) / power;
}