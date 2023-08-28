package ra.service;

import org.springframework.stereotype.Service;
import ra.model.Category;
import ra.model.Product;
import ra.model.Product;
import ra.util.ConnectDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService implements IGenericService<Product, Integer>{
    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectDB.getConnection();
            call = conn.prepareCall("{call show_product()}");
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setUrl(rs.getString("url"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getInt("price"));
                p.setQuantity(rs.getInt("quantity"));

                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                p.setCategoryId(category);
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return list;
    }

    @Override
    public void save(Product product) throws SQLException {

    }

    @Override
    public Product findById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
    public List<Product> findProByCateId(Integer integer) {
        List<Product> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectDB.getConnection();
            call = conn.prepareCall("{call findProByCateId(?)}");
            call.setInt(1, integer);
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setUrl(rs.getString("url"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getInt("price"));
                p.setQuantity(rs.getInt("quantity"));

                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                p.setCategoryId(category);
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return list;
    }
}
