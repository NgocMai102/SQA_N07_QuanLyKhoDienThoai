/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.XuatXuDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maii
 */
public class XuatXuDAOTest {
    
    private Connection conn;
    XuatXuDAO dao;
    
    public XuatXuDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new XuatXuDAO();
    }

    @After
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.rollback(); // rollback các thay đổi
            conn.close();
        }
    }

//    /**
//     * Test of insert method, of class XuatXuDAO.
//     */
//    // TT_128: Đủ thông tin hợp lệ
//    @Test
//    public void testInsert_DuThongTin_TT_128() {
//        XuatXuDTO dto = new XuatXuDTO(0, "Nhật Bản");
//        int result = dao.insert(dto);
//        assertEquals(1, result); // Thành công
//    }
//
//    // TT_129: Mã đã tồn tại
//    @Test
//    public void testInsert_MaDaTonTai_TT_129() {
//        XuatXuDTO dto = new XuatXuDTO(1, "Nhật Bản"); // Giả sử mã 1 đã có
//        int result = dao.insert(dto);
//        assertEquals(0, result); // Không thêm được vì trùng mã
//    }
//
//    // TT_130: Mã không hợp lệ (âm)
//    @Test
//    public void testInsert_MaKhongHopLe_TT_130() {
//        XuatXuDTO dto = new XuatXuDTO(-1, "Nhật Bản");
//        int result = dao.insert(dto);
//        assertEquals(0, result); // Không thêm được
//    }
//
//    // TT_131: Tên null
//    @Test(expected = Exception.class)
//    public void testInsert_ThieuTen_TT_131() {
//        XuatXuDTO dto = new XuatXuDTO(0, null);
//        dao.insert(dto); // Gây exception
//    }


//    /**
//     * Test of update method, of class XuatXuDAO.
//     */
//    // TT_132: Mã tồn tại, cập nhật hợp lệ
//    @Test
//    public void testUpdate_TonTai_TT_132() {
//        XuatXuDTO dto = new XuatXuDTO(1, "Hồng Kông");
//        int result = dao.update(dto);
//        assertEquals(1, result); // Cập nhật thành công
//    }
//
//    // TT_133: Mã không tồn tại
//    @Test
//    public void testUpdate_KhongTonTai_TT_133() {
//        XuatXuDTO dto = new XuatXuDTO(-1, "Nhật Bản"); // Mã không có trong DB
//        int result = dao.update(dto);
//        assertEquals(0, result); // Không có gì được cập nhật
//    }
//
//    // TT_135: Thiếu dữ liệu (maId null) – giả định bạn truyền kiểu String (nếu là int thì không có null)
//    @Test(expected = IllegalArgumentException.class)
//    public void testUpdate_MaIdNull_TT_134() {
//        XuatXuDTO dto = new XuatXuDTO(1, null); // Nếu bạn dùng kiểu Integer
//        dao.update(dto);
//    }

    
//    /**
//     * Test of delete method, of class XuatXuDAO.
//     */
//    // TT_135: Xóa với mã tồn tại
//    @Test
//    public void testDelete_TT_135() {
//        int result = dao.delete("1");
//        assertEquals(1, result);
//    }
//
//    // TT_136: Xóa với mã không tồn tại
//    @Test
//    public void testDelete_TT_136() {
//        int result = dao.delete("9999");
//        assertEquals(0, result);
//    }
//
//    // TT_137: Xóa với mã không hợp lệ
//    @Test(expected = IllegalArgumentException.class)
//    public void testDelete_TT_137() {
//        dao.delete("-1");
//    }
//
//    // TT_138: Xóa với mã null
//    @Test(expected = NullPointerException.class)
//    public void testDelete_TT_138() {
//        dao.delete(null);
//    }


    /**
     * Test of selectAll method, of class XuatXuDAO.
     */
//    // TT_139: selectAll khi DB trống
//    @Test
//    public void testSelectAll_TT_139() {
//        ArrayList<XuatXuDTO> list = dao.selectAll();
//        assertNotNull(list);
//        assertEquals(0, list.size());
//    }
//
//    // TT_140: selectAll khi DB có dữ liệu
//    @Test
//    public void testSelectAll_TT_140() {
//        ArrayList<XuatXuDTO> list = dao.selectAll();
//        assertNotNull(list);
//        assertTrue(list.size() > 0);
//    }
//
//    /**
//     * Test of selectById method, of class XuatXuDAO.
//     */
    // TT_141: Mã Id tồn tại
    @Test
    public void testSelectById_TT_141() {
        XuatXuDTO result = dao.selectById("1");
        assertNotNull(result);
    }

    // TT_142: Mã Id không tồn tại
    @Test
    public void testSelectById_TT_142() {
        XuatXuDTO result = dao.selectById("9999");
        assertNull(result);
    }

    // TT_143: Mã Id không hợp lệ (chữ cái)
    @Test(expected = NumberFormatException.class)
    public void testSelectById_TT_143() {
        dao.selectById("abc");
    }

    // TT_144: Mã Id là null
    @Test(expected = NullPointerException.class)
    public void testSelectById_TT_144() {
        dao.selectById(null);
    }

    
//    /**
//     * Test of getAutoIncrement method, of class XuatXuDAO.
//     */
//    @Test
//    public void testGetAutoIncrement() {
//        System.out.println("getAutoIncrement");
//        XuatXuDAO instance = new XuatXuDAO();
//        int expResult = 0;
//        int result = instance.getAutoIncrement();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
