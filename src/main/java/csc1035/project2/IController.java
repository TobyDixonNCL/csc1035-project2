package csc1035.project2;

import java.util.List;

/**
 * This is an interface used by Controller.java
 * @param <E> The table which is being used by the controller.
 * @see <a href="https://nucode.ncl.ac.uk/scomp/stage1/csc1035/code-examples/part-2/csc1035-hibernate-examples/blob/master/src/main/java/controller/IController.java">(Lecture, Jordan Barnes, 2020)</a>
 */
public interface IController<E>  {
    void create(E e);

    void update(E e);

    E readById(Class<E> c, String id);

    List<E> readAll(Class<E> c);

    void delete(Class<E> c, String id);
}
