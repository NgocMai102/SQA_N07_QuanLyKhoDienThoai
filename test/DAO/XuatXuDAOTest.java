/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.XuatXuDTO;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maii
 */
public class XuatXuDAOTest {
    
    public XuatXuDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getInstance method, of class XuatXuDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        XuatXuDAO expResult = null;
        XuatXuDAO result = XuatXuDAO.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class XuatXuDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        XuatXuDTO t = null;
        XuatXuDAO instance = new XuatXuDAO();
        int expResult = 0;
        int result = instance.insert(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class XuatXuDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        XuatXuDTO t = null;
        XuatXuDAO instance = new XuatXuDAO();
        int expResult = 0;
        int result = instance.update(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class XuatXuDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String t = "";
        XuatXuDAO instance = new XuatXuDAO();
        int expResult = 0;
        int result = instance.delete(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectAll method, of class XuatXuDAO.
     */
    @Test
    public void testSelectAll() {
        System.out.println("selectAll");
        XuatXuDAO instance = new XuatXuDAO();
        ArrayList<XuatXuDTO> expResult = null;
        ArrayList<XuatXuDTO> result = instance.selectAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectById method, of class XuatXuDAO.
     */
    @Test
    public void testSelectById() {
        System.out.println("selectById");
        String t = "";
        XuatXuDAO instance = new XuatXuDAO();
        XuatXuDTO expResult = null;
        XuatXuDTO result = instance.selectById(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAutoIncrement method, of class XuatXuDAO.
     */
    @Test
    public void testGetAutoIncrement() {
        System.out.println("getAutoIncrement");
        XuatXuDAO instance = new XuatXuDAO();
        int expResult = 0;
        int result = instance.getAutoIncrement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
