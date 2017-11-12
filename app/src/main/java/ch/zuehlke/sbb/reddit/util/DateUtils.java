package ch.zuehlke.sbb.reddit.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by chsc on 11.11.17.
 */

public class DateUtils {



    public static String friendlyTime(long created){
        double sec = Math.floor(((new Date().getTime()/1000) - created));
        double interval = Math.floor(sec / 31536000);


        if (interval >= 1) {
            if(interval == 1) return interval + " year ago";
            else
                return interval + " years ago";
        }
        interval = Math.floor(sec / 2592000);
        if (interval >= 1) {
            if(interval == 1) return interval + " month ago";
            else
                return interval + " months ago";
        }
        interval = Math.floor(sec / 86400);
        if (interval >= 1) {
            if(interval == 1) return interval + " day ago";
            else
                return interval + " days ago";
        }
        interval = Math.floor(sec / 3600);
        if (interval >= 1) {
            if(interval == 1) return interval + " hour ago";
            else
                return interval + " hours ago";
        }
        interval = Math.floor(sec / 60);
        if (interval >= 1) {
            if(interval == 1) return interval + " minute ago";
            else
                return interval + " minutes ago";
        }
        return Math.floor(sec) + " seconds ago";

    }
}
