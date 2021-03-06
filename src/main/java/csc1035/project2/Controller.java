package csc1035.project2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * This class is a generic controller for the table classes.
 * @see <a href="https://nucode.ncl.ac.uk/scomp/stage1/csc1035/code-examples/part-2/csc1035-hibernate-examples/blob/master/src/main/java/controller/Controller.java">(Uses code from this lecture, Jordan Barnes, 2020)</a>
 * @param <E> The hibernate class which is being interacted with.
 * @author Adam Winstanley
 */
public class Controller<E> implements IController<E> {

    private Session s = null;

    /**
     * This method will attempt to insert a new row into the database and handle errors in the event that the data provided is not valid.
     * @param e The object (row) which you are trying to insert into the database.
     */
    @Override
    public void create(E e) {
        try {
            // Begin session
            s = HibernateUtil.getSessionFactory().openSession();

            // Attempt to save the object into the database.
            s.beginTransaction();
            s.persist(e); // raises PersistenceException if data provided is not valid for the database.
            s.getTransaction().commit();
        }
        // Handle errors
        catch (PersistenceException exception) {
            if (s != null) s.getTransaction().rollback();
            exception.printStackTrace();
        }
        finally {
            // Close the session
            if (s != null) s.close();
        }
    }

    /**
     * This method will attempt to update a row in the database around the ID.
     * @param e The row which you are trying to update in the database.
     */
    @Override
    public void update(E e) {
        try {
            // Begin session
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            // Attempt to update the object.
            s.saveOrUpdate(e);
            s.getTransaction().commit();
        }
        catch (HibernateException exception) {
            // Handle errors
            if (s != null) s.getTransaction().rollback();
            exception.printStackTrace();
        }
        finally {
            // Close the session
            if (s != null) s.close();
        }

    }

    /**
     * This will read a row from the specified table based on the specified id.
     * @param c The class which represents the table which you are trying to read from.
     * @param id The id of the row that is being read.
     * @return An object of class `c` representing the row with id `id`. Returns null if there is no id.
     */
    @Override
    public E readById(Class<E> c, String id) {
        E entry = null;
        try {
            // Begin session
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            // Get the row of the database with the corresponding id.
            entry = c.cast(s.get(c, id));
            s.getTransaction().commit();
        }
        catch (HibernateException exception) {
            // Handle errors
            if (s != null) s.getTransaction().rollback();
            exception.printStackTrace();
        }
        finally {
            // Close the session
            if (s != null) s.close();
        }

        return entry;
    }

    /**
     * Reads all rows from the table which is represented by class c.
     * @param c The class which represents the table which is being read from.
     * @return A list of objects representing rows in the table `c`.
     */
    @Override
    public List<E> readAll(Class<E> c) {
        List<E> entries = null;
        try {
            // Begin session
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            // Get all entries from the given class.
            entries = s.createQuery("FROM " + c.getSimpleName()).list();
            s.getTransaction().commit();
        }
        catch (HibernateException exception) {
            // Handle errors
            if (s != null) s.getTransaction().rollback();
            exception.printStackTrace();
        }
        finally {
            // Close the session
            if (s != null) s.close();
        }

        return entries;
    }

    /**
     * This will delete a specified row from the specified table.
     * @param c The table which is being deleted from.
     * @param id The id of the row which is being deleted.
     */
    @Override
    public void delete(Class<E> c, String id) {
        try {
            // Begin session
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            // Attempt to delete a row in the table.
            Object entry = c.cast(s.get(c, id));
            s.delete(entry);
            s.getTransaction().commit();
        }
        catch (HibernateException exception) {
            // Handle errors
            if (s != null) s.getTransaction().rollback();
            exception.printStackTrace();
        }
        finally {
            // Close the session
            if (s != null) s.close();
        }
    }
}
