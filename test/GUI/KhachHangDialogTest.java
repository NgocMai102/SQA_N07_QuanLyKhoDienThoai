/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import GUI.Dialog.KhachHangDialog;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.core.matcher.JButtonMatcher;
import javax.swing.*;
import static org.assertj.swing.finder.JOptionPaneFinder.findOptionPane;
import org.assertj.swing.fixture.JOptionPaneFixture;
import static org.junit.Assert.fail;

/**
 *
 * @author MSI GF63
 */

public class KhachHangDialogTest extends AssertJSwingJUnitTestCase {

    private DialogFixture dialog;

    @Override
    protected void onSetUp() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        robot().settings().delayBetweenEvents(300);
    }


    @Override
    protected void onTearDown() {
        if (dialog != null) {
            dialog.cleanUp();
        }
    }

    private void createDialogCreate() {
        KhachHangDialog khachHangDialog = GuiActionRunner.execute(() ->
                new KhachHangDialog(null, null, "Thêm khách hàng", true, "create"));
        dialog = new DialogFixture(robot(), khachHangDialog);
        dialog.show(); 
    }
    
    @org.junit.Test
    public void testInsertUI_KH001() {
        createDialogCreate();

        dialog.textBox("txtForm_Tên khách hàng").setText("Nguyen Van A");
        dialog.textBox("txtForm_Số điện thoại").setText("0987654321");
        dialog.textBox("txtForm_Địa chỉ").setText("123 Đường ABC");

        dialog.button(JButtonMatcher.withText("Thêm khách hàng")).click();
    }

    @org.junit.Test
    public void testInsertUI_KH002() {
        createDialogCreate();

        dialog.textBox("txtForm_Tên khách hàng").setText("");
        dialog.textBox("txtForm_Số điện thoại").setText("0987654321");
        dialog.textBox("txtForm_Địa chỉ").setText("123 Đường ABC");

        dialog.button(JButtonMatcher.withText("Thêm khách hàng")).click();

        // Bắt và kiểm tra JOptionPane Warning Message
        JOptionPaneFixture optionPane = dialog.optionPane();
        optionPane.requireVisible();
        optionPane.requireMessage("Tên khách hàng không được để trống");
        optionPane.okButton().click();
    }

    @org.junit.Test
    public void testInsertUI_KH003() {
        createDialogCreate();

        dialog.textBox("txtForm_Tên khách hàng").setText("Nguyen Van A");
        dialog.textBox("txtForm_Số điện thoại").setText("abc123");
        dialog.textBox("txtForm_Địa chỉ").setText("123 Đường ABC");

        dialog.button(JButtonMatcher.withText("Thêm khách hàng")).click();

        JOptionPaneFixture optionPane = dialog.optionPane();
        optionPane.requireVisible();
        optionPane.requireMessage("Số điện thoại không được để trống");
        optionPane.okButton().click();
    }

    @org.junit.Test
    public void testInsertUI_KH004() {
        createDialogCreate();

        dialog.textBox("txtForm_Tên khách hàng").setText("Nguyen Van A");
        dialog.textBox("txtForm_Số điện thoại").setText("0987654321");
        dialog.textBox("txtForm_Địa chỉ").setText("");

        dialog.button(JButtonMatcher.withText("Thêm khách hàng")).click();

        JOptionPaneFixture optionPane = dialog.optionPane();
        optionPane.requireVisible();
        optionPane.requireMessage("Địa chỉ không được để trống");
        optionPane.okButton().click();
    }
    
    @org.junit.Test
    public void testInsertUI_KH005() {
        createDialogCreate();

        dialog.textBox("txtForm_Tên khách hàng").setText("Nguyen Van A");
        dialog.textBox("txtForm_Số điện thoại").setText("AB0987654321");
        dialog.textBox("txtForm_Địa chỉ").setText("123 Đường ABC");

        dialog.button(JButtonMatcher.withText("Thêm khách hàng")).click();

        JOptionPaneFixture optionPane = dialog.optionPane();
        optionPane.requireVisible();
        optionPane.requireMessage("Số điện thoại phải gồm 10 ký tự số");
        optionPane.okButton().click();
    }
    
    @org.junit.Test
    public void testInsertUI_KH006() {
        createDialogCreate();

        dialog.textBox("txtForm_Tên khách hàng").setText("Nguyen Van A");
        dialog.textBox("txtForm_Số điện thoại").setText("0987654321");
        dialog.textBox("txtForm_Địa chỉ").setText("123 Đường ABC");

        dialog.button(JButtonMatcher.withText("Thêm khách hàng")).click();

        try {
            JOptionPaneFixture optionPane = findOptionPane()
                    .withTimeout(5000)
                    .using(robot());

            optionPane.requireVisible();
            optionPane.requireMessage("Khách hàng đã tồn tại");
            optionPane.okButton().click();

        } catch (org.assertj.swing.exception.WaitTimedOutError e) {
            fail("Không hiển thị thông báo 'Khách hàng đã tồn tại' sau khi thêm khách hàng trùng.");
        }
    }
}

