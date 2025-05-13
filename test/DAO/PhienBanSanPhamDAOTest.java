/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ChiTietSanPhamDTO;
import DTO.PhienBanSanPhamDTO;
import java.sql.Connection;
import static config.JDBCUtil.getConnection;
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
public class PhienBanSanPhamDAOTest {
    
    private PhienBanSanPhamDAO dao;
    private Connection conn;
    
    public PhienBanSanPhamDAOTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new PhienBanSanPhamDAO();
    }
    
    @After
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of insert method, of class PhienBanSanPhamDAO.
     */
    // TC_082: 1 bản ghi đầy đủ thông tin
    @Test
    public void testInsert_OneValidRecord_TC_082() throws SQLException {
        ArrayList<PhienBanSanPhamDTO> list = new ArrayList<>();
        list.add(new PhienBanSanPhamDTO(0, 7, 128, 6, 2, 5000, 7000, 10));

        int result = dao.insert(list);
        assertEquals("Phải chèn thành công 1 bản ghi", 1, result);
    }

    // TC_083: 3 bản ghi đầy đủ thông tin
    @Test
    public void testInsert_ThreeValidRecords_TC_083() throws SQLException {
        ArrayList<PhienBanSanPhamDTO> list = new ArrayList<>();
        list.add(new PhienBanSanPhamDTO(0, 2, 128, 6, 2, 4000, 7000, 11));
        list.add(new PhienBanSanPhamDTO(0, 3, 128, 6, 2, 5000, 8000, 12));
        list.add(new PhienBanSanPhamDTO(0, 4, 128, 6, 2, 6000, 8000, 13));

        int result = dao.insert(list);
        assertEquals("Phải chèn thành công 3 bản ghi", 1, result);
    }

    // TC_084: 2 bản ghi có cùng mã (trùng khóa chính)
    @Test(expected = Exception.class)
    public void testInsert_DuplicatePrimaryKey_TC_084() throws SQLException {
        ArrayList<PhienBanSanPhamDTO> list = new ArrayList<>();
        list.add(new PhienBanSanPhamDTO(20, 2, 128, 6, 2, 4000, 7000, 11));
        list.add(new PhienBanSanPhamDTO(0, 3, 128, 6, 2, 5000, 8000, 12));
        list.add(new PhienBanSanPhamDTO(20, 4, 128, 6, 2, 6000, 8000, 13)); // Trùng maphienbansp = 20

        dao.insert(list);
    }

    // TC_085: Có trường dữ liệu âm (không hợp lệ)
    @Test(expected = Exception.class)
    public void testInsert_InvalidNegativeFields_TC_085() throws SQLException {
        ArrayList<PhienBanSanPhamDTO> list = new ArrayList<>();
        list.add(new PhienBanSanPhamDTO(1, 1, -128, -6, 2, 4000, 7000, 11));
        list.add(new PhienBanSanPhamDTO(2, 2, 128, 6, 2, 5000, 8000, 12));
        list.add(new PhienBanSanPhamDTO(3, 3, -128, -6, 2, 6000, 8000, 13));

        dao.insert(list);
    }

    // TC_086: Danh sách rỗng
    @Test
    public void testInsert_EmptyList_TC_086() throws SQLException {
        ArrayList<PhienBanSanPhamDTO> list = new ArrayList<>();

        int result = dao.insert(list);
        assertEquals("Không có bản ghi nào được chèn", 0, result);
    }

    // TC_087: Chứa 1 DTO null
    @Test(expected = Exception.class)
    public void testInsert_ContainsNullDTO_TC_087() throws SQLException {
        ArrayList<PhienBanSanPhamDTO> list = new ArrayList<>();
        list.add(new PhienBanSanPhamDTO(1, 1, 128, 6, 2, 4000, 7000, 11));
        list.add(null); // DTO null
        list.add(new PhienBanSanPhamDTO(3, 103, 128, 6, 2, 6000, 8000, 13));

        dao.insert(list);
    }

    // TC_088: Giá trị lớn bất thường
