//解密界面的一些监听器设置
package UI;
import functionalClass.funcforaes;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PoliceListenEncrypt4 implements MyCommandListener {
    JTextField keyA1,IV,plaintext;
    JTextArea outputEncrypt;

    public void setJTextField1(JTextField text) {
        keyA1=text;
    }
    public void setJTextField2(JTextField text) {
        IV=text;
    }
    public void setJTextField3(JTextField text) {
        plaintext=text;
    }
    public void setJTextArea(JTextArea area) {
        outputEncrypt=area;
    }

    public void actionPerformed(ActionEvent e) {
        String keyA1Text = keyA1.getText();
        String ivText = IV.getText();
        String binaryPlaintext = plaintext.getText();

        // 检查密钥
        if (!isValidBinary(keyA1Text, 16)&&!isValidBinary(ivText, 16)) {
            showError("密钥和初始向量必须是16位二进制数。");
            return;
        }

        // 检查并处理输入
        if (!binaryPlaintext.isEmpty()) {
            // 处理二进制明文
            String[] plaintextBlocks = splitIntoBlocks(binaryPlaintext, 16); // 将明文分块
            StringBuilder ciphertextBuilder = new StringBuilder();

            String previousCiphertext = ivText; // 初始化为IV
            for (String block : plaintextBlocks) {
                if (!isValidBinary(block, 16)) {
                    showError("每个明文块必须是16位二进制数。");
                    return;
                }
                String xorBlock = funcforaes.xor(block, previousCiphertext); // 进行异或操作
                String ciphertextBlock = funcforaes.encrypt(xorBlock, keyA1Text);
                ciphertextBuilder.append(ciphertextBlock);
                previousCiphertext = ciphertextBlock; // 更新为当前密文块
            }

            outputEncrypt.append("二进制明文加密结果: " + ciphertextBuilder.toString() + "\n"); // 修改了输出
        } else {
            showError("请输入明文（二进制或ASCII）。");
            return;
        }
    }
    // 新增方法：将明文分块
    public static String[] splitIntoBlocks(String plaintext, int blockSize) {
        int length = plaintext.length();
        int numBlocks = (length + blockSize - 1) / blockSize; // 计算块数
        String[] blocks = new String[numBlocks];
        for (int i = 0; i < numBlocks; i++) {
            int start = i * blockSize;
            int end = Math.min(start + blockSize, length);
            blocks[i] = plaintext.substring(start, end);
        }
        return blocks;
    }
    public static boolean isValidBinary(String str, int length) {
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
