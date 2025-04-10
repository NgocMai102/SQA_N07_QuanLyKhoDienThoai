/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.ThuongHieuDTO;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maii
 */
public class ThuongHieuDAOTest {
    
    public ThuongHieuDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getInstance method, of class ThuongHieuDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ThuongHieuDAO expResult = null;
        ThuongHieuDAO result = ThuongHieuDAO.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class ThuongHieuDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        ThuongHieuDTO t = null;
        ThuongHieuDAO instance = new ThuongHieuDAO();
        int expResult = 0;
        int result = instance.insert(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class ThuongHieuDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        ThuongHieuDTO t = null;
        ThuongHieuDAO instance = new ThuongHieuDAO();
        int expResult = 0;
        int result = instance.update(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class ThuongHieuDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String t = "";
        ThuongHieuDAO instance = new ThuongHieuDAO();
        int expResult = 0;
        int result = instance.delete(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectAll method, of class ThuongHieuDAO.
     */
    @Test
    public void testSelectAll() {
        System.out.println("selectAll");
        ThuongHieuDAO instance = new ThuongHieuDAO();
        ArrayList<ThuongHieuDTO> expResult = null;
        ArrayList<ThuongHieuDTO> result = instance.selectAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectById method, of class ThuongHieuDAO.
     */
    @Test
    public void testSelectById() {
        System.out.println("selectById");
        String t = "";
        ThuongHieuDAO instance = new ThuongHieuDAO();
        ThuongHieuDTO expResult = null;
        ThuongHieuDTO result = instance.selectById(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAutoIncrement method, of class ThuongHieuDAO.
     */
    @Test
    public void testGetAutoIncrement() {
        System.out.println("getAutoIncrement");
        ThuongHieuDAO instance = new ThuongHieuDAO();
        int expResult = 0;
        int result = instance.getAutoIncrement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
