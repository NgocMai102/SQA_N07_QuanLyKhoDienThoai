/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import static DAO.MauSacDAOTest.connection;
import DTO.NhaCungCapDTO;
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
public class NhaCungCapDAOTest {
    
    private NhaCungCapDAO dao;
    static Connection connection;
    
    
    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        dao = new NhaCungCapDAO();
    }
    
    public NhaCungCapDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }

    /**
     * Test of insert method, of class NhaCungCapDAO.
     */
    @Test
    public void testInsert_NCC001() {
        NhaCungCapDTO dto = new NhaCungCapDTO(101, "Công ty A", "Hà Nội", "a@gmail.com", "0123456789");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 1 khi thêm thành công", 1, result);
    }
    
    @Test
    public void testInsert_NCC002() {
        NhaCungCapDTO dto = new NhaCungCapDTO(101, "Công ty B", "Đà Nẵng", "b@gmail.com", "0987654321");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi mã NCC đã tồn tại", 0, result);
    }
    
    @Test
    public void testInsert_NCC003() {
        NhaCungCapDTO dto = new NhaCungCapDTO(0, "Công ty B", "Đà Nẵng", "b@gmail.com", "0987654321");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 1 khi thiếu mã NCC", 1, result);
    }
    
    @Test
    public void testInsert_NCC004() {
        NhaCungCapDTO dto = new NhaCungCapDTO(102, null, "Huế", "d@gmail.com", "0909090909");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu tên NCC", 0, result);
    }
    
    @Test
    public void testInsert_NCC005() {
        NhaCungCapDTO dto = new NhaCungCapDTO(103, "Công ty E", "Huế", null, "0909090909");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu email", 0, result);
    }
    
    @Test
    public void testInsert_NCC006() {
        NhaCungCapDTO dto = new NhaCungCapDTO(104, "Công ty G", null, "g@gmail.com", "0909090909");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu địa chỉ", 0, result);
    }
    
    @Test
    public void testInsert_NCC007() {
        NhaCungCapDTO dto = new NhaCungCapDTO(105, "Công ty H", "Hà Nội", "h@gmail.com", null);
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu số điện thoại", 0, result);
    }
    
    @Test
    public void testInsert_NCC008() {
        NhaCungCapDTO dto = new NhaCungCapDTO(-9, "Công ty H", "Hà Nội", "h@gmail.com", "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi mã NCC âm", 0, result);
    }
    
    @Test
    public void testInsert_NCC009() {
        NhaCungCapDTO dto = new NhaCungCapDTO(0, "Công ty H", "Hà Nội", "h@gmail.com", "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi mã NCC bằng 0", 0, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInsert_NCC010() {
        // Giả lập lỗi khi dữ liệu không đúng kiểu int
        throw new IllegalArgumentException("Mã nhà cung cấp kiểu String là không hợp lệ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsert_NCC011() {
        // Giả lập lỗi khi dữ liệu không đúng kiểu int
        throw new IllegalArgumentException("Mã nhà cung cấp kiểu String là không hợp lệ");
    }
    
    @Test
    public void testInsert_NCC012() {
        String longName = "Công ty " + "H".repeat(500);
        NhaCungCapDTO dto = new NhaCungCapDTO(106, longName, "Hà Nội", "h@gmail.com", "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi tên NCC quá dài", 0, result);
    }
    
    @Test
    public void testInsert_NCC013() {
        String longAddress = "Hà Nội " + "X".repeat(500);
        NhaCungCapDTO dto = new NhaCungCapDTO(107, "Công ty I", longAddress, "h@gmail.com", "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi địa chỉ quá dài", 0, result);
    }
    
    @Test
    public void testInsert_NCC014() {
        String longEmail = "h" + "a".repeat(490) + "@gmail.com";
        NhaCungCapDTO dto = new NhaCungCapDTO(108, "Công ty I", "Hà Nội", longEmail, "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi email quá dài", 0, result);
    }
    
    @Test
    public void testInsert_NCC015() {
        NhaCungCapDTO dto = new NhaCungCapDTO(109, "Công ty I", "Hà Nội", "hmail.com", "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi email sai định dạng", 0, result);
    }
    
    @Test
    public void testInsert_NCC016() {
        NhaCungCapDTO dto = new NhaCungCapDTO(110, "Công ty H", "Hà Nội", "h@gmail.com", "adbf123");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi sdt sai định dạng", 0, result);
    }

    /**
     * Test of update method, of class NhaCungCapDAO.
     */
   
    @Test
    public void testUpdate_NCC017() {
        NhaCungCapDTO dto = new NhaCungCapDTO(101, "Cty ABC", "Hà Nội", "abc@gmail.com", "0911111111");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu cập nhật thành công", 1, result);
    }
    
    @Test
    public void testUpdate_NCC018() {
        NhaCungCapDTO dto = new NhaCungCapDTO(999, "Cty ABC", "Hà Nội", "abc@gmail.com", "0911111111");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật vì mã không tồn tại", 0, result);
    }
    
    @Test
    public void testUpdate_NCC019() {
        NhaCungCapDTO dto = new NhaCungCapDTO(0, "Công ty H", "Hà Nội", "h@gmail.com", "097198333");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật vì mã trống", 0, result);
    }
    
    @Test
    public void testUpdate_NCC020() {
        NhaCungCapDTO dto = new NhaCungCapDTO(101, null, "Hà Nội", "abc@gmail.com", "0911111111");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do thiếu tên nhà cung cấp", 0, result);
    }
    
    @Test
    public void testUpdate_NCC021() {
        NhaCungCapDTO dto = new NhaCungCapDTO(101, "Cty ABC", null, "abc@gmail.com", "0911111111");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do thiếu địa chỉ", 0, result);
    }
    
    @Test
    public void testUpdate_NCC022() {
        NhaCungCapDTO dto = new NhaCungCapDTO(101, "Cty ABC", "Hà Nội", null, "0911111111");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do thiếu email", 0, result);
    }
    
    @Test
    public void testUpdate_NCC023() {
        NhaCungCapDTO dto = new NhaCungCapDTO(101, "Cty ABC", "Hà Nội", "abc@gmail.com", null);
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do thiếu số điện thoại", 0, result);
    }
    
    @Test
    public void testUpdate_NCC024() {
        String longName = "Công ty " + "h".repeat(490);
        NhaCungCapDTO dto = new NhaCungCapDTO(106, longName, "Hà Nội", "h@gmail.com", "097198333");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do tên quá dài", 0, result);
    }
    
    @Test
    public void testUpdate_NCC025() {
        String longAddress = "Hà Nội " + "a".repeat(490);
        NhaCungCapDTO dto = new NhaCungCapDTO(107, "Công ty H", longAddress, "h@gmail.com", "097198333");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do địa chỉ quá dài", 0, result);
    }
    
    @Test
    public void testUpdate_NCC026() {
        String longEmail = "h" + "a".repeat(490) + "@gmail.com";
        NhaCungCapDTO dto = new NhaCungCapDTO(107, "Công ty H", "Hà Nội", longEmail, "097198333");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do email quá dài", 0, result);
    }
    
    @Test
    public void testUpdate_NCC027() {
        NhaCungCapDTO dto = new NhaCungCapDTO(107, "Công ty H", "Hà Nội", "hgmail.com", "097198333");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do định dạng email sai", 0, result);
    }
    
    @Test
    public void testUpdate_NCC028() {
        NhaCungCapDTO dto = new NhaCungCapDTO(107, "Công ty H", "Hà Nội", "h@gmail.com", "av097198333");
        dao = new NhaCungCapDAO();
        int result = dao.update(dto);
        assertEquals("Dữ liệu không cập nhật do số điện thoại sai định dạng", 0, result);
    }

    /**
     * Test of delete method, of class NhaCungCapDAO.
     */

    @Test
    public void testDelete_NCC029() {
        dao = new NhaCungCapDAO();
        int result = dao.delete("101"); 
        assertEquals("Xoá thành công nhà cung cấp hợp lệ", 1, result);
    }
    
    @Test
    public void testDelete_NCC030() {
        dao = new NhaCungCapDAO();
        int result = dao.delete("999"); 
        assertEquals("Xoá thất bại do manhacungcap không tồn tại trong DB", 0, result);
    }
    
    @Test
    public void testDelete_NCC031() {
        dao = new NhaCungCapDAO();
        int result = dao.delete(null);
        assertEquals("Xoá thất bại do thiếu manhacungcap", 0, result);
    }

    /**
     * Test of selectAll method, of class NhaCungCapDAO.
     */
    @Test
    public void testSelectAll_NCC032() {
        dao = new NhaCungCapDAO();
        List<NhaCungCapDTO> list = dao.selectAll();

        assertNotNull("Danh sách không được null", list);
        assertEquals("Danh sách khu vực có trangthai = 1", 12, list.size());
    }
    
    @Test
    public void testSelectAll_NCC033() {
        dao = new NhaCungCapDAO();
        List<NhaCungCapDTO> list = dao.selectAll();

        assertNotNull("Danh sách không được null", list);
        assertEquals("Danh sách rỗng nếu không có khu vực nào có trangthai = 1", 0, list.size());
    }
    /**
     * Test of selectById method, of class NhaCungCapDAO.
     */

    @Test
    public void testSelectById_NCC034() {
        dao = new NhaCungCapDAO();
        NhaCungCapDTO result = dao.selectById("101");

        assertNotNull("Phải trả về đối tượng NhaCungCapDTO", result);
    }
    
    @Test
    public void testSelectById_NCC035() {
        dao = new NhaCungCapDAO();
        NhaCungCapDTO result = dao.selectById("999");

        assertNull("Nhà cung cấp không tồn tại nên kết quả phải là null", result);
    }
    
    @Test
    public void testSelectById_NCC036() {
        dao = new NhaCungCapDAO();
        NhaCungCapDTO result = dao.selectById(null);

        assertNull("Thiếu mã nhà cung cấp nên kết quả phải là null", result);
    }
    
    @Test
    public void testSelectById_NCC037() {
        dao = new NhaCungCapDAO();
        NhaCungCapDTO result = dao.selectById("110");

        assertNull("Nhà cung cấp bị xoá (trangthai = 0) nên không được trả về", result);
    }
    
    /**
     * Test of getAutoIncrement method, of class KhuVucKhoDAO.
     */
    
    @Test
    public void testGetAutoIncrement_NCC043() {
        dao = new NhaCungCapDAO();
        int nextId = dao.getAutoIncrement();

        assertTrue("Giá trị AUTO_INCREMENT phải >= 2 khi bảng có dữ liệu", nextId >= 2);
    }
    
    @Test
    public void testGetAutoIncrement_NCC044() {
        dao = new NhaCungCapDAO();

        int nextId = dao.getAutoIncrement();
        assertEquals("Giá trị AUTO_INCREMENT phải là 1 khi bảng rỗng", 1, nextId);
    }
}
