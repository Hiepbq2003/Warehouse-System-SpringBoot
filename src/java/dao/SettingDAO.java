package dao;

import context.DBContext;
import model.Setting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SettingDAO  {

    // Lấy tất cả setting
    @Override
    public List<Setting> findAll() {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT * FROM setting";
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                settings.add(getFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all settings: " + e.getMessage());
        } finally {
            closeResources();
        }
        return settings;
    }

    // Tìm setting theo id
    @Override
    public Setting findById(Integer id) {
        String sql = "SELECT * FROM setting WHERE id = ?";
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error getting setting by ID: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    // Thêm setting mới
    @Override
    public int insert(Setting setting) {
        String sql = "INSERT INTO setting (`key`, `value`) VALUES (?, ?)";
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, setting.getKey());
            statement.setString(2, setting.getValue());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating setting failed, no rows affected.");
            }
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error inserting setting: " + e.getMessage());
        } finally {
            closeResources();
        }
        return -1;
    }

    // Cập nhật setting
    @Override
    public boolean update(Setting setting) {
        String sql = "UPDATE setting SET `key` = ?, `value` = ? WHERE id = ?";
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, setting.getKey());
            statement.setString(2, setting.getValue());
            statement.setInt(3, setting.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating setting: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }

    // Xóa setting
    @Override
    public boolean delete(Setting setting) {
        if (setting == null || setting.getId() == null) {
            System.out.println("Error deleting setting: Setting or ID is null.");
            return false;
        }
        String sql = "DELETE FROM setting WHERE id = ?";
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, setting.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting setting: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }

    // Chuyển ResultSet thành object Setting
    @Override
    public Setting getFromResultSet(ResultSet rs) throws SQLException {
        Setting s = new Setting();
        s.setId(rs.getInt("id"));
        s.setKey(rs.getString("key"));
        s.setValue(rs.getString("value"));
        return s;
    }

    // Tìm setting theo key
    public Setting findByKey(String key) {
        String sql = "SELECT * FROM setting WHERE `key` = ?";
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, key);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error getting setting by key: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    // Lấy giá trị setting theo key
    public String getValueByKey(String key) {
        Setting s = findByKey(key);
        return s != null ? s.getValue() : null;
    }

    // Cập nhật giá trị setting theo key, nếu chưa có thì tạo mới
    public boolean updateValueByKey(String key, String value) {
        Setting s = findByKey(key);
        if (s != null) {
            s.setValue(value);
            return update(s);
        } else {
            Setting newSetting = new Setting();
            newSetting.setKey(key);
            newSetting.setValue(value);
            return insert(newSetting) > 0;
        }
    }

    // Test
    public static void main(String[] args) {
        SettingDAO dao = new SettingDAO();
        List<Setting> list = dao.findAll();
        for (Setting s : list) {
            System.out.println(s);
        }
    }
}
