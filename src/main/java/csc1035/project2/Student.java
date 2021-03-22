package csc1035.project2;

    import javax.persistence.*;
    import java.time.LocalDateTime;
    import java.util.HashSet;
    import java.util.Objects;
    import java.util.Set;

@Entity
@Table(name = "Student")
public class Student {
    /**
     *
     */
    public Student() {}

    public Student(String id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    @Id
    @Column(name = "id", length = 6)
    private String id;

    @Column(name = "first_name", length = 30)
    private String first_name;

    @Column(name = "last_name")
    private int last_name;
    @OneToMany(mappedBy = "Student")
    private Set<Bookings> bookings = new HashSet<>();

    public void setid(String id) {
        this.id = id;
    }

    public String getid() {
        return id;
    }

    public String getfirst_name() {
        return first_name;
    }

    public void setfirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getlast_name() {
        return last_name;
    }

    public void setlast_name(int last_name) {
        this.last_name = last_name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name);
    }
}

