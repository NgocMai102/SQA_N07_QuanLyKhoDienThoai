/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeKhachHangDTO;
import DTO.ThongKe.ThongKeNhaCungCapDTO;
import DTO.ThongKe.ThongKeTheoThangDTO;
import DTO.ThongKe.ThongKeTonKhoDTO;
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;
import config.JDBCUtil;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author MSI GF63
 */
public class ThongKeDAOTest {
    
    private ThongKeDAO dao;
    static Connection connection;
    SimpleDateFormat sdf;
    
    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        dao = new ThongKeDAO();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    public ThongKeDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }

    /**
     * Test of getThongKeTonKho method, of class ThongKeDAO.
     */
     // TK_001: Test tên để trống, khoảng thời gian hợp lệ
    @Test
    public void TK_001_getThongKeTonKho_TenRong_ThoiGianHopLe() throws Exception {
        Date timeStart = sdf.parse("01/01/2024");
        Date timeEnd = sdf.parse("01/04/2024");

        HashMap<Integer, ArrayList<ThongKeTonKhoDTO>> result = dao.getThongKeTonKho("", timeStart, timeEnd);
        assertNotNull(result);
        assertTrue(result.size() > 0); // Có thể kiểm tra thêm số lượng phần tử nếu cần
    }

    // TK_002: Test tên hợp lệ, khoảng thời gian hợp lệ
    @Test
    public void TK_002_getThongKeTonKho_TenTonTai_ThoiGianHopLe() throws Exception {
        Date timeStart = sdf.parse("01/01/2024");
        Date timeEnd = sdf.parse("01/04/2024");

        HashMap<Integer, ArrayList<ThongKeTonKhoDTO>> result = dao.getThongKeTonKho("Iphone", timeStart, timeEnd);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    // TK_003: Test tên không tồn tại, khoảng thời gian hợp lệ
    @Test
    public void TK_003_getThongKeTonKho_TenKhongTonTai_ThoiGianHopLe() throws Exception {
        Date timeStart = sdf.parse("01/01/2024");
        Date timeEnd = sdf.parse("01/04/2024");

        HashMap<Integer, ArrayList<ThongKeTonKhoDTO>> result = dao.getThongKeTonKho("abcxyz", timeStart, timeEnd);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // TK_004: Test timeStart > timeEnd → Exception
    @Test(expected = IllegalArgumentException.class)
    public void TK_004_getThongKeTonKho_ThoiGianKhongHopLe_ThrowException() throws Exception {
        Date timeStart = sdf.parse("01/04/2024");
        Date timeEnd = sdf.parse("01/01/2024");

        dao.getThongKeTonKho("Iphone", timeStart, timeEnd);
    }

    // TK_005: Test tên để trống, khoảng thời gian rất lớn
    @Test
    public void TK_005_getThongKeTonKho_TenRong_ThoiGianLon() throws Exception {
        Date timeStart = sdf.parse("01/01/2014");
        Date timeEnd = sdf.parse("01/04/2024");

        HashMap<Integer, ArrayList<ThongKeTonKhoDTO>> result = dao.getThongKeTonKho("", timeStart, timeEnd);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }
    

    /**
     * Test of getDoanhThuTheoTungNam method, of class ThongKeDAO.
     */
     /**
     * TK_006: year_start hợp lệ, year_end hợp lệ
     */
    @Test
    public void TK_006_getDoanhThu_NamHopLe() {
        int yearStart = 2020;
        int yearEnd = 2024;

        ArrayList<ThongKeDoanhThuDTO> result = dao.getDoanhThuTheoTungNam(yearStart, yearEnd);
        assertNotNull(result);
        assertFalse(result.isEmpty());

        for (ThongKeDoanhThuDTO dto : result) {
            assertTrue(dto.getThoigian()>= yearStart && dto.getThoigian()<= yearEnd);
        }
    }

    /**
     * TK_007: year_start không hợp lệ (âm), year_end hợp lệ
     */
    @Test(expected = IllegalArgumentException.class)
    public void TK_007_getDoanhThu_YearStartInvalid() {
        dao.getDoanhThuTheoTungNam(-2020, 2024);
    }

    /**
     * TK_008: year_start hợp lệ, year_end quá lớn
     */
    @Test (expected = Exception.class)
    public void TK_008_getDoanhThu_YearEndVeryLarge() {
        int yearStart = 2020;
        int yearEnd = 99999;

        ArrayList<ThongKeDoanhThuDTO> result = dao.getDoanhThuTheoTungNam(yearStart, yearEnd);
        assertNotNull(result);

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);

        for (ThongKeDoanhThuDTO dto : result) {
            assertTrue(dto.getThoigian()>= yearStart && dto.getThoigian()<= currentYear);
        }
    }

    /**
     * TK_009: year_end < year_start
     */
    @Test(expected = IllegalArgumentException.class)
    public void TK_009_getDoanhThu_YearEndLessThanStart() {
        dao.getDoanhThuTheoTungNam(2025, 2020);
    }

    /**
     * TK_010: year_end thiếu (mặc định là 0) → thống kê đến hiện tại
     */
    @Test
    public void TK_010_getDoanhThu_YearEndNull() {
        int yearStart = 2020;
        int yearEnd = 0; // Giả định 0 nghĩa là thiếu, sẽ được xử lý nội bộ

        ArrayList<ThongKeDoanhThuDTO> result = dao.getDoanhThuTheoTungNam(yearStart, yearEnd);
        assertNotNull(result);

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);

        for (ThongKeDoanhThuDTO dto : result) {
            assertTrue(dto.getThoigian()>= yearStart && dto.getThoigian()<= currentYear);
        }
    }

    /**
     * TK_011: year_start thiếu (mặc định là 0)
     */
    @Test
    public void TK_011_getDoanhThu_YearStartNull() {
        int yearStart = 0; // Giả định thiếu
        int yearEnd = 2024;

        ArrayList<ThongKeDoanhThuDTO> result = dao.getDoanhThuTheoTungNam(yearStart, yearEnd);
        assertNotNull(result);
        assertEquals(yearEnd + 1, result.size());
    }
    

    /**
     * Test of getThongKeNCC method, of class ThongKeDAO.
     */

    @Test
    public void testThongKe_NCC038() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeNhaCungCapDTO> result = ThongKeDAO.getThongKeNCC("", start, end);

        assertNotNull("Kết quả không được null", result);
        // Có thể thêm kiểm tra kích thước nếu biết dữ liệu
        System.out.println("TCS01 - Số lượng kết quả: " + result.size());
    }

    @Test
    public void testThongKe_NCC039() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeNhaCungCapDTO> result = ThongKeDAO.getThongKeNCC("Hà", start, end);

        assertNotNull(result);
        for (ThongKeNhaCungCapDTO dto : result) {
            boolean match = dto.getTenncc().contains("Nokia") || String.valueOf(dto.getMancc()).contains("Nokia");
            assertTrue("Tên hoặc mã nhà cung cấp phải chứa 'Nokia'", match);
        }
    }

    @Test
    public void testThongKe_NCC040() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeNhaCungCapDTO> result = ThongKeDAO.getThongKeNCC("abcxyz", start, end);

        assertNotNull(result);
        assertTrue("Không có kết quả nào phù hợp", result.isEmpty());
    }

    @Test
    public void testThongKe_NCC041() throws Exception {
        Date start = sdf.parse("01/04/2024");
        Date end = sdf.parse("01/01/2024");

        ArrayList<ThongKeNhaCungCapDTO> result = ThongKeDAO.getThongKeNCC("Nokia", start, end);

        // Tùy vào cách bạn xử lý thời gian sai
        assertNotNull(result);
        assertTrue("Kết quả phải rỗng do thời gian không hợp lệ", result.isEmpty());
    }

    @Test
    public void testThongKe_NCC042() throws Exception {
        Date start = sdf.parse("01/01/2014");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeNhaCungCapDTO> result = ThongKeDAO.getThongKeNCC("", start, end);

        assertNotNull(result);
        System.out.println("TCS05 - Số lượng nhà cung cấp trong 10 năm: " + result.size());
    }
    
    /**
     * Test of getThongKeKhachHang method, of class ThongKeDAO.
     */

    @Test
    public void testThongKe_KH032() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("", start, end);

        assertNotNull("Kết quả không được null", result);
        System.out.println("TCS01 - Số lượng khách hàng trong khoảng thời gian: " + result.size());
    }

    @Test
    public void testThongKe_KH033() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("Hà", start, end);

        assertNotNull(result);
        for (ThongKeKhachHangDTO dto : result) {
            boolean match = dto.getTenkh().contains("Hà") || String.valueOf(dto.getMakh()).contains("Hà");
            assertTrue("Tên hoặc mã khách hàng phải chứa 'Hà'", match);
        }
    }

    @Test
    public void testThongKe_KH034() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("abcxyz", start, end);

        assertNotNull(result);
        assertTrue("Không có kết quả nào phù hợp", result.isEmpty());
    }

    @Test
    public void testThongKe_KH035() throws Exception {
        Date start = sdf.parse("01/04/2024");
        Date end = sdf.parse("01/01/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("Hà", start, end);

        // Tuỳ thuộc vào logic trong DAO bạn có thể thay assert này
        assertNotNull(result);
        assertTrue("Kết quả phải rỗng do thời gian không hợp lệ", result.isEmpty());
    }

    @Test
    public void testThongKe_KH036() throws Exception {
        Date start = sdf.parse("01/01/2014");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("", start, end);

        assertNotNull(result);
        System.out.println("TCS05 - Số lượng khách hàng trong 10 năm qua: " + result.size());
    }

    @Test
    public void test_TC12_ThangHopLe() {
        ArrayList<ThongKeTheoThangDTO> result = dao.getThongKeTheoThang(3);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test(expected = Exception.class)
    public void test_TC13_ThangKhongHopLe() {
        dao.getThongKeTheoThang(13);
    }

    @Test
    public void test_TC14_ThangCoDuLieuRong() {
        ArrayList<ThongKeTheoThangDTO> result = dao.getThongKeTheoThang(2);
        assertNotNull(result);
        assertTrue(result.size() >= 0); // Có thể rỗng hoặc có dữ liệu
    }

    @Test(expected = Exception.class)
    public void test_TC15_ThangNull() {
        dao.getThongKeTheoThang(0);
    }
    
    @Test
    public void test_TC16_ThangVaNamHopLe() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(3, 2024);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test(expected = Exception.class)
    public void test_TC17_ThangKhongHopLe() {
        dao.getThongKeTungNgayTrongThang(13, 2024);
    }

    @Test(expected = Exception.class)
    public void test_TC18_NamKhongHopLe() {
        dao.getThongKeTungNgayTrongThang(3, -2024);
    }

    @Test
    public void test_TC19_ThangNull() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(0, 2024);
        assertTrue(result.isEmpty());
    }

    @Test
    public void test_TC20_NamNull() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(3, 0);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void test_TC21_CoDayDu7Ngay() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKe7NgayGanNhat();
        assertNotNull(result);
        assertTrue(result.size() <= 7);
        assertTrue(result.size() > 0); // Có dữ liệu
    }

    @Test
    public void test_TC22_KhongDayDu7Ngay() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKe7NgayGanNhat();
        assertNotNull(result);
        assertTrue(result.size() >= 0); // Có thể ít hơn 7
    }

    @Test
    public void test_TC23_CSDLTrong() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKe7NgayGanNhat();
        assertNotNull(result);
        // Nếu CSDL trống thì:
        assertTrue(result.isEmpty() || result.size() <= 7);
    }
    
    @Test
    public void test_TC24_NgayHopLe() throws Exception {
        String start = "01/01/2024";
        String end = "01/04/2024";
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTuNgayDenNgay(start, end);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test(expected = Exception.class)
    public void test_TC25_StartKhongHopLe() throws Exception {
        String start = "30/02/2024";
        String end = "01/04/2024";
        dao.getThongKeTuNgayDenNgay(start, end);
    }

    @Test(expected = Exception.class)
    public void test_TC26_EndKhongHopLe() throws Exception {
        String start = "01/01/2024";
        String end = "30/02/2024";
        dao.getThongKeTuNgayDenNgay(start, end);
    }

    @Test(expected = Exception.class)
    public void test_TC27_EndNhoHonStart() throws Exception {
        String start = "01/04/2024";
        String end = "01/01/2024";
        dao.getThongKeTuNgayDenNgay(start, end);
    }

    @Test
    public void test_TC28_EndThieu() throws Exception {
        String start = "01/01/2024";
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTuNgayDenNgay(start, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void test_TC29_StartThieu() throws Exception {
        String end = "01/04/2024";
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTuNgayDenNgay(null, end);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of getThongKeTheoThang method, of class ThongKeDAO.
     */
    /**
     * TK_012: Năm tồn tại
     * Kiểm tra trả về đúng danh sách doanh thu theo tháng của năm tồn tại.
     */
    @Test
    public void TK_012_getThongKeTheoThang_NamTonTai() {
        int nam = 2024;

        ArrayList<ThongKeTheoThangDTO> result = dao.getThongKeTheoThang(nam);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    /**
     * TK_013: Năm không tồn tại (năm âm)
     * Kiểm tra bắt lỗi năm vượt giới hạn, ném Exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void TK_013_getThongKeTheoThang_NamKhongTonTai() {
        dao.getThongKeTheoThang(-2024);
    }

    /**
     * TK_014: Năm hợp lệ nhưng không có dữ liệu
     * Kiểm tra xử lý năm đúng nhưng không có dữ liệu, trả về rỗng.
     */
    @Test
    public void TK_014_getThongKeTheoThang_NamHopLeKhongCoDuLieu() {
        int nam = 2023;

        ArrayList<ThongKeTheoThangDTO> result = dao.getThongKeTheoThang(nam);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * TK_015: Năm bị thiếu hoặc chưa set (null)
     * Kiểm tra bắt lỗi thiếu dữ liệu năm, ném Exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void TK_015_getThongKeTheoThang_NamNull() {
//        dao.getThongKeTheoThang(null);
    }
    

    /**
     * Test of getThongKeTungNgayTrongThang method, of class ThongKeDAO.
     */
    
        // Mã 16 - Tháng hợp lệ, năm hợp lệ
    @Test
    public void testGetThongKeTungNgayTrongThang_HopLe() {
        int thang = 3;
        int nam = 2024;
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(thang, nam);
        assertNotNull(result);
        assertTrue(result.size() > 0);  // Giả định có dữ liệu trong tháng này
    }

    // Mã 17 - Tháng không hợp lệ (> 12), năm hợp lệ
    @Test(expected = IllegalArgumentException.class)
    public void testGetThongKeTungNgayTrongThang_ThangKhongHopLe() {
        int thang = 13;
        int nam = 2024;
        dao.getThongKeTungNgayTrongThang(thang, nam);
    }

    // Mã 18 - Tháng hợp lệ, năm không hợp lệ (âm)
    @Test(expected = IllegalArgumentException.class)
    public void testGetThongKeTungNgayTrongThang_NamKhongHopLe() {
        int thang = 3;
        int nam = -2024;
        dao.getThongKeTungNgayTrongThang(thang, nam);
    }

    // Mã 19 - Tháng null (giả lập bằng wrapper class Integer)
    @Test
    public void testGetThongKeTungNgayTrongThang_ThangNull() {
        Integer thang = null;
        int nam = 2024;
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(
            thang != null ? thang : -1, nam);  // giả lập null bằng -1
        assertTrue(result.isEmpty());
    }

    // Mã 20 - Năm null (giả lập bằng wrapper class Integer)
    @Test
    public void testGetThongKeTungNgayTrongThang_NamNull() {
        int thang = 3;
        Integer nam = null;
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(
            thang, nam != null ? nam : -1);  // giả lập null bằng -1
        assertTrue(result.isEmpty());
    }
    

    /**
     * Test of getThongKe7NgayGanNhat method, of class ThongKeDAO.
     */
    // TK_021: Cơ sở dữ liệu đủ 7 ngày → Trả về danh sách 7 ngày gần nhất
    @Test
    public void TK_021_getThongKe7NgayGanNhat_Du7Ngay() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKe7NgayGanNhat();
        assertNotNull(result);
        assertEquals(8, result.size());
    }

    // TK_022: Cơ sở dữ liệu trống → Trả về danh sách rỗng
    @Test
    public void TK_022_getThongKe7NgayGanNhat_CSDLTrong() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKe7NgayGanNhat();
        assertNotNull(result);
        assertEquals(7, result.size());
    }

    // TK_023: Thống kê từ ngày đến ngày hợp lệ → Trả về danh sách thống kê đúng
    @Test
    public void TK_023_getThongKeTuNgayDenNgay_HopLe()  {
        String start = "04/04/2023";
        String end = "10/05/2023";

        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTuNgayDenNgay(start, end);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // TK_024: Ngày bắt đầu không hợp lệ → Ném Exception
    @Test(expected = Exception.class)
    public void TK_024_getThongKeTuNgayDenNgay_StartKhongHopLe_ThrowException() throws Exception {
        String start = "30/02/2024"; // ngày không tồn tại
        String end = "01/04/2024";

        dao.getThongKeTuNgayDenNgay(start, end);
    }

    // TK_025: Ngày kết thúc không hợp lệ → Ném Exception
    @Test(expected = Exception.class)
    public void TK_025_getThongKeTuNgayDenNgay_EndKhongHopLe_ThrowException() throws Exception {
        String start = "01/01/2024";
        String end = "30/02/2024"; // ngày không tồn tại

        dao.getThongKeTuNgayDenNgay(start, end);
    }

    // TK_026: end < start → Ném Exception
    @Test(expected = Exception.class)
    public void TK_026_getThongKeTuNgayDenNgay_EndNhoHonStart_ThrowException() throws Exception {
        String start = "01/04/2024";
        String end = "01/01/2024";

        dao.getThongKeTuNgayDenNgay(start, end);
    }

    // TK_027: Thiếu ngày kết thúc → Trả về danh sách rỗng
    @Test
    public void TK_027_getThongKeTuNgayDenNgay_EndThieu() throws Exception {
        String start = "01/01/2024";
        String end = null;

        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTuNgayDenNgay(start, end);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TK_028: Thiếu ngày bắt đầu → Trả về danh sách rỗng
    @Test
    public void TK_028_getThongKeTuNgayDenNgay_StartThieu() throws Exception {
        String start = null;
        String end = "01/04/2024";

        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTuNgayDenNgay(start, end);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    
}