package ra.service;

import org.springframework.stereotype.Service;
import ra.model.Category;
import ra.util.ConnectDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryService implements IGenericService<Category, Integer> {
    private List<Category> categoryList;
    public CategoryService() {
        categoryList = new ArrayList<>();
    }
    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectDB.getConnection();
            call = conn.prepareCall("{call show_category()}");
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                c.setCreated(rs.getDate("created_date"));
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return list;
    }

    @Override
    public void save(Category category) throws SQLException {
        Connection conn = ConnectDB.getConnection();
        try {
            if (category.getId() == 0) {
                CallableStatement callst = conn.prepareCall("{Call insert_category(?,?) }");
                callst.setString(1, category.getName());
                callst.setString(2, category.getDescription());
                callst.executeUpdate();
            } else {
                CallableStatement callst = conn.prepareCall("{Call updateCategory(?,?,?,?)}");

                callst.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    @Override
    public Category findById(Integer integer) {
        Connection conn = ConnectDB.getConnection();
        Category c = null;
        try {
            CallableStatement callst = conn.prepareCall("{Call findById(?)}");
            callst.setInt(1, integer);
            ResultSet rs = callst.executeQuery();
            while (rs.next()) {
                c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                c.setCreated(rs.getDate("created_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return c;
    }

    @Override
    public void deleteById(Integer integer) {
        Connection conn = ConnectDB.getConnection();
        try {
            CallableStatement callst = conn.prepareCall("{Call deleteById(?)}");
            callst.setInt(1, integer);
            callst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }
}
