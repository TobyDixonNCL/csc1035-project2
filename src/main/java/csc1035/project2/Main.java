package csc1035.project2;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        Timetable test = new Timetable();
        LocalDateTime time1 = LocalDateTime.of(2020, 9, 21, 10, 00);
        LocalDateTime time2 = LocalDateTime.of(2021, 7, 21, 10, 00);

        test.getAdminTimetable(time1, time2);
    }
}
