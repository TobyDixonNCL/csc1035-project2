package csc1035.project2;

/*
    This class controls all the functionality of timetables.
    needs to be able to:
        - Create a timetable
            - Different timetable for admin, student and staff members.
            - Admin timetable contains all room bookings across all modules for the school year
            - Staff and Student timetables should contain all bookings for that individual for
            a day/week/etc...
        - Make room bookings for that timetable

 */

import org.hibernate.Session;
import org.hibernate.query.Query;


public class Timetable {

    boolean socialDistanced;
    List<Bookings> bookings;

    public Timetable(boolean socialDistanced, List<Bookings> bookings){
        this.socialDistanced = socialDistanced;
        this.bookings = bookings;
    }

    public void getStudentTimetable(Student student){

        /*
        Get modules
        Get respective module IDs
        Find Bookings where those module IDs are used
        Create a timetable object and return it, that holds
        all bookings.

        - Student has ID, which is stored with a module ID to make student timetable.
        - select ModuleID from StudentModules where StudentID = :student_id
        - With this list of ModuleID's:
        - select * from Bookings where ModuleID = :module_id
        - Timetable timetable = new Timetable(list of all bookings).
         */
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query query = s.createSQLQuery("select * from Students where StudentID = :student_id");
        query.setParameter("student_id", student.getId());
    }

    public void getStaffTimetable(Staff staff){
        System.out.println(staff.getClass());
    }

    public void getAdminTimetable(){
        //
    }

    public void bookRoom(){

        /*
        This method should be moved to an interfacing class

            Need:
                - Room number
                - Type
                - Max capacity
                - Social distancing capacity

            Check if room is booked
            if not then add room booking to database
         */
    }


}
