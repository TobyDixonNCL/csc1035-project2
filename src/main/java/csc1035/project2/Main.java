package csc1035.project2;

public class Main {
    public static void main(String[] args) {

        Student student = new Student();
        Timetable test = new Timetable(student, false);

        for (Bookings b: test.bookings){
            System.out.println(b);
        }
    }
}
