package csc1035.project2;

import org.hibernate.Session;

import java.util.List;
import org.hibernate.query.Query;

public class Main {
    public static void main(String[] args) {

        Session s = HibernateUtil.getSessionFactory().openSession();

        s.beginTransaction();

        Query query = s.createSQLQuery("select * from Students where StudentID = 216906208");
        List<Object> results = query.list();

        for (Object o: results) {
            System.out.println(o);
        }
    }
}
