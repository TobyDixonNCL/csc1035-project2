package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Staff")
public class Staff {
    /**
     *
     */
    public Staff() { }

    public Staff(String staffID, String firstname, String surname) {
        this.staffID = staffID;
        this.firstname = firstname;
        this.surname = surname;
    }

    @Id
    @Column(name = "StaffID", length = 10)
    private String staffID;

    @Column(name = "Firstname")
    private String firstname;

    @Column(name = "Surname")
    private String surname;
    @ManyToMany(mappedBy="staff")
    private Set<Modules> modules;

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

}
