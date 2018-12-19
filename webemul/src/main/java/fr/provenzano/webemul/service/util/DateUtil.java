package fr.provenzano.webemul.service.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtil {


    private DateUtil() {
    }

    public static Date setDate(Date date, int dayShift, int hours, int minutes) {
    	Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayShift);        
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE,minutes);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
        
    }
    
    public static int getDayOfWeek(Date date) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.DAY_OF_WEEK);
    }
    
}
