package functionalClass;

import java.util.*;
import java.util.function.BiConsumer;

public class KeyCollision {

    public void checkKeyCollisions(BiConsumer<String, String> uiUpdater) {
        StringBuilder collisionOutput = new StringBuilder();
        collisionOutput.append("检查密钥碰撞情况：\n");
        boolean collisionFound = false;

        for (int plaintext = 0; plaintext < 256; plaintext++) {
            String binaryPlaintext = String.format("%8s", Integer.toBinaryString(plaintext)).replace(' ', '0');
            int[] plaintextArray = stringToIntArray(binaryPlaintext);

            Map<String, List<String>> ciphertextToKeys = new HashMap<>();

            for (int i = 0; i < 1024; i++) {
                String key = String.format("%10s", Integer.toBinaryString(i)).replace(' ', '0');
                int[] encrypted = Encrypt.encryptData(plaintextArray, key);
                String ciphertext = intArrayToString(encrypted);

                ciphertextToKeys.computeIfAbsent(ciphertext, k -> new ArrayList<>()).add(key);
            }

            for (Map.Entry<String, List<String>> entry : ciphertextToKeys.entrySet()) {
                if (entry.getValue().size() > 1) {
                    collisionFound = true;
                    collisionOutput.append("发现碰撞：\n");
                    collisionOutput.append("明文: ").append(binaryPlaintext).append("\n");
                    collisionOutput.append("密文: ").append(entry.getKey()).append("\n");
                    collisionOutput.append("使用以下不同密钥得到相同密文：\n");
                    for (String key : entry.getValue()) {
                        collisionOutput.append(key).append("\n");
                    }
                    collisionOutput.append("\n");
                }
            }
        }

        if (collisionFound) {
            uiUpdater.accept(collisionOutput.toString(), "发现密钥碰撞");
        } else {
            uiUpdater.accept("未发现密钥碰撞\n", "未发现密钥碰撞");
        }
    }

    private int[] stringToIntArray(String binaryString) {
        int[] result = new int[8];
        for (int i = 0; i < 8; i++) {
            result[i] = binaryString.charAt(i) - '0';
        }
        return result;
    }

    private String intArrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        KeyCollision checker = new KeyCollision();
        checker.checkKeyCollisions((output, status) -> {
            System.out.println(output);
            System.out.println("状态: " + status);
        });
    }
}