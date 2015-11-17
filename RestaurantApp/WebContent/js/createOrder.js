/**
 * javascript for add orders
 */
var order = {
	orderItems : [],
	addItem : function addItem(menuItemId) {
		var addNewOrderItem = true;
		for (i = 0; i < this.orderItems.length; i++) {
			var oldOrderItem = this.orderItems[i];
			if (oldOrderItem.menuItemId == menuItemId) {
				oldOrderItem.quantity += 1;
				addNewOrderItem = false;
			}
		}

		if (addNewOrderItem) {
			this.orderItems.push({
				menuItemId: menuItemId,
				quantity: 1
			});
		}
		updateView();
	}
}

function toFixed(value, precision) {
    var power = Math.pow(10, precision || 0);
    return Math.round(value * power) / power;
}

function updateView() {
	var summary = "";
	var total = 0.00;
	
	for (i = 0; i < order.orderItems.length; i++) {
		var orderItem = order.orderItems[i];
		if (menuItems) {
			for(j=0; j < menuItems.length; j++) {
				var menuItem = menuItems[j];
				if (menuItem.id == orderItem.menuItemId) {
					var name  = menuItem.name;
					var price = menuItem.price;
					var quantity = orderItem.quantity;
					
					total = toFixed(total + (price *  quantity), 2);
					summary += "\n";
					summary += name + "\t$" + price + " x" + quantity;
				}
			}
		}		
	}
	document.getElementById("summary").innerHTML = summary;
	document.getElementById("total").innerHTML = "" + total;
}

function placeOrder() {
	if (order.orderItems.length > 0) {
		  var xhttp = new XMLHttpRequest();
		  
		  // set the callback function for this object
		  xhttp.onreadystatechange = function() {
		    if (xhttp.readyState == 4 && xhttp.status == 200) {
		    	var respJSON = JSON.parse(xhttp.responseText);
		    	var added = respJSON.added;
		    	if (added) {
		    		order.orderItems = [];
		    		updateView(order);
		    		alert("succesfully added a new order");		
		    	} else {
		    		alert("failed to add a new order");	
		    	}
		    }
		  }
		  
		  //sync
		  var jsonData = JSON.stringify(order.orderItems);
		  //console.log(jsonData);
		  var encodedData = "requestType=ajax&orderItems=" + encodeURIComponent(jsonData);
		  //console.log(encodedData);
		  
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