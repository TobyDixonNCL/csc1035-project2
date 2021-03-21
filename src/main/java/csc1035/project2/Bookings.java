package csc1035.project2;

import javax.persistence.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the bookings table in the database. It establishes a many to many relationship with the rooms class, as well as a many to one relationship with the school class.
 * Objects of this class represent a row in the table.
 */
@Entity
@Table(name = "Bookings")
public class Bookings {

    // Constructors
    public Bookings() { }

    public Bookings(boolean sociallyDistanced, LocalDateTime time, LocalTime duration, Modules module, Rooms rooms) {
        this.bookingID = module.getModuleID() + "." + rooms.getRoomID() + "." + time.format(DateTimeFormatter.ofPattern("yyyyMMdd_hhmm"));
        this.sociallyDistanced = sociallyDistanced;
        this.time = time;
        this.duration = duration;
        this.module = module;
        this.rooms = rooms;
    }

    // Set up columns
    @Id
    @Column(name = "BookingID", length = 100)
    private String bookingID;

    @Column(name = "SociallyDistanced")
    private boolean sociallyDistanced;

    @Column(name = "Time")
    private LocalDateTime time;

    @Column(name = "Duration")
    private LocalTime duration;


    // Relationships
    @ManyToOne
    @JoinColumn(name = "ModuleID")
    private Modules module;

    @ManyToOne
    @JoinColumn(name = "RoomID")
    private Rooms rooms;


    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingID() {
        return bookingID;
    }


    public boolean isSociallyDistanced() {
        return sociallyDistanced;
    }

    public void setSociallyDistanced(boolean sociallyDistanced) {
        this.sociallyDistanced = sociallyDistanced;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Modules getModule() {
        return module;
    }

    public void setModule(Modules module) {
        this.module = module;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }


    public LocalDateTime getEnd() {
        return time.plusHours(duration.getHour()).plusMinutes(duration.getMinute());
    }

    // Methods

    /**
     * Tests whether another booking conflicts with this booking.
     * @param b the booking which is being checked against.
     * @return false in the case that the booking does not share any rooms, or that the time does not conflict. True in any other case.
     */
    public boolean conflictsWith(Bookings b) {
        if (rooms == b.getRooms()) {
            return time.isBefore(b.getEnd()) && getEnd().isAfter(b.getEnd()) || time.isEqual(b.getTime()) || time.isBefore(b.getTime()) && getEnd().isAfter(b.getTime());
        }
        return false;
    }

    /**
     *Method to format the data for proper use in the confirmation
     * @Author Jake Wilson
     */
    //Formats all the information so it is ready to be returned in an easily readable way
    public String confirmation() {
        String f = "BookingID: " + this.getBookingID() + "\nTime: " + this.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\nDuration: " + this.getDuration();
        return f;
    }
    public List<Rooms> availableRooms() {
        List<Rooms> available = new ArrayList<>();
        IController<Rooms> controllerObject = new Controller<>();
        Rooms f = controllerObject.readById(Rooms.class, this.rooms.getRoomID(), true);

        return available;
    }
}
