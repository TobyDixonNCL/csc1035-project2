package csc1035.project2;

import javax.persistence.*;

@Entity(name = "Bookings")
public class Bookings {

    //create variables
    @Id
    @Column(updatable = false, nullable = false)
    private String BookingID;

    @Column
    private String Duration;

    @Column
    private String ModuleID;

    @Column
    private String RoomID;

    @Column
    private String SociallyDistanced;

    @Column
    private String Time;

    public Bookings(String bookingID, String duration, String moduleID, String roomID, String sociallyDistanced, String time) {
        BookingID = bookingID;
        Duration = duration;
        ModuleID = moduleID;
        RoomID = roomID;
        SociallyDistanced = sociallyDistanced;
        Time = time;
    }

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getModuleID() {
        return ModuleID;
    }

    public void setModuleID(String moduleID) {
        ModuleID = moduleID;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getSociallyDistanced() {
        return SociallyDistanced;
    }

    public void setSociallyDistanced(String sociallyDistanced) {
        SociallyDistanced = sociallyDistanced;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
