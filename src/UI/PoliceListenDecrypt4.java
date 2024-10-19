//解密界面的一些监听器设置
package UI;
import functionalClass.funcforaes;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PoliceListenDecrypt4 implements MyCommandListener {
    JTextField keyB1,IV,ciphertext;
    JTextArea outputDecrypt;

    public void setJTextField1(JTextField text) {
        keyB1=text;
    }
    public void setJTextField2(JTextField text) {
        IV=text;
    }
    public void setJTextField3(JTextField text) {
        ciphertext=text;
    }
    public void setJTextArea(JTextArea area) {
        outputDecrypt=area;
    }

    public void actionPerformed(ActionEvent e) {
        String keyA1Text = keyB1.getText();
        String ivText = IV.getText();
        String binaryCiphertext = ciphertext.getText(); // 假设输入的是密文

        if (!PoliceListenEncrypt4.isValidBinary(keyA1Text, 16) || !PoliceListenEncrypt4.isValidBinary(ivText, 16)) {
            showError("密钥和初始向量必须是16位二进制数。");
            return;
        }

        // 检查并处理输入
        if (!binaryCiphertext.isEmpty()) {
            // 处理二进制密文
            String[] ciphertextBlocks = PoliceListenEncrypt4.splitIntoBlocks(binaryCiphertext, 16); // 将密文分块
            StringBuilder plaintextBuilder = new StringBuilder();

            String previousCiphertext = ivText; // 初始化为IV
            for (String block : ciphertextBlocks) {
                if (!PoliceListenEncrypt4.isValidBinary(block, 16)) {
                    showError("每个密文块必须是16位二进制数。");
                    return;
                }
                String decryptedBlock = funcforaes.decrypt(block, keyA1Text); // 解密当前密文块
                String plaintextBlock = funcforaes.xor(decryptedBlock, previousCiphertext); // 进行异或操作
                plaintextBuilder.append(plaintextBlock);
                previousCiphertext = block; // 更新为当前密文块
            }

            outputDecrypt.append("解密结果: " + plaintextBuilder.toString() + "\n"); // 修改了输出
        } else {
            showError("请输入密文（二进制或ASCII）。");
            return;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "输入错误", JOptionPane.ERROR_MESSAGE);
    }


}
