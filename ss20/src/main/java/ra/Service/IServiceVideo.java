package Service;

import java.sql.SQLException;
import java.util.List;

public interface IServiceProducts<T, E> {
    List<T> findAll();

    void save(T t) throws SQLException;

    T findById(E e);

    void deleteBYe(E e);

    List<T> sortProducts();
}
