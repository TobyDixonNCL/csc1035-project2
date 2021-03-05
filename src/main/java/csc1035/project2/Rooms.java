package csc1035.project2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
