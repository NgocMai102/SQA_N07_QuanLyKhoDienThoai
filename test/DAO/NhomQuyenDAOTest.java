/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.NhomQuyenDTO;
import config.JDBCUtil;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static config.JDBCUtil.*;
import java.sql.*; 

/**
 *
 * @author Admin
 */
public class NhomQuyenDAOTest {
    static NhomQuyenDAO dao;
    static Connection connection;
    
    public NhomQuyenDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        dao = new NhomQuyenDAO();
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#"
        );
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

    /**
     * Test of getInstance method, of class NhomQuyenDAO.
     */
    @Test
    public void testInsert_NhomQuyenChuaTonTai() throws SQLException {
        NhomQuyenDTO dto = new NhomQuyenDTO(11, "Quản lý TEST");
        int result = dao.insert(dto); // Pass test connection
        assertEquals(1, result);
    }

    @Test
    public void testInsert_NhomQuyenDaTonTai() {
        NhomQuyenDTO dto = new NhomQuyenDTO(1, "Quản lý kho");
        int result = dao.insert(dto);
        assertEquals(0, result); 
    }

    @Test
    public void testUpdate_NhomQuyenTonTai() {
        NhomQuyenDTO dto = new NhomQuyenDTO(5, "Cập nhật TEST");
        int result = dao.update(dto);
        assertEquals(1, result);
    }

    @Test
    public void testUpdate_NhomQuyenKhongTonTai() {
        NhomQuyenDTO dto = new NhomQuyenDTO(100, "Không tồn tại");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    @Test
    public void testDelete_NhomQuyenTonTai() {
        int result = dao.delete("6");
        assertEquals(1, result);
    }

    @Test
    public void testDelete_NhomQuyenKhongTonTai() {
        int result = dao.delete("1000");
        assertEquals(0, result);
    }

    @Test
    public void testSelectAll_CoDuLieu() {
        ArrayList<NhomQuyenDTO> list = dao.selectAll();
        assertNotNull(list);
        System.out.println(list.size());
        System.out.println(list);
        assertTrue(list.size() == 7);
    }

    @Test
    public void testSelectById_TonTai() {
        NhomQuyenDTO found = dao.selectById("1");
        assertNotNull(found);
        assertEquals(1, found.getManhomquyen());
        assertEquals(new NhomQuyenDTO(1, "Quản lý kho"), found) ;
    }

    @Test
    public void testSelectById_KhongTonTai() {
        NhomQuyenDTO found = dao.selectById("UNKNOWN");
        assertNull(found);
    }

    @Test
    public void testGetAutoIncrement() throws SQLException {
        int nextIdBefore = dao.getAutoIncrement();
        ArrayList<NhomQuyenDTO> list = dao.selectAll();

        // Insert a new record
        String insertSql = "INSERT INTO nhomquyen (tennhomquyen, trangthai) VALUES (?, ?)";
        PreparedStatement pst = connection.prepareStatement(insertSql);
        pst.setString(1, "AutoIncTest");
        pst.setInt(2, 1);
        pst.executeUpdate();

        int nextIdAfter = dao.getAutoIncrement();

        // The auto-increment should increase by 1
        assertEquals(nextIdBefore + 1, nextIdAfter);
        
    }
}
