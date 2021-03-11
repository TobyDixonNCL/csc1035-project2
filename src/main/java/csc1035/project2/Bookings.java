package csc1035.project2;

import javax.persistence.*;
import java.time.*;
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

    public Bookings(String bookingID, boolean sociallyDistanced, LocalDateTime time, LocalTime duration, Set<Students> students, Set<Staff> staff, Modules module, Rooms rooms) {
        this.bookingID = bookingID;
        this.sociallyDistanced = sociallyDistanced;
        this.time = time;
        this.duration = duration;
        this.students = students;
        this.staff = staff;
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
    @ManyToMany
    @JoinTable(
            name = "StudentTimetable",
            joinColumns = {@JoinColumn(name = "BookingID")},
            inverseJoinColumns = {@JoinColumn(name = "StudentID")}
    )
    private Set<Students> students = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "StaffTimetable",
            joinColumns = {@JoinColumn(name = "BookingID")},
            inverseJoinColumns = {@JoinColumn(name = "StaffID")}
    )
    private Set<Staff> staff = new HashSet<>();

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

    public Set<Students> getStudents() {
        return students;
    }

    public void setStudents(Set<Students> students) {
        this.students = students;
    }

    public Set<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Set<Staff> staff) {
        this.staff = staff;
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
}
