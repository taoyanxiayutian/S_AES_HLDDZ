package functionalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static functionalClass.funcforaes.*;

public class AESMiddleAttack {

    // 中间相遇攻击的模拟过程，支持多对明密文输入
    public static String performMiddleAttack(List<String[]> plaintextCiphertextPairs) {
        // 中间值映射表
        Map<Integer, String> intermediateValues = new HashMap<>();
        List<String> foundKeys = new ArrayList<>();

        // 遍历所有的密钥1，计算每对明文的中间加密结果
        for (int key1 = 0; key1 < 0x10000; key1++) {
            boolean validKey1 = true;
            for (String[] pair : plaintextCiphertextPairs) {
                String plaintext = pair[0];
                String encrypted = funcforaes.encrypt(plaintext, String.format("%16s", Integer.toBinaryString(key1)).replace(' ', '0'));
                int[] encryptedArray = From16to4(encrypted);
                int intermediateValue = matrixToInt(S_replace(encryptedArray));

                // 检查是否与之前不同的中间值冲突
                if (!intermediateValues.containsKey(intermediateValue)) {
                    intermediateValues.put(intermediateValue, String.format("%16s", Integer.toBinaryString(key1)).replace(' ', '0'));
                } else if (!intermediateValues.get(intermediateValue).equals(String.format("%16s", Integer.toBinaryString(key1)).replace(' ', '0'))) {
                    validKey1 = false;
                    break;
                }
            }
            if (!validKey1) {
                continue; // 如果此密钥1无效，则跳过
            }
        }

        // 遍历所有的密钥2，计算每对密文的中间解密结果
        for (int key2 = 0; key2 < 0x10000; key2++) {
            boolean validKey2 = true;
            String matchingKey1 = null;

            for (String[] pair : plaintextCiphertextPairs) {
                String ciphertext = pair[1];
                String decrypted = funcforaes.decrypt(ciphertext, String.format("%16s", Integer.toBinaryString(key2)).replace(' ', '0'));
                int[] decryptedArray = From16to4(decrypted);
                int intermediateValue = matrixToInt(S_replace(decryptedArray));

                // 检查是否在中间值字典中存在
                if (intermediateValues.containsKey(intermediateValue)) {
                    if (matchingKey1 == null) {
                        matchingKey1 = intermediateValues.get(intermediateValue);
                    } else if (!matchingKey1.equals(intermediateValues.get(intermediateValue))) {
                        validKey2 = false;
                        break;
                    }
                } else {
                    validKey2 = false;
                    break;
                }
            }

            // 如果找到匹配的密钥对，记录密钥1和密钥2
            if (validKey2 && matchingKey1 != null) {
                // 确保密钥对的输出为16位
                String formattedKey1 = String.format("%16s", matchingKey1).replace(' ', '0');
                String formattedKey2 = String.format("%16s", Integer.toBinaryString(key2)).replace(' ', '0');

                // 确保密钥对是完整的32位（16位密钥1和16位密钥2）
                foundKeys.add(formattedKey1 + " " + formattedKey2);
            }
        }

        // 返回找到的密钥对，如果为空则表示未找到，使用逗号换行分隔
        return foundKeys.isEmpty() ? "未找到密钥" : String.join(",\n", foundKeys);
    }




    // 矩阵转换为整数
    public static int matrixToInt(int[] array) {
        StringBuilder binString = new StringBuilder();
        for (int i : array) {
            binString.append(ToBinary(i, 4));
        }
        return Integer.parseInt(binString.toString(), 2);
    }
}
