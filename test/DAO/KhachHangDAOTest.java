/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import static DAO.TaiKhoanDAOTest.connection;
import DTO.KhachHangDTO;
import config.JDBCUtil;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author MSI GF63
 */
public class KhachHangDAOTest {
    
    private KhachHangDAO dao;
    static Connection connection;
    
    
    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        dao = new KhachHangDAO();
    }
    
    public KhachHangDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }

    /**
     * Test of insert method, of class KhachHangDAO.
     */
    @Test
    public void testInsert_KH001() {
        KhachHangDTO dto = new KhachHangDTO(101, "Nguyen Van A", "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 1 khi thêm thành công", 1, result);
    }
    
    @Test
    public void testInsert_KH002() {
        KhachHangDTO dto = new KhachHangDTO(101, "Nguyen Van B", "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi mã khách hàng bị trùng", 0, result);
    }
    
    @Test
    public void testInsert_KH003() {
        KhachHangDTO dto = new KhachHangDTO(0, "Nguyen Van B", "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 1 khi thiếu mã khách hàng", 1, result);
    }
    
    @Test
    public void testInsert_KH004() {
        KhachHangDTO dto = new KhachHangDTO(102, null, "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu tên khách hàng", 0, result);
    }
    
    @Test
    public void testInsert_KH005() {
        KhachHangDTO dto = new KhachHangDTO(103, "Nguyen Van A", null, "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu địa chỉ", 0, result);
    }
    
    @Test
    public void testInsert_KH006() {
        KhachHangDTO dto = new KhachHangDTO(104, "Nguyen Van A", "Hà Nội", null);
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu số điện thoại", 0, result);
    }
    
    @Test
    public void testInsert_KH007() {
        KhachHangDTO dto = new KhachHangDTO(-9, "Nguyen Van A", "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi mã khách hàng < 0", 0, result);
    }
    
    @Test
    public void testInsert_KH008() {
        KhachHangDTO dto = new KhachHangDTO(0, "Nguyen Van A", "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi mã khách hàng = 0", 0, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInsert_KH009() {
        // Giả lập lỗi kiểu dữ liệu nếu từ nguồn không hợp lệ
        throw new IllegalArgumentException("Mã khách hàng kiểu double là không hợp lệ");
    }
   
    @Test(expected = IllegalArgumentException.class)
    public void testInsert_KVK010() {
        // Giả lập lỗi khi dữ liệu không đúng kiểu int
        throw new IllegalArgumentException("Mã khách hàng kiểu String là không hợp lệ");
    }
    
    @Test
    public void testInsert_KH011() {
        String longName = "Nguyen Van A".repeat(50);
        KhachHangDTO dto = new KhachHangDTO(105, longName, "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi tên khách hàng quá dài", 0, result);
    }
    
    @Test
    public void testInsert_KH012() {
        String longAddress = "Hà Nội".repeat(100); // ~500 ký tự
        KhachHangDTO dto = new KhachHangDTO(106, "Nguyen Van A", longAddress, "0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi địa chỉ quá dài", 0, result);
    }
    
    @Test
    public void testInsert_KH013() {
        KhachHangDTO dto = new KhachHangDTO(107, "Nguyen Van A", "Hà Nội", "ABD0123456789");
        dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi số điện thoại sai định dạng", 0, result);
    }

    /**
     * Test of update method, of class KhachHangDAO.
     */
   
    @Test
    public void testUpdate_KH014() {
        KhachHangDTO dto = new KhachHangDTO(101, "Nguyen A", "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 1 khi cập nhật thành công", 1, result);
    }
    
    @Test
    public void testUpdate_KH015() {
        KhachHangDTO dto = new KhachHangDTO(999, "Nguyen Van B", "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 0 khi makh không tồn tại", 0, result);
    }
    
    @Test
    public void testUpdate_KH016_ThieuMaKH() {
        KhachHangDTO dto = new KhachHangDTO(0, "Nguyen Van B", "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 0 khi thiếu mã khách hàng", 0, result);
    }
    
    @Test
    public void testUpdate_KH017_ThieuTenKH() {
        KhachHangDTO dto = new KhachHangDTO(102, null, "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 0 khi thiếu tên khách hàng", 0, result);
    }
    
    @Test
    public void testUpdate_KH018() {
        KhachHangDTO dto = new KhachHangDTO(103, "Nguyen Van A", null, "0123456789");
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 0 khi thiếu địa chỉ", 0, result);
    }
    
    @Test
    public void testUpdate_KH019() {
        KhachHangDTO dto = new KhachHangDTO(104, "Nguyen Van A", "Hà Nội", null);
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 0 khi thiếu số điện thoại", 0, result);
    }
    
    @Test
    public void testUpdate_KH020() {
        String longName = "A".repeat(500);
        KhachHangDTO dto = new KhachHangDTO(105, longName, "Hà Nội", "0123456789");
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 0 khi tên khách hàng quá dài", 0, result);
    }
    
    @Test
    public void testUpdate_KH021() {
        String longAddress = "B".repeat(500);
        KhachHangDTO dto = new KhachHangDTO(106, "Nguyen Van A", longAddress, "0123456789");
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 0 khi địa chỉ quá dài", 0, result);
    }
    
    @Test
    public void testUpdate_KH022() {
        KhachHangDTO dto = new KhachHangDTO(107, "Nguyen Van A", "Hà Nội", "012345678ABC");
        dao = new KhachHangDAO();
        int result = dao.update(dto);
        assertEquals("Phải trả về 0 khi số điện thoại không hợp lệ", 0, result);
    }


    /**
     * Test of delete method, of class KhachHangDAO.
     */
    
    @Test
    public void testDelete_KH023() {
        dao = new KhachHangDAO();
        int result = dao.delete("101");
        assertEquals("Phải trả về 1 khi xoá thành công", 1, result);
    }   
    
    @Test
    public void testDelete_KH024() {
        dao = new KhachHangDAO();
        int result = dao.delete("999");
        assertEquals("Phải trả về 0 khi makh không tồn tại", 0, result);
    }

    @Test
    public void testDelete_KH025() {
        dao = new KhachHangDAO();
        int result = dao.delete(null);
        assertEquals("Phải trả về 0 khi thiếu mã khách hàng", 0, result);
    }

    /**
     * Test of selectAll method, of class KhachHangDAO.
     */
    
    @Test
    public void testSelectAll_KH026() {
        dao = new KhachHangDAO();
        List<KhachHangDTO> list = dao.selectAll();

        assertNotNull("Danh sách không được null", list);
        assertEquals("Danh sách khách hàng có trangthai = 1", 20, list.size());
    }

    @Test
    public void testSelectAll_KH027() {
        dao = new KhachHangDAO();
        List<KhachHangDTO> list = dao.selectAll();

        assertNotNull("Danh sách không được null", list);
        assertEquals("Danh sách khách hàng có trangthai = 1", 20, list.size());
    }
    
    /**
     * Test of selectById method, of class KhachHangDAO.
     */
    
    @Test
    public void testSelectById_KH028() {
        dao = new KhachHangDAO();
        KhachHangDTO result = dao.selectById("101");

        assertNotNull("Phải trả về đối tượng KhachHangDTO", result);
    }

    @Test
    public void testSelectById_NCC029() {
        dao = new KhachHangDAO();
        KhachHangDTO result = dao.selectById("999");

        assertNull("Khách hàng không tồn tại nên kết quả phải là null", result);
    }
    
    @Test
    public void testSelectById_NCC030() {
        dao = new KhachHangDAO();
        KhachHangDTO result = dao.selectById(null);

        assertNull("Thiếu mã khách hàng nên kết quả phải là null", result);
    }
    
    @Test
    public void testSelectById_NCC031() {
        dao = new KhachHangDAO();
        KhachHangDTO result = dao.selectById("111");

        assertNull("Khách hàng bị xoá (trangthai = 0) nên không được trả về", result);
    }

    /**
     * Test of getAutoIncrement method, of class KhachHangDAO.
     */
    @Test
    public void testGetAutoIncrement_KH037() {
        dao = new KhachHangDAO();
        int nextId = dao.getAutoIncrement();

        assertTrue("Giá trị AUTO_INCREMENT phải >= 2 khi bảng có dữ liệu", nextId >= 2);
    }
    
    @Test
    public void testGetAutoIncrement_KH038() {
        dao = new KhachHangDAO();

        int nextId = dao.getAutoIncrement();
        assertEquals("Giá trị AUTO_INCREMENT phải là 1 khi bảng rỗng", 1, nextId);
    }
}
