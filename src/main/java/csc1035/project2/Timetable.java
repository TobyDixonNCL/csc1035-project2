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

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class Timetable {

    boolean socialDistanced;
    List<Bookings> bookings;

    public Timetable(){
        this.socialDistanced = socialDistanced;

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

        Query moduleQuery = s.createSQLQuery("select ModuleID from StudentModules where StudentID = :student_id");
        moduleQuery.setParameter("student_id", student.getID());
        List modules = moduleQuery.list();

        System.out.println(modules.size());

        List<Bookings> bookings = new ArrayList<>();

        for (Object module: modules) {
            Query bookingQuery = s.createSQLQuery("select * from Bookings where ModuleID = :module_id");
            bookingQuery.setParameter("module_id", module);
            System.out.println(module);
            List<Bookings> moduleBooking = bookingQuery.list();
            for (Bookings b: moduleBooking){
                bookings.add(b);
            }
        }

        s.close();
        this.bookings = bookings;

    }

    public void getStaffTimetable(Staff staff){
        /*
        Functions the same as student but for staff modules
         */

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        Query moduleQuery = s.createSQLQuery("select ModuleID from StaffModules where StaffID = :staff_id");
        moduleQuery.setParameter("staff_id", staff.getID());
        List modules = moduleQuery.list();

        System.out.println(modules.size());

        List<Bookings> bookings = new ArrayList<>();

        for (Object module: modules) {
            Query bookingQuery = s.createSQLQuery("select * from Bookings where ModuleID = :module_id");
            bookingQuery.setParameter("module_id", module);
            System.out.println(module);
            List<Bookings> moduleBooking = bookingQuery.list();
            for (Bookings b: moduleBooking){
                bookings.add(b);
            }
        }

        s.close();
        this.bookings = bookings;

    }

    public void getAdminTimetable(boolean socialDistanced){
        /*
        Allows the admin to create a timetable (and book relevant rooms) for the school,
        under socially distant conditions
           - Include option to select between normal conditions and socially distant conditions
           - Same method as above, but use the social_distancing_capacity instead of max_capacity

         */




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
