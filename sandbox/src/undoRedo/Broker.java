package undoRedo;

import java.util.ArrayList;
import java.util.List;

public class Broker {
	private List<Order> orders = new ArrayList<>();
	
	public void takeOrder(Order order) {
		orders.add(order);
	}//takeOrder
	
	public void placeOrders() {
		for(Order order:orders) {
			order.execute();
		}//for
		orders.clear();
	}//placeOrders
}//class Broker
