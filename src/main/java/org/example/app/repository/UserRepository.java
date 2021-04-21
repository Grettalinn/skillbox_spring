package org.example.app.repository;
import java.util.List;

public interface UserRepository<T> {
    List<T> retreiveAll();

    void save(T loginForm);

    boolean authUser(T loginForm);
}
