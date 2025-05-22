/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import static DAO.ThongKeDAOTest.connection;
import DTO.ThuocTinhSanPham.ThuongHieuDTO;
import config.JDBCUtil;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maii
 */
public class ThuongHieuDAOTest {
    
    static Connection connection;
    ThuongHieuDAO dao;
    
    public ThuongHieuDAOTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        dao = new ThuongHieuDAO();
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
     * Test of insert method, of class ThuongHieuDAO.
     */
    // TT_109: Đủ thông tin - insert thành công
    @Test
    public void testInsert_ValidData_TT_109() {
        ThuongHieuDTO th = new ThuongHieuDTO(0, "Huwei");
        int result = dao.insert(th);
        assertEquals(1, result); // Cơ sở dữ liệu được cập nhật đúng
    }

    // TT_110: Tên thương hiệu đã tồn tại - insert thất bại
    @Test
    public void testInsert_Duplicate_TT_110() {
        ThuongHieuDTO th = new ThuongHieuDTO(1, "Huwei110");
        int result = dao.insert(th);
        assertEquals(0, result); // Cơ sở dữ liệu không thay đổi
    }

    // TT_111: Dữ liệu không hợp lệ - insert thất bại
    @Test
    public void testInsert_InvalidData_TT_111() {
        ThuongHieuDTO th = new ThuongHieuDTO(-1, "Huwei111");
        int result = dao.insert(th);
        assertEquals(0, result); // Cơ sở dữ liệu không thay đổi
    }

    // TT_112: Thiếu tên thương hiệu (null) - Exception
    @Test(expected = Exception.class)
    public void testInsert_NullTenThuongHieu_TT_112() {
        ThuongHieuDTO th = new ThuongHieuDTO(0, null);
        dao.insert(th); // Ném ngoại lệ
    }

    /**
     * Test of update method, of class ThuongHieuDAO.
     */
    // TT_113: Mã tồn tại - update thành công
    @Test
    public void testUpdate_ValidData_TT_113() {
        ThuongHieuDTO th = new ThuongHieuDTO(1, "Huwei113");
        int result = dao.update(th);
        assertEquals(1, result); // Cơ sở dữ liệu được cập nhật đúng
    }

    // TT_114: Mã không tồn tại - update thất bại
    @Test
    public void testUpdate_MaKhongTonTai_TT_114() {
        ThuongHieuDTO th = new ThuongHieuDTO(-1, "Huwei");
        int result = dao.update(th);
        assertEquals(0, result); // Cơ sở dữ liệu không thay đổi
    }

    // TT_115: Thiếu tên thương hiệu (null) - update thất bại
    @Test
    public void testUpdate_TenThuongHieuNull_TT_115() {
        ThuongHieuDTO th = new ThuongHieuDTO(1, null);
        int result = dao.update(th);
        assertEquals(0, result); // Cơ sở dữ liệu không thay đổi
    }

    /**
     * Test of delete method, of class ThuongHieuDAO.
     */
    // TT_116: Mã tồn tại - delete thành công
   @Test
   public void testDelete_TonTai_TT_116() {
       int result = dao.delete("1");
       assertEquals(1, result); // Cơ sở dữ liệu được cập nhật đúng
   }

   // TT_117: Mã không tồn tại - delete thất bại
   @Test
   public void testDelete_KhongTonTai_TT_117() {
       int result = dao.delete("9999");
       assertEquals(0, result); // Cơ sở dữ liệu không thay đổi
   }

   // TT_118: Mã không hợp lệ - Exception
   @Test(expected = Exception.class)
   public void testDelete_MaKhongHopLe_TT_118() {
       dao.delete("-1"); // Ném ngoại lệ
   }

   // TT_119: Mã null - Exception
   @Test(expected = Exception.class)
   public void testDelete_MaNull_TT_119() {
       dao.delete(null); // Ném ngoại lệ
   }


    /**
     * Test of selectAll method, of class ThuongHieuDAO.
     */
    // TT_120: Cơ sở dữ liệu có bản ghi - danh sách > 0
    @Test
    public void testSelectAll_CoDuLieu_TT_120() {
        ArrayList<ThuongHieuDTO> list = dao.selectAll();
        assertNotNull(list);
        assertTrue(list.size() > 0); // Có ít nhất một thương hiệu
    }

    // TT_121: Cơ sở dữ liệu rỗng - danh sách size = 0
    @Test
    public void testSelectAll_KhongCoDuLieu_TT_121() {        
        ArrayList<ThuongHieuDTO> list = dao.selectAll();
        assertNotNull(list);
        assertEquals(0, list.size()); // Danh sách rỗng
    }

    
    /**
     * Test of selectById method, of class ThuongHieuDAO.
     */
    // TT_122: Mã tồn tại - trả về đúng dữ liệu
    @Test
    public void testSelectById_TonTai_TT_122() {
        ThuongHieuDTO th = dao.selectById("1");
        assertNotNull(th);
        assertEquals(1, th.getMathuonghieu());
    }

    // TT_123: Mã không tồn tại - trả về null
    @Test
    public void testSelectById_KhongTonTai_TT_123() {
        ThuongHieuDTO th = dao.selectById("9999");
        assertNull(th);
    }

    // TT_124: Mã không hợp lệ (abc) - Exception
    @Test(expected = Exception.class)
    public void testSelectById_KhongHopLe_TT_124() {
        dao.selectById("abc"); // Gây ra ngoại lệ
    }

    // TT_125: Mã null - Exception
    @Test(expected = Exception.class)
    public void testSelectById_Null_TT_125() {
        dao.selectById(null); // Gây ra ngoại lệ
    }


    /**
     * Test of getAutoIncrement method, of class ThuongHieuDAO.
     */
    // TT_126: Cơ sở dữ liệu có dữ liệu - trả về Auto Increment hiện tại
    @Test
    public void testGetAutoIncrement_CoDuLieu_TT_126() {
        int autoIncrement = dao.getAutoIncrement();
        assertTrue(autoIncrement > 1); // Vì đã có dữ liệu trước
    }

    // TT_127: Cơ sở dữ liệu rỗng - trả về giá trị mặc định là 1
    @Test
    public void testGetAutoIncrement_KhongDuLieu_TT_127() {
        int autoIncrement = dao.getAutoIncrement();
        assertEquals(1, autoIncrement); // Giá trị mặc định là 1
    }

    
}