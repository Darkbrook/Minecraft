package com.darkbrook.library.event.tick.sync;

import com.darkbrook.library.event.DarkbrookEvent;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.runnable.ConstantRunnable;
import com.darkbrook.library.util.runnable.RunnableState;

public class TickCycleEvent extends DarkbrookEvent implements IRegistryValue
{
	
	public TickCycleEvent()
	{		
		ConstantRunnable.execute(() -> register(), RunnableState.SYNC, 20, 0);
	}

}