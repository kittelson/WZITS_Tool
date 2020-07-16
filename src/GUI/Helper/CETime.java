package GUI.Helper;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * This class is used to store analysis period time and perform basic
 * modification of the time.
 *
 * @author Shu Liu
 */
public class CETime implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5345236644L;

    /**
     * Hour of the time
     */
    public int hour;

    /**
     * Minute of the time
     */
    public int minute;

    /**
     * Default Constructor (00:00)
     */
    public CETime() {
        hour = 0;
        minute = 0;
    }

    /**
     * Constructor with hour only
     *
     * @param hour hour the time
     */
    public CETime(int hour) {
        this.hour = hour;
    }

    /**
     * Constructor with hour and minute
     *
     * @param hour hour of the time
     * @param minute minute of the time
     */
    public CETime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Convert time to number of minutes from 00:00
     *
     * @return number of minutes from 00:00
     */
    public int toMinute() {
        return hour * 60 + minute;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("00");
        return hour + ":" + formatter.format(minute);
    }

    public String toAMPMString() {
        DecimalFormat formatter = new DecimalFormat("00");
        if (hour > 12) {
            return (hour - 12) + ":" + formatter.format(minute) + "pm";
        } else if (hour < 12) {
            return (hour == 0 ? 12 : hour) + ":" + formatter.format(minute) + "am";
        } else {
            return hour + ":" + formatter.format(minute) + "pm";
        }
    }

    public String toHourAMPMString() {
        if (hour == 0) {
            return "12am";
        } else if (hour < 12) {
            return hour + "am";
        } else if (hour > 12) {
            return (hour - 12) + "pm";
        } else {
            return "12pm";
        }
    }

    /**
     * Add two CETime object
     *
     * @param time1 first CETime object
     * @param time2 second CETime object
     * @return sum of the two CETime objects
     */
    static public CETime addTime(CETime time1, CETime time2) {
        return addTime(time1, time2, 1);
    }

    /**
     * Add multiple times of one CETime object to another
     *
     * @param time1 first CETime object
     * @param time2 second CETime object
     * @param numOfTime2 number of the second CETime object to be added
     * @return sum of the CETime objects
     */
    static public CETime addTime(CETime time1, CETime time2, int numOfTime2) {
        int hour = time1.hour + time2.hour * numOfTime2;
        int minute = time1.minute + time2.minute * numOfTime2;

        while (minute < 0) {
            hour--;
            minute += 60;
        }

        hour += minute / 60;
        minute %= 60;

        while (hour < 0) {
            hour += 24;
        }
        hour %= 24;

        return new CETime(hour, minute);
    }

    /**
     * Method to determine if this CETime object falls before another CETime
     * object.
     *
     * @param compareTime
     * @return
     */
    public boolean isBefore(CETime compareTime) {
        return (this.hour < compareTime.hour || (this.hour == compareTime.hour && this.minute < compareTime.minute));
    }

    /**
     * Method to determine if this CETime object falls after another CETime
     * object.
     *
     * @param compareTime
     * @return
     */
    public boolean isAfter(CETime compareTime) {
        return (this.hour > compareTime.hour || (this.hour == compareTime.hour && this.minute > compareTime.minute));
    }

    /**
     * Static method to generate the "fancy" display string for time represented
     * by an analysis period. The value is based on the start time passed to the
     * method. For example, for a start time of 6:00am, and period 4, the
     * returned string would be 7:00am-7:15am
     *
     * @param startTime
     * @param period (indexing starts from 0)
     * @return
     */
    public static String getFancyAPStr(CETime startTime, int period) {
        String t1 = CETime.addTime(startTime, PERIOD_INTERVAL, period).toAMPMString();
        String t2 = CETime.addTime(startTime, PERIOD_INTERVAL, period + 1).toAMPMString();

        return t1 + "-" + t2;
    }

    public static String getTimeRangeAsString(CETime startTime, int period1, int period2) {
        CETime inc = PERIOD_INTERVAL;
        String timeRangeStr = CETime.addTime(startTime, inc, period1).toString();
        timeRangeStr = timeRangeStr + "-";
        timeRangeStr = timeRangeStr + CETime.addTime(startTime, inc, period2).toString();
        return timeRangeStr;
    }

    public static final CETime PERIOD_INTERVAL = new CETime(0, 15);
}
