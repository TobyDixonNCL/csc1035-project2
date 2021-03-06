package csc1035.project2;

import javax.persistence.*;
import java.util.Set;

/**
 * This class represents the Staff table in the database, it establishes a many to many relationship to the School table.
 * Objects of this class represent a row in the table.
 * @author Adam Winstanley
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
    private Set<Schools> school;

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

    public Set<Schools> getSchool() {
        return school;
    }

    public void setSchool(Set<Schools> school) {
        this.school = school;
    }
}
