package functionalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static functionalClass.funcforaes.*;

public class AESMiddleAttack {

    // 中间相遇攻击的模拟过程，支持多对明密文输入
    public static String performMiddleAttack(List<String[]> plaintextCiphertextPairs) {
        // 中间值映射表，存储每个密钥1生成的中间值及对应的密钥
        Map<Integer, List<String>> intermediateValuesMap = new HashMap<>();
        List<String> foundKeys = new ArrayList<>();

        // 遍历所有的密钥1，计算每对明文的中间加密结果
        for (int key1 = 0; key1 < 0x10000; key1++) {
            String key1Binary = String.format("%16s", Integer.toBinaryString(key1)).replace(' ', '0');
            boolean isValidKey1 = true;
            List<Integer> currentIntermediateValues = new ArrayList<>();

            // 对每对明密文进行加密，存储中间值
            for (String[] pair : plaintextCiphertextPairs) {
                String plaintext = pair[0];
                String encrypted = funcforaes.encrypt(plaintext, key1Binary);
                int[] encryptedArray = From16to4(encrypted);
                int intermediateValue = matrixToInt(S_replace(encryptedArray));
                currentIntermediateValues.add(intermediateValue);
            }

            // 将每个密钥1对应的中间值存入映射表
            for (int intermediateValue : currentIntermediateValues) {
                intermediateValuesMap.computeIfAbsent(intermediateValue, k -> new ArrayList<>()).add(key1Binary);
            }
        }

        // 遍历所有的密钥2，计算每对密文的中间解密结果，并查找匹配的密钥1
        for (int key2 = 0; key2 < 0x10000; key2++) {
            String key2Binary = String.format("%16s", Integer.toBinaryString(key2)).replace(' ', '0');
            boolean isValidKey2 = true;
            String matchingKey1 = null;

            // 对每对密文进行解密，生成中间值
            for (String[] pair : plaintextCiphertextPairs) {
                String ciphertext = pair[1];
                String decrypted = funcforaes.decrypt(ciphertext, key2Binary);
                int[] decryptedArray = From16to4(decrypted);
                int intermediateValue = matrixToInt(S_replace(decryptedArray));

                // 检查中间值映射表，寻找匹配的密钥1
                if (intermediateValuesMap.containsKey(intermediateValue)) {
                    List<String> potentialKeys1 = intermediateValuesMap.get(intermediateValue);
                    if (matchingKey1 == null) {
                        matchingKey1 = potentialKeys1.get(0); // 假设第一个匹配的密钥1为正确的
                    } else if (!potentialKeys1.contains(matchingKey1)) {
                        isValidKey2 = false;
                        break;
                    }
                } else {
                    isValidKey2 = false;
                    break;
                }
            }

            // 如果找到匹配的密钥对，记录密钥1和密钥2
            if (isValidKey2 && matchingKey1 != null) {
                // 确保密钥对输出为16位
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
