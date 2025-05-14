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
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
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
    private Connection conn;
    SimpleDateFormat sdf;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        dao = new ThongKeDAO();
    }
    
    public ThongKeDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

//    /**
//     * Test of getThongKeTonKho method, of class ThongKeDAO.
//     */
//     // TK_001: Test tên để trống, khoảng thời gian hợp lệ
//    @Test
//    public void TK_001_getThongKeTonKho_TenRong_ThoiGianHopLe() throws Exception {
//        Date timeStart = sdf.parse("01/01/2024");
//        Date timeEnd = sdf.parse("01/04/2024");
//
//        HashMap<Integer, ArrayList<ThongKeTonKhoDTO>> result = dao.getThongKeTonKho("", timeStart, timeEnd);
//        assertNotNull(result);
//        assertTrue(result.size() > 0); // Có thể kiểm tra thêm số lượng phần tử nếu cần
//    }
//
//    // TK_002: Test tên hợp lệ, khoảng thời gian hợp lệ
//    @Test
//    public void TK_002_getThongKeTonKho_TenTonTai_ThoiGianHopLe() throws Exception {
//        Date timeStart = sdf.parse("01/01/2024");
//        Date timeEnd = sdf.parse("01/04/2024");
//
//        HashMap<Integer, ArrayList<ThongKeTonKhoDTO>> result = dao.getThongKeTonKho("Iphone", timeStart, timeEnd);
//        assertNotNull(result);
//        assertTrue(result.size() > 0);
//    }
//
//    // TK_003: Test tên không tồn tại, khoảng thời gian hợp lệ
//    @Test
//    public void TK_003_getThongKeTonKho_TenKhongTonTai_ThoiGianHopLe() throws Exception {
//        Date timeStart = sdf.parse("01/01/2024");
//        Date timeEnd = sdf.parse("01/04/2024");
//
//        HashMap<Integer, ArrayList<ThongKeTonKhoDTO>> result = dao.getThongKeTonKho("abcxyz", timeStart, timeEnd);
//        assertNotNull(result);
//        assertEquals(0, result.size());
//    }
//
//    // TK_004: Test timeStart > timeEnd → Exception
//    @Test(expected = IllegalArgumentException.class)
//    public void TK_004_getThongKeTonKho_ThoiGianKhongHopLe_ThrowException() throws Exception {
//        Date timeStart = sdf.parse("01/04/2024");
//        Date timeEnd = sdf.parse("01/01/2024");
//
//        dao.getThongKeTonKho("Iphone", timeStart, timeEnd);
//    }
//
//    // TK_005: Test tên để trống, khoảng thời gian rất lớn
//    @Test
//    public void TK_005_getThongKeTonKho_TenRong_ThoiGianLon() throws Exception {
//        Date timeStart = sdf.parse("01/01/2014");
//        Date timeEnd = sdf.parse("01/04/2024");
//
//        HashMap<Integer, ArrayList<ThongKeTonKhoDTO>> result = dao.getThongKeTonKho("", timeStart, timeEnd);
//        assertNotNull(result);
//        assertTrue(result.size() > 0);
//    }
    

