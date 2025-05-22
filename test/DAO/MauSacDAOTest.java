/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import static DAO.KhuVucKhoDAOTest.connection;
import DTO.ThuocTinhSanPham.MauSacDTO;
import config.JDBCUtil;
import static config.JDBCUtil.getConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Maii
 */
public class MauSacDAOTest {
    static Connection connection;
    MauSacDAO dao;
    
    public MauSacDAOTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        dao = new MauSacDAO();
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }
    
    @After
    public void tearDown() throws Exception {
    }


    /**
     * Test of insert method, of class MauSacDAO.
     */
    // TT_062: Đủ thông tin - insert thành công
    @Test
    public void testInsert_ValidData_TT_062() {
        MauSacDTO mau = new MauSacDTO(14, "Đỏ");
        int result = dao.insert(mau);
        assertEquals(1, result); // Cơ sở dữ liệu được cập nhật đúng
    }

    // TT_063: Mã Id đã tồn tại - insert thất bại
    @Test
    public void testInsert_ExistingId_TT_063() {
        MauSacDTO mau = new MauSacDTO(1, "Xanh");
        int result = dao.insert(mau);
        assertEquals(0, result); // Cơ sở dữ liệu không thay đổi
    }

    // TT_064: Dữ liệu không hợp lệ - insert ném Exception
    @Test(expected = Exception.class)
    public void testInsert_InvalidData_TT_064() {
        MauSacDTO mau = new MauSacDTO(-1, "Xanh");
        dao.insert(mau); // Expect: Exception do mã âm
    }

    // TT_065: Dữ liệu thiếu (null) - insert ném Exception
    @Test(expected = Exception.class)
    public void testInsert_NullField_TT_065() {
        MauSacDTO mau = new MauSacDTO(2, null);
        dao.insert(mau); // Expect: Exception do tên màu null
    }



    /**
     * Test of update method, of class MauSacDAO.
     */
    // TT_066: Mã Id tồn tại - update thành công
    @Test
    public void testUpdate_ValidData_TT_066() {
        MauSacDTO mau = new MauSacDTO(1, "Hồng");
        int result = dao.update(mau);
        assertEquals(1, result); // Cơ sở dữ liệu được cập nhật đúng
    }

    // TT_067: Mã Id không tồn tại - update thất bại
    @Test
    public void testUpdate_NonExistentId_TT_067() {
        MauSacDTO mau = new MauSacDTO(-1, "Đỏ");
        int result = dao.update(mau);
        assertEquals(0, result); // Cơ sở dữ liệu không thay đổi
    }

    // TT_068: Mã không hợp lệ - update ném Exception
    @Test(expected = Exception.class)
    public void testUpdate_InvalidId_TT_068() {
        MauSacDTO mau = new MauSacDTO(Integer.parseInt("A@"), null); // sẽ ném NumberFormatException
        dao.update(mau); // Expect: Exception
    }

    // TT_069: Trường dữ liệu thiếu (null) - update ném Exception
    @Test(expected = Exception.class)
    public void testUpdate_NullField_TT_069() {
        dao.insert(new MauSacDTO(1, "Đỏ")); // Chuẩn bị dữ liệu
        MauSacDTO mau = new MauSacDTO(1, null);
        dao.update(mau); // Expect: Exception do tên màu null
    }


    /**
     * Test of delete method, of class MauSacDAO.
     */
    // TT_070: Mã Id tồn tại - xóa thành công
    @Test
    public void testDelete_MaIdTonTai_TT_070() {
        String maId = "1";
        int result = dao.delete(maId);
        assertEquals(1, result); // Cơ sở dữ liệu được cập nhật đúng
    }

    // TT_071: Mã Id không tồn tại - không xóa
    @Test
    public void testDelete_MaIdKhongTonTai_TT_071() {
        String maId = "9999";
        int result = dao.delete(maId);
        assertEquals(0, result); // Cơ sở dữ liệu không thay đổi
    }

    // TT_072: Mã Id không hợp lệ - ném ngoại lệ
    @Test(expected = Exception.class)
    public void testDelete_MaIdKhongHopLe_TT_072() {
        String maId = "-1";
        dao.delete(maId); // Expect exception
    }

    // TT_073: Mã Id null - ném ngoại lệ
    @Test(expected = Exception.class)
    public void testDelete_MaIdNull_TT_073() {
        String maId = null;
        dao.delete(maId); // Expect exception
    }
    
    
    

    /**
     * Test of selectAll method, of class MauSacDAO.
     */
    // TC_074: Cơ sở dữ liệu có dữ liệu - trả về danh sách đầy đủ và đúng
    @Test
    public void testSelectAll_CoDuLieu_TC_074() {
        ArrayList<MauSacDTO> list = dao.selectAll();
        assertNotNull(list);
        assertTrue(list.size() > 0); // Danh sách có dữ liệu
        // Có thể kiểm tra thêm dữ liệu cụ thể nếu cần
    }

    // TC_075: Cơ sở dữ liệu không có dữ liệu - trả về danh sách rỗng
    @Test
    public void testSelectAll_KhongCoDuLieu_TC_075() throws SQLException {
        ArrayList<MauSacDTO> list = dao.selectAll();
        assertNotNull(list);
        assertEquals(0, list.size()); // Danh sách rỗng
    } 


    /**
     * Test of selectById method, of class MauSacDAO.
     */
    // TT_076: Mã Id tồn tại - trả về đúng MauSacDTO
    @Test
    public void testSelectById_TonTai_TT_076() {
        MauSacDTO result = dao.selectById("1");
        assertNotNull(result);
        assertEquals(1, result.getMamau());
    }

    // TT_077: Mã Id không tồn tại - trả về null
    @Test
    public void testSelectById_KhongTonTai_TT_077() {
        MauSacDTO result = dao.selectById("9999");
        assertNull(result);
    }

    // TT_078: Mã Id không hợp lệ - ném ngoại lệ
    @Test(expected = Exception.class)
    public void testSelectById_KhongHopLe_TT_078() {
        dao.selectById("abc"); // Expect exception
    }

    // TT_079: Mã Id null - ném ngoại lệ
    @Test(expected = Exception.class)
    public void testSelectById_Null_TT_079() {
        dao.selectById(null); // Expect exception
    }

    /**
     * Test of getAutoIncrement method, of class MauSacDAO.
     */
    // TT_080: Cơ sở dữ liệu có dữ liệu - trả về giá trị AUTO_INCREMENT > 1
    @Test
    public void testGetAutoIncrement_CoDuLieu_TT_080() {
        int auto = dao.getAutoIncrement();
        assertTrue(auto > 1); // Giả sử đã có dữ liệu trong bảng
    }

    // TT_081: Cơ sở dữ liệu trống - AUTO_INCREMENT = 1
    @Test
    public void testGetAutoIncrement_KhongDuLieu_TT_081() throws SQLException {
        int auto = dao.getAutoIncrement();
        assertEquals(1, auto); // Giá trị mặc định khi bảng rỗng
    }
    
}