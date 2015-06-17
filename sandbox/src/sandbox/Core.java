package sandbox;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Core {
	private byte[] storage;
	private int maximumAddress; // maximum address
	private int protectedBoundary; // highest location in protected area
	private boolean trapEnabled;
	private HashMap<Integer, TRAP> trapLocations; // locations that can be
													// trapped duh!

	public Core(Integer size) {
		this(size, 0);
	}// Constructor

	public Core(Integer size, Integer protectedBoundary) {
		this.trapEnabled = false;
		trapLocations = new HashMap<Integer, TRAP>();
		if (size <= 0) {
			System.err.printf("Memory size %d not valid%n", size);
			System.exit(-1);
		}// if

		storage = new byte[size];
		maximumAddress = storage.length - 1;

		if ((protectedBoundary >= maximumAddress) || (protectedBoundary < 0)) {
			String errorMessage = String
					.format("Invalid protected highest location: 0X%1$04X - Decimal %1$d   %n"
							+ "  Memory's highest location is:     0X%2$04X - Decimal %2$d%n"
							+ "   - will set to 0 - No protected Memory",
							protectedBoundary, maximumAddress);
			JOptionPane.showMessageDialog(null, errorMessage,
					"Setting up Main Memory", JOptionPane.ERROR_MESSAGE);

			protectedBoundary = 0;
		}// if
		this.protectedBoundary = protectedBoundary;
	}// Constructor

	public Core() {
		this(MAXIMUM_MEMORY, 0);
	}// Constructor

	public void write(int location, byte value) {
		if (checkAddress(location) == true) {
			storage[location] = value;
		}// if
	}// setContent

	public byte read(int location) {
		if (checkAddress(location) == true) {
			return storage[location];
		}// if
		return 00;
	}// getContent

	public int getSize() {
		return storage.length;
	}// getSize

	public int getProtectedBoundary() {
		return protectedBoundary;
	}//getProtectedBoundary
	
	public void setTrapEnabled(boolean state){
		this.trapEnabled = state;
	}//enableTrap
	
	public boolean isTrapEnabled(){
		return this.isTrapEnabled();
	}
	
	//public boolean

	public void addTrapLocation(int location, TRAP trap) {
		if (!trapLocations.containsKey(location)) { // Not trapped yet
			if (checkAddress(location) == false) {
				return; // bad address - get out of here
			} // inner if
		}// if
		trapLocations.put(location, trap);

		// do nothing if bad address
	}// addTrapLocation

	public void removeTrapLocation(int location, TRAP trap) {
		// only remove if the trap is the same type
		trapLocations.remove(location, trap);

	}// removeTrapLocation

	private boolean checkAddress(int location) {
		boolean checkAddress = true;
		if (location < protectedBoundary) {
			// protection violation
			fireAccessError(location, "Protected memory access");
			checkAddress = false;
		} else if (location > maximumAddress) {
			// out of bounds error
			fireAccessError(location, "Invalid memory location");
			checkAddress = false;
		} else if (trapEnabled & trapLocations.containsKey(location)) {
			fireMemoryTrap(location, (trapLocations.get(location)));
			checkAddress = true;
		}// if

		return checkAddress; // true if all is good or a Trap
	}// checkAddress

	private void fireAccessError(int location, String errorType) {
		Vector<MemoryAccessErrorListener> mael;
		synchronized (this) {
			mael = (Vector<MemoryAccessErrorListener>) memoryAccessErrorListeners
					.clone();
		}// sync
		int size = mael.size();
		if (0 == size) {
			return; // no listeners
		}// if
		MemoryAccessErrorEvent memoryAccessErrorEvent = new MemoryAccessErrorEvent(
				this, location, errorType);
		for (int i = 0; i < size; i++) {
			MemoryAccessErrorListener listener = (MemoryAccessErrorListener) mael
					.elementAt(i);
			listener.memoryAccessError(memoryAccessErrorEvent);
		}// for
	}// fireProtectedMemoryAccess

	private Vector<MemoryAccessErrorListener> memoryAccessErrorListeners = new Vector<MemoryAccessErrorListener>();

	public synchronized void addMemoryAccessErrorListener(
			MemoryAccessErrorListener mael) {
		if (memoryAccessErrorListeners.contains(mael)) {
			return; // Already here
		}// if
		memoryAccessErrorListeners.addElement(mael);
	}// addMemoryListener

	public synchronized void removeMemoryAccessErrorListener(
			MemoryAccessErrorListener mael) {
		memoryAccessErrorListeners.remove(mael);
	}// removeMemoryListener

	private void fireMemoryTrap(int location, TRAP trap) {
		Vector<MemoryTrapListener> mtl;
		synchronized (this) {
			mtl = (Vector<MemoryTrapListener>) memoryTrapListeners.clone();

			int size = mtl.size();
			if (0 == size) {
				return; // no listeners
			}// if
			MemoryTrapEvent memoryTrapEvent = new MemoryTrapEvent(this,
					location, trap);
			for (int i = 0; i < size; i++) {
				MemoryTrapListener listener = (MemoryTrapListener) mtl
						.elementAt(i);
				listener.memoryTrap(memoryTrapEvent);
			}// for
		}// sync
	}// fireProtectedMemoryAccess

	private Vector<MemoryTrapListener> memoryTrapListeners = new Vector<MemoryTrapListener>();

	public synchronized void addMemoryTrapListener(MemoryTrapListener mtl) {
		if (memoryTrapListeners.contains(mtl)) {
			return; // Already here
		}// if
		memoryTrapListeners.addElement(mtl);
	}// addMemoryListener

	public synchronized void removeMemoryTrapListener(MemoryTrapListener mtl) {
		memoryTrapListeners.remove(mtl);
	}// removeMemoryListener

	public enum TRAP {
		IO, DEBUG
	}

	static int K = 1024;
	static int PROTECTED_MEMORY = 0; // 100;
	static int MINIMUM_MEMORY = 8 * K;
	static int MAXIMUM_MEMORY = 64 * K;
	static int DEFAULT_MEMORY = 16 * K;

}// class Mem
