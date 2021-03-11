package csc1035.project2;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class represents the module requirements table in the database. It establishes a one to one relationship with the modules class.
 * Objects of this class represent a row in the table.
 * @author Adam Winstanley
 */
@Entity
@Table(name = "ModuleRequirements")
public class ModuleRequirements {
    // Constructor
    public ModuleRequirements() { }

    public ModuleRequirements(String moduleID, LocalDate weekCommencing, Integer lecturesPerWeek, LocalTime lectureLength, Integer practicalsPerWeek, LocalTime practicalLength) {
        this.moduleID = moduleID;
        this.weekCommencing = weekCommencing;
        this.lecturesPerWeek = lecturesPerWeek;
        this.lectureLength = lectureLength;
        this.practicalsPerWeek = practicalsPerWeek;
        this.practicalLength = practicalLength;
    }

    // Columns
    @Id
    @Column(name = "ModuleID", length = 7)
    private String moduleID;

    @Column(name = "WeekCommencing")
    private LocalDate weekCommencing;

    @Column(name = "LecturesPerWeek")
    private Integer lecturesPerWeek;

    @Column(name = "LectureLength")
    private LocalTime lectureLength;

    @Column(name = "PracticalsPerWeek")
    private Integer practicalsPerWeek;

    @Column(name = "PracticalLength")
    private LocalTime practicalLength;

    // Relationships
    @OneToOne(mappedBy = "moduleRequirements")
    private Modules module;


    // Getters and setters
    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public LocalDate getWeekCommencing() {
        return weekCommencing;
    }

    public void setWeekCommencing(LocalDate weekCommencing) {
        this.weekCommencing = weekCommencing;
    }

    public Integer getLecturesPerWeek() {
        return lecturesPerWeek;
    }

    public void setLecturesPerWeek(Integer lecturesPerWeek) {
        this.lecturesPerWeek = lecturesPerWeek;
    }

    public LocalTime getLectureLength() {
        return lectureLength;
    }

    public void setLectureLength(LocalTime lectureLength) {
        this.lectureLength = lectureLength;
    }

    public Integer getPracticalsPerWeek() {
        return practicalsPerWeek;
    }

    public void setPracticalsPerWeek(Integer practicalsPerWeek) {
        this.practicalsPerWeek = practicalsPerWeek;
    }

    public LocalTime getPracticalLength() {
        return practicalLength;
    }

    public void setPracticalLength(LocalTime practicalLength) {
        this.practicalLength = practicalLength;
    }

    public Modules getModule() {
        return module;
    }

    public void setModule(Modules module) {
        this.module = module;
    }
}
