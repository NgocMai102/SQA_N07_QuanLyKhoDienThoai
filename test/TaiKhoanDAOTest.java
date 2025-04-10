/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import static DAO.NhomQuyenDAOTest.connection;
import static DAO.NhomQuyenDAOTest.dao;
import DTO.TaiKhoanDTO;
import static config.JDBCUtil.getConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class TaiKhoanDAOTest {
    static TaiKhoanDAO dao;
    
    public TaiKhoanDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        dao = new TaiKhoanDAO();
        connection = getConnection();
        connection.setAutoCommit(false);
        
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // Roll back all changes made during tests
        connection.setAutoCommit(true);
        connection.close();
    }

    /**
     * Test of getInstance method, of class TaiKhoanDAO.
     */
    @Test
    public void testInsert_NotExist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(5, "manhnh", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1, 1);
        int result = dao.insert(tk);
        assertEquals(1, result); // chèn thành công
        dao.delete("5"); // xóa để giữ database sạch
    }
    
    @Test
    public void testSelectByUser_Exist() {
        String username = "admin"; // giả sử đã tồn tại
        TaiKhoanDTO result = dao.selectByUser(username);
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testSelectByUser_NotExist() {
        String username = "nonexistentuser";
        TaiKhoanDTO result = dao.selectByUser(username);
        assertNull(result);
    }

    @Test
    public void testInsert_Exist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(5, "nhmanh", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1, 1);
        int result = dao.insert(tk);
        assertEquals(0, result); // chèn thất bại vì trùng
    }


    @Test
    public void testUpdate_Exist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(2, "hgbaodev1", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1, 1);
        int result = dao.update(tk);
        assertEquals(1, result);
    }

    @Test
    public void testUpdate_NotExist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(100, "manhnh", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1, 1);
        int result = dao.update(tk);
        assertEquals(0, result);
    }

    @Test
    public void testUpdatePass_EmailExist() {
        String email = "transinh085@gmail.com";
        String newPassword = "123@12345";
        String oldPass = dao.selectByEmail(email).getMatkhau();
        dao.updatePass(email, newPassword);
        TaiKhoanDTO result = dao.selectByEmail(email);
        assertEquals(newPassword, result.getMatkhau());
        dao.updatePass(email, oldPass);
    }

    @Test
    public void testUpdatePass_EmailNotExist() {
        String email = "ghost@gmail.com";
        String newPassword = "Ghost@123";
        dao.updatePass(email, newPassword);
        TaiKhoanDTO result = dao.selectByEmail(email);
        assertNull(result);
    }

    @Test
    public void testSelectByEmail_Exist() {
        String email = "transinh085@gmail.com";
        TaiKhoanDTO result = dao.selectByEmail(email);
        assertNotNull(result);
        TaiKhoanDTO expected = new TaiKhoanDTO(1, "admin", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1, 1);
        assertEquals(expected, result);
    }

    @Test
    public void testSelectByEmail_NotExist() {
        String email = "noone@gmail.com";
        TaiKhoanDTO result = dao.selectByEmail(email);
        assertNull(result);
    }

    @Test
    public void testSendOtp_EmailExist() {
        String email = "chinchin@gmail.com";
        String otp = "123456";
        dao.sendOpt(email, otp);
        assertTrue(dao.checkOtp(email, otp));
    }

    @Test
    public void testSendOtp_EmailNotExist() {
        String email = "none@gmail.com";
        String otp = "451418";
        dao.sendOpt(email, otp);
        assertFalse(dao.checkOtp(email, otp));
    }

    @Test
    public void testCheckOtp_Correct() {
        String email = "chinchin@gmail.com";
        String otp = "123456";
        dao.sendOpt(email, otp);
        boolean result = dao.checkOtp(email, otp);
        assertTrue(result);
    }

    @Test
    public void testCheckOtp_EmailNotExist() {
        String email = "ghost@gmail.com";
        boolean result = dao.checkOtp(email, "000000");
        assertFalse(result);
    }

    @Test
    public void testCheckOtp_WrongOtp() {
        String email = "musicanime2501@gmail.com";
        dao.sendOpt(email, "123456");
        boolean result = dao.checkOtp(email, "000000");
        assertFalse(result);
    }

    @Test
    public void testDelete_Exist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(5, "nhmanh", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1, 1);
        dao.insert(tk);
        int result = dao.delete("5");
        assertEquals(1, result);
    }

    @Test
    public void testDelete_NotExist() {
        int result = dao.delete("100");
        assertEquals(0, result);
    }

    @Test
    public void testSelectAll_WithData() {
        ArrayList<TaiKhoanDTO> list = dao.selectAll();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectById_Exist() {
        TaiKhoanDTO result = dao.selectById("1");
        assertNotNull(result);
        assertEquals(1, result.getManv());
    }

    @Test
    public void testSelectById_NotExist() {
        TaiKhoanDTO result = dao.selectById("TK404");
        assertNull(result);
    }

//    @Test
//    public void testGetAutoIncrement_HasDataOrAutoIncrementDefined() {
//        int result = dao.getAutoIncrement();
//        System.out.println("AUTO_INCREMENT value: " + result);
//
//        // Check if auto-increment is defined (i.e., result is not -1)
//        if (result == -1) {
//            fail("AUTO_INCREMENT is not defined for the 'nhomquyen' table. Check if the table has an AUTO_INCREMENT primary key.");
//        } else {
//            assertTrue("Expected AUTO_INCREMENT to be a positive integer", result > 0);
//        }
//    }

    
}
