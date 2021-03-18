package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * This class represents the Staff table in the database, it establishes a many to many relationship to the School table.
 * Objects of this class represent a row in the table.
 */
@Entity
@Table(name = "Staff")
public class Staff {
    // Constructors
    public Staff() { }

    public Staff(String staffID, String firstname, String surname) {
        this.staffID = staffID;
        this.firstname = firstname;
        this.surname = surname;
    }

    // Getters and setters
    @Id
    @Column(name = "StaffID", length = 10)
    private String staffID;

    @Column(name = "Firstname")
    private String firstname;

    @Column(name = "Surname")
    private String surname;

    // Relationships
    @ManyToMany(mappedBy="staff")
    private Set<Modules> modules;

    // Getters and setters
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<Modules> getModules() {
        return modules;
    }

    public void setModules(Set<Modules> modules) {
        this.modules = modules;
    }

    /**
     * This method checks whether a staff member is in a booking at a given time.
     * @param from the beginning of the period of time being checked.
     * @param to the end of the period of time being checked.
     * @return true if the room is available in the given time frame, false if otherwise.
     */
    public boolean isAvailable(LocalDateTime from, LocalDateTime to) {
        Session s = HibernateUtil.getSessionFactory().openSession();

        // Get all of the staff member's bookings
        String sqlString = "SELECT b.time, b.duration FROM Staff s INNER JOIN s.modules m INNER JOIN m.bookings b WHERE s.staffID = :staffID";

        Query<?> q = s.createQuery(sqlString);
        q.setParameter("staffID", staffID);
        List<?> results = q.getResultList();

        s.close();

        LocalDateTime endTime;
        LocalDateTime startTime;
        LocalTime duration;
        for (Object result : results) {
            // Get the start time, duration, and end time from the query.
            Object[] parts = (Object[]) result;
            duration = (LocalTime)parts[1];
            startTime = (LocalDateTime)parts[0];
            endTime = startTime.plusHours(duration.getHour()).plusMinutes(duration.getMinute());

            // Check if the timeframe provided conflicts
            if ((from.isBefore(endTime) || from.isEqual(endTime)) && (to.isEqual(startTime) || to.isAfter(startTime))) {
                return false;
            }
        }
        return true;
    }

}
