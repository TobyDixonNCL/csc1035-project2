package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

/**
 * This class controls all the functionality related to creating and retrieving timetables. It can
 * be used to create a timetable object that contains a list off booking objects which should contain
 * information about each individual booking for a given individual.
 */

public class Timetable {

    List<Bookings> bookings;

    /**
     * This method is used to turn an empty timetable object into a timetable for a specific student.
     * @param student - The student object of the student who's timetable is to be retrieved.
     */

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

    /**
     * This method is used to turn an empty timetable object into the timetable for a given staff member.
     * @param staff - The staff member who's timetable is to be retrieved.
     */
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


    /**
     * This method converts an empty timetable object into a full collection of all room bookings
     * for a particular school year.
     *
     * @param yearBegin - This is a LocalDateTime object that represents the start of the school year in question.
     * @param yearEnd - This is a LocalDateTime object that represents the end of the school year in question.
     */

    public void getAdminTimetable(LocalDateTime yearBegin, LocalDateTime yearEnd, boolean sociallyDistanced){
        /*
        Allows the admin to create a timetable (and book relevant rooms) for the school,
        under socially distant conditions
           - Include option to select between normal conditions and socially distant conditions
           - Same method as above, but use the social_distancing_capacity instead of max_capacity


        - Return all bookings (possibly within a given timeframe)
            - from year begin september 21 - year end july 22.
        - Should return different results for socially distanced or not
         */

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        Query moduleQuery = s.createSQLQuery("select * from Bookings");
        List<Bookings> list = moduleQuery.list();

        s.close();

        for (int i = 0; i < list.size(); i++){
            if (!(list.get(i).getTime().isAfter(yearBegin) &&
                    list.get(i).getTime().isBefore(yearEnd))){
                list.remove(list.get(i));
            }
            if (sociallyDistanced && !list.get(i).isSociallyDistanced()){
                list.remove(list.get(i));
            }
        }

        this.bookings = list;

    }
}
