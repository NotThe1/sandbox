package xml;

public class Item {
	private String date;
	private String mode;
	private String unit;
	private String current;
	private String interactive;

	public String getDate() {
		return date;
	}//getDate

	public void setDate(String date) {
		this.date = date;
	}//setDate

	public String getMode() {
		return mode;
	}//getMode

	public void setMode(String mode) {
		this.mode = mode;
	}//setMode

	public String getUnit() {
		return unit;
	}//getUnit

	public void setUnit(String unit) {
		this.unit = unit;
	}//setUnit

	public String getCurrent() {
		return current;
	}//getCurrent

	public void setCurrent(String current) {
		this.current = current;
	}//setCurrent

	public String getInteractive() {
		return interactive;
	}//getInteractive

	public void setInteractive(String interactive) {
		this.interactive = interactive;
	}//setInteractive

	@Override
	public String toString() {
		return "Item [current=" + current + ", date=" + date + ", interactive=" + interactive + ", mode=" + mode
				+ ", unit=" + unit + "]";
	}//toString
}// class Item
