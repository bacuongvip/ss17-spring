package ra.Service;


import ra.ConnectDB.ConnectDB;
import ra.model.Video;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceVideo implements IServiceVideo<Video, Long> {

    private List<Video> VideoList;

    public ServiceVideo() {
        VideoList = new ArrayList<>();
    }

    @Override
    public List findAll() {
        List<Video> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectDB.getConnection();
            call = conn.prepareCall("{call show_product}");
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Video p = new Video();
                p.setId(rs.getLong("id"));
                p.setUrl(rs.getString("url"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
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
    public void save(Video Video) throws SQLException {
        Connection conn = ConnectDB.getConnection();
        try {
            if (Video.getId() == 0) {
                CallableStatement callst = conn.prepareCall("{Call add_product(?,?,?) }");
                callst.setString(1, Video.getUrl());
                callst.setString(2, Video.getName());
                callst.setString(3, Video.getDescription());
                callst.executeUpdate();
            } else {
                CallableStatement callst = conn.prepareCall("{Call update_Video(?,?,?,?)}");
                callst.setString(1, Video.getUrl());
                callst.setString(2, Video.getName());
                callst.setString(3, Video.getDescription());
                callst.setLong(4, Video.getId());

                callst.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    @Override
    public Video findById(Long aLong) {
        Connection conn = ConnectDB.getConnection();
        Video p = null;
        try {
            CallableStatement callst = conn.prepareCall("{Call find_product_by_id(?)}");
            callst.setLong(1, aLong);
            ResultSet rs = callst.executeQuery();
            while (rs.next()) {
                p = new Video();
                p.setId(rs.getLong("id"));
                p.setUrl(rs.getString("url"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return p;
    }

    @Override
    public void deleteById(Long aLong) {
        Connection conn = ConnectDB.getConnection();
        try {
            CallableStatement callst = conn.prepareCall("{Call delete_product(?)}");
            callst.setLong(1, aLong);
            callst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }
}
