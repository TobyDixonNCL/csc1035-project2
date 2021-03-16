package csc1035.project2;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomBooking {

    public static List<RoomDetails> GetRoomList() {
        Session session;
        List<RoomDetails> roomList = new ArrayList<>();
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        try {
            //create list of rooms
            List dbRead = session.createQuery("FROM Rooms").list();

            //loop and add each record to an arraylist
            //parse to RoomDetails object
            roomList.addAll(dbRead);


        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return roomList;
    }

    //A way of producing a timetable for a room
    //Make a list of every booking for a room and compile them together into a timetable
    public static void ProduceRoomTimetable(BufferedReader consoleReader) throws IOException {

        List<RoomDetails> roomList = GetRoomList();

        //asks the user for a room to make the timetable for
        System.out.printf("%nChoose a room to get the timetable for:");
        RoomDetails chosenRoom = ChooseRoom(consoleReader, roomList);



        //for (Booking x: BookingList) {
        System.out.println("Details of X");

    }


    public static void UpdateRoomDetails(BufferedReader consoleReader) {

    }

    public static RoomDetails ChooseRoom(BufferedReader consoleReader, List<RoomDetails> roomList) throws IOException {
        RoomDetails chosenRoom = null;
        //for each room in room list, print the room name and its position in the array
        int loop = 0;
        for (RoomDetails x : roomList) {
            System.out.println(loop + ": " + x.getRoomID() + " " + x.getType());
            loop++;
        }

        //Gets an input from the user, then gets the room in the same array position
        int choice;
        boolean stopLoop = false;
        while (!stopLoop) {
            //Parses user input as an integer. May need validation for catching non-integer inputs.
            choice = Integer.parseInt(consoleReader.readLine());

            if ((choice >= 0) && (choice <= loop)) {
                chosenRoom = roomList.get(choice);
                stopLoop = true;
            }
        }

        return chosenRoom;
    }

}
