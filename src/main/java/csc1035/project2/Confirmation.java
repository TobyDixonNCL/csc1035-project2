package csc1035.project2;

import java.sql.Time;

public class Confirmation {
    private String bookingID;
    private Time time;
    private String duration;
    private String roomID;

    public Confirmation(String bookingID, Time time, String duration, String roomID) {
        this.bookingID = bookingID;
        this.time = time;
        this.duration = duration;
        this.roomID = roomID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public Time getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

    public String getRoomID() {
        return roomID;
    }


}
