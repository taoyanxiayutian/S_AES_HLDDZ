//解密界面的一些监听器设置
package UI;
import functionalClass.funcforaes;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PoliceListenDecrypt3 implements MyCommandListener {
    JTextField keyB1,keyB2,ciphertext;
    JTextArea outputDecrypt;

    public void setJTextField1(JTextField text) {
        keyB1=text;
    }
    public void setJTextField2(JTextField text) {
        keyB2=text;
    }
    public void setJTextField3(JTextField text) {
        ciphertext=text;
    }
    public void setJTextArea(JTextArea area) {
        outputDecrypt=area;
    }

    public void actionPerformed(ActionEvent e) {
        String keyB1Text = keyB1.getText();
        String keyB2Text = keyB2.getText();
        String binaryciphertext = ciphertext.getText();

        // 检查密钥
        if (!isValidBinary(keyB1Text, 16)&&!isValidBinary(keyB2Text, 16)) {
            showError("密钥必须是16位二进制数。");
            return;
        }

        // 检查并处理输入
        if (!binaryciphertext.isEmpty()) {
            // 处理二进制明文
            if (!isValidBinary(binaryciphertext, 16)) {
                showError("二进制密文必须是16位二进制数。");
                return;
            }
            String ciphertext1 = funcforaes.decrypt(binaryciphertext, keyB1Text);
            String ciphertext2 = funcforaes.encrypt(ciphertext1, keyB2Text);
            String ciphertext3 = funcforaes.decrypt(ciphertext2, keyB1Text);
            outputDecrypt.append("二进制明文解密结果: " + ciphertext3 + "\n");
        } else {
            showError("请输入密文（二进制或ASCII）。");
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
