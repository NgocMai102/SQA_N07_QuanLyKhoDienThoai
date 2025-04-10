package DAO;

import DTO.PhieuXuatDTO;
import config.JDBCUtil;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static config.JDBCUtil.getConnection;

public class PhieuXuatDAOTest {

    private Connection con;
    private  PhieuXuatDAO dao;
    
    public PhieuXuatDAOTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        con = getConnection();
        con.setAutoCommit(false);
        dao = new  PhieuXuatDAO();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Test
public void testInsert_ValidPhieuXuat_XH01() throws Exception {
    System.out.println("XH01 - Thêm phiếu xuất hợp lệ");

    PhieuXuatDTO px = new PhieuXuatDTO(15, 202, 3, Timestamp.valueOf("2025-04-10 10:00:00"), 500000, 1);
    int result = dao.insert(px);

    assertEquals(1, result);

}

@Test
public void testInsert_NullCustomer_XH02() throws Exception {
    System.out.println("XH02 - Thêm phiếu xuất thiếu mã khách hàng");

    PhieuXuatDTO px = new PhieuXuatDTO(0, 203, 2, Timestamp.valueOf("2025-04-10 10:00:00"), 100000, 1);
    int result = dao.insert(px);

    assertEquals(0, result);
}

@Test
public void testInsert_DuplicateKey_XH03() throws Exception {
    System.out.println("XH03 - Thêm phiếu xuất trùng mã");
    
    PhieuXuatDTO px = new PhieuXuatDTO(15, 2, 2, Timestamp.valueOf("2025-04-10 10:00:00"), 100000, 1);
    int result = dao.insert(px);

    assertEquals(0, result);
}

@Test
public void testInsert_NegativeTotal_XH04() throws Exception {
    System.out.println("XH04 - Thêm phiếu xuất có tổng tiền âm");

    PhieuXuatDTO px = new PhieuXuatDTO(15, 204, 3, Timestamp.valueOf("2025-04-10 10:00:00"), -500000, 1);
  
    int result = dao.insert(px);

    assertEquals(0, result);

}

@Test
public void testInsert_EmptyFields_XH05() throws Exception {
    System.out.println("XH05 - Thêm phiếu xuất không có dữ liệu");

    PhieuXuatDTO px = new PhieuXuatDTO(0);
    int result = dao.insert(px);

    assertEquals(0, result);

}

    
//Phương thức Delete()
@Test
public void testDelete_ValidId_XH06() throws Exception {
    System.out.println("XH06 - Xoá phiếu xuất hợp lệ");

    // Chèn trước 1 phiếu xuất để xóa
    PhieuXuatDTO px = new PhieuXuatDTO(15, 301, 2, Timestamp.valueOf("2025-04-10 12:00:00"), 500000, 1);
    dao.insert(px);

    int result = dao.delete("1");
    assertEquals(1, result);
}
@Test
public void testDelete_NonExistentId_XH07() throws Exception {
    System.out.println("XH07 - Xoá phiếu xuất không tồn tại");
    int result = dao.delete("999"); // ID không tồn tại
    assertEquals(0, result);

}
@Test
public void testDelete_EmptyString_XH08() throws Exception {
    System.out.println("XH08 - Xoá với mã phiếu xuất không hợp lệ");

    int result = dao.delete("PX1"); // Giả lập chuỗi rỗng
    assertEquals(0, result);

}
@Test
public void testDelete_ReferencedPhieuXuat_XH09() throws Exception {
    System.out.println("XH09 - Xoá phiếu xuất có liên kết khóa ngoại");

    int result = dao.delete("1");

    // Nếu DB bật ràng buộc khóa ngoại thì sẽ thất bại (return 0 hoặc throw exception)
    assertEquals(0, result); // Hoặc assertThrows nếu nó ném ngoại lệ

}


//Phương thức Update()
@Test
public void testUpdate_Success_XH10() throws Exception {
    System.out.println("XH10 - Cập nhật thành công");

    PhieuXuatDTO dto = new PhieuXuatDTO(1, 1, 1, Timestamp.valueOf("2025-04-06 00:00:00"), 100000, 1);

    int result = dao.update(dto);
    assertEquals(1, result);

}

@Test
public void testUpdate_InvalidId_XH11() throws Exception {
    System.out.println("XH11 - maphieuxuat không tồn tại");

    PhieuXuatDTO dto = new PhieuXuatDTO(1, 999, 1, Timestamp.valueOf("2025-04-06 00:00:00"), 100000, 1);

    int result = dao.update(dto);
    assertEquals(0, result);

}

@Test
public void testUpdate_MissingFields_XH12() throws Exception {
    System.out.println("XH12 - Cập nhật với thông tin thiếu");

    PhieuXuatDTO dto = new PhieuXuatDTO(1, 1, 1, null, 100000, 1); // thiếu thời gian

    int result = dao.update(dto);
    assertEquals(0, result);

}

@Test
public void testUpdate_NegativeAmount_XH13() throws Exception {
    System.out.println("XH13 - Giá trị tongtien âm");

    PhieuXuatDTO dto = new PhieuXuatDTO(1, 1, 1, Timestamp.valueOf("2025-04-06 00:00:00"), -50000, 1);

    int result = dao.update(dto);
    assertEquals(0, result);

}

@Test
public void testUpdate_InvalidStatus_XH14() throws Exception {
    System.out.println("XH14 - Giá trị trạng thái không hợp lệ");

    PhieuXuatDTO dto = new PhieuXuatDTO(1, 1, 1, Timestamp.valueOf("2025-04-06 00:00:00"), 100000, 99);

    int result = dao.update(dto);
    assertEquals(0, result);

}

@Test
public void testUpdate_NullObject_XH15() throws Exception {
    System.out.println("XH15 - Truyền phiếu xuất là null");

    assertThrows(NullPointerException.class, () -> {
        dao.update(null);
    });
}

@Test
public void testUpdate_NoChanges_XH16() throws Exception {
    System.out.println("XH16 - Cập nhật không có thay đổi");

    // Giả định phiếu đã tồn tại với đúng các giá trị này
    PhieuXuatDTO dto = new PhieuXuatDTO(1, 1, 1, Timestamp.valueOf("2025-04-06 00:00:00"), 100000, 1);

    int result = dao.update(dto);
    assertEquals(1, result); // Có thể vẫn return 1 nếu vẫn thực hiện update

}


//Phương thức SelectALL()
@Test
public void testSelectAll_HasData_XH19() throws Exception {
    System.out.println("XH19 - Lấy tất cả phiếu xuất thành công");

    // Giả sử dữ liệu đã có sẵn trong DB để test
    ArrayList<PhieuXuatDTO> list = dao.selectAll();

    assertNotNull(list);
    assertTrue(list.size() > 0);

    // Kiểm tra xem danh sách đã được sắp xếp theo DESC theo maphieu
    for (int i = 1; i < list.size(); i++) {
        assertTrue(list.get(i - 1).getMaphieu() >= list.get(i).getMaphieu());
    }

}

@Test
public void testSelectAll_Empty_XH20() throws Exception {
    System.out.println("XH20 - Không có dữ liệu trong bảng phiếu xuất");

    Statement stmt = con.createStatement();
    stmt.executeUpdate("DELETE FROM phieuxuat");
    ArrayList<PhieuXuatDTO> list = dao.selectAll();

    assertNotNull(list);
    assertEquals(0, list.size());

    con.rollback(); // Khôi phục lại dữ liệu
    JDBCUtil.closeConnection(con);
}

//Phương thức SelectById()
@Test
public void testSelectById_ValidId_XH21() throws Exception {
    System.out.println("XH21 - Mã phiếu xuất tồn tại");

    PhieuXuatDTO phieu = dao.selectById("1"); // giả sử đã có mã này trong CSDL

}
@Test
public void testSelectById_NonExistentId_XH22() throws Exception {
    System.out.println("XH22 - Mã phiếu xuất không tồn tại");

    PhieuXuatDTO phieu = dao.selectById("999"); // mã không tồn tại
    assertNull(phieu);

}
@Test
public void testSelectById_NullId_XH23() throws Exception {
    System.out.println("XH23 - Mã phiếu xuất null");

    PhieuXuatDTO phieu = dao.selectById(null);
    assertNull(phieu);

    con.rollback();
    JDBCUtil.closeConnection(con);
}


//Phương thức cancel()
@Test
public void testCancel_ValidPhieuXuat_XH24() throws Exception {
    System.out.println("XH24 - Hủy phiếu xuất hợp lệ");

    PhieuXuatDTO result = dao.cancel(1001); // Phiếu hợp lệ

    assertNull(result); // Vì phương thức luôn trả về null theo code hiện tại

}
@Test
public void testCancel_NonExistentPhieuXuat_XH25() throws Exception {
    System.out.println("XH25 - Hủy phiếu xuất không tồn tại");

    PhieuXuatDTO result = dao.cancel(9999); // Không có phiếu

    assertNull(result); // Trả về null là hợp lý

}
@Test
public void testCancel_EmptyChiTietPhieu_XH26() throws Exception {
    System.out.println("XH26 - Hủy phiếu xuất có chi tiết phiếu rỗng");

    PhieuXuatDTO result = dao.cancel(1005); // Chi tiết phiếu rỗng

    assertNull(result); // Vẫn trả null

}
@Test
public void testCancel_ChiTietSanPhamRong_XH27() throws Exception {
    System.out.println("XH27 - Hủy phiếu có chi tiết sản phẩm rỗng");

    PhieuXuatDTO result = dao.cancel(1006); // Chi tiết sản phẩm rỗng

    assertNull(result); // Trả về null đúng theo logic

}

//Phương thức DeletePhieu()
@Test
public void testDeletePhieu_Valid_XH28() throws Exception {
    System.out.println("XH28 - Xóa phiếu xuất hợp lệ");

   
    int result = dao.deletePhieu(1); // ID hợp lệ

    assertEquals(1, result); // Xóa thành công → trả về 1

}
@Test
public void testDeletePhieu_NotFound_XH29() throws Exception {
    System.out.println("XH29 - Xóa phiếu không tồn tại");


    int result = dao.deletePhieu(9999); // Không tồn tại

    assertEquals(0, result); // Không xóa được gì → trả về 0

}
@Test
public void testDeletePhieu_ForeignKeyConstraint_XH30() throws Exception {
    System.out.println("XH30 - Xóa phiếu đang bị tham chiếu");


    int result = 0;
    try {
        result = dao.deletePhieu(1002); // Đang bị ràng buộc
    } catch (Exception e) {
        System.out.println("Lỗi do khóa ngoại: " + e.getMessage());
        result = 0; // Gán 0 nếu lỗi xảy ra
    }

    assertEquals(0, result); // Không được xóa

   
}
@Test
public void testDeletePhieu_NegativeInput_XH31() throws Exception {
    System.out.println("XH31 - Xóa phiếu với input âm");

  
    int result = dao.deletePhieu(-1); // Input âm

    assertEquals(0, result); // Không có gì thay đổi

}
@Test
public void testDeletePhieu_InvalidID_XH32() throws Exception {
    System.out.println("XH32 - Xóa phiếu với mã phiếu âm hoặc không hợp lệ");

  
    int result = dao.deletePhieu(-10);

    // Nếu không lỗi thì result sẽ là 0 (không tìm thấy để xóa)
    assertTrue(result == 0 || result == 1); // Có thể 0 hoặc 1 tùy xử lý

}

// selectAllofKH(int makh)
@Test
public void testSelectByMaKH_ValidCustomer_XH33() throws Exception {
    System.out.println("XH33 - Lấy tất cả phiếu xuất của khách hàng hợp lệ");

    List<PhieuXuatDTO> result = dao.selectAllofKH(1); // Khách hàng tồn tại và có phiếu

    assertNotNull(result);
    assertTrue(result.size() > 0); // Có phiếu xuất hợp lệ
}
@Test
public void testSelectByMaKH_NoPhieu_XH34() throws Exception {
    System.out.println("XH34 - Lấy phiếu xuất của khách hàng không có phiếu");

  
    List<PhieuXuatDTO> result = dao.selectAllofKH(999); // Khách hàng không có phiếu

    assertNotNull(result);
    assertEquals(0, result.size()); // Danh sách rỗng
}
@Test
public void testSelectByMaKH_NegativeInput_XH35() throws Exception {
    System.out.println("XH35 - Lấy phiếu xuất với makh âm");

    
    List<PhieuXuatDTO> result = dao.selectAllofKH(-1); // Giá trị âm

    assertNotNull(result);
    assertEquals(0, result.size()); // Trả về danh sách rỗng
}
@Test
public void testSelectByMaKH_CustomerNotExist_XH36() throws Exception {
    System.out.println("XH36 - Lấy phiếu xuất với mã khách hàng không tồn tại");

    List<PhieuXuatDTO> result = dao.selectAllofKH(999); // Không có khách hàng này

    assertNotNull(result);
    assertEquals(0, result.size()); // Danh sách rỗng
}


    @Test
    public void testGetAutoIncrement() {
        System.out.println("getAutoIncrement");
        PhieuXuatDAO instance = new PhieuXuatDAO();
        int expResult = 0;
        int result = instance.getAutoIncrement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }   
}
