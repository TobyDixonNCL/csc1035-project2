package csc1035.project2;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class RoomBooking {

    //A way of producing a timetable for a room
    //Make a list of every booking for a room and compile them together into a timetable
    public static void ProduceRoomTimetable(BufferedReader consoleReader) throws IOException {

        IController<Rooms> roomController= new Controller<>();
        List<Rooms> roomsList = roomController.readAll(Rooms.class);

        IController<Bookings> bookingsController = new Controller<>();
        List<Bookings> bookingsList = bookingsController.readAll(Bookings.class);

        //asks the user for a room to make the timetable for
        System.out.printf("%nChoose a room to get the timetable for:");
        Rooms chosenRoom = ChooseRoom(consoleReader, roomsList);
        //gets that room's ID
        String chosenID = chosenRoom.getRoomID();

        for (Bookings x : bookingsList) {
            if (x.getBookingID().equals(chosenID))
            {
                System.out.println("Booking " + x.getBookingID() + " on " + x.getTime() + " for " + x.getDuration());
            }
        }


    }


    public static void UpdateRoomDetails(BufferedReader consoleReader) throws IOException {

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
