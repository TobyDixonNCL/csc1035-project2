package csc1035.project2;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the bookings table in the database. It establishes a many to many relationship with the rooms class, as well as a many to one relationship with the school class.
 * Objects of this class represent a row in the table.
 * @author Adam Winstanley
 */
@Entity
@Table(name = "Bookings")
public class Bookings {

    // Constructors
    public Bookings() { }

    public Bookings(String bookingID, boolean sociallyDistanced, LocalDateTime time, LocalTime duration) {
        this.bookingID = bookingID;
        this.sociallyDistanced = sociallyDistanced;
        this.time = time;
        this.duration = duration;
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
    @JoinColumn(name = "SchoolID")
    private Schools school;

    @ManyToMany
    @JoinTable(
            name = "BookingsRooms",
            joinColumns = {@JoinColumn(name = "BookingID")},
            inverseJoinColumns = {@JoinColumn(name = "RoomID")}
    )
    private Set<Rooms> rooms = new HashSet<>();


    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public Schools getCls() {
        return school;
    }

    public void setCls(Schools school) {
        this.school = school;
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

    public Set<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Rooms> rooms) {
        this.rooms = rooms;
    }

    public Schools getSchool() {
        return school;
    }

    public void setSchool(Schools school) {
        this.school = school;
    }
}