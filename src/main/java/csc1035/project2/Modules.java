package csc1035.project2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the modules table in the database. It establishes a one to one relationship with the module requirements class and a one to many relationship with the school class.
 * Objects of this class represent a row in the table.
 * @author Adam Winstanley
 */
@Entity
@Table(name = "Modules")
public class Modules {
    // Constructor
    public Modules() {}

    public Modules(String moduleID, String name, Integer credits, Integer weeks) {
        this.moduleID = moduleID;
        this.name = name;
        this.credits = credits;
        this.weeks = weeks;
    }

    // Columns
    @Id
    @Column(name = "ModuleID", length = 7)
    private String moduleID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Credits")
    private Integer credits;

    @Column(name = "Weeks")
    private Integer weeks;

    // Relationships

    @OneToOne
    @JoinColumn(name = "ModuleID", referencedColumnName = "ModuleID")
    private ModuleRequirements moduleRequirements;

    @OneToMany(mappedBy = "module")
    private Set<Bookings> bookings;

    @ManyToMany
    @JoinTable(
            name = "StudentModules",
            joinColumns = {@JoinColumn(name = "ModuleID")},
            inverseJoinColumns = {@JoinColumn(name = "StudentID")}
    )
    private Set<Students> students = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "StaffModules",
            joinColumns = {@JoinColumn(name = "ModuleID")},
            inverseJoinColumns = {@JoinColumn(name = "StaffID")}
    )
    private Set<Staff> staff = new HashSet<>();


    // Getters and setters
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
