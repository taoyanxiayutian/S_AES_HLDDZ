package functionalClass;

import java.util.Arrays;
//加密函数的书写
public class Encrypt {
    // 定义置换盒和S盒
    private static final int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};  // 密钥扩展置换盒
    private static final int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};  // 密钥压缩置换盒
    private static final int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};  // 初始置换盒
    private static final int[] IP_INV = {4, 1, 3, 5, 7, 2, 8, 6};  // 最终置换盒（IP的逆）
    private static final int[][] S_BOX1 = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 0, 2}};  // S盒1
    private static final int[][] S_BOX2 = {{0, 1, 2, 3}, {2, 3, 1, 0}, {3, 0, 1, 2}, {2, 1, 0, 3}};  // S盒2
    private static final int[] SP_BOX = {2, 4, 3, 1};  // S盒后的置换盒
    private static final int[] EP_BOX = {4, 1, 2, 3, 2, 3, 4, 1};  // 扩展置换盒

    public static void main(String[] args) {
        String key = "0000011111";  // 10位密钥
        int[] plaintext = {1, 0, 1, 0, 1, 0, 1, 0};  // 8位明文
        int[] ciphertext = encryptData(plaintext, key);
        System.out.println("8-bit array encryption result: " + Arrays.toString(ciphertext));

        String plaintextASCII = "fire at 10pm";  // ASCII明文
        String ciphertextASCII = encryptASCII(plaintextASCII, key);
        System.out.println("ASCII encryption result: " + ciphertextASCII);
    }

    // 加密ASCII字符串
    public static String encryptASCII(String plaintextASCII, String key) {
        int[][] plaintextArray = stringToIntArray(plaintextASCII);
        int[][] ciphertextArray = new int[plaintextASCII.length()][8];

        for (int i = 0; i < plaintextASCII.length(); i++) {
            ciphertextArray[i] = encryptData(plaintextArray[i], key);
        }

        return intArrayToString(ciphertextArray);
    }

    // 主要的加密函数
    public static int[] encryptData(int[] plaintext, String key) {
        int[][] keys = KeyGeneration.generateKey(P10, P8, key);
        int[] k1 = keys[0], k2 = keys[1];

        int[] block = permute(IP, plaintext);  // 初始置换
        block = roundFunction(k1, block);  // 第一轮函数
        block = swapHalves(block);  // 交换左右半部分
        block = roundFunction(k2, block);  // 第二轮函数
        return permute(IP_INV, block);  // 最终置换
    }

    // 轮函数
    private static int[] roundFunction(int[] roundKey, int[] inputBlock) {
        int[] left = Arrays.copyOfRange(inputBlock, 0, 4);
        int[] right = Arrays.copyOfRange(inputBlock, 4, 8);
        int[] fResult = f(right, roundKey);
        int[] newLeft = xor(left, fResult);
        return concatenate(newLeft, right);
    }

    // F函数
    private static int[] f(int[] right, int[] roundKey) {
        int[] expanded = permute(EP_BOX, right);  // 扩展置换
        int[] xored = xor(expanded, roundKey);  // 与轮密钥异或
        int[] substituted = substitute(xored);  // S盒替换
        return permute(SP_BOX, substituted);  // SP盒置换
    }

    // S盒替换
    private static int[] substitute(int[] input) {
        int[] left = Arrays.copyOfRange(input, 0, 4);
        int[] right = Arrays.copyOfRange(input, 4, 8);
        int[] result = new int[4];

        result[0] = (S_BOX1[binaryToInt(left[0], left[3])][binaryToInt(left[1], left[2])] >> 1) & 1;
        result[1] = S_BOX1[binaryToInt(left[0], left[3])][binaryToInt(left[1], left[2])] & 1;
        result[2] = (S_BOX2[binaryToInt(right[0], right[3])][binaryToInt(right[1], right[2])] >> 1) & 1;
        result[3] = S_BOX2[binaryToInt(right[0], right[3])][binaryToInt(right[1], right[2])] & 1;

        return result;
    }

    // 通用置换函数
    private static int[] permute(int[] permutation, int[] input) {
        int[] output = new int[permutation.length];
        for (int i = 0; i < permutation.length; i++) {
            output[i] = input[permutation[i] - 1];
        }
        return output;
    }

    // 异或操作
    private static int[] xor(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] ^ b[i];
        }
        return result;
    }

    // 交换左右半部分
    private static int[] swapHalves(int[] input) {
        return concatenate(Arrays.copyOfRange(input, 4, 8), Arrays.copyOfRange(input, 0, 4));
    }

    // 连接两个数组
    private static int[] concatenate(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    // 将两个二进制位转换为整数
    private static int binaryToInt(int b1, int b0) {
        return (b1 << 1) | b0;
    }

    // 将字符串转换为二维整数数组
    private static int[][] stringToIntArray(String input) {
        int[][] result = new int[input.length()][8];
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            for (int j = 7; j >= 0; j--) {
                result[i][j] = (c >> (7 - j)) & 1;
            }
        }
        return result;
    }

    // 将二维整数数组转换为字符串
    private static String intArrayToString(int[][] array) {
        StringBuilder result = new StringBuilder();
        for (int[] value : array) {
            char c = 0;
            for (int j = 0; j < 8; j++) {
                c |= (value[j] << (7 - j));
            }
            result.append(c);
        }
        return result.toString();
    }
}