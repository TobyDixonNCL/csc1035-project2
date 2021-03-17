package csc1035.project2;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the students table in the database. It establishes a many to many relationship with the School class.
 * Objects of this class represent a row in the table.
 */
@Entity
@Table(name = "Students")
public class Students {
    // Constructor
    public Students() { }

    public Students(String studentID, String firstname, String surname) {
        this.studentID = studentID;
        this.firstname = firstname;
        this.surname = surname;
    }

    // Columns
    @Id
    @Column(name = "StudentID", length = 9)
    private String studentID;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    // Relationships
    @ManyToMany(mappedBy = "students")
    private Set<Modules> modules = new HashSet<>();

    // Getters and setters
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentID() {
        return studentID;
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
     * This method checks whether a student is in a booking at a given time.
     * @param from the beginning of the period of time being checked.
     * @param to the end of the period of time being checked.
     * @return true if the room is available in the given time frame, false if otherwise.
     */
    public boolean isAvailable(LocalDateTime from, LocalDateTime to) {
        IController<Modules> mic = new Controller<>();

        // Get the modules for which the staff member teaches.
        for (Modules module : modules) {
            // Update relationships.
            module = mic.readById(Modules.class, module.getModuleID(), true);

            // Get the times at which the staff member is booked.
            for (Bookings booking : module.getBookings()) {
                if ((from.isBefore(booking.getEnd()) || from.isEqual(booking.getEnd())) && (to.isEqual(booking.getTime()) || to.isAfter(booking.getTime()))) {
                    return false;
                }
            }
        }
        return true;
    }

}
