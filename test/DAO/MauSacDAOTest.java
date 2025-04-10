/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.MauSacDTO;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maii
 */
public class MauSacDAOTest {
    
    public MauSacDAOTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getInstance method, of class MauSacDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        MauSacDAO expResult = null;
        MauSacDAO result = MauSacDAO.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class MauSacDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        MauSacDTO t = null;
        MauSacDAO instance = new MauSacDAO();
        int expResult = 0;
        int result = instance.insert(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class MauSacDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        MauSacDTO t = null;
        MauSacDAO instance = new MauSacDAO();
        int expResult = 0;
        int result = instance.update(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class MauSacDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String t = "";
        MauSacDAO instance = new MauSacDAO();
        int expResult = 0;
        int result = instance.delete(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectAll method, of class MauSacDAO.
     */
    @Test
    public void testSelectAll() {
        System.out.println("selectAll");
        MauSacDAO instance = new MauSacDAO();
        ArrayList<MauSacDTO> expResult = null;
        ArrayList<MauSacDTO> result = instance.selectAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class MauSacDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        MauSacDAO instance = new MauSacDAO();
        ArrayList<MauSacDTO> expResult = null;
        ArrayList<MauSacDTO> result = instance.getAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectById method, of class MauSacDAO.
     */
    @Test
    public void testSelectById() {
        System.out.println("selectById");
        String t = "";
        MauSacDAO instance = new MauSacDAO();
        MauSacDTO expResult = null;
        MauSacDTO result = instance.selectById(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAutoIncrement method, of class MauSacDAO.
     */
    @Test
    public void testGetAutoIncrement() {
        System.out.println("getAutoIncrement");
        MauSacDAO instance = new MauSacDAO();
        int expResult = 0;
        int result = instance.getAutoIncrement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
