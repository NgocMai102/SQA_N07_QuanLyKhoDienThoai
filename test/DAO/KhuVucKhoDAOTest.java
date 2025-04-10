/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.KhuVucKhoDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
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
    private Connection conn;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new KhuVucKhoDAO();
    }
   
    
    public KhuVucKhoDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of insert method, of class KhuVucKhoDAO.
     */  
    @Test
    public void testInsert_ThemMoiThanhCong() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(6, "Khu A", "Tầng trệt");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm mới khu vực hợp lệ phải thành công", 1, result);
    }

    @Test
    public void testInsert_TrungMaKhuVuc() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(6, "Khu B", "Tầng 1");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm thất bại do trùng mã khu vực", 0, result);
    }

    @Test
    public void testInsert_ThieuTenKhuVuc() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(102, null, "Ghi chú");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm thất bại do thiếu tenkhuvuc", 0, result);
    }

    @Test
    public void testInsert_ThieuGhiChu() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(103, "Khu A", null);
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm thất bại do thiếu ghichu", 0, result);
    }

    @Test
    public void testInsert_MaKhuVucKhongHopLe01() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(-9, "Khu A", "Tầng trệt");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm thất bại do makhuvuc không hợp lệ", 0, result);
    }
    
    @Test
    public void testInsert_MaKhuVucKhongHopLe02() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(0, "Khu A", "Tầng trệt");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm thất bại do makhuvuc không hợp lệ", 0, result);
    }

    @Test
    public void testInsert_TenKhuVucQuaDai() {
        String longName = "K".repeat(500);
        KhuVucKhoDTO dto = new KhuVucKhoDTO(104, longName, "Ghi chú khu G");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm thất bại do tenkhuvuc quá dài", 0, result);
    }

    @Test
    public void testInsert_GhiChuQuaDai() {
        String longNote = "G".repeat(500);
        KhuVucKhoDTO dto = new KhuVucKhoDTO(105, "Khu E", longNote);
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.insert(dto);
        assertEquals("Thêm thất bại do ghichu quá dài", 0, result);
    }

    /**
     * Test of update method, of class KhuVucKhoDAO.
     */
    
    @Test
    public void testUpdate_TCU01_HopLe() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(6, "Khu A Mới", "Đã cập nhật");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thành công với dữ liệu hợp lệ", 1, result);
    }

    @Test
    public void testUpdate_TCU02_MaKhongTonTai() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(999, "Khu mới", "Không có mã");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do makhuvuc không tồn tại", 0, result);
    }
    

    @Test
    public void testUpdate_TCU04_ThieuTenKhuVuc() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(102, null, "Ghi chú gì đó");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do thiếu tenkhuvuc", 0, result);
    }

    @Test
    public void testUpdate_TCU05_ThieuGhiChu() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(103, "Khu A", null);
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do thiếu ghichu", 0, result);
    }

    @Test
    public void testUpdate_TCU06_TenQuaDai() {
        String tenKhuVuc = "A".repeat(500);
        KhuVucKhoDTO dto = new KhuVucKhoDTO(104, tenKhuVuc, "Ghi chú khu G");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do tenkhuvuc quá dài", 0, result);
    }

    @Test
    public void testUpdate_TCU07_GhiChuQuaDai() {
        String ghiChu = "B".repeat(500);
        KhuVucKhoDTO dto = new KhuVucKhoDTO(105, "Khu E", ghiChu);
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do ghichu quá dài", 0, result);
    }

    @Test
    public void testUpdate_TCU09_KhuDaXoa() {
        KhuVucKhoDTO dto = new KhuVucKhoDTO(4, "Khu A Mới", "Đã cập nhật");
        KhuVucKhoDAO dao = new KhuVucKhoDAO();
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do khu đã bị xóa mềm", 0, result);
    }
    
    /**
     * Test of delete method, of class KhuVucKhoDAO.
     */
    
    @Test
    public void testDelete_TCD01_HopLe() {
        int result = dao.delete("6");
        assertEquals("Cập nhật trạng thái thành công với makhuvuc hợp lệ", 1, result);
    }

    @Test
    public void testDelete_TCD02_MaKhongTonTai() {
        int result = dao.delete("999");
        assertEquals("Cập nhật thất bại do makhuvuc không tồn tại", 0, result);
    }

    /**
     * Test of selectAll method, of class KhuVucKhoDAO.
     */
    
    @Test
    public void testGetAllActive_TCS01_CoDuLieu() {
        List<KhuVucKhoDTO> danhSach = dao.selectAll();
        assertNotNull(danhSach);
        assertFalse(danhSach.isEmpty());
        assertEquals("Phải trả về đúng số lượng khu vực active", 5, danhSach.size());
    }

    
    /**
     * Test of selectById method, of class KhuVucKhoDAO.
     */

    @Test
    public void testSelectById_TCG01_MaKhuVucTonTai() {
        System.out.println("TCG01 - Lấy khu vực theo makhuvuc thành công");
        KhuVucKhoDAO instance = new KhuVucKhoDAO();
        KhuVucKhoDTO result = instance.selectById("4");
        
        assertNotNull("Phải trả về thông tin khu vực", result);
        assertEquals("Mã khu vực phải khớp", 4, result.getMakhuvuc());
    }

    @Test
    public void testSelectById_TCG02_MaKhuVucKhongTonTai() {
        System.out.println("TCG02 - Lấy khu vực thất bại do makhuvuc không hợp lệ");
        String[] invalidInputs = {"999", "0", "-1", "1.5"};
        KhuVucKhoDAO instance = new KhuVucKhoDAO();
        
        for (String makhuvuc : invalidInputs) {
            KhuVucKhoDTO result = instance.selectById(makhuvuc);
            assertNull("Phải trả về null khi makhuvuc không hợp lệ: " + makhuvuc, result);
        }
    }

    @Test
    public void testSelectById_TCG04_KhuVucDaXoaMem() {
        System.out.println("TCG04 - Lấy khu vực thất bại do bản ghi đã bị xóa mềm");
        String makhuvuc = "6"; // Giả sử khu vực này có trong DB nhưng trạng thái là 0
        KhuVucKhoDAO instance = new KhuVucKhoDAO();
        KhuVucKhoDTO result = instance.selectById(makhuvuc);

        assertNull("Phải trả về null nếu bản ghi đã bị xóa mềm", result);
    }

    /**
     * Test of getAutoIncrement method, of class KhuVucKhoDAO.
     */

    
}
