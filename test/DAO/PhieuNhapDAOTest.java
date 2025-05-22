
package DAO;

import DTO.PhieuNhapDTO;
import com.mysql.cj.jdbc.PreparedStatementWrapper;
import config.JDBCUtil;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PhieuNhapDAOTest {
    
    public PhieuNhapDAOTest() {
    }
    static Connection connection;
    private PhieuNhapDAO instance;
    
    
     @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);
        instance = new PhieuNhapDAO();
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }

    
//    Phương thức insert()
    @Test
    public void testInsert_ValidPhieuNhap_NH01() throws Exception {
        System.out.println("Test NH01 - Thêm phiếu nhập hợp lệ");

        PhieuNhapDTO t = new PhieuNhapDTO(
            1,                          // manhacungcap
            100,                       // maphieu
            1,                          // manguoitao
            Timestamp.valueOf("2024-04-06 10:00:00"),
            1500000L,
            0
        );

        int result = instance.insert(t);

        assertEquals(1, result); // mong đợi thêm thành công

    }

    @Test
    public void testInsert_NullTimestamp_NH02() throws Exception {
        System.out.println("Test NH02 - Thêm phiếu nhập thiếu thời gian");


        // timestamp rỗng sẽ gây lỗi, ta kiểm tra exception
        try {
            PhieuNhapDTO t = new PhieuNhapDTO(
                1, 1002, 1,
                null, // null Timestamp
                1500000L,
                0
            );

            int result = instance.insert(t);
            assertEquals(0, result); // mong đợi không thêm được
        } catch (Exception e) {
            System.out.println("NH02 - Lỗi xảy ra như mong đợi: " + e.getMessage());
            assertTrue(true); // pass nếu lỗi
        }

    }

    @Test
    public void testInsert_DuplicatePrimaryKey_NH03() throws Exception {
        System.out.println("Test NH03 - Trùng mã phiếu nhập");

        PhieuNhapDTO t = new PhieuNhapDTO(
            1, 1, 1,
            Timestamp.valueOf("2024-04-06 10:00:00"),
            1500000L,
            0
        );

        int result = instance.insert(t);

        assertEquals(0, result); // mong đợi lỗi vì trùng khóa chính

    }

    @Test
    public void testInsert_InvalidForeignKey_NH04() throws Exception {
        System.out.println("Test NH04 - Mã nhà cung cấp không tồn tại");


        PhieuNhapDTO t = new PhieuNhapDTO(
            9999, 1004, 1,
            Timestamp.valueOf("2024-04-06 10:00:00"),
            1500000L,
            0
        );

        int result = instance.insert(t);

        assertEquals(0, result); // mong đợi lỗi do FK

    }
    
//    Phương thức Delete()
    @Test
public void testDelete_ExistingPhieuNhap_NH05() throws Exception {
    System.out.println("Test NH05 - Xóa phiếu nhập với mã đã tồn tại");

    // Giả sử mã phiếu "1005" có thể thêm rồi xóa
    PhieuNhapDTO t = new PhieuNhapDTO(
        1, 1005, 1,
        Timestamp.valueOf("2024-04-06 11:00:00"),
        1500000L,
        0
    );

    instance.insert(t); // chèn trước để có cái mà xóa

    int result = instance.delete("1005"); // xóa bằng chuỗi

    assertEquals(1, result); // mong đợi xóa thành công

}

@Test
public void testDelete_NonExistentPhieuNhap_NH06() throws Exception {
    System.out.println("Test NH06 - Xóa phiếu nhập không tồn tại");

    int result = instance.delete("999"); // mã không tồn tại

    assertEquals(0, result); // mong đợi xóa không thành công
}

@Test
public void testDelete_UsedInAnotherTable_NH07() throws Exception {
    System.out.println("Test NH07 - Xóa phiếu nhập đang được dùng trong đơn hàng");

    int result = 0;
    try {
        result = instance.delete("1"); // đang được dùng => vi phạm FK
    } catch (Exception e) {
        System.out.println("NH07 - Không thể xóa do ràng buộc khóa ngoại: " + e.getMessage());
    }

    assertEquals(0, result); // mong đợi không xóa được

}


//Phương thức Update()
@Test
public void testUpdate_ValidPhieuNhap_NH08() throws Exception {
    System.out.println("Test NH08 - Cập nhật phiếu nhập hợp lệ");


    PhieuNhapDTO t = new PhieuNhapDTO(1, 1, 1,
        Timestamp.valueOf("2025-04-06 10:00:00"), 100000L, 1);

    int result = instance.update(t);

    assertEquals(1, result);

}

@Test
public void testUpdate_NonExistingPhieuNhap_NH09() throws Exception {
    System.out.println("Test NH09 - Cập nhật phiếu nhập không tồn tại");

    PhieuNhapDTO t = new PhieuNhapDTO(1, 999, 1,
        Timestamp.valueOf("2025-04-06 10:00:00"), 100000L, 1);

    int result = instance.update(t);

    assertEquals(0, result);

}

@Test
public void testUpdate_MissingTimestamp_NH10() throws Exception {
    System.out.println("Test NH10 - Cập nhật thiếu thời gian");

    PhieuNhapDTO t = new PhieuNhapDTO(1, 1, 1,
        null, 100000L, 1); // thiếu thời gian

    int result = instance.update(t);

    assertEquals(0, result);

}

