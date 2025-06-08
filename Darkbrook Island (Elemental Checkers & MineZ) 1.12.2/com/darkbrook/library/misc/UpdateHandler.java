package com.darkbrook.library.misc;

import org.bukkit.Bukkit;

import com.darkbrook.library.plugin.PluginGrabber;

public class UpdateHandler {
	
	public interface UpdateListener {
		public void onUpdate();
	}
	
	public interface ThreadListener {
		public void onUpdate(Thread thread);
	}
	
	public static int repeat(UpdateListener listener, long delay, long period) {

		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PluginGrabber.getPlugin(), new Runnable() {

			@Override
			public void run() {
				listener.onUpdate();				
			}
			
		}, delay, period);
		
		return id;
		
	}
	
	public static int delay(UpdateListener listener, long delay) {
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PluginGrabber.getPlugin(), new Runnable() {

			@Override
			public void run() {
				listener.onUpdate();				
			}
			
		}, delay);
					
		return id;
		
	}
	
	public static void cancle(int id) {
		Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	public static void thread(ThreadListener listener) {
		new UpdaterThread(listener).start();
	}
	
	public static class UpdaterThread extends Thread {
	
		private ThreadListener listener;
		
		public UpdaterThread(ThreadListener listener) {
			this.listener = listener;
		}
		
		@Override
		public void run() {
			listener.onUpdate(this);
		}
		
	}
	
}
