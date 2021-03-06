package csc1035.project2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the school table in the database. It establishes a many to many relationship with the students, and staff classes; As well as a many to one relationship with the modules class;
 * and a one to many relationship with the bookings class.
 * Objects of this class represent a row in the table.
 * @author Adam Winstanley
 */
@Entity
@Table(name = "Schools")
public class Schools {

    @Id
    @Column(name = "SchoolID", length = 100)
    private String schoolID;


    // Relationships
    @ManyToOne
    @JoinColumn(name = "ModuleID")
    private Modules module;

    @ManyToMany
    @JoinTable(
            name = "SchoolStudents",
            joinColumns = {@JoinColumn(name = "SchoolID")},
            inverseJoinColumns = {@JoinColumn(name = "StudentID")}
    )
    private Set<Students> students = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "SchoolStaff",
            joinColumns = {@JoinColumn(name = "SchoolID")},
            inverseJoinColumns = {@JoinColumn(name = "StaffID")}
    )
    private Set<Staff> staff = new HashSet<>();

    @OneToMany(mappedBy = "school")
    private List<Bookings> bookings;


    // Getters and setters
    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public Modules getModule() {
        return module;
    }

    public void setModuleID(Modules module) {
        this.module = module;
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

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }

    public void setModule(Modules module) {
        this.module = module;
    }
}
