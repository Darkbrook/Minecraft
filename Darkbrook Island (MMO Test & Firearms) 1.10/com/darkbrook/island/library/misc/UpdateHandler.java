package com.darkbrook.island.library.misc;

import org.bukkit.Bukkit;

import com.darkbrook.island.References;

public class UpdateHandler {
	
	public interface UpdateListener {
		public void onUpdate();
	}
	
	public static int repeat(UpdateListener listener, long delay, long period) {

		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(References.plugin, new Runnable() {

			@Override
			public void run() {
				listener.onUpdate();				
			}
			
		}, delay, period);
		
		return id;
		
	}
	
	public static int delay(UpdateListener listener, long delay) {
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(References.plugin, new Runnable() {

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
	
	public static void thread(UpdateListener listener) {
		new UpdaterThread(listener).start();
	}
	
	private static class UpdaterThread extends Thread {
	
		private UpdateListener listener;
		
		public UpdaterThread(UpdateListener listener) {
			this.listener = listener;
		}
		
		@Override
		public void run() {
			listener.onUpdate();
		}
		
	}
	
}
