package csc1035.project2;

import javax.persistence.*;


/**
 * This is a class which represents the Rooms table in the database. Objects of this class represent a row in the table.
 * @author Adam Winstanley
 */
@Entity
@Table(name = "Rooms")
public class Rooms {

    // Set up the columns
    @Id
    @Column(name = "RoomID")
    private String roomID;

    @Column(name = "Type")
    private String type;

    @Column(name = "MaxCapacity")
    private int maxCapacity;

    @Column(name = "SocialDistancingCapacity")
    private int socialDistancingCapacity;

    // Getters and setters

    public void setRoomID(String RoomID) {
        this.roomID = RoomID;
    }

    @Id
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

    // Temporary toString for debugging purposes, delete later.
    @Override
    public String toString() {
        return "Rooms{" +
                "roomID='" + roomID + '\'' +
                ", type='" + type + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", socialDistancingCapacity=" + socialDistancingCapacity +
                '}';
    }
}
