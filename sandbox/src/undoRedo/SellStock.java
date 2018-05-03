package undoRedo;

public class SellStock implements Order {
	private Stock abcStock;

	public SellStock(Stock abcStock) {
		this.abcStock = abcStock;
	}// Constructor

	@Override
	public void execute() {
		abcStock.sell();
	}// execute
}//class SellStock
