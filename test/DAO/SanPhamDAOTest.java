/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.SanPhamDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
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
    public void testInsert_ValidProduct() throws SQLException {
        // TC01 - Thêm sản phẩm đủ thông tin
        SanPhamDTO sp1 = new SanPhamDTO(0, "Sản phẩm A", "image_a.jpg", 1, "Chip A", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.insert(sp1);
        assertEquals(1, result);
    }

    @Test
    public void testInsert_ExistingProductId() throws SQLException {
        // TC03 - Thêm sản phẩm trùng mã
        SanPhamDTO sp3 = new SanPhamDTO(1, "Sản phẩm B", "image_b.jpg", 1, "Chip B", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.insert(sp3);
        assertEquals(0, result);
    }

    @Test
    public void testInsert_InvalidField() throws SQLException {
        // TC04 - Thêm sản phẩm với trường dữ liệu không hợp lệ
        SanPhamDTO sp4 = new SanPhamDTO(0, "Sản phẩm C", "image_c.jpg", -1, "Chip C", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.insert(sp4);
        assertEquals(0, result);
    }

    @Test
    public void testInsert_MissingField() throws SQLException {
        // TC05 - Thêm sản phẩm thiếu tên
        SanPhamDTO sp5 = new SanPhamDTO(0, null, "image_c.jpg", 1, "Chip C", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.insert(sp5);
        assertEquals(0, result);
    }

    @Test
    public void testUpdate_ValidProduct() throws SQLException {
        // TC06 - Cập nhật sản phẩm hợp lệ
        SanPhamDTO sp1 = new SanPhamDTO(1, "Sản phẩm A", "image_a.jpg", 1, "Chip A", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 10);
        int result = dao.update(sp1);
        assertEquals(1, result);
    }

    @Test
    public void testUpdate_NonExistentId() throws SQLException {
        // TC07 - Cập nhật sản phẩm không tồn tại
        SanPhamDTO sp2 = new SanPhamDTO(9999, "Sản phẩm B", "image_b.jpg", 1, "Chip B", 6000, 6.8, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 5);
        int result = dao.update(sp2);
        assertEquals(0, result);
    }

    @Test
    public void testUpdate_InvalidField() throws SQLException {
        // TC08 - Cập nhật sản phẩm với trường không hợp lệ
        SanPhamDTO t = new SanPhamDTO(2, "Sản phẩm C", "image_c.jpg", -1, "Chip C", 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 5);
        int result = dao.update(t);
        assertEquals(0, result);
    }

    @Test
    public void testUpdate_MissingField() throws SQLException {
        // TC09 - Cập nhật sản phẩm với dữ liệu thiếu
        SanPhamDTO t = new SanPhamDTO(2, "Sản phẩm C", "image_c.jpg", 1, null, 5000, 6.5, 1, 2,
                "Camera sau 12MP", "Camera trước 8MP", 24, 1, 1, 5);
        int result = dao.update(t);
        assertEquals(0, result);
    }

    @Test
    public void testDelete_ValidId() throws SQLException {
        // TC010 - Xóa sản phẩm hợp lệ
        int result = dao.delete("1");
        assertEquals(1, result);
    }

    @Test
    public void testDelete_NonExistentId() throws SQLException {
        // TC011 - Xóa mã sản phẩm không tồn tại
        int result = dao.delete("999");
        assertEquals(0, result);
    }

    @Test(expected = SQLException.class)
    public void testDelete_InvalidId() throws SQLException {
        // TC012 - Mã sản phẩm không hợp lệ
        dao.delete("abc");
    }

    @Test(expected = SQLException.class)
    public void testDelete_NullId() throws SQLException {
        // TC013 - Thiếu mã sản phẩm
        dao.delete(null);
    }

    @Test
    public void testSelectAll() throws SQLException {
        // TC014 - Cơ sở dữ liệu có dữ liệu
        List<SanPhamDTO> list = dao.selectAll();
        assertNotNull(list);
    }

    @Test
    public void testSelectById_Exists() throws SQLException {
        // TC016 - Tìm sản phẩm theo ID có tồn tại
        SanPhamDTO result = dao.selectById("1");
        assertNotNull(result);
        assertEquals(1, result.getMasp());
    }

    @Test
    public void testSelectById_NotExists() throws SQLException {
        // TC017 - ID không tồn tại
        SanPhamDTO result = dao.selectById("9999");
        assertNull(result);
    }

    @Test(expected = SQLException.class)
    public void testSelectById_Invalid() throws SQLException {
        // TC018 - ID không hợp lệ
        dao.selectById("abc");
    }

    @Test(expected = SQLException.class)
    public void testSelectById_Null() throws SQLException {
        // TC019 - ID null
        dao.selectById(null);
    }

    @Test
    public void testSelectByPhienBan_Exists() throws SQLException {
        // TC020 - Phiên bản có tồn tại
        SanPhamDTO result = dao.selectByPhienBan("A");
        assertNotNull(result);
    }

    @Test
    public void testSelectByPhienBan_NotExists() throws SQLException {
        // TC021 - Phiên bản không tồn tại
        SanPhamDTO result = dao.selectByPhienBan("Z");
        assertNull(result);
    }

    @Test(expected = SQLException.class)
    public void testSelectByPhienBan_Invalid() throws SQLException {
        // TC022 - Phiên bản không hợp lệ
        dao.selectByPhienBan("$$$");
    }

    @Test(expected = SQLException.class)
    public void testSelectByPhienBan_Null() throws SQLException {
        // TC023 - Phiên bản null
        dao.selectByPhienBan(null);
    }

    @Test
    public void testGetAutoIncrement() throws SQLException {
        // TC024 - Kiểm tra auto increment hiện tại
        int value = dao.getAutoIncrement();
        assertTrue(value >= 1);
    }

    @Test
    public void testUpdateSoLuongTon_Valid() throws SQLException {
        // TC026 - Cập nhật số lượng tồn hợp lệ
        int result = dao.updateSoLuongTon(1, 5);
        assertEquals(1, result);
    }

    @Test
    public void testUpdateSoLuongTon_NonExistent() throws SQLException {
        // TC027 - masp không tồn tại
        int result = dao.updateSoLuongTon(-1, 5);
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSoLuongTon_InvalidQuantity() throws SQLException {
        // TC028 - số lượng âm
        int result = dao.updateSoLuongTon(1, -999);
        assertEquals(0, result);
    }

//    @Test(expected = SQLException.class)
//    public void testUpdateSoLuongTon_NullMasp() throws SQLException {
//        // TC029 - masp null
//        dao.updateSoLuongTon(null, 5);
//    }
//
//    @Test
//    public void testUpdateSoLuongTon_NullSoLuong() throws SQLException {
//        // TC030 - số lượng null
//        int result = dao.updateSoLuongTon(1, null);
//        assertEquals(0, result);
//    }

    
}
