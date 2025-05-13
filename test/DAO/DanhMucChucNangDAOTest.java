/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.DanhMucChucNangDTO;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maii
 */
public class DanhMucChucNangDAOTest {
    
    private DanhMucChucNangDAO dao;
    private Connection conn;
    
    
    @Before
    public void setUp() throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);
        dao = new DanhMucChucNangDAO();
    }
    
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public DanhMucChucNangDAOTest() {
    }
    

    @Test
    public void testSelectAllDanhMucChucNang_WithData_TT001() {
        // TT001_Giả sử đã có sẵn dữ liệu hợp lệ trong DB

        List<DanhMucChucNangDTO> result = dao.selectAll();

        assertNotNull("Danh sách không được null", result);
        assertFalse("Danh sách phải có ít nhất 1 phần tử", result.isEmpty());

        DanhMucChucNangDTO first = result.get(0);
        assertNotNull(first.getMachucnang());
        assertNotNull(first.getTenchucnang());
    }
    
    @Test
    public void testSelectAllDanhMucChucNang_NoValidRecords_TT002() {
        // TT002_Trường hợp này giả sử CSDL không có bản ghi hợp lệ nào

        List<DanhMucChucNangDTO> result = dao.selectAll();

        assertNotNull("Danh sách không được null");
        assertEquals("Danh sách phải rỗng", 0, result.size());
    }
}
