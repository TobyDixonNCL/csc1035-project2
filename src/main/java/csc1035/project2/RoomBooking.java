package csc1035.project2;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomBooking {

    public static List<Rooms> GetRoomList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Rooms> roomList = new ArrayList<>();
        session.beginTransaction();

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            //create list of rooms
            List dbRooms = session.createQuery("FROM Rooms").list();

            //parses database records as Rooms objects and adds them to the room list.
            roomList.addAll(dbRooms);


        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return roomList;
    }

    public static List<Bookings> GetBookingList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Bookings> bookingList = new ArrayList<>();

        session.beginTransaction();

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            //create list of bookings
            List dbBookings = session.createQuery("FROM Bookings").list();

            //parses database records as Bookings objects and adds them to the room list.
            bookingList.addAll(dbBookings);


        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return bookingList;
    }


    //A way of producing a timetable for a room
    //Make a list of every booking for a room and compile them together into a timetable
    public static void ProduceRoomTimetable(BufferedReader consoleReader) throws IOException {

        List<Rooms> roomList = GetRoomList();

        List<Bookings> bookingList = GetBookingList();

        //asks the user for a room to make the timetable for
        System.out.printf("%nChoose a room to get the timetable for:");
        Rooms chosenRoom = ChooseRoom(consoleReader, roomList);
        //gets that room's ID
        String chosenID = chosenRoom.getRoomID();

        for (Bookings x : bookingList) {
            if (x.getBookingID().equals(chosenID))
            {
                System.out.println("Booking " + x.getBookingID() + " on " + x.getTime() + " for " + x.getDuration());
            }
        }


    }


    public static void UpdateRoomDetails(BufferedReader consoleReader) throws IOException {

        List<Rooms> roomList = GetRoomList();

        System.out.println("Input Room ID");
        String ID = consoleReader.readLine();
        System.out.println("Input Room Max. Capacity");
        int maxNormal =  Integer.parseInt(consoleReader.readLine());
        System.out.println("Input Room Max. Capacity (Social Distancing)");
        int maxCovid = Integer.parseInt(consoleReader.readLine());
        System.out.println("Input Room Description");
        String type = consoleReader.readLine();



        Session session = HibernateUtil.getSessionFactory().openSession();

        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //get row with same ID from Rooms table
            Rooms updateRoom = (session.get(Rooms.class, ID));
            //set the new values
            updateRoom.setMaxCapacity(maxNormal);
            updateRoom.setSocialDistancingCapacity(maxCovid);
            updateRoom.setType(type);
            session.update(updateRoom);
            session.getTransaction().commit();
            System.out.println("Updated details.");

        } catch (HibernateException e) {
            if (session!=null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public static Rooms ChooseRoom(BufferedReader consoleReader, List<Rooms> roomList) throws IOException {
        Rooms chosenRoom = null;
        //for each room in room list, print the room name and its position in the array
        int loop = 0;
        for (Rooms x : roomList) {
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
            else {
                System.out.println("Invalid selection");
            }
        }

        return chosenRoom;
    }

}
