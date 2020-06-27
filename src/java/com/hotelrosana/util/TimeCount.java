package com.hotelrosana.util;

/**
 * Class that accepts seconds and formats them into
 * counts like seconds, minutes, hours and so on.
 */
public class TimeCount {
    private long seconds, minutes, hours, days, weeks, months, years;
    private String timeCount;
    
    private void setSeconds(long seconds) {
        this.seconds = seconds;
    }
    
    private String calculateTime() {
	if (Math.floor(seconds / 60) < 1) {
            if(seconds == 1)
		timeCount = "1 second";
            else
		timeCount = seconds + " seconds";
	}
	
        if (Math.floor(seconds / 60) >= 1) {
            minutes = (int) Math.floor(seconds / 60);
            if (minutes == 1)
		timeCount = "1 minute";
            else
		timeCount = minutes + " minutes";
        }
        
        if (Math.floor(seconds / 3600) >= 1) {
            hours = (int) Math.floor(seconds / 3600);
            if (hours == 1)
		timeCount = "1 hour";
            else
		timeCount = hours + " hours";
        }
        
        if (Math.floor(seconds / 86400) >= 1) {
            days = (int) Math.floor(seconds / 86400);
            if (days == 1)
		timeCount = "1 day";
            else
		timeCount = days + " days";
        }
        
        if (Math.floor(seconds / 604800) >= 1) {
            weeks = (int) Math.floor(seconds / 604800);
            if (weeks == 1)
		timeCount = "1 week";
            else
		timeCount = weeks + " weeks";
        }
        
        if (Math.floor(seconds / 2592000) >= 1) {
            months = (int) Math.floor(seconds / 2592000);
            if (months == 1)
		timeCount = "1 month";
            else
		timeCount = months + " months";
        }
        
        if (Math.floor(seconds / 31536000) >= 1) {
            years = (int) Math.floor(seconds / 31536000);
            if (years == 1)
		timeCount = "1 year";
            else
		timeCount = years + " years";
        }
        return timeCount;
    }
    
    /**
     * @return Calculated time.
     */
    public String getTime() {
	calculateTime();
	return timeCount;
    }
    
     public TimeCount(long seconds) {
        setSeconds(seconds);
    }
}