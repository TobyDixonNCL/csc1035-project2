package csc1035.project2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.*;

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
     * Usage: controllerObject.create(tableRowObject);
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
     * Usage: controllerObject.update(tableRowObject);
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
     * Lazily reads a row from the specified table based on the specified id.
     * Use this method when you do not need to make use of a relationship.
     * Usage: controllerObject.readById(Class, "some_id");
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
     * Eagerly reads a row from the specified table based on the specified id.
     * Use this method when you do need to access a relationship.
     * Usage: controllerObject.readById(Class, "some_id", true);
     * @param c The class which represents the table which you are trying to read from.
     * @param id The id of the row that is being read.
     * @param eager Selects whether you would like to fetch the row eagerly or lazily.
     * @return An object of class `c` representing the row with id `id`. Returns null if there is no id.
     */
    @Override
    public E readById(Class<E> c, String id, boolean eager) {
        // The ID column name is required for eager fetching.
        String idColumn = "";
        List<String> tables = new ArrayList<>();

        // If the user wants to fetch eagerly, get required tables and the id column name.
        if (eager) {
            for (Field f : c.getDeclaredFields()) {
                // Check for the id column for use when checking against the given id.
                if (f.isAnnotationPresent(Id.class)) idColumn = f.getName();

                // Only these two types of relationship need to be specified. as the default fetching method for ManyToOne and OneToOne is EAGER anyway (src: https://vladmihalcea.com/hibernate-facts-the-importance-of-fetch-strategy/)
                if (f.isAnnotationPresent(ManyToMany.class) || f.isAnnotationPresent(OneToMany.class)) tables.add(f.getName());
            }
        } else {
            // Otherwise return lazy fetching
            return readById(c, id);
        }

        E entry = null;

        try {
            // Begin session
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            // Set up the criteria builder.
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(c);
            Root<E> r = cq.from(c);
            cq.select(r).where(cb.equal(r.<String>get(idColumn), id));

            // Add the tables which are not fetched eagerly by default to the query.
            for (String table : tables){
                r.join(table, JoinType.LEFT);
                r.fetch(table, JoinType.LEFT);
            }

            // Get the row of the database with the corresponding id.
            Query<E> query = s.createQuery(cq);
            entry = query.getSingleResult();

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
     * Lazily reads all the rows from a table represented by class c.
     * Use this method when you do not want to make use of the relationships of the table.
     * Usage: controllerObject.readAll(Class);
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
     * Eagerly reads all the rows from a table represented by class c.
     * Use this method when you want to make use of the relationships of the table.
     * Usage: controllerObject.readAll(Class, true);
     * @param c The class which represents the table which is being read from.
     * @return A list of objects representing rows in the table `c`.
     */
    @Override
    public List<E> readAll(Class<E> c, boolean eager) {
        // If user wants to read table lazily, use existing function.
        if (!eager) {
            return readAll(c);
        }

        // Find the tables which need joining.
        List<String> tables = new ArrayList<>();
        for (Field f : c.getDeclaredFields()) {
            if (f.isAnnotationPresent(ManyToMany.class) || f.isAnnotationPresent(OneToMany.class)) tables.add(f.getName());
        }

        List<E> entries = null;

        try {
            // Begin session
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            // Set up criteria builder.
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(c);
            Root<E> r = cq.from(c);
            cq.select(r);

            // Join and fetch tables.
            for (String table : tables) {
                r.join(table, JoinType.LEFT);
                r.fetch(table, JoinType.LEFT);
            }

            // Execute query, get all the rows from the given class (fetched eagerly)
            Query<E> query = s.createQuery(cq);
            entries = query.getResultList();

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
     * Usage: controllerObject.delete(Class, "id_to_delete");
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
