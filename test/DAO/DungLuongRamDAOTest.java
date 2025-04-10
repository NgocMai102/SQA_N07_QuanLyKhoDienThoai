/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.DungLuongRamDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maii
 */
public class DungLuongRamDAOTest {
    
    private DungLuongRamDAO dao;
    private Connection conn;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new DungLuongRamDAO();
    }
    
    
    public DungLuongRamDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }


    /**
     * Test of insert method, of class DungLuongRamDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        DungLuongRamDTO t = null;
        DungLuongRamDAO instance = new DungLuongRamDAO();
        int expResult = 0;
        int result = instance.insert(t);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate_DuLieuHopLe_TonTai() {
        DungLuongRamDTO dto = new DungLuongRamDTO(1, 16);
        int result = dao.update(dto);
        assertEquals("Cập nhật thành công", 1, result);
    }

    @Test
    public void testUpdate_MaKhongTonTai() {
        DungLuongRamDTO dto = new DungLuongRamDTO(-1, 16);
        int result = dao.update(dto);
        assertEquals("Không có dòng nào được cập nhật", 0, result);
    }

    @Test(expected = Exception.class)
    public void testUpdate_DuLieuKhongHopLe() {
        DungLuongRamDTO dto = new DungLuongRamDTO(1, -1); // RAM không hợp lệ
        dao.update(dto); // Mong đợi exception xảy ra
    }


    /**
     * Test of delete method, of class DungLuongRamDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String t = "";
        DungLuongRamDAO instance = new DungLuongRamDAO();
        int expResult = 0;
        int result = instance.delete(t);
        assertEquals(expResult, result);
  
    }

    /**
     * Test of selectAll method, of class DungLuongRamDAO.
     */
    @Test
    public void testSelectAll() {
        System.out.println("selectAll");
        DungLuongRamDAO instance = new DungLuongRamDAO();
        ArrayList<DungLuongRamDTO> expResult = null;
        ArrayList<DungLuongRamDTO> result = instance.selectAll();
        assertNotNull("Danh sách trả về không được null", result);
        assertTrue("Danh sách phải có ít nhất 1 phần tử", result.size() > 0);
    }

    /**
     * Test of selectById method, of class DungLuongRamDAO.
     */
    @Test
    public void testSelectById() {
        System.out.println("selectById");
        String t = "";
        DungLuongRamDAO instance = new DungLuongRamDAO();
        DungLuongRamDTO expResult = null;
        DungLuongRamDTO result = instance.selectById(t);
        assertEquals(expResult, result);
     
    }

    /**
     * Test of getAutoIncrement method, of class DungLuongRamDAO.
     */
    @Test
    public void testGetAutoIncrement() {
        System.out.println("getAutoIncrement");
        DungLuongRamDAO instance = new DungLuongRamDAO();
        int expResult = 8;
        int result = instance.getAutoIncrement();
        assertEquals(expResult, result);
    }
    
}
