package com.darkbrook.library.event;

public class CancelableEvent extends DarkbrookEvent
{

	private boolean isCanceled;
	
	public void cancel() 
	{
		isCanceled = true;
	}
	
	public static boolean isCanceled(CancelableEvent event)
	{
		return ((CancelableEvent) event.register()).isCanceled;
	}
	
}
