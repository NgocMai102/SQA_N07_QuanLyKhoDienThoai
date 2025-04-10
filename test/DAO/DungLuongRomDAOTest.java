/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.DungLuongRomDTO;
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
public class DungLuongRomDAOTest {
    
    private DungLuongRomDAO dao;
    private Connection conn;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new DungLuongRomDAO();
    }
    
    
    public DungLuongRomDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }


   
    @Test
    public void testInsert() {
        System.out.println("insert");
        DungLuongRomDTO t = null;
        DungLuongRomDAO instance = new DungLuongRomDAO();
        int expResult = 0;
        int result = instance.insert(t);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate_DuLieuHopLe_TonTai() {
        DungLuongRomDTO dto = new DungLuongRomDTO(1, 16);
        int result = dao.update(dto);
        assertEquals("Cập nhật thành công", 1, result);
    }

    @Test
    public void testUpdate_MaKhongTonTai() {
        DungLuongRomDTO dto = new DungLuongRomDTO(-1, 16);
        int result = dao.update(dto);
        assertEquals("Không có dòng nào được cập nhật", 0, result);
    }

    @Test(expected = Exception.class)
    public void testUpdate_DuLieuKhongHopLe() {
        DungLuongRomDTO dto = new DungLuongRomDTO(1, -1); // RAM không hợp lệ
        dao.update(dto); // Mong đợi exception xảy ra
    }


   
    @Test
    public void testDelete() {
        System.out.println("delete");
        String t = "";
        DungLuongRomDAO instance = new DungLuongRomDAO();
        int expResult = 0;
        int result = instance.delete(t);
        assertEquals(expResult, result);
  
    }

    
    @Test
    public void testSelectAll() {
        System.out.println("selectAll");
        DungLuongRomDAO instance = new DungLuongRomDAO();
        ArrayList<DungLuongRomDTO> expResult = null;
        ArrayList<DungLuongRomDTO> result = instance.selectAll();
        assertNotNull("Danh sách trả về không được null", result);
        assertTrue("Danh sách phải có ít nhất 1 phần tử", result.size() > 0);
    }

    @Test
    public void testSelectById() {
        System.out.println("selectById");
        String t = "";
        DungLuongRomDAO instance = new DungLuongRomDAO();
        DungLuongRomDTO expResult = null;
        DungLuongRomDTO result = instance.selectById(t);
        assertEquals(expResult, result);
     
    }

    /**
     * Test of getAutoIncrement method, of class DungLuongRomDAO.
     */
    @Test
    public void testGetAutoIncrement() {
        System.out.println("getAutoIncrement");
        DungLuongRomDAO instance = new DungLuongRomDAO();
        int expResult = 8;
        int result = instance.getAutoIncrement();
        assertEquals(expResult, result);
    }
    
}
