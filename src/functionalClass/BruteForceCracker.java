package functionalClass;
//暴力解密
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class BruteForceCracker implements BruteForceListener {

    @Override
    public void startBruteForce(List<String> plaintexts, List<String> ciphertexts, List<Boolean> useFlags, BiConsumer<String, String> uiUpdater) {
        long startTime = System.currentTimeMillis();
        List<String> matchingKeys = new ArrayList<>();

        for (int i = 0; i < 1024; i++) {
            String binaryKey = String.format("%10s", Integer.toBinaryString(i)).replace(' ', '0');
            boolean allMatch = true;

            for (int pair = 0; pair < 3; pair++) {
                if (useFlags.get(pair)) {
                    int[] plaintext = stringToIntArray(plaintexts.get(pair));
                    int[] ciphertext = stringToIntArray(ciphertexts.get(pair));
                    int[] decrypted = Decrypt.decryptData(ciphertext, binaryKey);

                    if (!Arrays.equals(plaintext, decrypted)) {
                        allMatch = false;
                        break;
                    }
                }
            }

            if (allMatch) {
                matchingKeys.add(binaryKey);
            }
        }

        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime) / 1000.0;

        StringBuilder output = new StringBuilder();
        if (!matchingKeys.isEmpty()) {
            output.append("找到 ").append(matchingKeys.size()).append(" 个匹配的密钥:\n");
            for (String key : matchingKeys) {
                output.append(key).append("\n");
            }
            output.append("\n验证结果:\n");
            for (String key : matchingKeys) {
                output.append("密钥 ").append(key).append(" 的验证结果:\n");
                for (int i = 0; i < 3; i++) {
                    if (useFlags.get(i)) {
                        String plaintext = plaintexts.get(i);
                        String ciphertext = ciphertexts.get(i);
                        int[] decrypted = Decrypt.decryptData(stringToIntArray(ciphertext), key);
                        String decryptedStr = intArrayToString(decrypted);
                        output.append("对 ").append(i + 1).append(": 明文 = ").append(plaintext)
                                .append(", 解密结果 = ").append(decryptedStr)
                                .append(plaintext.equals(decryptedStr) ? " (匹配)" : " (不匹配)").append("\n");
                    }
                }
                output.append("\n");
            }
        } else {
            output.append("未找到匹配的密钥\n");
        }
        output.append("破解完成，总耗时: ").append(String.format("%.3f", totalTime)).append(" 秒\n");

        uiUpdater.accept(output.toString(), "总耗时: " + String.format("%.3f", totalTime) + " 秒");
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
}