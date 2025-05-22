/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.DanhMucChucNangDTO;
import config.JDBCUtil;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maii
 */
public class DanhMucChucNangDAOTest {
    
    private DanhMucChucNangDAO dao;
    static Connection connection;
    
    
    @Before
    public void setUp() throws SQLException {
        dao = new DanhMucChucNangDAO();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
    }
    
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }
    
    public DanhMucChucNangDAOTest() {
    }
    

    @Test
    public void testSelectAllDanhMucChucNang_WithData_TT001() {
        // TT001_Giả sử đã có sẵn dữ liệu hợp lệ trong DB

        List<DanhMucChucNangDTO> result = dao.selectAll();

        assertNotNull("Danh sách không được null", result);
        assertFalse("Danh sách phải có ít nhất 1 phần tử", result.isEmpty());

        DanhMucChucNangDTO first = result.get(0);
        assertNotNull(first.getMachucnang());
        assertNotNull(first.getTenchucnang());
    }
    
    @Test
    public void testSelectAllDanhMucChucNang_NoValidRecords_TT002() {
        // TT002_Trường hợp này giả sử CSDL không có bản ghi hợp lệ nào

        List<DanhMucChucNangDTO> result = dao.selectAll();

        assertNotNull("Danh sách không được null");
        assertEquals("Danh sách phải rỗng", 0, result.size());
    }
}