@Test
public void testUpdate_NegativeTongTien_NH11() throws Exception {
    System.out.println("Test NH11 - Cập nhật với tổng tiền âm");


    PhieuNhapDTO t = new PhieuNhapDTO(1, 1, 1,
        Timestamp.valueOf("2025-04-06 10:00:00"), -50000L, 1);

    int result = instance.update(t);

    assertEquals(0, result);

}

@Test
public void testUpdate_NoChanges_NH12() throws Exception {
    System.out.println("Test NH12 - Cập nhật khi không có thay đổi");

    // Giả sử thông tin đã có là giống y chang
    PhieuNhapDTO t = new PhieuNhapDTO(1, 1, 1,
        Timestamp.valueOf("2025-04-06 10:00:00"), 100000L, 1);

    int result = instance.update(t);

    assertEquals(1, result);
}

//Phương thức SelectAll()
@Test
public void testSelectAll_ReturnsSortedList_NH13() throws Exception {
    System.out.println("Test NH13 - Lấy tất cả phiếu nhập thành công");

    ArrayList<PhieuNhapDTO> result = instance.selectAll();

    assertNotNull(result);
    assertTrue(result.size() > 0);

    // Kiểm tra xem danh sách có được sắp xếp giảm dần theo maphieu không
    for (int i = 0; i < result.size() - 1; i++) {
        assertTrue(result.get(i).getMaphieu() >= result.get(i + 1).getMaphieu());
    }
}
@Test
public void testSelectAll_EmptyTable_NH14() throws Exception {
    System.out.println("Test NH14 - Không có dữ liệu trong bảng phiếu nhập");

    ArrayList<PhieuNhapDTO> result = instance.selectAll();

    assertNotNull(result);
    assertEquals(0, result.size());

}

//Phương thức selectById()
@Test
public void testSelectById_Exists_NH15() throws Exception {
    System.out.println("Test NH15 - Mã phiếu nhập tồn tại");

    String maphieu = "1";

    PhieuNhapDTO result = instance.selectById(maphieu);

    assertNotNull(result);
    assertEquals(1, result.getMaphieu());
}
@Test
public void testSelectById_NotExists_NH16() throws Exception {
    System.out.println("Test NH16 - Mã phiếu nhập không tồn tại");

    String maphieu = "999";

    PhieuNhapDTO result = instance.selectById(maphieu);

    assertNull(result);
}
@Test
public void testSelectById_NullInput_NH17() throws Exception {
    System.out.println("Test NH17 - Mã phiếu nhập null");

    String maphieu = null;

    PhieuNhapDTO result = instance.selectById(maphieu);

    assertNull(result);
}


//Phương thức statistical(long min, long max)
@Test
public void testSstatistical_NH18() throws Exception {
    System.out.println("Test NH18 - Khoảng tiền hợp lệ có dữ liệu");

    List<PhieuNhapDTO> result = instance.statistical(1000000L, 5000000L);

    assertNotNull(result);
    assertTrue(result.size() >= 1);
}
@Test
public void teststatistical_NH19() throws Exception {
    System.out.println("Test NH19 - Khoảng tiền hợp lệ không có dữ liệu");

    List<PhieuNhapDTO> result = instance.statistical(100000000L, 200000000L);

    assertNotNull(result);
    assertEquals(0, result.size());
}
@Test
public void teststatistical_ZeroRange_NH20() throws Exception {
    System.out.println("Test NH20 - min = max = 0");

    List<PhieuNhapDTO> result = instance.statistical(0, 0);

    assertNotNull(result);
    // Có thể có 0 hoặc 1 kết quả nếu có phiếu nhập 0 đồng
    assertTrue(result.size() == 0 || result.size() == 1);
}
@Test
public void teststatistical_MinGreaterThanMax_NH21() throws Exception {
    System.out.println("Test NH21 - min > max");

    List<PhieuNhapDTO> result = instance.statistical(5000000L, 1000000L);

    assertNotNull(result);
    assertEquals(0, result.size());
}
@Test
public void teststatistical_NegativeRange_NH22() throws Exception {
    System.out.println("Test NH22 - Giá trị âm");

    List<PhieuNhapDTO> result = instance.statistical(-10000L, -1L);

    assertNotNull(result);
    assertEquals(0, result.size());
}
@Test
public void teststatistical_AllRange_NH23() throws Exception {
    System.out.println("Test NH23 - Khoảng bao phủ toàn bộ dữ liệu");

    List<PhieuNhapDTO> result = instance.statistical(0L, Long.MAX_VALUE);

    assertNotNull(result);
    assertTrue(result.size() >= 1); // Có ít nhất 1 phiếu nhập
}


//Phương thức Cancel()
@Test
public void testCancelPn_NH29() throws Exception {
    System.out.println("Test NH29 - Phiếu nhập hợp lệ");

    int maphieu = 1;
    int result = instance.cancelPhieuNhap(maphieu);

    assertEquals(1, result); // Thành công: đã xóa chi tiết, cập nhật kho và xóa phiếu
}
@Test
public void testCancelPn_NH30() throws Exception {
    System.out.println("Test NH30 - Phiếu nhập không tồn tại");

    int maphieu = 999;
    int result = instance.cancelPhieuNhap(maphieu);

    assertEquals(0, result); // Không thành công: không tìm thấy phiếu nhập
}
@Test
public void testCancelPn_NH31() throws Exception {
    System.out.println("Test NH31 - Chi tiết phiếu nhập không có sản phẩm");

    int maphieu = 2;
    int result = instance.cancelPhieuNhap(maphieu);

    assertEquals(1, result); // Thành công: xóa phiếu nhập, không ảnh hưởng kho
}

}
