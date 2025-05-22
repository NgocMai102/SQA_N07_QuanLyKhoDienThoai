/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import static DAO.TaiKhoanDAOTest.connection;
import DTO.ThuocTinhSanPham.HeDieuHanhDTO;
import config.JDBCUtil;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import java.sql.Connection;
import java.sql.SQLException;
import static config.JDBCUtil.getConnection;
import java.sql.DriverManager;
import java.util.List;
import static org.junit.Assert.*;


/**
 *
 * @author Maii
 */
public class HeDieuHanhDAOTest {
    
    private HeDieuHanhDAO dao;
    private Connection conn;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        dao = new HeDieuHanhDAO();
    }

    @After
    public void tearDown() throws SQLException {

    }
    
    public HeDieuHanhDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }

    /**
     * Test of insert method, of class HeDieuHanhDAO.
     */
    // TT_042 - Đủ thông tin hợp lệ
    @Test
    public void testInsert_Valid_TT_042() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(0, "Windows");
        int result = dao.insert(dto);
        assertEquals(1, result);
    }

    // TT_043 - Mã ID đã tồn tại
    @Test
    public void testInsert_DuplicateId_TT_043() {
        // Giả sử ID = 1 đã tồn tại
        HeDieuHanhDTO dto = new HeDieuHanhDTO(1, "Linux");
        int result = dao.insert(dto);
        assertEquals(0, result); // Không thêm được
    }

    // TT_044 - Dữ liệu không hợp lệ (ID âm)
    @Test(expected = Exception.class)
    public void testInsert_InvalidId_TT_044() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(-1, "Windows");
        dao.insert(dto); // Ném IllegalArgumentException
    }

    // TT_045 - Thiếu dữ liệu (tên HĐH bị null)
    @Test
    public void testInsert_MissingName_TT_045() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(2, null);
        int result = dao.insert(dto);
        assertEquals(0, result); // Không chèn vào
    }

     /**
     * Test of update method, of class HeDieuHanhDAO.
     */
    // TT_046 - Mã ID tồn tại, dữ liệu hợp lệ
    @Test
    public void testUpdate_ValidIdAndName_TT_046() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(3, "Windows");
        int result = dao.update(dto);
        assertEquals(1, result);
    }

    // TT_047 - Mã ID không tồn tại
    @Test
    public void testUpdate_IdNotExist_TT_047() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(-2, "Windows");
        int result = dao.update(dto);
        assertEquals(0, result); // Không có dòng nào được cập nhật
    }

    // TT_048 - ID hợp lệ, tên HĐH null → Exception
    @Test(expected = Exception.class)
    public void testUpdate_NullName_TT_048() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(1, null);
        dao.update(dto); // Phải ném exception nếu validate
    }

    // TT_049 - ID không hợp lệ, tên hợp lệ → Exception
//    @Test(expected = Exception.class)
//    public void testUpdate_InvalidIdFormat_TT_049() {
//        HeDieuHanhDTO dto = new HeDieuHanhDTO("@#$", "Ubuntu");
//        dao.update(dto); // Ném exception nếu ID không hợp lệ
//    }
    
    // TT_050 - Mã ID tồn tại
    @Test
    public void testDelete_IdExists_TT_050() {
        int result = dao.delete("3");
        assertEquals(1, result);
    }

    // TT_051 - Mã ID không tồn tại
    @Test
    public void testDelete_IdNotFound_TT_051() {
        int result = dao.delete("9999");
        assertEquals(0, result);
    }

    // TT_052 - Mã ID không hợp lệ (ví dụ: số âm)
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_InvalidId_TT_052() {
        dao.delete("-1"); // Hoặc bất kỳ giá trị được coi là không hợp lệ
    }

    // TT_053 - Mã ID là null
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_NullId_TT_053() {
        dao.delete(null);
    }
    @Test
    public void testSelectAll_DataExists_TT_054() {
        dao.insert(new HeDieuHanhDTO(1, "Windows"));
        dao.insert(new HeDieuHanhDTO(2, "Ubuntu"));

        List<HeDieuHanhDTO> list = dao.selectAll();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectAll_DataEmpty_TT_054() {
        List<HeDieuHanhDTO> list = dao.selectAll();
        assertNotNull(list);
        assertEquals(0, list.size());
    }
    
    // TT_056 - Mã Id tồn tại
    @Test
    public void testSelectById_IdExists_TT_056() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(1, "Windows");
        dao.insert(dto);

        HeDieuHanhDTO result = dao.selectById("1");

        assertNotNull(result);
        assertEquals(dto.getMahdh(), result.getMahdh());
        assertEquals(dto.getMahdh(), result.getMahdh());
    }

    // TT_057 - Mã Id không tồn tại
    @Test
    public void testSelectById_IdNotFound_TT_057() {
        HeDieuHanhDTO result = dao.selectById("9999");
        assertNull(result);
    }

    // TT_058 - Mã Id không hợp lệ (không phải số)
    @Test(expected = Exception.class)
    public void testSelectById_InvalidId_TT_058() {
        dao.selectById("abc");
    }

    // TT_059 - Mã Id là null
    @Test(expected = Exception.class)
    public void testSelectById_NullId_TT_059() {
        dao.selectById(null);
    }
    /**
     * Test of getAutoIncrement method, of class DungLuongRomDAO.
     */
    // TT_060: Cơ sở dữ liệu có dữ liệu
    @Test
    public void testGetAutoIncrement_WithData_TT_060() throws SQLException {
        int autoIncrement = dao.getAutoIncrement();
        assertTrue("Giá trị auto increment phải >= 1", autoIncrement >= 1);
    }

    // TT_061: Cơ sở dữ liệu trống
    @Test
    public void testGetAutoIncrement_EmptyTable_TT_061() throws SQLException {
        // Giả định trước đó bảng đã được dọn trống hoặc mock
        int autoIncrement = dao.getAutoIncrement();
        assertEquals("Khi bảng trống, giá trị auto increment phải là 1", 1, autoIncrement);
    }
    
}
