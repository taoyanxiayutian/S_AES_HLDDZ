package functionalClass;
import javax.swing.*;

public class AESMiddleAttack {
    // 假设这是AES的简化加密方法（替代实际的AES算法）
    public static String encrypt(String key, String plaintext) {
        // 简单加密逻辑：假设每次加密只是XOR操作
        return xor(key, plaintext);
    }

    // 假设这是AES的简化解密方法（替代实际的AES算法）
    public static String decrypt(String key, String ciphertext) {
        // 简单解密逻辑：XOR的逆运算仍然是XOR
        return xor(key, ciphertext);
    }

    // XOR运算作为简单的加密解密模拟
    private static String xor(String key, String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(key.charAt(i) == text.charAt(i) ? '0' : '1');
        }
        return result.toString();
    }

    // 中间相遇攻击的模拟过程
    public static String performMiddleAttack(String plaintext, String ciphertext, JTextArea output) {
        // 初始化
        output.append("开始中间相遇攻击...\n");

        // 遍历所有可能的密钥组合
        for (int i = 0; i < 65536; i++) {
            String key1 = String.format("%16s", Integer.toBinaryString(i)).replace(' ', '0');
            String intermediateEncrypt = encrypt(key1, plaintext);

            for (int j = 0; j < 65536; j++) {
                String key2 = String.format("%16s", Integer.toBinaryString(j)).replace(' ', '0');
                String intermediateDecrypt = decrypt(key2, ciphertext);

                // 输出查找过程
                output.append("尝试密钥1: " + key1 + " | 尝试密钥2: " + key2 + "\n");

                if (intermediateEncrypt.equals(intermediateDecrypt)) {
                    output.append("找到匹配的密钥对!\n");
                    return "密钥1: " + key1 + " 密钥2: " + key2;
                }
            }
        }
        output.append("未找到匹配的密钥对\n");
        return "攻击失败";
    }
}
