/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.HeDieuHanhDTO;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import java.sql.Connection;
import java.sql.SQLException;
import static config.JDBCUtil.getConnection;
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
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new HeDieuHanhDAO();
    }

    @After
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }
    
    public HeDieuHanhDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getInstance method, of class HeDieuHanhDAO.
     */
    @Test
    public void testInsert_Valid() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(1, "Windows");
        int result = dao.insert(dto);
        assertEquals(1, result);
    }

    // TC23: insert với id đã tồn tại
    @Test
    public void testInsert_ExistingId() {
        dao.insert(new HeDieuHanhDTO(1, "Windows"));
        HeDieuHanhDTO dto = new HeDieuHanhDTO(1, "Linux");
        int result = dao.insert(dto);
        assertEquals(0, result);
    }

    // TC24: insert dữ liệu không hợp lệ
    @Test(expected = Exception.class)
    public void testInsert_InvalidData() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(-1, "Windows");
        dao.insert(dto);
    }

    // TC25: insert thiếu dữ liệu
    @Test
    public void testInsert_MissingData() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(1, null);
        int result = dao.insert(dto);
        assertEquals(0, result);
    }

    // TC26: update hợp lệ
    @Test
    public void testUpdate_Valid() {
        dao.insert(new HeDieuHanhDTO(1, "Linux"));
        HeDieuHanhDTO dto = new HeDieuHanhDTO(1, "Windows");
        int result = dao.update(dto);
        assertEquals(1, result);
    }

    // TC27: update mã không tồn tại
    @Test
    public void testUpdate_NonExistingId() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(-1, "Windows");
        int result = dao.update(dto);
        assertEquals(0, result);
    }

    // TC28: update dữ liệu không hợp lệ
    @Test(expected = Exception.class)
    public void testUpdate_InvalidData() {
        HeDieuHanhDTO dto = new HeDieuHanhDTO(1, "Ubuntu@123");
        dao.update(dto);
    }

    // TC29: delete mã tồn tại
    @Test
    public void testDelete_ExistingId() {
        dao.insert(new HeDieuHanhDTO(1, "Windows"));
        int result = dao.delete("1");
        assertEquals(1, result);
    }

    // TC30: delete mã không tồn tại
    @Test
    public void testDelete_NonExistingId() {
        int result = dao.delete("9999");
        assertEquals(0, result);
    }

    // TC31: delete mã không hợp lệ
    @Test(expected = Exception.class)
    public void testDelete_InvalidId() {
        dao.delete("-1");
    }

    // TC32: delete null
    @Test(expected = Exception.class)
    public void testDelete_NullId() {
        dao.delete(null);
    }

    // TC33: selectAll có dữ liệu
    @Test
    public void testSelectAll_WithData() {
        dao.insert(new HeDieuHanhDTO(1, "Windows"));
        ArrayList<HeDieuHanhDTO> list = dao.selectAll();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    // TC34: selectAll không có dữ liệu
    @Test
    public void testSelectAll_Empty() {
        ArrayList<HeDieuHanhDTO> list = dao.selectAll();
        assertTrue(list.isEmpty());
    }

    // TC35: selectById tồn tại
    @Test
    public void testSelectById_Existing() {
        dao.insert(new HeDieuHanhDTO(1, "Windows"));
        HeDieuHanhDTO dto = dao.selectById("1");
        assertNotNull(dto);
        assertEquals(1, dto.getMahdh());
    }

    // TC36: selectById không tồn tại
    @Test
    public void testSelectById_NonExisting() {
        HeDieuHanhDTO dto = dao.selectById("9999");
        assertNull(dto);
    }

    // TC37: selectById mã không hợp lệ
    @Test(expected = Exception.class)
    public void testSelectById_InvalidId() {
        dao.selectById("abc");
    }

    // TC38: selectById null
    @Test(expected = Exception.class)
    public void testSelectById_NullId() {
        dao.selectById(null);
    }

    // TC39: getAutoIncrement có dữ liệu
    @Test
    public void testGetAutoIncrement_WithData() {
        dao.insert(new HeDieuHanhDTO(1, "Windows"));
        int autoInc = dao.getAutoIncrement();
        assertTrue(autoInc > 1);
    }

    // TC40: getAutoIncrement không có dữ liệu
    @Test
    public void testGetAutoIncrement_NoData() {
        int autoInc = dao.getAutoIncrement();
        assertEquals(1, autoInc);
    }
    
}
