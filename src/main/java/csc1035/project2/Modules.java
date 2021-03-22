package csc1035.project2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the modules table in the database.
 * Objects of this class represent a row in the table.
 */
@Entity
@Table(name = "Modules")
public class Modules {
    /**
     *
     */
    public Modules() {}

    public Modules(String moduleID, String name, Integer credits, Integer weeks) {
        this.moduleID = moduleID;
        this.name = name;
        this.credits = credits;
        this.weeks = weeks;
    }

    @Id
    @Column(name = "ModuleID", length = 7)
    private String moduleID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Credits")
    private Integer credits;

    @Column(name = "Weeks")
    private Integer weeks;

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }
    public String getModuleID() {
        return moduleID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getCredits() {
        return credits;
    }
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    public Integer getWeeks() {
        return weeks;
    }
    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }
    public ModuleRequirements getModuleRequirements() {
        return moduleRequirements;
    }
    public void setModuleRequirements(ModuleRequirements moduleRequirements) {
        this.moduleRequirements = moduleRequirements;
    }
    public Set<Bookings> getBookings() {
        return bookings;
    }
    public void setBookings(Set<Bookings> bookings) {
        this.bookings = bookings;
    }
    public Set<Students> getStudents() {
        return students;
    }
    public void setStudents(Set<Students> students) {
        this.students = students;
    }
    public Set<Staff> getStaff() {
        return staff;
    }
    public void setStaff(Set<Staff> staff) {
        this.staff = staff;
    }
}
