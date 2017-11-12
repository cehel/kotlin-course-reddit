package ch.zuehlke.sbb.reddit.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by chsc on 11.11.17.
 */

public class DateUtils {

    public static String toFriendlyTime(long dateValue){
        Date dateTime = new Date(dateValue * 1000);
        StringBuffer sb = new StringBuffer();
        Date current = Calendar.getInstance().getTime();
        int diffInSeconds = (int)((current.getTime() - dateTime.getTime())/1000);

        int sec,min,hrs,days,months,years;

        if(diffInSeconds >= 60){
            sec = diffInSeconds % 60;
        }else{
            sec = diffInSeconds;
            diffInSeconds /= 60;
        }

        if(diffInSeconds >= 60){
            min = diffInSeconds % 60;
        }else{
            min = diffInSeconds;
            diffInSeconds /= 60;
        }

        if(diffInSeconds >= 24){
            hrs = diffInSeconds % 24;
        }else{
            hrs = diffInSeconds;
            diffInSeconds /= 24;
        }

        if(diffInSeconds >= 30){
            days = diffInSeconds % 30;
        }else{
            days = diffInSeconds;
            diffInSeconds /= 30;
        }

        if(diffInSeconds >= 12){
            months = diffInSeconds % 12;
        }else{
            months = diffInSeconds;
            diffInSeconds /= 12;
        }
        years = diffInSeconds;


        if (years > 0) {
            if (years == 1) {
                sb.append("a year");
            } else {
                sb.append(years+" years");
            }
            if (years <= 6 && months > 0) {
                if (months == 1) {
                    sb.append(" and a month");
                } else {
                    sb.append(" and "+months +" months");
                }
            }
        } else if (months > 0) {
            if (months == 1) {
                sb.append("a month");
            } else {
                sb.append(months+" months");
            }
            if (months <= 6 && days > 0) {
                if (days == 1) {
                    sb.append(" and a day");
                } else {
                    sb.append(" and "+days+" days");
                }
            }
        } else if (days > 0) {
            if (days == 1) {
                sb.append("a day");
            } else {
                sb.append(days+" days");
            }
            if (days <= 3 && hrs > 0) {
                if (hrs == 1) {
                    sb.append(" and an hour");
                } else {
                    sb.append(" and "+hrs+" hours");
                }
            }
        } else if (hrs > 0) {
            if (hrs == 1) {
                sb.append("an hour");
            } else {
                sb.append(hrs+" hours");
            }
            if (min > 1) {
                sb.append(" and "+min+" minutes");
            }
        } else if (min > 0) {
            if (min == 1) {
                sb.append("a minute");
            } else {
                sb.append(min+" minutes");
            }
            if (sec > 1) {
                sb.append(" and "+sec+" seconds");
            }
        } else {
            if (sec <= 1) {
                sb.append("about a second");
            } else {
                sb.append("about "+sec+" seconds");
            }
        }

        sb.append(" ago");

        return sb.toString();

    }
}
