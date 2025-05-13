/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.DungLuongRamDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maii
 */
public class DungLuongRamDAOTest {
    
    private DungLuongRamDAO dao;
    private Connection conn;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new DungLuongRamDAO();
    }
    
    
    public DungLuongRamDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @After
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.rollback(); // rollback các thay đổi
            conn.close();
        }
    }


    /**
     * Test of insert method, of class DungLuongRamDAO.
     */
    // TT_023: Đủ thông tin
    @Test
    public void testInsert_ValidInput_TT_023() throws SQLException {
        DungLuongRamDTO dto = new DungLuongRamDTO(0, 8);
        int result = dao.insert(dto);
        assertEquals("Cơ sở dữ liệu được thêm bản ghi mới", 1, result);
    }

    // TT_024: Mã Id đã tồn tại
    @Test
    public void testInsert_ExistingId_TT_024() throws SQLException {
        DungLuongRamDTO dto = new DungLuongRamDTO(1, 8);
        int result = dao.insert(dto);
        assertEquals("Cơ sở dữ liệu không thay đổi", 0, result);
    }

    // TT_025: Dữ liệu không hợp lệ
    @Test(expected = Exception.class)
    public void testInsert_InvalidValue_TT_025() throws SQLException {
        DungLuongRamDTO dto = new DungLuongRamDTO(2, -4);
        dao.insert(dto);
    }

    // TT_026: Dữ liệu thiếu (null)
//    @Test(expected = Exception.class)
//    public void testInsert_MissingField_TT_026() throws SQLException {
//        DungLuongRamDTO dto = new DungLuongRamDTO(1, null);
//        dao.insert(dto);
//    }

    // TT_027: Mã Id đã tồn tại
    @Test
    public void testUpdate_Valid_TT_027() throws SQLException {
        DungLuongRamDTO dto = new DungLuongRamDTO(1, 16);
        int result = dao.update(dto);
        assertEquals(1, result);
    }

    // TT_028: Mã Id không tồn tại
    @Test
    public void testUpdate_IdNotFound_TT_028() throws SQLException {
        DungLuongRamDTO dto = new DungLuongRamDTO(-1, 16);
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TT_029: Giá trị không hợp lệ
    @Test(expected = Exception.class)
    public void testUpdate_InvalidValue_TT_029() throws SQLException {
        DungLuongRamDTO dto = new DungLuongRamDTO(1, -1);
        dao.update(dto);
    }


    /**
     * Test of delete method, of class DungLuongRamDAO.
     */
     // TT_030 - Mã sản phẩm tồn tại
    @Test
    public void testDelete_IdExists_TT_030() {
        String maId = "8"; // ID đã tồn tại trong DB
        int result = dao.delete(maId);
        assertEquals(1, result); // Xóa thành công
    }

    // TT_031 - Mã sản phẩm không tồn tại
    @Test
    public void testDelete_IdNotFound_TT_031() {
        String maId = "9999"; // ID không tồn tại
        int result = dao.delete(maId);
        assertEquals(0, result); // Không có gì bị xóa
    }

    // TT_032 - Mã sản phẩm không hợp lệ (ví dụ: -1)
    @Test(expected = Exception.class)
    public void testDelete_InvalidId_TT_032() {
        String maId = "abc@#"; // ID không hợp lệ
        dao.delete(maId); // Ném ra IllegalArgumentException
    }

    // TT_033 - Mã sản phẩm null
    @Test(expected = Exception.class)
    public void testDelete_NullId_TT_033() {
        String maId = null; // Thiếu mã sản phẩm
        dao.delete(maId); // Ném ra IllegalArgumentException
    }
    
    /**
     * Test of selectAll method, of class DungLuongRamDAO.
     */    
    // TT_034 - Cơ sở dữ liệu có dữ liệu
    @Test
    public void testSelectAll_HasData_TT_034() {
        List<DungLuongRamDTO> list = dao.selectAll();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        // Có thể kiểm tra dữ liệu cụ thể nếu biết trước
    }

    // TT_035 - Cơ sở dữ liệu không có dữ liệu
    @Test
    public void testSelectAll_NoData_TT_035() {
        // Giả lập/trước đó đã xóa toàn bộ dữ liệu RAM
        // dao.clearAll();  // Nếu có hàm dọn dữ liệu
        List<DungLuongRamDTO> list = dao.selectAll();
        assertNotNull(list);
        assertEquals(0, list.size());
    }
    

    /**
     * Test of selectById method, of class DungLuongRamDAO.
     */
    // TT_036 - Mã sản phẩm tồn tại
    @Test
    public void testSelectById_IdExists_TT_036() {
        String id = "1";
        DungLuongRamDTO dto = dao.selectById(id);
        assertNotNull(dto);
        assertEquals(Integer.parseInt(id), dto.getMadlram());
    }

    // TT_037 - Mã sản phẩm không tồn tại
    @Test
    public void testSelectById_IdNotFound_TT_037() {
        String id = "9999";
        DungLuongRamDTO dto = dao.selectById(id);
        assertNull(dto);
    }

    // TT_038 - Mã sản phẩm không hợp lệ
    @Test(expected = Exception.class)
    public void testSelectById_InvalidId_TT_038() {
        String id = "abc";
        dao.selectById(id); // Ném ngoại lệ nếu xử lý input đúng cách
    }

    // TT_039 - Mã sản phẩm là null
    @Test(expected = Exception.class)
    public void testSelectById_NullId_TT_039() {
        String id = null;
        dao.selectById(id);
    }

    /**
     * Test of getAutoIncrement method, of class DungLuongRamDAO.
     */
    // TT_040 - Bảng có dữ liệu
    @Test
    public void testGetAutoIncrement_HasData_TT_040() {
        int value = dao.getAutoIncrement();
        assertTrue(value > 1); // Vì đã có ít nhất 1 dòng
    }

    // TT_041 - Bảng không có dữ liệu
    @Test
    public void testGetAutoIncrement_EmptyTable_TT_041() {
        // Nếu có thể xóa toàn bộ bản ghi trước khi test
        // dao.clearAll(); // Nếu có hàm dọn bảng
        int value = dao.getAutoIncrement();
        assertEquals(1, value); // Giá trị khởi tạo mặc định
    }
    
}
