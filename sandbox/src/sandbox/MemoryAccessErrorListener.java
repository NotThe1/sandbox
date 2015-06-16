package sandbox;

import java.util.EventListener;

public interface MemoryAccessErrorListener extends EventListener {
	void memoryAccessError(MemoryAccessErrorEvent me);
}// MemoryErorListener
