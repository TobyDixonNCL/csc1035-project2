package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the students table in the database.
 * Objects of this class represent a row in the table.
 */
@Entity
@Table(name = "Students")
public class Students {
    public Students() { }

    public Students(String studentID, String firstname, String surname) {
        this.studentID = studentID;
        this.firstname = firstname;
        this.surname = surname;
    }
    @Id
    @Column(name = "StudentID", length = 9)
    private String studentID;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    @ManyToMany(mappedBy = "students")
    private Set<Modules> modules = new HashSet<>();

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
}
