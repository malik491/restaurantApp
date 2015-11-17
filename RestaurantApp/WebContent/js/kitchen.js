/**
 * 
 */

var kitchenOrders = {
		topOrder : undefined,
		orders : [],
		setNextTopOrder: function() {
					this.topOrder = undefined;
					if (orders.length > 0) {
						topOrder = orders[orders.length-1];
						//orders[orders.length-1] = undefined;
					}
				  }		  
};



