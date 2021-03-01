package com.crd.carrental.utils;

import java.sql.Timestamp;

/**********************************************************************************************************************
 * A collection of date and time helper methods. Utility classes should not have a public or default constructor.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class DateAndTimeUtil {

    /**
     * Converts a DateTime-Local into a Timestamp.
     * @param dateTimeLocal The Date-Time Local represents the desired start or end of the customers reservation
     *                      request.
     * @return A Timestamp representing the start or end of the customers reservation request.
     */
    public static Timestamp convertDateAndTime(String dateTimeLocal) {
        return Timestamp.valueOf(dateTimeLocal.replaceAll("T", " ") + ":00");
    }

    /**
     *  A valid reservation request can only happen if the request is in the future and the reservation start date is
     *  before the reservation end date.
     */
    public static boolean isStartAndEndValid(Timestamp start, Timestamp end) {
        Timestamp current = Timestamp.valueOf(new Timestamp(System.currentTimeMillis()).toString());

        return start.compareTo(end) <= 0 && current.compareTo(start) <= 0;
    }
}
