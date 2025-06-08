package com.darkbrook.library.command.basic.realtime;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class TimeHandler {
	
	private static int lastTime;
	private static int currentTime;
	public static boolean isRealTime = true;
	
	public static void enableRealTime() {
		
		UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
				
				if(!isRealTime) return;
				
				currentTime = getMinecraftTime();
							
				if(currentTime != lastTime) {
					
					for(World world : Bukkit.getWorlds()) {
						world.setTime(currentTime);
					}
					
					lastTime = currentTime;
					
				}
								
			}
			
		}, 0, 20);
		
	}
	
	@SuppressWarnings("deprecation")
	public static int getHour() {
		return Calendar.getInstance().getTime().getHours();
	}
	
	@SuppressWarnings("deprecation")
	public static int getMinute() {
		return Calendar.getInstance().getTime().getMinutes();
	}
	
	@SuppressWarnings("deprecation")
	public static int getSecond() {
		return Calendar.getInstance().getTime().getSeconds();
	}
	
	public static String getDisplayHour() {
		return getHour() < 10 ? "0" + getHour() : "" + getHour();
	}
	
	public static String getDisplayMinute() {
		return getMinute() < 10 ? "0" + getMinute() : "" + getMinute();
	}
	
	public static String getDisplaySecond() {
		return getSecond() < 10 ? "0" + getSecond() : "" + getSecond();
	}
	
	public static String getAmericanDisplayHour() {
		int hour = getHour() > 12 ? getHour() - 12 : getHour() > 0 ? getHour() : 12;
		return hour < 10 ? "0" + hour : "" + hour;
	}
	
	public static String getAmOrPm() {
		return getHour() < 12 ? "AM" : "PM";
	}
	
	public static String getTimeStamp() {
		return getDisplayHour() + ":" + getDisplayMinute() + ":" + getDisplaySecond();
	}
	
	public static String getAmericanTimeStamp() {
		return getAmericanDisplayHour() + ":" + getDisplayMinute() + ":" + getDisplaySecond() + " " + getAmOrPm();
	}
	
	private static String simplify(int in, int amount) {
		return in % amount > 9 ? "" + in % amount : "0" + in % amount;
	}
	
	public static String convertSecondsToTimeStamp(int seconds) {
		return simplify(seconds / 3600, 3600) + ":" + simplify(seconds / 60, 60) + ":" + simplify(seconds, 60);
	}
	
	public static int getMinecraftTime() {
		
		double hour = getHour();
		hour = hour < 6 ? (18000 + (hour * 1000)) : (-6000 + (hour * 1000));
		
		double minute = getMinute();
		minute = minute * 16.7;

		double second = getSecond();
		second = second * 0.27825;
		
		return (int) (hour + minute + second);
		
	}

}
