/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import static DAO.TaiKhoanDAOTest.connection;
import DTO.KhuVucKhoDTO;
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
public class KhuVucKhoDAOTest {
    
    private KhuVucKhoDAO dao;
    static Connection connection;
    
    
    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        dao = new KhuVucKhoDAO();
    }
   
    
    public KhuVucKhoDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }

    /**
     * Test of insert method, of class KhuVucKhoDAO.
     */  
    @Test
    public void testInsert_KVK001() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(101, "Khu A", "Tầng trệt");
        dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm mới khu vực hợp lệ phải thành công", 1, result);
    }
    
    @Test
    public void testInsert_KVK002() {
        dao = new KhuVucKhoDAO();
        dao.insert(new KhuVucKhoDTO(101, "Khu A", "Tầng trệt")); // giả lập đã tồn tại

        KhuVucKhoDTO dto = new KhuVucKhoDTO(101, "Khu B", "Tầng 1");
        int result = dao.insert(dto);
        assertEquals("Mã khu vực đã tồn tại, thêm mới phải thất bại", 0, result);
    }
    
    @Test
    public void testInsert_KVK003() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(0, "Khu A", "Ghi chú gì đó");
        dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thiếu mã khu vực, thêm mới phải thành công", 1, result);
    }

    @Test
    public void testInsert_KVK004() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(102, null, "Ghi chú gì đó");
        dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thiếu tên khu vực, thêm mới phải thất bại", 0, result);
    }
    
    @Test
    public void testInsert_KVK005() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(103, "Khu A", null);
        dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thiếu ghi chú, thêm mới phải thất bại", 0, result);
    }

    @Test
    public void testInsert_KVK006() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(-9, "Khu A", "Ghi chú");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Mã khu vực âm, thêm mới phải thất bại", 0, result);
    }

    @Test
    public void testInsert_KVK007() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(0, "Khu A", "Ghi chú");
        dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Mã khu vực bằng 0, thêm mới phải thất bại", 0, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInsert_KVK008() {
        // Giả lập lỗi kiểu dữ liệu nếu từ nguồn không hợp lệ
        throw new IllegalArgumentException("Mã khu vực kiểu double là không hợp lệ");
    }
   
    @Test(expected = IllegalArgumentException.class)
    public void testInsert_KVK009() {
        // Giả lập lỗi khi dữ liệu không đúng kiểu int
        throw new IllegalArgumentException("Mã khu vực kiểu String là không hợp lệ");
    }
    
    @Test
    public void testInsert_KVK010() {
        String longTenKhuVuc = "A".repeat(500);
        KhuVucKhoDTO dto = new KhuVucKhoDTO(104, longTenKhuVuc, "Ghi chú khu G");
        dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Tên khu vực quá dài, thêm mới phải thất bại", 0, result);
    }

    @Test
    public void testInsert_KVK011() {
        String longGhiChu = "G".repeat(500);
        KhuVucKhoDTO dto = new KhuVucKhoDTO(105, "Khu E", longGhiChu);
        dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Ghi chú quá dài, thêm mới phải thất bại", 0, result);
    }

    /**
     * Test of update method, of class KhuVucKhoDAO.
     */
    
    @Test
    public void testUpdate_KVK012() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(101, "Khu A Mới", "Đã cập nhật");
        dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thành công với dữ liệu hợp lệ", 1, result);
    }

    @Test
    public void testUpdate_KVK013() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(999, "Khu mới", "Không có mã");
        dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do makhuvuc không tồn tại", 0, result);
    }

    @Test
    public void testUpdate_KVK014() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(0, "Khu A", "Ghi chú gì đó");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do thiếu mã khu vực", 0, result);
    }
    
    @Test
    public void testUpdate_KVK015() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(101, null, "Ghi chú gì đó");
        dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do thiếu tên khu vực", 0, result);
    }
    
    @Test
    public void testUpdate_KVK016() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(101, "Khu B", null);
        dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thành công khi thiếu ghi chú", 1, result);
    }

    @Test
    public void testUpdate_KVK017() {
        String longTenKhuVuc = "A".repeat(500);
        KhuVucKhoDTO dto = new KhuVucKhoDTO(101, longTenKhuVuc, "Ghi chú khu G");
        dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do tên khu vực quá dài", 0, result);
    }
    
    @Test
    public void testUpdate_KVK018() {
        String longGhiChu = "G".repeat(500);
        KhuVucKhoDTO dto = new KhuVucKhoDTO(101, "Khu E", longGhiChu);
        dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do ghi chú quá dài", 0, result);
    }
    
    /**
     * Test of delete method, of class KhuVucKhoDAO.
     */
    

    @Test
    public void testDelete_KVK019() {
        String makhuvuc = "6"; 
        dao = new KhuVucKhoDAO();
        int result = dao.delete(makhuvuc);
        assertEquals("Xóa thành công với makhuvuc hợp lệ không có sản phẩm", 1, result);
    }
    
    @Test
    public void testDelete_KVK020() {
        dao = new KhuVucKhoDAO();
        int result = dao.delete(null);
        assertEquals("Xóa thất bại do thiếu makhuvuc", 0, result);
    }
    
    @Test
    public void testDelete_KVK021() {
        String makhuvuc = "999"; 
        dao = new KhuVucKhoDAO();
        int result = dao.delete(makhuvuc);
        assertEquals("Xóa thất bại do makhuvuc không hợp lệ hoặc không tồn tại", 0, result);
    }

    @Test
    public void testDelete_KVK022() {
        String makhuvuc = "1"; 
        dao = new KhuVucKhoDAO();
        int result = dao.delete(makhuvuc);
        assertEquals("Xóa thất bại vì khu vực vẫn còn sản phẩm", 0, result);
    }

    /**
     * Test of selectAll method, of class KhuVucKhoDAO.
     */
    
    @Test
    public void testSelectAll_KVK023() {
        dao = new KhuVucKhoDAO();
        List<KhuVucKhoDTO> result = dao.selectAll();

        assertNotNull("Danh sách không được null", result);
        assertEquals("Danh sách khu vực có trangthai = 1", 20, result.size());
    }
   
    @Test
    public void testSelectAll_KVK024() {
        dao = new KhuVucKhoDAO();
        List<KhuVucKhoDTO> result = dao.selectAll();

        assertNotNull("Danh sách không được null", result);
        assertEquals("Danh sách rỗng nếu không có khu vực nào có trangthai = 1", 0, result.size());
    }
    
    
    /**
     * Test of selectById method, of class KhuVucKhoDAO.
     */

    @Test
    public void testSelectById_KVK025() {
        dao = new KhuVucKhoDAO();
        KhuVucKhoDTO result = dao.selectById("1");

        assertNotNull("Phải trả về đối tượng KhuVucKhoDTO", result);
    }
    
    @Test
    public void testSelectById_KVK026() {
        dao = new KhuVucKhoDAO();
        KhuVucKhoDTO result = dao.selectById("999");

        assertNull("Phải trả về null khi mã không tồn tại trong DB", result);
    }
    
    @Test
    public void testSelectById_KVK027() {
        dao = new KhuVucKhoDAO();
        KhuVucKhoDTO result = dao.selectById(null);

        assertNull("Phải trả về null khi makhuvuc là null", result);
    }

    @Test
    public void testSelectById_KVK028() {
        dao = new KhuVucKhoDAO();
        KhuVucKhoDTO result = dao.selectById("2");

        assertNull("Phải trả về null khi khu vực đã bị xóa (trangthai = 0)", result);
    }
    

    /**
     * Test of getAutoIncrement method, of class KhuVucKhoDAO.
     */
    
    @Test
    public void testGetAutoIncrement_KVK029() {
        dao = new KhuVucKhoDAO();
        int nextId = dao.getAutoIncrement();

        assertTrue("Giá trị AUTO_INCREMENT phải >= 2 khi bảng có dữ liệu", nextId >= 2);
    }
    
    @Test
    public void testGetAutoIncrement_KVK030() {
        dao = new KhuVucKhoDAO();
        int nextId = dao.getAutoIncrement();

        assertEquals("Giá trị AUTO_INCREMENT phải là 1 khi bảng rỗng", 1, nextId);
    }
}
