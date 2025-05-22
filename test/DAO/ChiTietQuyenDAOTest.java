/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import static DAO.PhieuNhapDAOTest.connection;
import DTO.ChiTietQuyenDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static config.JDBCUtil.getConnection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChiTietQuyenDAOTest {
    
    static Connection connection;
    static ChiTietQuyenDAO dao;
    
    public ChiTietQuyenDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        dao = new ChiTietQuyenDAO() ;
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getInstance method, of class ChiTietQuyenDAO.
     */
    /**
     * Test of delete method, of class ChiTietQuyenDAO.
     */
    @Test
    public void testDelete_ChiTietQuyenTonTai() {
        int result = dao.delete("4");
        assertEquals(5, result);
    }

    @Test
    public void testDelete_ChiTietQuyenKhongTonTai() {
        int result = dao.delete("1000");
        assertEquals(0, result);
    }

    /**
     * Test of insert method, of class ChiTietQuyenDAO.
     */
    @Test
    public void testInsert_ChiTietQuyenChuaTonTai() throws SQLException {
        ChiTietQuyenDTO dto = new ChiTietQuyenDTO(7, "TEST", "view");
        ArrayList<ChiTietQuyenDTO> ls = new ArrayList(); 
        ls.add(dto) ;
        int result = dao.insert(ls); // Pass test connection
        assertEquals(0, result);
    }

    @Test
    public void testInsert_ChiTietQuyenDaTonTai() {
        ChiTietQuyenDTO dto = new ChiTietQuyenDTO(1, "khu vuc kho", "view");
        ArrayList<ChiTietQuyenDTO> ls = new ArrayList(); 
        ls.add(dto);
        int result = dao.insert(ls);
        assertEquals(1, result); 
    }

    /**
     * Test of selectAll method, of class ChiTietQuyenDAO.
     */
    @Test
    public void testSelectAll() {
        ArrayList<ChiTietQuyenDTO> list = dao.selectAll("5");
        assertNotNull(list);
        assertTrue(list.size() == 2);
    }

    /**
     * Test of update method, of class ChiTietQuyenDAO.
     */
    @Test
    public void testUpdate_NhomQuyenTonTai() {
        ChiTietQuyenDTO dto = new ChiTietQuyenDTO(5, "khu vuc kho", "view");
        ArrayList<ChiTietQuyenDTO> ls = new ArrayList(); 
        ls.add(dto) ;
        int result = dao.update(ls, "5");
        assertEquals(1, result);
    }

    @Test
    public void testUpdate_NhomQuyenKhongTonTai() {
        ChiTietQuyenDTO dto = new ChiTietQuyenDTO(1, "khu vuc kho", "view");
        ArrayList<ChiTietQuyenDTO> ls = new ArrayList();
        ls.add(dto);
        int result = dao.update(ls, "1000");
        assertEquals(0, result);
    }
    
}
