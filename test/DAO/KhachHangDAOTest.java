/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.KhachHangDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
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
    private Connection conn;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new KhachHangDAO();
    }
    
    public KhachHangDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of insert method, of class KhachHangDAO.
     */
    @Test
    public void testInsert_TCC01_ThemThanhCong() {
        System.out.println("TCC01 - Thêm khách hàng thành công với dữ liệu hợp lệ");
        KhachHangDTO dto = new KhachHangDTO(99, "Nguyen Van A", "Hà Nội", "0123456789");
        KhachHangDAO dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 1 khi thêm thành công", 1, result);
    }

    @Test
    public void testInsert_TCC02_TrungMaKH() {
        System.out.println("TCC02 - Thêm khách hàng thất bại do trùng makh");
        KhachHangDTO dto = new KhachHangDTO(101, "Nguyen Van B", "Hà Nội", "0123456789");
        KhachHangDAO dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi trùng mã khách hàng", 0, result);
    }

    @Test
    public void testInsert_TCC04_ThieuTenKH() {
        System.out.println("TCC04 - Thêm thất bại do thiếu tên khách hàng");
        KhachHangDTO dto = new KhachHangDTO(102, null, "Hà Nội", "0123456789");
        KhachHangDAO dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu tên khách hàng", 0, result);
    }

    @Test
    public void testInsert_TCC05_ThieuDiaChi() {
        System.out.println("TCC05 - Thêm thất bại do thiếu địa chỉ");
        KhachHangDTO dto = new KhachHangDTO(103, "Nguyen Van A", null, "0123456789");
        KhachHangDAO dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu địa chỉ", 0, result);
    }

    @Test
    public void testInsert_TCC06_ThieuSDT() {
        System.out.println("TCC06 - Thêm thất bại do thiếu số điện thoại");
        KhachHangDTO dto = new KhachHangDTO(104, "Nguyen Van A", "Hà Nội", null);
        KhachHangDAO dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu số điện thoại", 0, result);
    }

    @Test
    public void testInsert_TCC07_MaKHKhongHopLe() {
        System.out.println("TCC07 - Thêm thất bại do makh không hợp lệ");
        KhachHangDAO dao = new KhachHangDAO();
        KhachHangDTO dto = new KhachHangDTO(-9, "Nguyen Van A", "Hà Nội", "0123456789"); // Ép kiểu vì DTO nhận int
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi makh không hợp lệ", 0, result);
    }

    @Test
    public void testInsert_TCC08_TenQuaDai() {
        System.out.println("TCC08 - Thêm thất bại do tên khách hàng quá dài");
        String longName = "Nguyen Van A" + "A".repeat(500);
        KhachHangDTO dto = new KhachHangDTO(105, longName, "Hà Nội", "0123456789");
        KhachHangDAO dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi tên quá dài", 0, result);
    }

    @Test
    public void testInsert_TCC09_DiaChiQuaDai() {
        System.out.println("TCC09 - Thêm thất bại do địa chỉ quá dài");
        String longDiaChi = "Hà Nội" + "B".repeat(500);
        KhachHangDTO dto = new KhachHangDTO(106, "Nguyen Van A", longDiaChi, "0123456789");
        KhachHangDAO dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi địa chỉ quá dài", 0, result);
    }

    @Test
    public void testInsert_TCC10_SdtQuaDai() {
        System.out.println("TCC10 - Thêm thất bại do số điện thoại quá dài");
        String longSDT = "0123" + "4".repeat(500);
        KhachHangDTO dto = new KhachHangDTO(107, "Nguyen Van A", "Hà Nội", longSDT);
        KhachHangDAO dao = new KhachHangDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi số điện thoại quá dài", 0, result);
    }

    /**
     * Test of update method, of class KhachHangDAO.
     */
   

    // Helper: Tạo đối tượng NhaCungCapDTO nhanh
    private KhachHangDTO createDTO(Integer id, String ten, String sdt, String diachi) {
        KhachHangDTO dto = new KhachHangDTO();
        dto.setMaKH(id);
        dto.setHoten(ten);
        dto.setDiachi(diachi);
        dto.setSdt(sdt);
        return dto;
    }
    
    // TCU01: Cập nhật thành công với dữ liệu hợp lệ
    @Test
    public void testUpdate_TCU01_ThanhCong() {
        KhachHangDTO dto = createDTO(99, "Nguyen Van A", "Hà Nội", "0123456789");
        int result = dao.update(dto);
        assertEquals(1, result);
    }

    // TCU02: Cập nhật thất bại do makh không tồn tại
    @Test
    public void testUpdate_TCU02_MaKHKhongTonTai() {
        KhachHangDTO dto = createDTO(999, "Nguyen Van B", "Hà Nội", "0123456789");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU04: Thiếu tên khách hàng
    @Test
    public void testUpdate_TCU04_ThieuTenKH() {
        KhachHangDTO dto = createDTO(102, null, "Hà Nội", "0123456789");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU05: Thiếu địa chỉ
    @Test
    public void testUpdate_TCU05_ThieuDiaChi() {
        KhachHangDTO dto = createDTO(103, "Nguyen Van A", null, "0123456789");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU06: Thiếu sdt
    @Test
    public void testUpdate_TCU06_ThieuSDT() {
        KhachHangDTO dto = createDTO(104, "Nguyen Van A", "Hà Nội", null);
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU08: Tên khách hàng quá dài
    @Test
    public void testUpdate_TCU08_TenKHQuaDai() {
        String longName = "A".repeat(500);
        KhachHangDTO dto = createDTO(105, longName, "Hà Nội", "0123456789");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU09: Địa chỉ quá dài
    @Test
    public void testUpdate_TCU09_DiaChiQuaDai() {
        String longAddress = "Hà Nội".repeat(100);
        KhachHangDTO dto = createDTO(106, "Nguyen Van A", longAddress, "0123456789");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU10: SDT quá dài
    @Test
    public void testUpdate_TCU10_SDTQuaDai() {
        String longPhone = "0123456789".repeat(50);
        KhachHangDTO dto = createDTO(107, "Nguyen Van A", "Hà Nội", longPhone);
        int result = dao.update(dto);
        assertEquals(0, result);
    }
   

    /**
     * Test of delete method, of class KhachHangDAO.
     */
    
    // TCD01: Xoá thành công khách hàng tồn tại
    @Test
    public void testDelete_TCD01_ThanhCong() {
        int result = dao.delete("101"); // makh tồn tại
        assertEquals(1, result);
    }

    // TCD02: Xoá thất bại do makh không hợp lệ (không tồn tại)
    @Test
    public void testDelete_TCD02_MaKHKhongTonTai() {
        int result = dao.delete("999"); // makh không tồn tại
        assertEquals(0, result);
    }
    
    /**
     * Test of selectAll method, of class KhachHangDAO.
     */
    
//    @Test
//    public void testGetAllActive_TCS01_CoDuLieu() {
//        KhachHangDAO dao = new KhachHangDAO();
//        List<KhachHangDTO> danhSach = dao.selectAll();
//        assertNotNull(danhSach);
//        assertFalse(danhSach.isEmpty());
//        assertEquals("Phải trả về đúng số lượng khách hàng active", 19, danhSach.size());
//    }


    /**
     * Test of selectById method, of class KhachHangDAO.
     */
    // TCG01: Lấy khách hàng theo makh thành công
    @Test
    public void testSelectById_TCG01_KhachHangTonTai() {
        System.out.println("TCG01 - Lấy khách hàng theo makh thành công");
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO result = instance.selectById("99");

        assertNotNull("Phải trả về thông tin khách hàng", result);
        assertEquals("Mã khách hàng phải khớp", 99, result.getMaKH());
    }

    // TCG02: Lấy khách hàng thất bại do makh không hợp lệ (không tồn tại, = 0, < 0, thập phân)
    @Test
    public void testSelectById_TCG02_KhachHangKhongHopLe() {
        System.out.println("TCG02 - Lấy khách hàng thất bại do makh không hợp lệ");
        String[] invalidInputs = {"999", "0", "-1", "1.5"};
        KhachHangDAO instance = new KhachHangDAO();

        for (String makh : invalidInputs) {
            KhachHangDTO result = instance.selectById(makh);
            assertNull("Phải trả về null khi makh không hợp lệ: " + makh, result);
        }
    }

    // TCG04: Lấy khách hàng thất bại do bản ghi đã bị xóa mềm (trangthai = 0)
    @Test
    public void testSelectById_TCG04_KhachHangDaXoaMem() {
        System.out.println("TCG04 - Lấy khách hàng thất bại do bản ghi đã bị xóa mềm");
        String makh = "101"; // Giả sử khách hàng này đã bị xóa mềm (trangthai = 0)
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO result = instance.selectById(makh);

        assertNull("Phải trả về null nếu khách hàng đã bị xóa mềm", result);
    }

    /**
     * Test of getAutoIncrement method, of class KhachHangDAO.
     */

}
