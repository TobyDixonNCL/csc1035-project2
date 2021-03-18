package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class handles methods related to the Bookings.java class. It allows for the creation of bookings, as well as the deletion of individual bookings or deletion of bookings in bulk.
 *
 */
public class BookingIO {

    final Scanner sc = new Scanner(System.in);

    /**
     * Books a room for the entire duration of the module. Some data is provided by the database
     * Takes user input for the time of the booking, room to book, day of booking, whether it is socially distanced, and the module to book.
     */
    public void smartBooking() {
        // Set up the formatters to extract a datetime object from the user's input.
        DateTimeFormatter durationFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Set up the controller objects for the class to use.
        IController<Staff> sic = new Controller<>();
        IController<Modules> mic = new Controller<>();

        // Set up the variables which store the information about the booking.
        boolean sociallyDistanced;
        LocalDateTime time;
        LocalDateTime endTime;
        LocalTime duration;
        LocalTime timeOfBooking;
        Staff staffMember;
        Modules module;
        Rooms room;
        int numberOfBookingsPerWeek;

        // Get staff.
        staffMember = sic.readById(Staff.class, getStaff().getStaffID(), true); // initialize only the relationship that is needed.

        // Read module IDs -> gets module requirements (week commencing, number of bookings required.)
        Map<String, Modules> moduleMap = new HashMap<>();
        System.out.println("Module options");
        for (Modules m : staffMember.getModules()) {
            System.out.print(" " + m.getModuleID() + ": " + m.getName());
            m = mic.readById(Modules.class, m.getModuleID(), true);
            if (!m.getBookings().isEmpty()) System.out.println(" (this booking already has bookings set up.)\n");
            else System.out.println();
            moduleMap.put(m.getModuleID(), m);
        }
        String moduleKey = sc.next();
        while (!moduleMap.containsKey(moduleKey)) {
            System.out.println("That is not a correct ID for a module. Look at the list above.)");
            moduleKey = sc.next();
        }
        sc.nextLine();

        // Ask type of lesson (duration) Gets duration from the module requirements
        System.out.println("What type of booking is this? \n" +
                "[0]: Practical.\n" +
                "[1]: Lecture.");
        String type = sc.next();
        while (!type.matches("^[01]$")) {
            System.out.println("Please enter (0 or 1)");
            type = sc.next();
        }
        sc.nextLine();


        // Get which type of lesson the user wants to book.
        module = mic.readById(Modules.class, moduleKey, true);  // update relations.
        ModuleRequirements requirements = module.getModuleRequirements();

        if (type.equals("0")) {
            duration = requirements.getPracticalLength();
            numberOfBookingsPerWeek = requirements.getPracticalsPerWeek();
        } else {
            duration = requirements.getLectureLength();
            numberOfBookingsPerWeek = requirements.getLecturesPerWeek();
        }

        // Creates a new recurring booking for the number of bookings required per week.
        for (int i = 1; i <= numberOfBookingsPerWeek; i++) {
            System.out.printf("------------------Booking #%d/%d------------------\n", i, numberOfBookingsPerWeek);

            // Ask for day of week -> (0-6), adds the number of days to the week commencing date.
            System.out.println("What day would you like this booking to be on? \n" +
                    "[0]: Monday.\n" +
                    "[1]: Tuesday\n" +
                    "[2]: Wednesday\n" +
                    "[3]: Thursday\n" +
                    "[4]: Friday\n" +
                    "[5]: Saturday\n" +
                    "[6]: Sunday.");
            String offset = sc.next();
            while (!offset.matches("^[0-6]$")) {
                System.out.println("Please enter (y/n)");
                offset = sc.next();
            }
            sc.nextLine();

            // Ask time -> gets the time for the booking
            System.out.println("What time would you like this booking to be (HH:MM)?");
            String sTime = sc.next();
            while (!sTime.matches("^[0-9]+:[0-9]+$")) {
                System.out.println("Please make sure you are using the correct format. (HH:MM)");
                sTime = sc.next();
            }
            sc.nextLine();

            // Attempt to set the time of the booking
            try {
                timeOfBooking = LocalTime.parse(sTime, durationFormatter);
            } catch (Exception e) {
                System.out.println("That is not a valid time. Going back to menu.");
                return;
            }

            // Set time
            time = requirements.getWeekCommencing().plusDays(Integer.parseInt(offset)).atTime(timeOfBooking);
            endTime = time.plusHours(duration.getHour()).plusMinutes(duration.getMinute());

            // Ask if it is social distanced.
            sociallyDistanced = isSociallyDistanced();

            // Ask for the room.
            room = getRooms(sociallyDistanced, time, endTime, module);

            List<Bookings> newBookings = new ArrayList<>();
            // Create booking.
            for (int bookingN = 1; bookingN <= module.getWeeks(); bookingN++) {
                newBookings.add(new Bookings(sociallyDistanced, time, duration, module, room));

                time = time.plusDays(7);
            }

            addBookings(newBookings);
        }
    }

