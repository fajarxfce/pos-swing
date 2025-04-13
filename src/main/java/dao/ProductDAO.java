package dao;

import model.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> getAll() throws Exception;
    Product getById(int id) throws Exception;
    List<Product> search(String keyword) throws Exception;
    boolean add(Product product) throws Exception;
    boolean update(Product product) throws Exception;
    boolean delete(int id) throws Exception;
}