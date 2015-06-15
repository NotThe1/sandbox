package sandbox;

import java.util.EventListener;

public interface MemoryListener extends EventListener {
	void protectedMemoryAccess(MemoryEvent me);
	void invalidMemoryAccess(MemoryEvent me);

}
