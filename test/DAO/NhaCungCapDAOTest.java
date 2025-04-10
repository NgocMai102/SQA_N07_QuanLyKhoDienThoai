/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.NhaCungCapDTO;
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
public class NhaCungCapDAOTest {
    
    private NhaCungCapDAO dao;
    private Connection conn;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new NhaCungCapDAO();
    }
    
    public NhaCungCapDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of insert method, of class NhaCungCapDAO.
     */
    @Test
    public void testInsert_TCC01_ThemThanhCong() {
        System.out.println("TCC01 - Thêm NCC thành công với dữ liệu hợp lệ");
        NhaCungCapDTO dto = new NhaCungCapDTO(8, "Công ty A", "Hà Nội", "a@gmail.com", "0123456789");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 1 khi thêm thành công", 1, result);
    }

    @Test
    public void testInsert_TCC02_TrungMaNCC() {
        System.out.println("TCC02 - Thêm thất bại vì trùng manhacungcap");
        NhaCungCapDTO dto = new NhaCungCapDTO(6, "Công ty B", "Đà Nẵng", "b@gmail.com", "0987654321");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi trùng mã nhà cung cấp", 0, result);
    }

    @Test
    public void testInsert_TCC04_ThieuTenNCC() {
        System.out.println("TCC04 - Thêm thất bại do thiếu tên NCC");
        NhaCungCapDTO dto = new NhaCungCapDTO(102, null, "Huế", "d@gmail.com", "0909090909");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu tên nhà cung cấp", 0, result);
    }

    @Test
    public void testInsert_TCC05_ThieuEmail() {
        System.out.println("TCC05 - Thêm thất bại do thiếu email");
        NhaCungCapDTO dto = new NhaCungCapDTO(103, "Công ty E", "Huế", null, "0909090909");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu email", 0, result);
    }

    @Test
    public void testInsert_TCC06_ThieuDiaChi() {
        System.out.println("TCC06 - Thêm thất bại do thiếu địa chỉ");
        NhaCungCapDTO dto = new NhaCungCapDTO(104, "Công ty G", null, "g@gmail.com", "0909090909");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu địa chỉ", 0, result);
    }

    @Test
    public void testInsert_TCC07_ThieuSDT() {
        System.out.println("TCC07 - Thêm thất bại do thiếu số điện thoại");
        NhaCungCapDTO dto = new NhaCungCapDTO(105, "Công ty H", "Hà Nội", "h@gmail.com", null);
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi thiếu số điện thoại", 0, result);
    }

    @Test
    public void testInsert_TCC08_MaNCCKhongHopLe() {
        System.out.println("TCC08 - Thêm thất bại do mã NCC không hợp lệ");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        NhaCungCapDTO dto = new NhaCungCapDTO(-9, "Công ty H", "Hà Nội", "h@gmail.com", "097198333");
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi mã NCC không hợp lệ", 0, result);
    }

    @Test
    public void testInsert_TCC09_TenQuaDai() {
        System.out.println("TCC09 - Thêm thất bại do tên NCC quá dài");
        String longTen = "Công ty " + "A".repeat(500);
        NhaCungCapDTO dto = new NhaCungCapDTO(106, longTen, "Hà Nội", "h@gmail.com", "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi tên quá dài", 0, result);
    }

    @Test
    public void testInsert_TCC10_DiaChiQuaDai() {
        System.out.println("TCC10 - Thêm thất bại do địa chỉ quá dài");
        String longDiaChi = "Hà Nội " + "B".repeat(500);
        NhaCungCapDTO dto = new NhaCungCapDTO(107, "Công ty H", longDiaChi, "h@gmail.com", "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi địa chỉ quá dài", 0, result);
    }

    @Test
    public void testInsert_TCC11_EmailQuaDai() {
        System.out.println("TCC11 - Thêm thất bại do email quá dài");
        String longEmail = "h@" + "gmail".repeat(100) + ".com";
        NhaCungCapDTO dto = new NhaCungCapDTO(108, "Công ty H", "Hà Nội", longEmail, "097198333");
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi email quá dài", 0, result);
    }

    @Test
    public void testInsert_TCC12_SdtQuaDai() {
        System.out.println("TCC12 - Thêm thất bại do số điện thoại quá dài");
        String longSDT = "09" + "7".repeat(500);
        NhaCungCapDTO dto = new NhaCungCapDTO(109, "Công ty H", "Hà Nội", "h@gmail.com", longSDT);
        NhaCungCapDAO dao = new NhaCungCapDAO();
        int result = dao.insert(dto);
        assertEquals("Phải trả về 0 khi số điện thoại quá dài", 0, result);
    }

    /**
     * Test of update method, of class NhaCungCapDAO.
     */
   

    // Helper: Tạo đối tượng NhaCungCapDTO nhanh
    private NhaCungCapDTO createDTO(Integer id, String ten, String diachi, String email, String sdt) {
        NhaCungCapDTO dto = new NhaCungCapDTO();
        dto.setMancc(id);
        dto.setTenncc(ten);
        dto.setDiachi(diachi);
        dto.setEmail(email);
        dto.setSdt(sdt);
        return dto;
    }

    // TCU01: Cập nhật thành công với dữ liệu hợp lệ
    @Test
    public void testUpdate_TCU01_ThanhCong() {
        NhaCungCapDTO dto = createDTO(6, "Cty ABC", "Hà Nội", "abc@gmail.com", "0911111111");
        int result = dao.update(dto);
        assertEquals(1, result);
    }

    // TCU02: manhacungcap không tồn tại
    @Test
    public void testUpdate_TCU02_KhongTonTai() {
        NhaCungCapDTO dto = createDTO(999, "Cty ABC", "Hà Nội", "abc@gmail.com", "0911111111");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU04: Thiếu tenhacungcap
    @Test
    public void testUpdate_TCU04_ThieuTen() {
        NhaCungCapDTO dto = createDTO(101, null, "Hà Nội", "abc@gmail.com", "0911111111");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU05: Thiếu diachi
    @Test
    public void testUpdate_TCU05_ThieuDiaChi() {
        NhaCungCapDTO dto = createDTO(101, "Cty ABC", null, "abc@gmail.com", "0911111111");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU06: Thiếu email
    @Test
    public void testUpdate_TCU06_ThieuEmail() {
        NhaCungCapDTO dto = createDTO(101, "Cty ABC", "Hà Nội", null, "0911111111");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU07: Thiếu sdt
    @Test
    public void testUpdate_TCU07_ThieuSDT() {
        NhaCungCapDTO dto = createDTO(101, "Cty ABC", "Hà Nội", "abc@gmail.com", null);
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU09: tennhacungcap > 255 ký tự
    @Test
    public void testUpdate_TCU09_TenQuaDai() {
        String longTen = "Công ty " + "h".repeat(500);
        NhaCungCapDTO dto = createDTO(106, longTen, "Hà Nội", "h@gmail.com", "097198333");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU10: diachi > 255 ký tự
    @Test
    public void testUpdate_TCU10_DiaChiQuaDai() {
        String longDiachi = "Hà Nội " + "x".repeat(500);
        NhaCungCapDTO dto = createDTO(107, "Công ty H", longDiachi, "h@gmail.com", "097198333");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU11: email > 255 ký tự
    @Test
    public void testUpdate_TCU11_EmailQuaDai() {
        String longEmail = "h@" + "gmail".repeat(100) + ".com";
        NhaCungCapDTO dto = createDTO(108, "Công ty H", "Hà Nội", longEmail, "097198333");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU12: sdt > 255 ký tự
    @Test
    public void testUpdate_TCU12_SDTQuaDai() {
        String longSDT = "0".repeat(300);
        NhaCungCapDTO dto = createDTO(109, "Công ty H", "Hà Nội", "h@gmail.com", longSDT);
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TCU13: nhà cung cấp đã bị xóa mềm (trangthai = 0)
    @Test
    public void testUpdate_TCU13_DaXoaMem() {
        NhaCungCapDTO dto = createDTO(8, "Cty ABC", "Hà Nội", "abc@gmail.com", "0911111111");
        int result = dao.update(dto);
        assertEquals(0, result);
    }
 

    /**
     * Test of delete method, of class NhaCungCapDAO.
     */

    // TCD01: Xoá thành công nhà cung cấp tồn tại
    @Test
    public void testDelete_TCD01_ThanhCong() {
        int result = dao.delete("8");
        assertEquals(1, result);
    }

    // TCD02: Xoá thất bại do manhacungcap không hợp lệ
    @Test
    public void testDelete_TCD02_KhongTonTai() {
        int result = dao.delete("999"); // hoặc -1, hoặc các giá trị không hợp lệ
        assertEquals(0, result);
    }

    // TCD02 (mở rộng): Xóa với manhacungcap là số âm
    @Test
    public void testDelete_TCD02_NegativeID() {
        int result = dao.delete("-5");
        assertEquals(0, result);
    }
    

    /**
     * Test of selectAll method, of class NhaCungCapDAO.
     */

    @Test
    public void testSelectById_TCG01_MaNhaCungCapTonTai() {
        System.out.println("TCG01 - Lấy nhà cung cấp theo manhacungcap thành công");
        NhaCungCapDAO instance = new NhaCungCapDAO();
        NhaCungCapDTO result = instance.selectById("7");

        assertNotNull("Phải trả về thông tin nhà cung cấp", result);
        assertEquals("Mã nhà cung cấp phải khớp", 7, result.getMancc());
    }

    @Test
    public void testSelectById_TCG02_MaNhaCungCapKhongTonTai() {
        System.out.println("TCG02 - Lấy nhà cung cấp thất bại do manhacungcap không hợp lệ");
        String[] invalidInputs = {"999", "0", "-1", "1.5"};
        NhaCungCapDAO instance = new NhaCungCapDAO();

        for (String manhacungcap : invalidInputs) {
            NhaCungCapDTO result = instance.selectById(manhacungcap);
            assertNull("Phải trả về null khi manhacungcap không hợp lệ: " + manhacungcap, result);
        }
}

    @Test
    public void testSelectById_TCG04_NhaCungCapDaXoaMem() {
        System.out.println("TCG04 - Lấy nhà cung cấp thất bại do bản ghi đã bị xóa mềm");
        String manhacungcap = "8"; // Giả sử nhà cung cấp này có trong DB nhưng trạngthai = 0
        NhaCungCapDAO instance = new NhaCungCapDAO();
        NhaCungCapDTO result = instance.selectById(manhacungcap);

        assertNull("Phải trả về null nếu bản ghi đã bị xóa mềm", result);
    }
    
    @Test
    public void testGetAllActive_TCS01_CoDuLieu() {
        NhaCungCapDAO dao = new NhaCungCapDAO();
        List<NhaCungCapDTO> danhSach = dao.selectAll();
        assertNotNull(danhSach);
        assertFalse(danhSach.isEmpty());
        assertEquals("Phải trả về đúng số lượng khu vực active", 7, danhSach.size());
    }
}
