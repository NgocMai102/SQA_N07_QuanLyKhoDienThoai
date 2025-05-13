/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.DungLuongRomDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maii
 */
public class DungLuongRomDAOTest {
    
    private DungLuongRomDAO dao;
    private Connection conn;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new DungLuongRomDAO();
    }
    
    
    public DungLuongRomDAOTest() {
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
     * Test of insert method, of class SanPhamDAO.
     */
    // TT_003: Đủ thông tin hợp lệ
    @Test
    public void testInsert_ValidROM_TT_003() throws SQLException {
        DungLuongRomDTO dto = new DungLuongRomDTO(0, 8);
        int result = dao.insert(dto);
        assertEquals("Thêm thành công", 1, result);
    }

    // TT_004: Mã Id đã tồn tại
    @Test
    public void testInsert_ExistingIdROM_TT_004() throws SQLException {
        DungLuongRomDTO dto = new DungLuongRomDTO(1, 8); // Giả sử ID = 1 đã tồn tại
        int result = dao.insert(dto);
        assertEquals("Thêm thất bại do trùng mã", 0, result);
    }

    // TT_005: Dữ liệu không hợp lệ (dungluongrom âm)
    @Test(expected = IllegalArgumentException.class)
    public void testInsert_InvalidDataROM_TT_005() throws SQLException {
        DungLuongRomDTO dto = new DungLuongRomDTO(2, -4);
        int result = dao.insert(dto);
    }

    // TT_006: Thiếu dữ liệu (dungluongrom = null)
//    @Test(expected = Exception.class)
//    public void testInsert_NullFieldROM_TT_006() throws SQLException {
//        DungLuongRomDTO dto = new DungLuongRomDTO(1, null);
//        dao.insert(dto); // Ném exception
//    }
    
    /**
     * Test of update method, of class SanPhamDAO.
     */
    // TT_007: Mã Id tồn tại - cập nhật thành công
    @Test
    public void testUpdate_ValidROM_TT_007() throws SQLException {
        DungLuongRomDTO dto = new DungLuongRomDTO(1, 64); // ID = 1 đã tồn tại
        int result = dao.update(dto);
        assertEquals("Cập nhật thành công", 1, result);
    }

    // TT_008: Mã Id không tồn tại - cập nhật thất bại
    @Test
    public void testUpdate_IdNotExistROM_TT_008() throws SQLException {
        DungLuongRomDTO dto = new DungLuongRomDTO(-1, 16); // ID không tồn tại
        int result = dao.update(dto);
        assertEquals("Cập nhật thất bại do mã không tồn tại", 0, result);
    }

    // TT_009: Thiếu dữ liệu (dungluongrom = null) - ném exception
//    @Test(expected = Exception.class)
//    public void testUpdate_NullFieldROM_TT_009() throws SQLException {
//        DungLuongRomDTO dto = new DungLuongRomDTO(1, null);
//        dao.update(dto); // Giả sử phương thức này sẽ ném NullPointerException
//    }

    // TT_010: Dữ liệu không hợp lệ (dungluongrom < 0) - ném exception
    @Test(expected = Exception.class)
    public void testUpdate_InvalidDataROM_TT_010() throws SQLException {
        DungLuongRomDTO dto = new DungLuongRomDTO(1, -1); // Dữ liệu không hợp lệ
        dao.update(dto); // Giả sử phương thức update sẽ ném IllegalArgumentException
    }


    /**
     * Test of delete method, of class SanPhamDAO.
     */
    // TT_011: Mã Id tồn tại - xóa thành công
    @Test
    public void testDelete_ValidId_TT_011() throws SQLException {
        String maId = "8"; // Giả sử ID 1 tồn tại
        int result = dao.delete(maId);
        assertEquals("Cơ sở dữ liệu xóa dung lượng rom có mã Id = 1", 1, result);
    }

    // TT_012: Mã Id không tồn tại - không có thay đổi
    @Test
    public void testDelete_IdNotExist_TT_012() throws SQLException {
        String maId = "-1"; // ID không tồn tại
        int result = dao.delete(maId);
        assertEquals("Cơ sở dữ liệu không thay đổi", 0, result);
    }

    // TT_013: Mã Id không hợp lệ (ví dụ -1) - ném exception
    @Test(expected = Exception.class)
    public void testDelete_InvalidId_TT_013() throws SQLException {
        String maId = "abc@#"; // ID không hợp lệ
        dao.delete(maId); // Giả sử phương thức delete ném IllegalArgumentException
    }

    // TT_014: Mã Id null - ném exception
    @Test(expected = Exception.class)
    public void testDelete_NullId_TT_014() throws SQLException {
        String maId = null; // Mã Id bị thiếu
        dao.delete(maId); // Giả sử phương thức delete ném NullPointerException
    }
    
    // TT_015: Cơ sở dữ liệu có dữ liệu
    @Test
    public void testSelectAll_WithData_TT_021() throws SQLException {
        List<DungLuongRomDTO> list = dao.selectAll();
        assertNotNull("Danh sách không được null", list);
        assertTrue("Danh sách phải có ít nhất 1 phần tử", list.size() > 0);

        // Có thể thêm kiểm tra một phần tử cụ thể (nếu biết trước dữ liệu)
        DungLuongRomDTO rom = list.get(0);
        assertNotNull("Phần tử đầu tiên không được null", rom);
        assertNotNull("Mã ROM không được null", rom.getMadungluongrom());
        assertNotNull("Dung lượng ROM không được null", rom.getDungluongrom());
    }

    // TT_016: Cơ sở dữ liệu trống
    @Test
    public void testSelectAll_EmptyDatabase_TT_022() throws SQLException {
        // Giả định trước đó bảng đã được dọn sạch dữ liệu (truncate/delete all)
        List<DungLuongRomDTO> list = dao.selectAll();
        assertNotNull("Danh sách không được null", list);
        assertEquals("Danh sách phải rỗng khi không có dữ liệu", 0, list.size());
    }

    
    // TT_017: Mã ID tồn tại
    @Test
    public void testSelectById_ValidId_TT_017() throws SQLException {
        String maSP = "1"; // Giả sử ID 1 tồn tại
        DungLuongRomDTO dto = dao.selectById(maSP);
        assertNotNull("DungLuongROMDTO không được null", dto);
        assertEquals("Mã sản phẩm phải là 1", 1, dto.getMadungluongrom());
    }

    // TT_018: Mã ID không tồn tại
    @Test
    public void testSelectById_IdNotExist_TT_018() throws SQLException {
        String maSP = "-1"; // ID không tồn tại
        DungLuongRomDTO dto = dao.selectById(maSP);
        assertNull("Phải trả về null nếu không tìm thấy", dto);
    }

    // TT_019: Mã ID không hợp lệ (ví dụ: ký tự không phải số)
    @Test(expected = Exception.class)
    public void testSelectById_InvalidId_TT_019() throws SQLException {
        String maSP = "abc"; // Không hợp lệ
        dao.selectById(maSP); // Giả sử ném IllegalArgumentException
    }

    // TT_020: Mã ID là null
    @Test(expected = Exception.class)
    public void testSelectById_NullId_TT_020() throws SQLException {
        String maSP = null;
        dao.selectById(maSP); // Giả sử ném NullPointerException
    }

    /**
     * Test of getAutoIncrement method, of class DungLuongRomDAO.
     */
    // TT_021: Cơ sở dữ liệu có dữ liệu
    @Test
    public void testGetAutoIncrement_WithData_TT_021() throws SQLException {
        int autoIncrement = dao.getAutoIncrement();
        assertTrue("Giá trị auto increment phải >= 1", autoIncrement >= 1);
    }

    // TT_022: Cơ sở dữ liệu trống
    @Test
    public void testGetAutoIncrement_EmptyTable_TT_022() throws SQLException {
        // Giả định trước đó bảng đã được dọn trống hoặc mock
        int autoIncrement = dao.getAutoIncrement();
        assertEquals("Khi bảng trống, giá trị auto increment phải là 1", 1, autoIncrement);
    }
    
}
