package csc1035.project2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * This is a class which represents the Rooms table in the database, it establishes a many to many relationship with the bookings class.
 * Objects of this class represent a row in the table.
 * @author Adam Winstanley
 */
@Entity
@Table(name = "Rooms")
public class Rooms {
    // Constructors
    public Rooms() {}

    public Rooms(String roomID, String type, int maxCapacity, int socialDistancingCapacity) {
        this.roomID = roomID;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.socialDistancingCapacity = socialDistancingCapacity;
    }

    // Set up the columns
    @Id
    @Column(name = "RoomID", length = 6)
    private String roomID;

    @Column(name = "Type", length = 30)
    private String type;

    @Column(name = "MaxCapacity")
    private int maxCapacity;

    @Column(name = "SocialDistancingCapacity")
    private int socialDistancingCapacity;

    // Relationships
    @ManyToMany(mappedBy = "rooms")
    private Set<Bookings> bookings = new HashSet<>();

    // Getters and setters
    public void setRoomID(String RoomID) {
        this.roomID = RoomID;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSocialDistancingCapacity() {
        return socialDistancingCapacity;
    }

    public void setSocialDistancingCapacity(int socialDistancingCapacity) {
        this.socialDistancingCapacity = socialDistancingCapacity;
    }

    public Set<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Bookings> bookings) {
        this.bookings = bookings;
    }
}
