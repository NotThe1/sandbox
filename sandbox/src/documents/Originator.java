package documents;

public class Originator {
	private String state;

	public void setState(String state) {
		this.state = state;
	}// setState

	public String getState() {
		return state;
	}// getState

	public Memento saveStateToMemento() {
		return new Memento(state);
	}// saveStateToMemento

	public void getStateFromMemento(Memento memento) {
		state = memento.getState();
	}// getStateFromMemento

}// class Originator
