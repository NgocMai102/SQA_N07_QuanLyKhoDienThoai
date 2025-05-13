/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.NhomQuyenDTO;
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
        connection = getConnection();
        connection.setAutoCommit(false);
        
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // Roll back all changes made during tests
        connection.setAutoCommit(true);
        connection.close();
    }

    /**
     * Test of getInstance method, of class NhomQuyenDAO.
     */
    @Test
    public void testInsert_NhomQuyenChuaTonTai() {
        NhomQuyenDTO dto = new NhomQuyenDTO(11, "Quản lý TEST");
        int result = dao.insert(dto);
        assertEquals(1, result);
    }

    @Test
    public void testInsert_NhomQuyenDaTonTai() {
        NhomQuyenDTO dto = new NhomQuyenDTO(1, "abc");
        int result = dao.insert(dto);
        assertEquals(0, result); 
    }

    @Test
    public void testUpdate_NhomQuyenTonTai() {
        NhomQuyenDTO dto = new NhomQuyenDTO(10, "Đã cập nhật TEST");
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
        int result = dao.delete("10");
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
        assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectById_TonTai() {
        NhomQuyenDTO found = dao.selectById("1");
        assertNotNull(found);
        assertEquals(1, found.getManhomquyen());
    }

    @Test
    public void testSelectById_KhongTonTai() {
        NhomQuyenDTO found = dao.selectById("UNKNOWN");
        assertNull(found);
    }

    
}
