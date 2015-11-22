/**
 * 
 */
var processingUpdateRequest = false;
window.onkeyup = function changeStatus(e){
	if (e.keyCode === 32 && processingUpdateRequest === false) {
		// if spacebar
		var topOrder = getTopView();		
		if (topOrder) {
			var orderId = topOrder.orderId;
			console.log(topOrder.divElement);
			console.log(orderId);
			processingUpdateRequest = true;
			changeOrderStatus(orderId);							
		}
	}
}

function getTopView() {
	var hElem = document.getElementById('h-' + curDivId);
	var divElem = document.getElementById('div-'+ curDivId);
	var orderId = divElem.getElementsByTagName("input")[0].value;
	if (hElem && divElem && orderId) {
		return {"hElement" : hElem, "divElement" : divElem, "orderId" : orderId};		
	}
	return null;
}

function removeTopView() {
	var topOrder = getTopView();
	
	// remove top order view
	var div = topOrder.divElement;
	var h = topOrder.hElement;	
	div.parentNode.removeChild(div);
	h.parentNode.removeChild(h);
	
	kitchenOrdersCount--;
	curDivId++;
	
	if (kitchenOrdersCount > 0) {
		var nextDiv = getTopView().divElement;
		if (nextDiv) {
			nextDiv.setAttribute("aria-selected", "true");
			nextDiv.setAttribute("aria-expanded", "true");
			nextDiv.setAttribute("style", "clear:both;");			
		} else {
			console.log(curDivId);
		}
	}
}


function processResponse(orders) {
	if (kitchenOrdersCount < 0)
		kitchenOrdersCount = 0;
	
	var newOrdesIndex = kitchenOrdersCount + 1;
	
	if (newOrdesIndex < orders.length) {
		var newId = Math.max(newOrdesIndex, curDivId);
		var container = document.getElementById("accordion");
		var size = orders.length;
		for (i=newOrdesIndex; i < orders.length; i++) {
			var divElem = getNewDiv(newId, orders[i]);
			var hElem = getNewHElem(newId, orders[i]);
			container.appendChild(hElem);
			container.appendChild(divElem);
			newId++;
		}
		kitchenOrdersCount = orders.length - kitchenOrdersCount;
	}
}

function getNewDiv(newId, order) {
	var div = document.createElement("div");
	div.setAttribute("id", "div-" + newId);
	div.setAttribute("aria-selected", "true");
	div.setAttribute("aria-expanded", "true");
	div.setAttribute("style", "clear:both;");
	
	var table = getNewTable(order);
	div.appendChild(table);
	return div;	
}

function getNewHElem(newId, order) {
	var h = document.createElement("h3");
	h.setAttribute("id", "h-" + newId);
	h.innerHTML = "Order(" + order.orderId + ")";
	return h;
}

function getNewTable(order) {
	var table = document.createElement("table");
	
	var rowIndex = 0;
	
	var thead =  table.createTHead();
	var row = thead.insertRow(rowIndex++);
	var hiddenInput = getHiddenInput(order.orderId);
	row.insertCell(0).appendChild(hiddenInput);
	row.insertCell(1);
	
	row = table.insertRow(rowIndex++);
	row.insertCell(0).innerHTML = "Placed On: ";
	row.insertCell(1).innerHTML = order.dateTime;
	
	var oItems = order.orderItems

	for (c=0; c < oItems.length; c++) {
		var oItem = oItems[c];
		var itemName = oItem.menuItem.name;
		var qty = oItem.quantity;
		row = table.insertRow(rowIndex++);
		row.insertCell(0).innerHTML = itemName;
		row.insertCell(1).innerHTML = qty;
	}
	return table;
}

function getHiddenInput(orderId) {
	var inputElem= document.createElement("input");
	inputElem.setAttribute("type", "hidden");
	inputElem.setAttribute("name", "orderId");
	inputElem.setAttribute("value", "" + orderId);
	return inputElem;
}

function fetchOrders() {
	var xhttp = new XMLHttpRequest();
	  
	// set the callback function for this object
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
		  	var response = JSON.parse(xhttp.responseText);
		   	var orders = response.orders; 
		   	console.log(orders);
		   	if (orders) {
		   		//
		   		processResponse(orders);
		   	}
		   	setTimeout(fetchOrders, 40000);
		}
	}
		  
	var params = "requestType=ajax";
	console.log(params);
	
	// for POST, must set the request header and then encoded data is added using send(string)
	// manually type the app name for now
	xhttp.open("POST", "/RestaurantApp/store/kitchenOrders", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
	
}


function changeOrderStatus(orderId) {
	var xhttp = new XMLHttpRequest();
		  
	// set the callback function for this object
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
		  	var respJSON = JSON.parse(xhttp.responseText);
		   	var updated = respJSON.updated; 
		   	var msg = respJSON.message;
		   	if (updated) {
		   		removeTopView();
		   		processingUpdateRequest = false;
		   	} else {
		   		alert(msg);	
		   	}
		}
	}
		  
	var params = "requestType=ajax&orderId=" + orderId;
	console.log(params);
	
	// for POST, must set the request header and then encoded data is added using send(string)
	// manually type the app name for now
	xhttp.open("POST", "/RestaurantApp/order/update", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}


