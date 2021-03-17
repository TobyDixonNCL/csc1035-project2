package csc1035.project2;

import java.sql.Time;

/**
 * This class is for the confirmation and is used for proper formatting of the confirmation
 * @Author Jake Wilson
 */

public class Confirmation {
    private String bookingID;
    private String time;
    private String duration;
    private String roomID;

    public Confirmation(String bookingID, String time, String duration, String roomID) {
        this.bookingID = bookingID;
        this.time = time;
        this.duration = duration;
        this.roomID = roomID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

    public String getRoomID() {
        return roomID;
    }


    //Formats all the information so it is ready to be returned in an easily readable way
    public String format() {
        String f = "BookingID: " + this.getBookingID() + "\nTime: " + this.getTime() + "\nDuration: " + this.getDuration() + "\nRoomID: " + this.getRoomID();
        return f;
    }
}
