package csc1035.project2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the students table in the database. It establishes a many to many relationship with the School class.
 * Objects of this class represent a row in the table.
 * @author Adam Winstanley
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
    private Set<Schools> schools = new HashSet<>();

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

    public Set<Schools> getSchools() {
        return schools;
    }

    public void setSchools(Set<Schools> schools) {
        this.schools = schools;
    }
}
