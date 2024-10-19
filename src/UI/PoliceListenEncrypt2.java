//解密界面的一些监听器设置
package UI;
import functionalClass.funcforaes;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PoliceListenEncrypt2 implements MyCommandListener {
    JTextField keyA1,keyA2,plaintext;
    JTextArea outputEncrypt;

    public void setJTextField1(JTextField text) {
        keyA1=text;
    }
    public void setJTextField2(JTextField text) {
        keyA2=text;
    }
    public void setJTextField3(JTextField text) {
        plaintext=text;
    }
    public void setJTextArea(JTextArea area) {
        outputEncrypt=area;
    }

    public void actionPerformed(ActionEvent e) {
        String keyA1Text = keyA1.getText();
        String keyA2Text = keyA2.getText();
        String binaryPlaintext = plaintext.getText();

        // 检查密钥
        if (!isValidBinary(keyA1Text, 16)&&!isValidBinary(keyA2Text, 16)) {
            showError("密钥必须是16位二进制数。");
            return;
        }

        // 检查并处理输入
        if (!binaryPlaintext.isEmpty()) {
            // 处理二进制明文
            if (!isValidBinary(binaryPlaintext, 16)) {
                showError("二进制明文必须是16位二进制数。");
                return;
            }
            String ciphertext1 = funcforaes.encrypt(binaryPlaintext, keyA1Text);
            String ciphertext2 = funcforaes.encrypt(ciphertext1, keyA2Text);
            outputEncrypt.append("二进制明文加密结果: " + ciphertext2 + "\n");
        } else {
            showError("请输入明文（二进制或ASCII）。");
            return;
        }
    }

    private boolean isValidBinary(String str, int length) {
        if (str.length() != length) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "输入错误", JOptionPane.ERROR_MESSAGE);
    }


}