//    /**
//     * Test of getDoanhThuTheoTungNam method, of class ThongKeDAO.
//     */
//     /**
//     * TK_006: year_start hợp lệ, year_end hợp lệ
//     */
//    @Test
//    public void TK_006_getDoanhThu_NamHopLe() {
//        int yearStart = 2020;
//        int yearEnd = 2024;
//
//        ArrayList<ThongKeDoanhThuDTO> result = dao.getDoanhThuTheoTungNam(yearStart, yearEnd);
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//
//        for (ThongKeDoanhThuDTO dto : result) {
//            assertTrue(dto.getThoigian()>= yearStart && dto.getThoigian()<= yearEnd);
//        }
//    }
//
//    /**
//     * TK_007: year_start không hợp lệ (âm), year_end hợp lệ
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void TK_007_getDoanhThu_YearStartInvalid() {
//        dao.getDoanhThuTheoTungNam(-2020, 2024);
//    }
//
//    /**
//     * TK_008: year_start hợp lệ, year_end quá lớn
//     */
//    @Test (expected = Exception.class)
//    public void TK_008_getDoanhThu_YearEndVeryLarge() {
//        int yearStart = 2020;
//        int yearEnd = 99999;
//
//        ArrayList<ThongKeDoanhThuDTO> result = dao.getDoanhThuTheoTungNam(yearStart, yearEnd);
//        assertNotNull(result);
//
//        Calendar now = Calendar.getInstance();
//        int currentYear = now.get(Calendar.YEAR);
//
//        for (ThongKeDoanhThuDTO dto : result) {
//            assertTrue(dto.getThoigian()>= yearStart && dto.getThoigian()<= currentYear);
//        }
//    }
//
//    /**
//     * TK_009: year_end < year_start
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void TK_009_getDoanhThu_YearEndLessThanStart() {
//        dao.getDoanhThuTheoTungNam(2025, 2020);
//    }
//
//    /**
//     * TK_010: year_end thiếu (mặc định là 0) → thống kê đến hiện tại
//     */
//    @Test
//    public void TK_010_getDoanhThu_YearEndNull() {
//        int yearStart = 2020;
//        int yearEnd = 0; // Giả định 0 nghĩa là thiếu, sẽ được xử lý nội bộ
//
//        ArrayList<ThongKeDoanhThuDTO> result = dao.getDoanhThuTheoTungNam(yearStart, yearEnd);
//        assertNotNull(result);
//
//        Calendar now = Calendar.getInstance();
//        int currentYear = now.get(Calendar.YEAR);
//
//        for (ThongKeDoanhThuDTO dto : result) {
//            assertTrue(dto.getThoigian()>= yearStart && dto.getThoigian()<= currentYear);
//        }
//    }
//
//    /**
//     * TK_011: year_start thiếu (mặc định là 0)
//     */
//    @Test
//    public void TK_011_getDoanhThu_YearStartNull() {
//        int yearStart = 0; // Giả định thiếu
//        int yearEnd = 2024;
//
//        ArrayList<ThongKeDoanhThuDTO> result = dao.getDoanhThuTheoTungNam(yearStart, yearEnd);
//        assertNotNull(result);
//        assertEquals(yearEnd + 1, result.size());
//    }
    

    /**
     * Test of getThongKeTheoThang method, of class ThongKeDAO.
     */
    /**
     * TK_012: Năm tồn tại
     * Kiểm tra trả về đúng danh sách doanh thu theo tháng của năm tồn tại.
     */
//    @Test
//    public void TK_012_getThongKeTheoThang_NamTonTai() {
//        int nam = 2024;
//
//        ArrayList<ThongKeTheoThangDTO> result = dao.getThongKeTheoThang(nam);
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//    }
//
//    /**
//     * TK_013: Năm không tồn tại (năm âm)
//     * Kiểm tra bắt lỗi năm vượt giới hạn, ném Exception.
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void TK_013_getThongKeTheoThang_NamKhongTonTai() {
//        dao.getThongKeTheoThang(-2024);
//    }
//
//    /**
//     * TK_014: Năm hợp lệ nhưng không có dữ liệu
//     * Kiểm tra xử lý năm đúng nhưng không có dữ liệu, trả về rỗng.
//     */
//    @Test
//    public void TK_014_getThongKeTheoThang_NamHopLeKhongCoDuLieu() {
//        int nam = 2023;
//
//        ArrayList<ThongKeTheoThangDTO> result = dao.getThongKeTheoThang(nam);
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    /**
//     * TK_015: Năm bị thiếu hoặc chưa set (null)
//     * Kiểm tra bắt lỗi thiếu dữ liệu năm, ném Exception.
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void TK_015_getThongKeTheoThang_NamNull() {
//        dao.getThongKeTheoThang(null);
//    }
    

    /**
     * Test of getThongKeTungNgayTrongThang method, of class ThongKeDAO.
     */
    
//        // Mã 16 - Tháng hợp lệ, năm hợp lệ
//    @Test
//    public void testGetThongKeTungNgayTrongThang_HopLe() {
//        int thang = 3;
//        int nam = 2024;
//        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(thang, nam);
//        assertNotNull(result);
//        assertTrue(result.size() > 0);  // Giả định có dữ liệu trong tháng này
//    }
//
//    // Mã 17 - Tháng không hợp lệ (> 12), năm hợp lệ
//    @Test(expected = IllegalArgumentException.class)
//    public void testGetThongKeTungNgayTrongThang_ThangKhongHopLe() {
//        int thang = 13;
//        int nam = 2024;
//        dao.getThongKeTungNgayTrongThang(thang, nam);
//    }
//
//    // Mã 18 - Tháng hợp lệ, năm không hợp lệ (âm)
//    @Test(expected = IllegalArgumentException.class)
//    public void testGetThongKeTungNgayTrongThang_NamKhongHopLe() {
//        int thang = 3;
//        int nam = -2024;
//        dao.getThongKeTungNgayTrongThang(thang, nam);
//    }
//
//    // Mã 19 - Tháng null (giả lập bằng wrapper class Integer)
//    @Test
//    public void testGetThongKeTungNgayTrongThang_ThangNull() {
//        Integer thang = null;
//        int nam = 2024;
//        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(
//            thang != null ? thang : -1, nam);  // giả lập null bằng -1
//        assertTrue(result.isEmpty());
//    }
//
//    // Mã 20 - Năm null (giả lập bằng wrapper class Integer)
//    @Test
//    public void testGetThongKeTungNgayTrongThang_NamNull() {
//        int thang = 3;
//        Integer nam = null;
//        ArrayList<ThongKeTungNgayTrongThangDTO> result = dao.getThongKeTungNgayTrongThang(
//            thang, nam != null ? nam : -1);  // giả lập null bằng -1
//        assertTrue(result.isEmpty());
//    }
    

    /**
     * Test of getThongKe7NgayGanNhat method, of class ThongKeDAO.
     */
    

    /**
     * Test of getThongKeTuNgayDenNgay method, of class ThongKeDAO.
     */
    
    
}
