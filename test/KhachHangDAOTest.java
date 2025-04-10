/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.KhachHangDTO;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class KhachHangDAOTest {
    
    public KhachHangDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of selectById method, of class KhachHangDAO.
     */
    @Test
    public void testSelectById_testNgoaiLe1() {
        System.out.println("selectById");
        String t = "abc";
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO expResult = null;
        KhachHangDTO result = instance.selectById(t);
        assertEquals(expResult, result);
    }


    @Test
    public void testSelectById_testNgoaiLe2() {
        System.out.println("selectById");
        String t = "1.2";
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO expResult = null;
        KhachHangDTO result = instance.selectById(t);
        assertEquals(expResult, result);
    }

 @Test
    public void testSelectById_testNgoaiLe3() {
        System.out.println("selectById - null case");
        String t = null;
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO expResult = null;
        KhachHangDTO result = instance.selectById(t);
        assertEquals(expResult, result);
    }

    @Test
    public void testSelectById_testNgoaiLe4() {
        System.out.println("selectById - empty string case");
        String t = "";
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO expResult = null;
        KhachHangDTO result = instance.selectById(t);
        assertEquals(expResult, result);
    }

@Test
    public void testSelectById_IDTonTai() {
        System.out.println("selectById - ID tồn tại");
        String t = "1";
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO result = instance.selectById(t);
        
        assertNotNull("Phải trả về đối tượng KhachHangDTO khi ID tồn tại", result);
    }
    
    @Test
    public void testSelectById_IDKhongTonTai1() {
        System.out.println("selectById - ID không tồn tại");
        String t = "-1"; // ID không hợp lệ hoặc không có trong DB
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO result = instance.selectById(t);
        
        assertNull("Phải trả về null khi ID không tồn tại", result);
    }
    
    @Test
    public void testSelectById_IDKhongTonTai2() {
        System.out.println("selectById - ID không tồn tại");
        String t = "0"; // ID không hợp lệ hoặc không có trong DB
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO result = instance.selectById(t);
        
        assertNull("Phải trả về null khi ID không tồn tại", result);
    }
    
    @Test
    public void testSelectById_IDKhongTonTai3() {
        System.out.println("selectById - ID không tồn tại");
        String t = "999"; // ID không hợp lệ hoặc không có trong DB
        KhachHangDAO instance = new KhachHangDAO();
        KhachHangDTO result = instance.selectById(t);
        
        assertNull("Phải trả về null khi ID không tồn tại", result);
    }


    
}
