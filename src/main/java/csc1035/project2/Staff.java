package csc1035.project2;

import javax.persistence.*;
import java.time.LocalDateTime;
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
