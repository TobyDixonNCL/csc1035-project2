package csc1035.project2;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class RoomBooking {

    //A way of producing a timetable for a room
    //Make a list of every booking for a room and compile them together into a timetable
    public static void ProduceRoomTimetable(BufferedReader consoleReader) {
        //TODO: This is all commented out for now since it won't work until I can get the
        //actual room list, which is being worked on by another member of the team.

        //TODO: have it get the actual room list, may be able to add it in as an argument instead
        //List<Room> roomList;

        //asks the user for a room to make the timetable for
        //System.out.printf("%nChoose a room to get the timetable for:");
        //chosenRoom = ChooseRoom(consoleReader);

        //Gets the list of bookings from the selected room, then prints the details of each individual booking.
        //List BookingList = chosenRoom.getBookingList

        //for (Booking x: BookingList) {
        //System.out.println("Details of X");

        }



    public static void UpdateRoomDetails(BufferedReader consoleReader) {

    }



    //TODO: also add the roomlist as an argument
    public static void ChooseRoom(BufferedReader consoleReader)
    {
        //Room chosenRoom = null;

        //for each room in roomlist, print the room name and its position in the array
        //NOTE: Arrays start at 0, this has the options start from 1 purely for aesthetic reasons, just make sure
        //this is accounted for when getting the user choice or change it entirely.

        //int loop = 0;
        //for (Room x:roomList) {
        //loop++;
        //String roomName = x.getName();
        //System.out.printf(loop + ": " + roomName);
        //}


        //Gets an input from the user, then gets the room in the same array position
        //int choice = 0;
        //while (choice is not invalid) {
            //Parses user input as an integer. May need validation for catching non-integer inputs.
            //choice = Integer.parseInt(consoleReader.readLine());
            //choice = choice - 1; //because we incremented it by 1 when writing out room names

            //chosenRoom = roomList[choice]
        //}

        //}
        //return chosenRoom;
    }

}
