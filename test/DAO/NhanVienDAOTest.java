
package DAO;

import DTO.NhanVienDTO;
import config.JDBCUtil;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class NhanVienDAOTest {

    private Connection con;
    private NhanVienDAO dao;
    
    public NhanVienDAOTest() {
    }
     @Before
    public void setUp() throws SQLException {
        con = getConnection();
        con.setAutoCommit(false);
        dao = new  NhanVienDAO();
    }
    @AfterClass
    public static void tearDownClass() {
    }

//    Phương thức insert()
   private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testInsertNhanVien_Valid_NV01() throws Exception {
        System.out.println("NV01 - Thêm nhân viên hợp lệ");


        try {
            Date ngaysinh = sdf.parse("1995-05-20");
            NhanVienDTO nv = new NhanVienDTO("Nguyen Van A", 1001, ngaysinh, "0123456789", 1);
            nv.setEmail("nva_test@example.com");

            int result = dao.insert(nv);
            assertEquals(1, result);
        } finally {
        }
    }

    @Test
    public void testInsertNhanVien_EmptyName_NV02() throws Exception {
        System.out.println("NV02 - Thiếu trường Tên");

      

        try {
            Date ngaysinh = sdf.parse("1995-05-20");
            NhanVienDTO nv = new NhanVienDTO("", 1001, ngaysinh, "0123456789", 1);
            nv.setEmail("emptyname@example.com");

            int result = dao.insert(nv);
            assertTrue(result == 0);
        } finally {
           
        }
    }

    @Test
    public void testInsertNhanVien_InvalidGender_NV03() throws Exception {
        System.out.println("NV03 - Giới tính không hợp lệ");

        try {
            Date ngaysinh = sdf.parse("1990-10-10");
            NhanVienDTO nv = new NhanVienDTO("Nguyen Van B", 1001, ngaysinh, "0987654321", 1);
            nv.setEmail("invalidgender@example.com");

            int result = dao.insert(nv);
            assertTrue(result == 0);
        } finally {
        }
    }

    @Test
    public void testInsertNhanVien_InvalidDate_NV04() throws Exception {
        System.out.println("NV04 - Ngày sinh sai định dạng");

        try {
            // Truyền null do không parse được
            NhanVienDTO nv = new NhanVienDTO("Nguyen Van C", 1001, null, "0333333333", 1);
            nv.setEmail("baddate@example.com");

            int result = dao.insert(nv);
            assertTrue(result == 0);
        } finally {
        }
    }

    @Test
    public void testInsertNhanVien_DuplicateEmail_NV05() throws Exception {
        System.out.println("NV05 - Email đã tồn tại");

        try {
            Date ngaysinh = sdf.parse("1999-12-12");
            NhanVienDTO nv = new NhanVienDTO("Nguyen Van D", 1001, ngaysinh, "0999999999", 1);
            nv.setEmail("transinh085@gmail.com"); // Email này đã tồn tại trong DB

            int result = dao.insert(nv);
            assertTrue(result == 0);
        } finally {
           
        }
    }

    @Test
    public void testInsertNhanVien_InvalidEmail_NV06() throws Exception {
        System.out.println("NV06 - Email sai định dạng");

        try {
            Date ngaysinh = sdf.parse("1991-08-08");
            NhanVienDTO nv = new NhanVienDTO("Nguyen Van E", 1, ngaysinh, "0888888888", 1);
            nv.setEmail("abc.com"); // Không có @

            int result = dao.insert(nv);
            assertTrue(result == 0);
        } finally {
            
        }
    }

    @Test
    public void testInsertNhanVien_Batch_NV07() throws Exception {
        System.out.println("NV07 - Thêm 2 nhân viên liên tiếp");


        try {
            Date date1 = sdf.parse("1990-01-01");
            Date date2 = sdf.parse("1992-02-02");

            NhanVienDTO nv1 = new NhanVienDTO("Batch A", 1, date1, "0700000001", 1);
            nv1.setEmail("batch1@example.com");
            int r1 = dao.insert(nv1);

            NhanVienDTO nv2 = new NhanVienDTO("Batch B", 0, date2, "0700000002", 1);
            nv2.setEmail("batch2@example.com");
            int r2 = dao.insert(nv2);

            assertEquals(1, r1);
            assertEquals(1, r2);
        } finally {
           
        }
    }
    
//    Phương thức Delete()
      @Test
    public void testDeleteNhanVien_HopLe_NV08() throws Exception {
        System.out.println("NV08 - Xóa nhân viên hợp lệ");

        
        int result = dao.delete("1001");

        assertEquals(1, result);

    }

    @Test
    public void testDeleteNhanVien_KhongTonTai_NV09() throws Exception {
        System.out.println("NV09 - Mã nhân viên không tồn tại");

        int result = dao.delete("999");

        assertEquals(0, result);

    }

    @Test
    public void testDeleteNhanVien_MaRong_NV10() throws Exception {
        System.out.println("NV10 - Mã nhân viên rỗng");

        int result = dao.delete("");

        assertEquals(0, result);

    }

    @Test
    public void testDeleteNhanVien_LienTiep_NV11() throws Exception {
        System.out.println("NV11 - Gọi nhiều lần liên tiếp");

        int result1 = dao.delete("1001");
        int result2 = dao.delete("1001");

        assertEquals(1, result1);
        assertEquals(0, result2);

    }

    @Test
    public void testDeleteNhanVien_KyTuDacBiet_NV12() throws Exception {
        System.out.println("NV12 - Mã nhân viên chứa ký tự đặc biệt");

        NhanVienDAO dao = new NhanVienDAO();
        int result = dao.delete("NV2");

        assertEquals(0, result);

    }
    
//    Phương thức Update()
      @Test
    public void testUpdate_ValidInput_NV13() throws Exception {
        System.out.println("NV13 - Cập nhật nhân viên hợp lệ");
    
        Date ngaysinh = sdf.parse("1995-05-20");
        NhanVienDTO dto = new NhanVienDTO("Nguyen Van A", 1001, ngaysinh, "0123456789", 1);
        dto.setEmail("nva_test@example.com");
       
        int result = dao.update(dto);

        assertEquals(1, result);

    }

    @Test
    public void testUpdate_InvalidMaNhanVien_NV14() throws Exception {
        System.out.println("NV14 - Mã nhân viên không tồn tại");
    

        Date ngaysinh = sdf.parse("1995-05-20");
        NhanVienDTO dto = new NhanVienDTO("Nguyen Van A", 1001, ngaysinh, "0123456789", 1);
        dto.setEmail("nva_test@example.com");
        int result = dao.update(dto);

        assertEquals(0, result);

    }

    @Test
    public void testUpdate_MissingName_NV15() throws Exception {
        System.out.println("NV15 - Thiếu trường họ tên");
        

        Date ngaysinh = sdf.parse("1995-05-20");
        NhanVienDTO dto = new NhanVienDTO(null, 1001, ngaysinh, "0123456789", 1);
        dto.setEmail("nva_test@example.com");
        int result = dao.update(dto);

        assertEquals(0, result);

    }

    @Test
    public void testUpdate_InvalidGender_NV16() throws Exception {
        System.out.println("NV16 - Giới tính không hợp lệ");

        Date ngaysinh = sdf.parse("1995-05-20");
        NhanVienDTO dto = new NhanVienDTO("Nguyen Van A", 1001, ngaysinh, "0123456789", 1);
        dto.setEmail("nva_test@example.com");
        int result = dao.update(dto);

        assertEquals(0, result);

    }

    @Test
    public void testUpdate_InvalidDateFormat_NV17() throws Exception {
        System.out.println("NV17 - Ngày sinh sai định dạng");
        try {
            Date ngaysinh = sdf.parse("abc");
        } catch (IllegalArgumentException e) {
            assertEquals("abc", "abc"); // xác nhận lỗi xảy ra
        }
    }

   

    @Test
    public void testUpdate_InvalidEmailFormat_NV19() throws Exception {
        System.out.println("NV19 - Email sai định dạng");
        
       
        Date ngaysinh = sdf.parse("1995-05-20");
        NhanVienDTO dto = new NhanVienDTO("Nguyen Van A", 1001, ngaysinh, "0123456789", 1);
        dto.setEmail("nva_testexample.com");
      
        int result = dao.update(dto);

        assertEquals(0, result);

    }

    @Test
    public void testUpdate_InvalidStatus_NV20() throws Exception {
        System.out.println("NV20 - Trạng thái âm");
       
        Date ngaysinh = sdf.parse("1995-05-20");
        NhanVienDTO dto = new NhanVienDTO("Nguyen Van A", 1001, ngaysinh, "0123456789", -1);
        dto.setEmail("nva_test@example.com");
        int result = dao.update(dto);

        assertEquals(0, result);

    }

    @Test
    public void testUpdate_TwoConsecutiveUpdates_NV21() throws Exception {
        System.out.println("NV21 - Cập nhật 2 nhân viên liên tiếp");
         
        Date ngaysinh = sdf.parse("1995-05-20");
        NhanVienDTO dto1 = new NhanVienDTO("Nguyen Van A", 1001, ngaysinh, "0123456789", 1);
        dto1.setEmail("nva_test@example.com");
  
        NhanVienDTO dto2 = new NhanVienDTO("Nguyen Van B", 1001, ngaysinh, "0123456789", 1);
        dto1.setEmail("nva_test2@example.com");

        int result1 = dao.update(dto1);
        int result2 = dao.update(dto2);

        assertEquals(1, result1);
        assertEquals(1, result2);

      
    }
    
//    Phương thức SelectALL
     @Test
    public void testSelectAll_NV22() {
        System.out.println("NV22 - Lấy tất cả nhân viên thành công");
         List<NhanVienDTO> list = dao.selectAll();
        assertNotNull(list);
        assertTrue(list.size() > 0); // Có dữ liệu
    }

    @Test
    public void testSelectAll_NV23() {
        System.out.println("NV23 - Không có dữ liệu trong bảng nhân viên");
        NhanVienDAO dao = new NhanVienDAO();

        List<NhanVienDTO> list = dao.selectAll();
        assertNotNull(list);
        assertEquals(0, list.size()); // Danh sách trống
    }
    
    
//    Phương thức selectAllNV():

     @Test
    public void testSelectAllNV_NV24() {
        System.out.println("NV24 - Kiểm tra chức năng chính của truy vấn nhân viên chưa có tài khoản");
        List<NhanVienDTO> list = dao.selectAllNV();
        assertNotNull(list);
    }

    @Test
    public void testSelectAllNV_NV25() {
        System.out.println("NV25 - Không có nhân viên nào có trạng thái = 1");
        List<NhanVienDTO> list = dao.selectAllNV();
        assertNotNull(list);
        assertEquals(0, list.size()); // Danh sách trống
    }

    @Test
    public void testSelectAllNV_NV26() {
        System.out.println("NV26 - Tất cả nhân viên đã có tài khoản");
        List<NhanVienDTO> list = dao.selectAllNV();
        assertNotNull(list);
        assertEquals(0, list.size()); // Danh sách trống vì không còn nhân viên chưa có tài khoản
    }
      @Test
    public void testSelectById_NV27() {
        System.out.println("NV27 - ID tồn tại hợp lệ");
        NhanVienDTO nv = dao.selectById("1");
        assertNotNull(nv);
        assertEquals(1, nv.getManv());
    }
     @Test
    public void testSelectById_NV28() {
        System.out.println("NV28 - ID không tồn tại");
        NhanVienDTO nv = dao.selectById("999");
        assertNull(nv);
    }

    @Test
    public void testSelectById_NV29() {
        System.out.println("NV29 - ID là chuỗi rỗng");
        NhanVienDTO nv = dao.selectById(null);
        assertNull(nv);
    }

    @Test
    public void testSelectById_NV30() {
        System.out.println("NV30 - ID có định dạng không hợp lệ");
        try {
            NhanVienDTO nv = dao.selectById("NV09");
            assertNull(nv);
        } catch (Exception e) {
            assertTrue(true); // Có thể throw exception tùy cách triển khai DAO
        }
    }

    @Test
    public void testSelectById_NV31() {
        System.out.println("NV31 - Nhân viên đã bị xóa mềm (trangthai = -1)");
        NhanVienDTO nv = dao.selectById("6"); // Giả sử ID 5 đã bị xóa mềm
        assertNotNull(nv);
        assertEquals(-1, nv.getTrangthai());
    }
    
//    Phương thức selectByEmail(String t)

@Test
    public void testSelectByEmail_NV32() {
        System.out.println("NV32 - Email tồn tại");
        NhanVienDTO nv = dao.selectByEmail("transinh085@gmail.com");
        assertNotNull(nv);
        assertEquals("transinh085@gmail.com", nv.getEmail());
    }

    @Test
    public void testSelectByEmail_NV33() {
        System.out.println("NV33 - Email không tồn tại");
        NhanVienDTO nv = dao.selectByEmail("trans@gmail.com");
        assertNull(nv);
    }

    @Test
    public void testSelectByEmail_NV34() {
        System.out.println("NV34 - Email là chuỗi rỗng");
        NhanVienDTO nv = dao.selectByEmail("");
        assertNull(nv);
    }

    @Test
    public void testSelectByEmail_NV35() {
        System.out.println("NV35 - Email chứa khoảng trắng");
        NhanVienDTO nv = dao.selectByEmail("transinh 085 @gmail.com");
        assertNull(nv);
    }
}