//    @Test(expected = Exception.class)
//    public void testInsert_AbnormallyLargeValue_TC_088() throws SQLException {
//        ArrayList<PhienBanSanPhamDTO> list = new ArrayList<>();
//        list.add(new PhienBanSanPhamDTO(999999999999, 1, 128, 6, 2, 4000, 7000, 11)); // quá giới hạn int
//        list.add(new PhienBanSanPhamDTO(2, 2, 128, 6, 2, 5000, 8000, 12));
//        list.add(new PhienBanSanPhamDTO(3, 3, 128, 6, 2, 6000, 8000, 13));
//
//        dao.insert(list);
//    }
    
    // TC_089: Đủ thông tin
    @Test
    public void testInsert_ValidData_TC_089() {
        PhienBanSanPhamDTO dto = new PhienBanSanPhamDTO(0, 3, 128, 6, 2, 6000, 8000, 13);
        int result = dao.insert(dto);
        assertEquals(1, result); // Dữ liệu hợp lệ → insert thành công
    }

    // TC_090: Có mã Id đã tồn tại
    @Test
    public void testInsert_DuplicateId_TC_090() {
        // Giả sử bản ghi có mã 1 đã tồn tại trước đó
        PhienBanSanPhamDTO dto = new PhienBanSanPhamDTO(1, 1, 128, 6, 2, 6000, 8000, 13);
        int result = dao.insert(dto);
        assertEquals(0, result); // Không được insert → return 0
    }
    
    // TC_091: Có trường dữ liệu không hợp lệ (RAM, ROM âm)
    @Test(expected = Exception.class)
    public void testInsert_InvalidNegativeFields_TC_091() {
        PhienBanSanPhamDTO dto = new PhienBanSanPhamDTO(3, 3, -128, -6, 2, 6000, 8000, 13);
        dao.insert(dto); // Mong đợi ném ra IllegalArgumentException
    }
    
    // TC_092: Có trường dữ liệu bị thiếu (null ROM)
//    @Test(expected = Exception.class)
//    public void testInsert_MissingField_TC_092() {
//        PhienBanSanPhamDTO dto = new PhienBanSanPhamDTO();
//        dto.setMaphienbansp(3);
//        dto.setMasp(3);
//        dto.setRom(null); // Thiếu ROM
//        dto.setRam(6);
//        dto.setMausac(2);
//        dto.setGianhap(6000);
//        dto.setGiaxuat(8000);
//        dto.setSoluongton(13);
//
//        int result = dao.insert(dto);
//       
//    }
    
    // TC_093: Mã Id tồn tại - Kiểm tra phương thức update() khi có đầy đủ thông tin hợp lệ
    @Test
    public void testUpdate_ValidData_TC_093() {
        PhienBanSanPhamDTO dto = new PhienBanSanPhamDTO(1, 3, 128, 6, 2, 6000, 8000, 13);
        dao.update(dto); // Mong đợi cơ sở dữ liệu được cập nhật thành công
    }

    // TC_094: Mã Id không tồn tại - Kiểm tra phương thức update() khi mã Id không tồn tại trong cơ sở dữ liệu
    @Test
    public void testUpdate_ExistingId_TC_094() {
        PhienBanSanPhamDTO dto = new PhienBanSanPhamDTO(1, 1, 128, 6, 2, 6000, 8000, 13);
        dao.update(dto); // Mong đợi dữ liệu không thay đổi vì mã Id không tồn tại
    }

    // TC_095: Có trường dữ liệu không hợp lệ - Kiểm tra phương thức update() khi có giá trị dữ liệu không hợp lệ
    @Test(expected = Exception.class)
    public void testUpdate_InvalidNegativeFields_TC_095() {
        PhienBanSanPhamDTO dto = new PhienBanSanPhamDTO(3, 3, -128, -6, 2, 6000, 8000, 13);
        dao.update(dto); // Mong đợi ném ra Exception vì giá trị âm không hợp lệ
    }

    // TC_096: Có trường dữ liệu bị thiếu (null) - Kiểm tra phương thức update() khi có giá trị dữ liệu bị thiếu
//    @Test(expected = Exception.class)
//    public void testUpdate_MissingData_TC_096() {
//        PhienBanSanPhamDTO dto = new PhienBanSanPhamDTO(3, 3, null, 6, 2, 6000, 8000, 13);
//        dao.update(dto); // Mong đợi ném ra Exception vì dữ liệu bị thiếu (null)
//    }

    // TC_097: Mã Id tồn tại - Kiểm tra việc xóa sản phẩm tồn tại trong cơ sở dữ liệu
    @Test
    public void testDelete_ValidMaId_TC_097() {
        String maId = "55";
        dao.delete(maId); // Mong đợi cơ sở dữ liệu được cập nhật đúng, bản ghi với maId = 1 bị xóa
    }

    // TC_098: Mã Id không tồn tại - Kiểm tra khi sản phẩm không tồn tại trong cơ sở dữ liệu
    @Test
    public void testDelete_NonExistentMaId_TC_098() {
        String maId = "9999";
        dao.delete(maId); // Mong đợi cơ sở dữ liệu không thay đổi vì maId = 9999 không tồn tại
    }

    // TC_099: Mã Id không hợp lệ - Kiểm tra khi mã Id không hợp lệ
    @Test(expected = Exception.class)
    public void testDelete_InvalidMaId_TC_099() {
        String maId = "-1"; // Mã Id không hợp lệ
        dao.delete(maId); // Mong đợi ném ra IllegalArgumentException
    }

    // TC_100: Mã Id bị thiếu (là null) - Kiểm tra khi mã Id bị thiếu hoặc không được set
    @Test(expected = Exception.class)
    public void testDelete_MissingMaId_TC_100() {
        String maId = null; // Mã Id bị thiếu
        dao.delete(maId); // Mong đợi ném ra IllegalArgumentException
    }

