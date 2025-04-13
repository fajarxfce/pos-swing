package dao;

import model.Category;
import java.util.List;

public interface CategoryDAO {
    List<Category> getAll() throws Exception;
    Category getById(int id) throws Exception;
    boolean add(Category category) throws Exception;
    boolean update(Category category) throws Exception;
    boolean delete(int id) throws Exception;
}