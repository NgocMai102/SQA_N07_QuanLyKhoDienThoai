/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.SanPhamDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;


/**
 *
 * @author Maii
 */
public class SanPhamDAOTest {
    
    private SanPhamDAO dao;
    private Connection conn;
    
    public SanPhamDAOTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new SanPhamDAO();
    }
    
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @After
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.rollback(); // rollback các thay đổi
            conn.close();
        }
    }
    /**
     * Test of insert method, of class SanPhamDAO.
     */
  
    @Test
    public void testInsert_ValidProduct_SP001() throws SQLException {
        // SP001 - Thêm sản phẩm đủ thông tin
        SanPhamDTO sp1 = new SanPhamDTO(0, "Iphone 13", "iphone13.jpg", 1, "A15 Bionic", 5000, 6.1, 1, 13, "12MP", "12MP", 12, 1, 101, 100);

        int result = dao.insert(sp1);
        assertEquals(1, result); // Kiểm tra insert thành công
    }

    @Test
    public void testInsert_ExistingProductId_SP002() throws SQLException {
        // SP002 - Thêm sản phẩm có mã đã tồn tại
        SanPhamDTO sp3 = new SanPhamDTO(1, "Sản phẩm Test 002", "image_b.jpg", 1, "Chip B", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.insert(sp3);
        assertEquals(0, result);
    }

    @Test    
    public void testInsert_InvalidField_SP003() throws SQLException {
        // SP003 - Thêm sản phẩm với trường dữ liệu không hợp lệ
        SanPhamDTO sp4 = new SanPhamDTO(30, "Sản phẩm Test 03", "image_c.jpg", -1, "Chip C", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.insert(sp4);
        assertEquals(0, result);
    }

    @Test
    public void testInsert_MissingField_SP004() throws Exception {
        // SP004 - Thêm sản phẩm thiếu tên
        SanPhamDTO sp5 = new SanPhamDTO(31, null, "image_c.jpg", 1, "Chip C", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.insert(sp5);
        assertEquals(0, result);
    }
    
    /**
     * Test of update method, of class SanPhamDAO.
     */

    @Test
    public void testUpdate_ValidProduct_SP005() throws SQLException {
        // SP005 - Cập nhật sản phẩm hợp lệ
        
        SanPhamDTO sp1 = new SanPhamDTO(31, "Sản phẩm Test 003", "image_a.jpg", 1, "Chip A", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.update(sp1);
        assertEquals(1, result);
    }

    @Test
    public void testUpdate_NonExistentId_SP006() throws SQLException {
        // SP006 - Cập nhật sản phẩm không tồn tại
        SanPhamDTO sp2 = new SanPhamDTO(9999, "Sản phẩm Test 003", "image_b.jpg", 1, "Chip B", 6000, 6.8, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 5);
        int result = dao.update(sp2);
        assertEquals(0, result);
    }

    @Test
    public void testUpdate_InvalidField_SP007() throws SQLException {
        // SP007 - Cập nhật sản phẩm với trường không hợp lệ
        SanPhamDTO t = new SanPhamDTO(2, "Sản phẩm C", "image_c.jpg", -1, "Chip C", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 5);
        int result = dao.update(t);
        assertEquals(0, result);
    }

    @Test
    public void testUpdate_MissingField_SP008() throws SQLException {
        // SP008 - Cập nhật sản phẩm với dữ liệu thiếu
        SanPhamDTO t = new SanPhamDTO(30, "Sản phẩm C", "image_c.jpg", 1, null, 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 5);
        int result = dao.update(t);
        assertEquals(0, result);
    }
    
    /**
     * Test of delete method, of class SanPhamDAO.
     */

    @Test
    public void testDelete_ValidId_SP009() throws SQLException {
        // SP009 - Xóa sản phẩm hợp lệ
        int result = dao.delete("31");
        assertEquals(1, result);
    }

    @Test
    public void testDelete_NonExistentId_SP010() throws SQLException {
        // SP010 - Xóa mã sản phẩm không tồn tại
        int result = dao.delete("999");
        assertEquals(0, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_InvalidId_SP011() throws SQLException {
        // SP011 - Mã sản phẩm không hợp lệ
        dao.delete("$#@");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_NullId_SP012() throws SQLException {
        // SP012 - Thiếu mã sản phẩm
        dao.delete(null);
    }
    
    /**
     * Test of select, of class SanPhamDAO.
     */
    @Test
    public void testSelectAll_WhenDataIsEmpty_SP013() throws SQLException {
        // SP013 - Cơ sở dữ liệu có dữ liệu
        List<SanPhamDTO> list = dao.selectAll();
        assertNull(list);
    }
    
    @Test
    public void testSelectAll_WhenDatabaseHasData_SP014() throws SQLException {
        // SP014 - Cơ sở dữ liệu có dữ liệu
        List<SanPhamDTO> list = dao.selectAll();
        assertNotNull(list);
    }

    @Test
    public void testSelectById_Exists_SP015() throws SQLException {
        // SP015 - Tìm sản phẩm theo ID có tồn tại
        SanPhamDTO result = dao.selectById("1");
        assertNotNull(result);
        assertEquals(1, result.getMasp());
    }

    @Test
    public void testSelectById_NotExists_SP016() throws SQLException {
        // SP016 - ID không tồn tại
        SanPhamDTO result = dao.selectById("9999");
        assertNull(result);
    }

    @Test(expected = Exception.class)
    public void testSelectById_Invalid_SP017() throws SQLException {
        // SP017 - ID không hợp lệ
        dao.selectById("%%%  ");
    }

    @Test(expected = Exception.class)
    public void testSelectById_Null_SP018() throws SQLException {
        // SP018 - ID null
        dao.selectById(null);
    }

    @Test
    public void testSelectByPhienBan_Exists_SP019() throws SQLException {
        // SP019 - Phiên bản có tồn tại
        SanPhamDTO result = dao.selectByPhienBan("2");
        assertNotNull(result);
        assertEquals(2, result.getPhienbanhdh());
    }

    @Test
    public void testSelectByPhienBan_NotExists_SP020() throws SQLException {
        // SP020 - Phiên bản không tồn tại
        SanPhamDTO result = dao.selectByPhienBan("100");
        assertNull(result);
    }

    @Test(expected = Exception.class)
    public void testSelectByPhienBan_Invalid_SP021() throws SQLException {
        // SP021 - Phiên bản không hợp lệ
        dao.selectByPhienBan("$$$");
    }

    @Test(expected = Exception.class)
    public void testSelectByPhienBan_Null_SP022() throws SQLException {
        // SP022 - Phiên bản null
        dao.selectByPhienBan(null);
    }

    @Test
    public void testGetAutoIncrement_WhenDatabaseHasData_023() throws SQLException {
        // SP024 - Kiểm tra auto increment khi db có data
        int value = dao.getAutoIncrement();
        assertTrue(value > 1);
    }
    
    @Test
    public void testGetAutoIncrement_WhenDatabaseIsEmpty_024() throws SQLException {
        // SP024 - Kiểm tra auto increment khi db rỗng
        int value = dao.getAutoIncrement();
        assertTrue(value == 1);
    }
    
    
    /**
     * Test update so luong voi ma sp
     */    

    @Test
    public void testUpdateSoLuongTon_Valid_SP025() throws SQLException {
        // SP025 - Cập nhật số lượng tồn hợp lệ
        int result = dao.updateSoLuongTon(1, 5);
        assertEquals(1, result);
    }

    @Test
    public void testUpdateSoLuongTon_NoExist_SP026() throws SQLException {
        // SP026 - masp không tồn tại
        int result = dao.updateSoLuongTon(-1, 5);
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSoLuongTon_InvalidQuantity_SP027() throws SQLException {
        // SP027 - số lượng âm
        int result = dao.updateSoLuongTon(1, -999);
        assertEquals(0, result);
    }

//    @Test(expected = Exception.class)
//    public void testUpdateSoLuongTon_NullMasp_SP028() throws SQLException {
//        // SP028 - masp null
//        dao.updateSoLuongTon(null, 5);
//    }
//
//    @Test(expected = Exception.class)
//    public void testUpdateSoLuongTon_NullSoLuong_SP029() throws SQLException {
//        // SP029 - số lượng null
//        int result = dao.updateSoLuongTon(1, null);
//    }
    
}
