package csc1035.project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Menu {
    public static void main(String[] args) throws IOException {

        // Disable hibernate's logging. (red connection messages are not shown)
        Logger l = Logger.getLogger("org.hibernate");
        l.setLevel(Level.SEVERE);

        //consoleReader is used to read what the user types into the console.
        //consoleReader is also passed to the other menus and methods just so we don't have to redeclare it every time.
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        //prints a welcome message and instructions for the user.
        System.out.printf("%nNUC TIMETABLE SYSTEM%n===============%nMake A Selection:");
        System.out.printf("%n1: Room Booking System%n2: Timetable System%n3:List all rooms/students/staff/modules.%nExit program%n");

        //Gets user choice, uses a while loop and a case switch.
        //If the user does not select one of the valid options (default case), shows an error message and returns to the start
        //of the loop. If the user selects the 'exit' option, the loop is allowed to end.
        //EM - This is the method I usually use for menus but if there is a more efficient option please update this.
        int choice = 0;
        while (choice != 3) {
            // regexChoice is used to check that the string is numbers only
            String regexChoice = Integer.toString(choice);
            if (regexChoice.matches("^[0-9]$")) {
                //Parses user input as an integer. May need validation for catching non-integer inputs.
                choice = Integer.parseInt(consoleReader.readLine());
                //1: Room Booking System
                //2: Timetabling System
                //3: List database
                //4: end program
                switch (choice) {
                    case 1:
                        BookingMenu(consoleReader);
                        break;
                    case 2:
                        TimetableMenu(consoleReader);
                        break;
                    case 3:
                        listMenu(consoleReader);
                        break;
                    case 4:
                        //exit program
                        //doesn't need a command; the program reaches the end and exits automatically
                        //once this loop ends.
                        break;
                    default:
                        System.out.println("Invalid input; input valid choice.");
                }
            } else {
                System.out.println("Invalid input; input valid choice.");
            }
            //when returning to main menu, display choices again.
            if (choice == 1 || choice == 2 || choice == 3) {
                System.out.printf("%nReturned to main menu.%n1:Room Booking System%n2:Timetabling System%n3:List all rooms/students/staff/modules.4:Exit%n");
            }
        }
    }

    //Opens menu for selecting methods from RoomBooking class.
    //Uses same while-loop menu as before.
    static void BookingMenu(BufferedReader consoleReader) throws IOException {

        System.out.printf("%nRoom Booking Menu%n1: Make a reservation%n2: Cancel a reservation%n3:Find available rooms");
        System.out.printf("%n4: Produce a timetable for a room%n5: Update room details%n6: Return to main menu.%n");
        BookingIO bookingIO = new BookingIO();


        int choice = 0;
        while (choice != 6) {
            // regexChoice is used to check that the string is numbers only
            String regexChoice = Integer.toString(choice);
            if (regexChoice.matches("^[0-9]$")) {
                //Parses user input as an integer. May need validation for catching non-integer inputs.
                choice = Integer.parseInt(consoleReader.readLine());
                //1: Make a reservation
                //2: Cancel reservation
                //3: Find available rooms
                //4: Produce a timetable for a room
                //5: Update room details
                //6: Return to main menu
                switch (choice) {
                    case 1:
                        //Make reservation
                        System.out.println("Would you like to make reservations for an entire module's length, or an individual lesson?\n" +
                                " 0: Entire module's length.\n" +
                                " 1: An individual lesson.");
                        String reservationType = consoleReader.readLine();
                        while (!reservationType.matches("^[01]$")) {
                            System.out.println("Please enter 0 or 1");
                            reservationType = consoleReader.readLine();
                        }

                        // Create a reservation of the type which the user selects.
                        if (reservationType.equals("0")) {
                            bookingIO.smartBooking();
                        }
                        else if (reservationType.equals("1")) {
                            bookingIO.manualBooking();
                        }
                        break;
                    case 2:
                        //Cancel reservation
                        System.out.println("Would you like to cancel an individual booking, or all bookings for a module?\n" +
                                " 0: An individual booking.\n" +
                                " 1: All bookings for a module.");
                        String cancellationType = consoleReader.readLine();
                        while (!cancellationType.matches("^[01]$")) {
                            System.out.println("Please enter 0 or 1");
                            cancellationType = consoleReader.readLine();
                        }

                        // Cancel a reservation based on the user's input
                        if (cancellationType.equals("0")) {
                            bookingIO.cancelBooking();
                        }
                        else if (cancellationType.equals("1")) {
                            bookingIO.bulkCancelBookings();
                        }
                        break;
                    case 3:
                        //Find available rooms
                        break;
                    case 4:
                        //produce timetable
                        RoomBooking.ProduceRoomTimetable(consoleReader);
                        break;
                    case 5:
                        //update room details
                        RoomBooking.UpdateRoomDetails(consoleReader);
                        break;
                    case 6:
                        //return to main menu
                        break;
                    default:
                        System.out.println("Invalid input; input valid choice.");
                }

            } else {
                System.out.println("Invalid input; input valid choice.");
            }
            //when returning to booking, display choices again.
            if (choice > 0 && choice < 6) {
                System.out.println("Returned to booking menu.");
                System.out.printf("1: Make a reservation%n2: Cancel a reservation%n 3:Find available rooms");
                System.out.printf("%n4:Produce a timetable for a room%n5: Update room details%n6: Return to main menu.%n");
            }
        }
    }

    //Opens menu for selecting methods from Timetabling class.
    //Uses same while-loop menu as before.
    static void TimetableMenu(BufferedReader consoleReader) throws IOException {

        System.out.printf("%n1: Create timetable and book relevant rooms for the school%n2: Produce timetable for a staff member or student%n3: Return to main menu%n");
        int choice = 0;
        while (choice != 3) {
            // regexChoice is used to check that the string is numbers only
            String regexChoice = Integer.toString(choice);
            if (regexChoice.matches("^[0-9]$")) {
                //Parses user input as an integer. May need validation for catching non-integer inputs.
                choice = Integer.parseInt(consoleReader.readLine());
                //1: Create timetable for school
                //2: Produce a timetable for a staff member or student
                //3: end program
                switch (choice) {
                    case 1:
                        //Create timetable for school
                        break;
                    case 2:
                        //produce individual timetable
                        break;
                    case 3:
                        //exit program
                        //doesn't need a command; the program reaches the end and exits automatically
                        //once this loop ends.
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Invalid input; input valid choice.");
                }
            } else {
                System.out.println("Invalid input; input valid choice.");
            }
            //when returning to timetable menu, display choices again.
            if (choice > 0 && choice < 6) {
                System.out.println("Returned to Timetable menu.");
                System.out.printf("1: Create timetable and book relevant rooms for the school%n2: Produce timetable for a staff member or student%n3: Return to main menu%n");
            }
        }
    }

    /**
     * Opens menu to read from the database.
     * @param consoleReader Reader for the console.
     * @throws IOException In the event of invalid input.
     */
    static void listMenu(BufferedReader consoleReader) throws IOException {

        System.out.printf("%n1: Output all rooms%n2: Output all students that take a module%n3: Output all staff that teach a module%n4: Output requirements for all modules%n5: Return to main menu");
        int choice = 0;
        while (choice != 5) {
            // regexChoice is used to check that the string is numbers only
            String regexChoice = Integer.toString(choice);
            if (regexChoice.matches("^[0-5]$")) {
                //Parses user input as an integer. May need validation for catching non-integer inputs.
                choice = Integer.parseInt(consoleReader.readLine());
                //1: All rooms
                //2: All students
                //3: All staff
                //4: All modules
                //5: end program
                switch (choice) {
                    case 1:
                        IController<Rooms> roomsIController = new Controller<>();

                        for (Rooms room : roomsIController.readAll(Rooms.class)) {
                            System.out.printf("%s: %s of capacity %d (socially distanced: %d)\n", room.getRoomID(), room.getType(), room.getMaxCapacity(), room.getSocialDistancingCapacity());
                        }
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid input; input valid choice.");
                }
            } else {
                System.out.println("Invalid input; input valid choice.");
            }
            //when returning to timetable menu, display choices again.
            if (choice > 0 && choice < 6) {
                System.out.println("Returned to List menu.");
                System.out.printf("%n1: Output all rooms%n2: Output all students that take a module%n3: Output all staff that teach a module%n 4: Return to main menu");
            }
        }
    }
}