package functionalClass;

import UI.PoliceListenEncrypt4;

public class CBCtest {
    private String key;
    private String iv;

    public CBCtest(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    public String encrypt(String plaintext) {
        StringBuilder binaryPlaintext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            String binaryChar = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binaryPlaintext.append(binaryChar);
        }

        String[] blocks = PoliceListenEncrypt4.splitIntoBlocks(binaryPlaintext.toString(), 16);
        StringBuilder ciphertextBuilder = new StringBuilder();
        String previousCiphertext = iv;

        for (String block : blocks) {
            String xorBlock = funcforaes.xor(block, previousCiphertext);
            String ciphertextBlock = funcforaes.encrypt(xorBlock, key);
            ciphertextBuilder.append(ciphertextBlock);
            previousCiphertext = ciphertextBlock;
        }

        return ciphertextBuilder.toString();
    }

    public String decrypt(String ciphertext) {
        String[] blocks = PoliceListenEncrypt4.splitIntoBlocks(ciphertext, 16);
        StringBuilder binaryPlaintextBuilder = new StringBuilder();
        String previousCiphertext = iv;

        for (String block : blocks) {
            String decryptedBlock = funcforaes.decrypt(block, key);
            String plaintextBlock = funcforaes.xor(decryptedBlock, previousCiphertext);
            binaryPlaintextBuilder.append(plaintextBlock);
            previousCiphertext = block;
        }

        StringBuilder plaintextBuilder = new StringBuilder();
        for (int i = 0; i < binaryPlaintextBuilder.length(); i += 8) {
            String byteString = binaryPlaintextBuilder.substring(i, Math.min(i + 8, binaryPlaintextBuilder.length()));
            int charCode = Integer.parseInt(byteString, 2);
            plaintextBuilder.append((char) charCode);
        }

        return plaintextBuilder.toString();
    }

    public static void main(String[] args) {
        String key = "1010101010101010"; // 16位二进制密钥
        String iv = "0000000000000000"; // 16位二进制初始向量
        CBCtest cbcTest = new CBCtest(key, iv);

        String plaintext = "Hello,S-AES!"; // 示例明文
        String ciphertext = cbcTest.encrypt(plaintext);

        // 在加密完成后对密文分组进行篡改
        char[] postTamperedCiphertext = ciphertext.toCharArray();
        postTamperedCiphertext[postTamperedCiphertext.length-1] = (char) (postTamperedCiphertext[0] + 1); // 修改第一个字符
        String postTamperedCiphertextStr = new String(postTamperedCiphertext);
        System.out.println("原始明文: " + plaintext);
        System.out.println("原始密文: " + ciphertext);
        System.out.println("在加密完成后对密文分组进行篡改最后一个字符的密文: " + postTamperedCiphertextStr);

        // 解密后续篡改后的密文
        String postTamperedDecryptedPlaintext = cbcTest.decrypt(postTamperedCiphertextStr);
        System.out.println("对上述篡改密文进行解密的明文: " + postTamperedDecryptedPlaintext);


    }
}