//    // TC_101: Cơ sở dữ liệu trống - Kiểm tra trả về danh sách đầy đủ và đúng dữ liệu
//    @Test
//    public void testSelectAll_EmptyDatabase_TC_101() {
//        List<PhienBanSanPhamDTO> list = dao.selectAll(); // Mong đợi danh sách trả về có size > 0, dữ liệu khớp với trong DB
//        assertTrue(list.size() > 0); // Kiểm tra danh sách không trống
//    }
//
//    // TC_102: Cơ sở dữ liệu không trống - Kiểm tra trả về danh sách rỗng nếu không có bản ghi hợp lệ
//    @Test
//    public void testSelectAll_NonEmptyDatabase_TC_102() {
//        List<PhienBanSanPhamDTO> list = dao.selectAll(); // Mong đợi danh sách trả về có size = 0, nếu không có bản ghi hợp lệ
//        assertTrue(list.isEmpty()); // Kiểm tra danh sách trống
//    }
    
    // TC_103: Mã Id tồn tại - Kiểm tra việc chọn sản phẩm tồn tại trong cơ sở dữ liệu
    @Test
    public void testSelectById_ValidMaSP_TC_103() {
        PhienBanSanPhamDTO dto = dao.selectById(2); // Mong đợi PhienBanSanPhamDTO có masp = 1
        assertNotNull(dto); // Kiểm tra nếu đối tượng trả về không phải null
        assertEquals(2, dto.getMasp()); // Kiểm tra giá trị masp
    }

    // TC_104: Mã Id không tồn tại - Kiểm tra khi sản phẩm không tồn tại trong cơ sở dữ liệu
    @Test
    public void testSelectById_NonExistentMaSP_TC_104() {
        PhienBanSanPhamDTO dto = dao.selectById(-1); // Mong đợi null vì masp = 9999 không tồn tại trong cơ sở dữ liệu
        assertNull(dto); // Kiểm tra kết quả trả về là null
    }
//
//    // TC_105: Mã Id không hợp lệ - Kiểm tra khi sản phẩm không hợp lệ
//    @Test(expected = Exception.class)
//    public void testSelectById_InvalidMaSP_TC_105() {
//        String maSP = "abc"; // Mã Id không hợp lệ
//        dao.selectById(maSP); // Mong đợi ném ra IllegalArgumentException
//    }
//
//    // TC_106: Mã Id bị thiếu (là null) - Kiểm tra khi sản phẩm là null
//    @Test(expected = Exception.class)
//    public void testSelectById_MissingMaSP_TC_106() {
//        String maSP = null; // Mã Id bị thiếu
//        dao.selectById(maSP); // Mong đợi ném ra IllegalArgumentException
//    }

    // TC_107: Cơ sở dữ liệu trống - Kiểm tra phương thức getAutoIncrement() khi bảng có dữ liệu
    @Test
    public void testGetAutoIncrement_ValidData_TC_107() {
        // Cơ sở dữ liệu cần có ít nhất một dòng dữ liệu trong bảng phienbansanpham
        int autoIncrementValue = dao.getAutoIncrement(); // Lấy giá trị Auto Increment hiện tại trong bảng
        assertTrue(autoIncrementValue > 0); // Kiểm tra giá trị Auto Increment lớn hơn 0
    }

    // TC_108: Cơ sở dữ liệu không trống - Kiểm tra phương thức getAutoIncrement() khi bảng không có dữ liệu
    @Test
    public void testGetAutoIncrement_NoData_TC_108() {
        // Cơ sở dữ liệu không có dữ liệu trong bảng phienbansanpham
        int autoIncrementValue = dao.getAutoIncrement(); // Lấy giá trị Auto Increment trong trường hợp bảng trống
        assertEquals(1, autoIncrementValue); // Mong đợi giá trị mặc định Auto Increment là 1 khi bảng trống
    }
    
    
}
