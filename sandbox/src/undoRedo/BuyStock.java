package undoRedo;

public class BuyStock implements Order {

	private Stock abcStock;

	public BuyStock(Stock abcStock) {
		this.abcStock = abcStock;
	}// Constructor

	@Override
	public void execute() {
		abcStock.buy();
	}// execute

}// class BuyStock
