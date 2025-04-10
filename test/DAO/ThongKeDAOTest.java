/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThongKe.ThongKeKhachHangDTO;
import DTO.ThongKe.ThongKeNhaCungCapDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new ThongKeDAO();
    }
    
    public ThongKeDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getThongKeTonKho method, of class ThongKeDAO.
     */
    

    /**
     * Test of getDoanhThuTheoTungNam method, of class ThongKeDAO.
     */
    

    /**
     * Test of getThongKeNCC method, of class ThongKeDAO.
     */
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void test_TCS01_TenRong_ThoiGianHopLe() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeNhaCungCapDTO> result = ThongKeDAO.getThongKeNCC("", start, end);

        assertNotNull("Kết quả không được null", result);
        // Có thể thêm kiểm tra kích thước nếu biết dữ liệu
        System.out.println("TCS01 - Số lượng kết quả: " + result.size());
    }

    @Test
    public void test_TCS02_TenTonTai_ThoiGianHopLe() throws Exception {
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
    public void test_TCS03_TenKhongTonTai_ThoiGianHopLe() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeNhaCungCapDTO> result = ThongKeDAO.getThongKeNCC("abcxyz", start, end);

        assertNotNull(result);
        assertTrue("Không có kết quả nào phù hợp", result.isEmpty());
    }

    @Test
    public void test_TCS04_ThoiGianKhongHopLe() throws Exception {
        Date start = sdf.parse("01/04/2024");
        Date end = sdf.parse("01/01/2024");

        ArrayList<ThongKeNhaCungCapDTO> result = ThongKeDAO.getThongKeNCC("Nokia", start, end);

        // Tùy vào cách bạn xử lý thời gian sai
        assertNotNull(result);
        assertTrue("Kết quả phải rỗng do thời gian không hợp lệ", result.isEmpty());
    }

    @Test
    public void test_TCS05_ThoiGianRatLon() throws Exception {
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
    public void test2_TCS01_TenRong_ThoiGianHopLe() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("", start, end);

        assertNotNull("Kết quả không được null", result);
        System.out.println("TCS01 - Số lượng khách hàng trong khoảng thời gian: " + result.size());
    }

    @Test
    public void test2_TCS02_TenTonTai_ThoiGianHopLe() throws Exception {
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
    public void test2_TCS03_TenKhongTonTai_ThoiGianHopLe() throws Exception {
        Date start = sdf.parse("01/01/2024");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("abcxyz", start, end);

        assertNotNull(result);
        assertTrue("Không có kết quả nào phù hợp", result.isEmpty());
    }

    @Test
    public void test2_TCS04_ThoiGianKhongHopLe() throws Exception {
        Date start = sdf.parse("01/04/2024");
        Date end = sdf.parse("01/01/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("Hà", start, end);

        // Tuỳ thuộc vào logic trong DAO bạn có thể thay assert này
        assertNotNull(result);
        assertTrue("Kết quả phải rỗng do thời gian không hợp lệ", result.isEmpty());
    }

    @Test
    public void test2_TCS05_ThoiGianRatLon() throws Exception {
        Date start = sdf.parse("01/01/2014");
        Date end = sdf.parse("01/04/2024");

        ArrayList<ThongKeKhachHangDTO> result = ThongKeDAO.getThongKeKhachHang("", start, end);

        assertNotNull(result);
        System.out.println("TCS05 - Số lượng khách hàng trong 10 năm qua: " + result.size());
    }


    /**
     * Test of getThongKeTheoThang method, of class ThongKeDAO.
     */
    

    /**
     * Test of getThongKeTungNgayTrongThang method, of class ThongKeDAO.
     */
    

    /**
     * Test of getThongKe7NgayGanNhat method, of class ThongKeDAO.
     */
    

    /**
     * Test of getThongKeTuNgayDenNgay method, of class ThongKeDAO.
     */
    
    
}
