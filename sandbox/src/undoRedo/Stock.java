package undoRedo;

public class Stock {
	private String name = "ABC";
	private int quantity = 10;
	
	public void buy() {
		System.out.printf("[Stock.buy] Qty: %d, name: %s bought%n",quantity,name);
	}//buy
	
	public void sell() {
		System.out.printf("[Stock.sell] Qty: %d, name: %s sold%n",quantity,name);
	}//buy
	
}//class Stock 
