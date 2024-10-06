package functionalClass;

import java.util.Arrays;

class KeyGeneration {
    // 生成密钥的函数：输入为P10、P8置换盒和10位密钥，输出为两个8位子密钥
    public static int[][] generateKey(int[] P10, int[] P8, String key) {
        int[][] keys = new int[2][8];  // 创建一个包含两个子密钥的数组

        int[] KP10 = permute(key, P10);  // 初始置换
        int[] KP10L = Arrays.copyOfRange(KP10, 0, 5);  // 左半部分
        int[] KP10R = Arrays.copyOfRange(KP10, 5, 10);  // 右半部分

        // 生成两个子密钥
        for (int round = 0; round < 2; round++) {
            // 循环左移
            KP10L = leftShift(KP10L);
            KP10R = leftShift(KP10R);
            if (round == 1) {  // 第二轮额外左移一次
                KP10L = leftShift(KP10L);
                KP10R = leftShift(KP10R);
            }

            int[] merged = merge(KP10L, KP10R);  // 合并左右两部分
            keys[round] = compressPermute(merged, P8);  // 压缩置换，生成子密钥
        }

        return keys;
    }

    // 初始置换函数：将输入字符串按置换表进行置换
    private static int[] permute(String input, int[] permutation) {
        int[] output = new int[permutation.length];
        for (int i = 0; i < permutation.length; i++) {
            output[i] = input.charAt(permutation[i] - 1) - '0';
        }
        return output;
    }

    // 左循环移位函数：将数组向左循环移动一位
    private static int[] leftShift(int[] arr) {
        int temp = arr[0];
        System.arraycopy(arr, 1, arr, 0, arr.length - 1);
        arr[arr.length - 1] = temp;
        return arr;
    }

    // 合并两个数组
    private static int[] merge(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    // 压缩置换函数：按置换表进行置换
    private static int[] compressPermute(int[] input, int[] permutation) {
        int[] output = new int[permutation.length];
        for (int i = 0; i < permutation.length; i++) {
            output[i] = input[permutation[i] - 1];
        }
        return output;
    }
}