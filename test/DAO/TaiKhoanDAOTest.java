/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.TaiKhoanDTO;
import config.JDBCUtil;
import static config.JDBCUtil.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
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
    static Connection connection;

    public TaiKhoanDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws SQLException {
        dao = new TaiKhoanDAO();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/quanlikhohang", "root", "0915166497Bc#");
        connection.setAutoCommit(false);
        JDBCUtil.setTestConnection(connection);

    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.rollback(); // undo all changes
        connection.setAutoCommit(true);
        JDBCUtil.clearTestConnection(); // stop using test connection
        connection.close();
    }

    /**
     * Test of getInstance method, of class TaiKhoanDAO.
     */

    @Test
    public void testInsert_Exist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(6, "admin", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1,
                1);
        int result = dao.insert(tk);
        assertEquals(0, result); // chèn thất bại vì trùng
    }

    @Test
    public void testInsert_IdExist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(4, "silverss03",
                "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1, 1);
        int result = dao.insert(tk);
        assertEquals(0, result); // chèn thất bại vì trùng
    }

    @Test
    public void testInsert_NotExist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(5, "manhnh", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1,
                1);
        int result = dao.insert(tk);
        assertEquals(1, result); // chèn thành công
    }

    @Test
    public void testSelectByUser_NotExist() {
        String username = "nonexistentuser";
        TaiKhoanDTO result = dao.selectByUser(username);
        assertNull(result);
    }
    
    @Test
    public void testSelectByUser_Exist() {
        String username = "hgbaodev";
        TaiKhoanDTO result = dao.selectByUser(username);
        
        assertEquals(new TaiKhoanDTO(2, "hgbaodev", "$2a$12$vKOctLxA84GkkITq7eXStOs91COUlkJrQciydRVbFQ7o223Y.kNzy", 1, 1), result) ;
    }

    @Test
    public void testUpdate_Exist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(2, "hgbaodev1", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6",
                1, 1);
        int result = dao.update(tk);
        assertEquals(1, result);
    }

    @Test
    public void testUpdate_Username_Exist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(2, "admin", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6", 1,
                1);
        int result = dao.update(tk);
        assertEquals(0, result);
    }

    //
    @Test
    public void testUpdate_NotExist() {
        TaiKhoanDTO tk = new TaiKhoanDTO(100, "manhnh", "$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6",
                1, 1);
        int result = dao.update(tk);
        assertEquals(0, result);
    }

    @Test
    public void testUpdatePass_EmailExist() {
        String email = "transinh085@gmail.com";
        String newPassword = "123@12345";
        dao.updatePass(email, newPassword);
        TaiKhoanDTO result = dao.selectByEmail(email);
        assertEquals(newPassword, result.getMatkhau());
    }

    //
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
        TaiKhoanDTO expected = new TaiKhoanDTO(1, "admin",
                "$2a$12$F03HM81fxYN3vgZe80Dbnulh/ZmkbWhz/WSN2sCkBts.hO3bRd76i", 1, 1);
        assertEquals(expected, result);
    }

    @Test
    public void testSelectByEmail_NotExist() {
        String email = "noone@gmail.com";
        TaiKhoanDTO result = dao.selectByEmail(email);
        assertNull(result);
    }

    //
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

    //
    @Test
    public void testCheckOtp_WrongOtp() {
        String email = "musicanime2501@gmail.com";
        dao.sendOpt(email, "123456");
        boolean result = dao.checkOtp(email, "000000");
        assertFalse(result);
    }

    @Test
    public void testDelete_Exist() {
        int result = dao.delete("4");
        assertEquals(1, result);
    }

    @Test
    public void testDelete_NotExist() {
        int result = dao.delete("1000");
        assertEquals(0, result);
    }

    @Test
    public void testSelectAll() {
        ArrayList<TaiKhoanDTO> list = dao.selectAll();
        assertNotNull(list);
        System.out.println(list.size());
        assertTrue(list.size() == 5);
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

    @Test
    public void testGetAutoIncrement() {
        TaiKhoanDAO dao = new TaiKhoanDAO();

        int before = dao.getAutoIncrement();

        // Insert a dummy user to affect auto-increment
        TaiKhoanDTO dummy = new TaiKhoanDTO(
                6, "testuser_autoinc", "password", 1, 1);
        dao.insert(dummy); // ensure this works or catches error

        int after = dao.getAutoIncrement();

        assertEquals("Auto-increment should increase by 1", before, after);
    }

}
