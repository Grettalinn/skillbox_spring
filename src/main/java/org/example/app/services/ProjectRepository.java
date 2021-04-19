package org.example.app.services;
import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    void removeItemById(Integer bookIdToRemove);

    void removeItemByString(String bookStrToRemove);

    List<T> filterItems(String bookParams);
}
