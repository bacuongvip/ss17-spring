package ra.service;

import java.sql.SQLException;
import java.util.List;

public interface IGenericService<T, E>{
    List<T> findAll();

    void save(T t) throws SQLException;

    T findById(E e);

    void deleteById(E e);
}
