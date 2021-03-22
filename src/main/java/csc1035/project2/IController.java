package csc1035.project2;

import java.util.List;

/**
 * This is an interface used by Controller.java
 * @paramThe table which is being used by the controller.
 */
public interface IController<E>  {
    void create(E e);
    void update(E e);
    E readById(Class<E> c, String id);
    E readById(Class<E> c, String id, boolean eager);
    List<E> readAll(Class<E> c);
    List<E> readAll(Class<E> c, boolean eager);
    void delete(Class<E> c, String id);
}