    /**
     * Creates one-off booking for a room and module, all data is added manually rather than from the database.
     */
    public void manualBooking() {
        IController<Modules> mic = new Controller<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter durationFormatter = DateTimeFormatter.ofPattern("HH:mm");

        boolean sociallyDistanced;
        LocalDateTime time;
        LocalTime duration;
        Modules module;
        Rooms room;

        // Is this booking socially distanced?
        sociallyDistanced = isSociallyDistanced();

        // Get Time.
        System.out.println("What time would you like this booking to be (HH:MM)?");
        String sTime = sc.next();
        while (!sTime.matches("^[0-9]+:[0-9]+$")) {
            System.out.println("Please make sure you are using the correct format. (HH:MM)");
            sTime = sc.next();
        }
        sc.nextLine();

        // Get date INCLUDE OPTION TO GO FROM `m.getStart()`
        System.out.println("What date would you like this booking to be on (dd/mm/yyyy)?");
        String sDate = sc.next();
        while (!sDate.matches("^[0-9]+/[0-9]/+[0-9]{4}$")) {
            System.out.println("Please make sure you are using the correct format. (HH:MM)");
            sDate = sc.next();
        }
        sc.nextLine();

        // Get datetime
        try {
            time = LocalDateTime.parse(sDate + " " + sTime, dateTimeFormatter);
        } catch (Exception e) {
            System.out.println("That is not a valid date. Going back to menu.");
            return;
        }

        // Get duration.
        System.out.println("How long would you like this booking to be? (HH:MM)?");
        String sDuration = sc.next();
        while (!sDuration.matches("^[0-9]{1,2}:[0-9]+$")) {
            System.out.println("Please make sure you are using the correct format. (HH:MM)");
            sDuration = sc.next();
        }
        sc.nextLine();

        // Attempt to set duration
        try {
            duration = LocalTime.parse(sTime, durationFormatter);
        } catch (Exception e) {
            System.out.println("That is not a valid time. Going back to menu.");
            return;
        }
        LocalDateTime endTime = time.plusHours(duration.getHour()).plusMinutes(duration.getMinute());

        // Get module.
        Map<String, Modules> moduleMap = new HashMap<>();
        System.out.println("Module options");
        for (Modules m : mic.readAll(Modules.class)) {
            System.out.println(" " + m.getModuleID() + ": " + m.getName());
            moduleMap.put(m.getModuleID(), m);
        }
        String moduleKey = sc.next();
        while (!moduleMap.containsKey(moduleKey)) {
            System.out.println("That is not a correct ID for a module. Look at the list above.)");
            moduleKey = sc.next();
        }
        sc.nextLine();

        module = moduleMap.get(moduleKey);

        room = getRooms(sociallyDistanced, time, endTime, module);

        Bookings newBooking;


        try {
            // create a non-recurring booking.
            newBooking = new Bookings(sociallyDistanced, time, duration, module, room);
            addBooking(newBooking);
        } catch (Exception e) {
            System.out.println("There was an error creating a booking on the date: " + time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }


    }

    /**
     * Cancels an individual booking based on the booking ID which the user selects.
     */
    public void cancelBooking() {
        // Set up controllers.
        IController<Bookings> bic = new Controller<>();
        IController<Staff> sic = new Controller<>();
        IController<Modules> mic = new Controller<>();

        Scanner sc = new Scanner(System.in);
        Staff staffMember;
        Modules module;

        // Get staff ID to delete from.
        staffMember = sic.readById(Staff.class, getStaff().getStaffID(), true);

        // Read module IDs, get a list of all modules which this staff member can delete from.
        Set<String> moduleSet = new HashSet<>();
        System.out.println("Module options");
        for (Modules m : staffMember.getModules()) {
            System.out.println(" " + m.getModuleID() + ": " + m.getName());
            moduleSet.add(m.getModuleID());
        }
        String moduleKey = sc.next();
        while (!moduleSet.contains(moduleKey)) {
            System.out.println("That is not a correct ID for a module. Look at the list above.)");
            moduleKey = sc.next();
        }
        sc.nextLine();

        module = mic.readById(Modules.class, moduleKey, true);

        // Get the booking to delete.
        Set<String> bookingSet = new HashSet<>();
        System.out.println("Bookings for this module you can delete.");
        for (Bookings b : module.getBookings()) {
            System.out.println(" " + b.getBookingID() + ": " + b.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " in the room " + b.getRooms().getRoomID());
            bookingSet.add(b.getBookingID());
        }
        String bookingKey = sc.next();
        while (!bookingSet.contains(bookingKey)) {
            System.out.println("That is not a correct ID for a module. Look at the list above.)");
            bookingKey = sc.next();
        }
        sc.nextLine();

        try {
            bic.delete(Bookings.class, bookingKey);
            System.out.println("Successfully cancelled the booking with the ID " + bookingKey);
        } catch (Exception e) {
            System.out.println("Unable to cancel the booking with the id: " + bookingKey);
        }
    }

    /**
     * Cancels all bookings from a module (useful in the case that bookings for a specific module need to be redone).
     */
    public void bulkCancelBookings() {
        // Set up controllers.
        IController<Bookings> bic = new Controller<>();
        IController<Staff> sic = new Controller<>();
        IController<Modules> mic = new Controller<>();

        Scanner sc = new Scanner(System.in);
        Staff staffMember;
        Modules module;

        // Get staff ID to delete from.
        staffMember = sic.readById(Staff.class, getStaff().getStaffID(), true);

        // Read module IDs, get which module to delete from.
        Set<String> moduleSet = new HashSet<>();
        System.out.println("Module options");
        for (Modules m : staffMember.getModules()) {
            System.out.print(" " + m.getModuleID() + ": " + m.getName());
            if (!m.getBookings().isEmpty()) System.out.println(" (this booking already has bookings set up.)");
            else System.out.println();
            moduleSet.add(m.getModuleID());
        }
        String moduleKey = sc.next();
        while (!moduleSet.contains(moduleKey)) {
            System.out.println("That is not a correct ID for a module. Look at the list above.)");
            moduleKey = sc.next();
        }
        sc.nextLine();

        module = mic.readById(Modules.class, moduleKey, true);

        // Get all the booking IDs which will be deleted.
        Set<String> bookingSet = new HashSet<>();
        System.out.println("Bookings for this module which will be deleted.");
        for (Bookings b : module.getBookings()) {
            System.out.println(" " + b.getBookingID() + ": " + b.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " in the room " + b.getRooms().getRoomID());
            bookingSet.add(b.getBookingID());
        }

        // Ensure that the user does want to cancel all of these bookings.
        System.out.println("\nAre you sure that you want to cancel all bookings for this module? (y/n)?");
        String cancelConfirmation = sc.next();
        while (!cancelConfirmation.matches("^[yY]$|^[nN]$")) {
            System.out.println("Please enter (y/n)");
            cancelConfirmation = sc.next();
        }
        sc.nextLine();
        if (cancelConfirmation.matches("^[nN]$")) {
            System.out.println("You have chosen to not cancel the bookings for this module, returning to the main menu.");

            return;  // halt if the user decides against cancelling.
        }

        // Remove all the bookings in the set.
        for (String bookingId: bookingSet) {
            try {
                bic.delete(Bookings.class, bookingId);
                System.out.println("Successfully cancelled the booking with the ID " + bookingId);
            } catch (Exception e) {
                System.out.println("Unable to cancel the booking with the id: " + bookingId);
            }
        }

    }


    /**
     * Get the user's
     * @return the staff member which the user selects to use.
     */
    private Staff getStaff() {
        IController<Staff> sic = new Controller<>();
        Staff staffMember;
        Map<String, Staff> staffMap = new HashMap<>();
        System.out.println("Enter your staff ID.");
        for (Staff s : sic.readAll(Staff.class)) {
            System.out.println(" " + s.getStaffID() + ": " + s.getFirstname() + " " + s.getSurname());
            staffMap.put(s.getStaffID(), s);
        }
        String staffKey = sc.next();
        while (!staffMap.containsKey(staffKey)) {
            if (!staffKey.equals("")) System.out.println("That is not a correct ID for a member of staff. Look at the list above.");
            staffKey = sc.next();
        }

        staffMember = staffMap.get(staffKey);
        sc.nextLine();
        return staffMember;
    }

    /**
     * Get's whether this specific booking should be socially distanced or not depending upon user input
     * @return whether the user selects this booking to be socially distanced.
     */
    private boolean isSociallyDistanced() {
        boolean sociallyDistanced;
        System.out.println("Is this booking socially distanced (y/n)?");
        String sSociallyDistanced = sc.next();
        while (!sSociallyDistanced.matches("^[yY]$|^[nN]$")) {
            System.out.println("Please enter (y/n)");
            sSociallyDistanced = sc.next();
        }
        sc.nextLine();
        sociallyDistanced = sSociallyDistanced.matches("^[yY]$");
        return sociallyDistanced;
    }

    /**
     * Gets the room which the user selects out of a list of rooms which are both available and meet the capacity demands at the time of the booking.
     * @param sociallyDistanced Whether the booking is socially distanced, effects which column of the Rooms table is accessed for capacity.
     * @param time The time at which the booking begins.
     * @param endTime The time at which the booking ends.
     * @param module The module which the room is being booked for.
     * @return the room which the user selects from a list of available rooms for the given time.
     */
    private Rooms getRooms(boolean sociallyDistanced, LocalDateTime time, LocalDateTime endTime, Modules module) {
        // Get viable rooms
        Session s = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Rooms> cq = cb.createQuery(Rooms.class);
        Root<Rooms> root = cq.from(Rooms.class);
        ParameterExpression<Integer> spacesNeeded = cb.parameter(Integer.class);
        if (sociallyDistanced) {
            cq.select(root).where(cb.greaterThanOrEqualTo(root.get("socialDistancingCapacity"), spacesNeeded));
        } else {
            cq.select(root).where(cb.greaterThanOrEqualTo(root.get("maxCapacity"), spacesNeeded));
        }

        root.join("bookings", JoinType.LEFT);
        root.fetch("bookings", JoinType.LEFT);

        Query<Rooms> query = s.createQuery(cq);
        query.setParameter(spacesNeeded, module.getStudents().size());

        List<Rooms> results = query.getResultList();

        s.close();


        Map<String, Rooms> possibleRooms = new HashMap<>();

        for (Rooms room : results) {
            if (room.isAvailable(time, endTime) && !possibleRooms.containsKey(room.getRoomID())) {
                System.out.println(" " + room.getRoomID() + " of type " + room.getType() + " is available and meets your capacity needs.");
                possibleRooms.put(room.getRoomID(), room);
            }
        }

        System.out.println("Please enter the room ID you would like to book.");
        String roomKey = sc.next();
        while (!possibleRooms.containsKey(roomKey)) {
            System.out.println("That is not a correct ID for a room. Look at the list above.)");
            roomKey = sc.next();
        }
        sc.nextLine();

        return possibleRooms.get(roomKey);
    }

    /**
     * Adds a list of new bookings only if all of the bookings do not clash with any existing booking.
     * @param newBookings The list of bookings which are being added into the database.
     */
    private void addBookings(List<Bookings> newBookings) {
        IController<Bookings> bookingController = new Controller<>();
        IController<Students> studentController = new Controller<>();
        IController<Staff> staffController = new Controller<>();

        // Get all bookings
        List<Bookings> allBookings = bookingController.readAll(Bookings.class);


        // Check if any booking clashes.
        int count = 0;
        for (Bookings b : newBookings) {
            System.out.println("Checking availability of booking #" + count);
            for (Bookings booking : allBookings) {
                if (b.conflictsWith(booking)) {
                    throw new IllegalArgumentException("That booking conflicts with another booking.");
                }
            }

            Modules relatedModule = b.getModule();

            if (!b.getRooms().isAvailable(b.getTime(), b.getEnd())) {
                throw new IllegalArgumentException("That room is unavailable at the time (" + b.getTime() + ").");
            }
            for (Students student : relatedModule.getStudents()) {
                student = studentController.readById(Students.class, student.getStudentID(), true);
                if (!student.isAvailable(b.getTime(), b.getEnd())) {
                    throw new IllegalArgumentException("A student is unavailable at this time. [" + b.getTime() + "] (" + student.getStudentID() + ")");
                }
            }
            for (Staff staff : relatedModule.getStaff()) {
                staff = staffController.readById(Staff.class, staff.getStaffID(), true);

                if (!staff.isAvailable(b.getTime(), b.getEnd())) {
                    throw new IllegalArgumentException("A staff member is unavailable at this time. [" + b.getTime() + "] (" + staff.getStaffID() + ")");
                }
            }

            count++;
        }

        // Create the bookings
        for (Bookings b : newBookings) {
            bookingController.create(b);
            System.out.println(b.confirmation() + "\n");
        }
    }

    /**
     * Adds an individual booking `b` into the database, after checking that none of the parameters (students, staff or time) clash with existing bookings.
     * @param b The booking object which is being added to the database.
     */
    public void addBooking(Bookings b) {
        IController<Bookings> ic = new Controller<>();

        // Check whether the booking conflicts with another booking.
        for (Bookings booking : ic.readAll(Bookings.class)) {
            if (b.conflictsWith(booking)) {
                throw new IllegalArgumentException("That booking conflicts with another booking.");
            }
        }

        Modules relatedModule = b.getModule();

        // Check whether the room is available at the given time.
        if (!b.getRooms().isAvailable(b.getTime(), b.getEnd())) {
            throw new IllegalArgumentException("That room is unavailable at this time.");
        }
        // Check whether the students are available at that time.
        for (Students student : relatedModule.getStudents()) {
            if (!student.isAvailable(b.getTime(), b.getEnd())) {
                throw new IllegalArgumentException("A student is unavailable at this time.");
            }
        }
        // Check whether the staff members are available at that time.
        for (Staff staff : relatedModule.getStaff()) {
            if (!staff.isAvailable(b.getTime(), b.getEnd())) {
                throw new IllegalArgumentException("A staff member is unavailable at this time.");
            }
        }

        // Put the booking into the database.
        ic.create(b);
        System.out.println(b.confirmation() + "\n");
    }
}