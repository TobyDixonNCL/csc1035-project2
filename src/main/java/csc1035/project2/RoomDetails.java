package csc1035.project2;

public class RoomDetails {

    //create variables
    private String roomID;
    private int maxCapacity;
    private int socialDistancingCapacity;
    private String type;

    //declare class
    public RoomDetails(String roomID, int maxCapacity, int socialDistancingCapacity, String type) {
        this.roomID = roomID;
        this.maxCapacity = maxCapacity;
        this.socialDistancingCapacity = socialDistancingCapacity;
        this.type = type;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
