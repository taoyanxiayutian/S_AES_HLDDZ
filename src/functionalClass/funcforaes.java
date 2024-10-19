package functionalClass;

import java.util.Base64;

public class funcforaes {
    public static int[][] S_box = new int[][] {
            {9, 4, 10, 11},
            {13, 1, 8, 5},
            {6, 2, 0, 3},
            {12, 14, 15, 7}};
    public static int[][] DeS_box = new int[][]{
            {10, 5, 9, 11},
            {1, 7, 8, 15},
            {6, 0, 2, 3},
            {12, 4, 13, 14}};
    public static int[] RCON1 = new int[] {8,0};
    public static int[] RCON2 = new int[] {3,0};

    public static void main(String[] args) {
        String k="1010011100111011";
        String plaintext="0100101001110100";
        String ciphertext= encrypt(plaintext,k);

        // 示例：ASCII加解密
        String asciiText = "Hello world!!";
        String asciiCiphertext = encryptASCII(asciiText, k);
        String decryptedAscii = decryptASCII(asciiCiphertext, k);

        System.out.println("ASCII加密结果: " + asciiCiphertext);
        System.out.println("ASCII解密结果: " + decryptedAscii);
    }

    public static String xor(String a, String b) { //进行异或操作
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                sb.append("0");
            } else {
                sb.append("1");
            }
        }
        return new String(sb);
    }

    public static String ToBinary(int num, int digit) {  //十进制转二进制（特定位数）
        String binStr = "";
        for (int i = digit-1; i >= 0; i--) {
            binStr += (num >> i) & 1;
        }
        return binStr;
    }

    public static int[] parseHexStr(int dec) {
        String binStr = ToBinary(dec, 4);  //二进制
        int a = Integer.parseInt(binStr.substring(0,2), 2);
        int b = Integer.parseInt(binStr.substring(2,4), 2);
        int[] result = {a,b};
        return result;
    }

    public static int[] From16to4(String bin) {
        String binPadded = String.format("%16s", bin).replace(' ', '0');
        int[] dec = new int[4];
        for (int i = 0; i < 4; i++) {
            dec[i] = Integer.parseInt(binPadded.substring(i * 4, i * 4 + 4), 2);
        }
        return dec;
    }

    public static String From4to16(int[] dec){
        String binStr="";
        for(int i=0; i<4; i++){
            binStr += ToBinary(dec[i], 4);
        }
        return binStr;
    }

    public static int[] S_replace(int[] sm) {    //半字节代替
        int num=sm.length;
        int[] replace=new int[num];
        for(int i=0; i<num; i++){
            int[] pos=parseHexStr(sm[i]);
            replace[i]=S_box[pos[0]][pos[1]];
        }
        return replace;
    }

    public static int[] DeS_replace(int[] sm) {    //半字节代替
        int num=sm.length;
        int[] replace=new int[num];
        for(int i=0; i<num; i++){
            int[] pos=parseHexStr(sm[i]);
            replace[i]=DeS_box[pos[0]][pos[1]];
        }
        return replace;
    }

    public static int[] Shift(int[] sm){    //行位移
        int a=sm[1];
        sm[1]=sm[3];
        sm[3]=a;
        return sm;
    }

    public static int[] G(int[] W, int[] rcon){    //g函数（用于密钥扩展）
        int[] newW=new int[2];
        newW[0]=W[1];
        newW[1]=W[0];
        newW=S_replace(newW);
        for(int i=0; i<2; i++){
            newW[i]=newW[i]^rcon[i];
        }
        return newW;
    }

    public static int[] ColCon(int[] sm){    //列混淆
        String binStr=From4to16(sm);
        int[] bin=new int[16];
        for(int i=0; i<16; i++){
            bin[i]=Integer.valueOf(binStr.substring(i,i+1));
        }
        int[] result=new int[16];
        result[0]=bin[0]^bin[6];
        result[1]=bin[1]^bin[4]^bin[7];
        result[2]=bin[2]^bin[4]^bin[5];
        result[3]=bin[3]^bin[5];
        result[4]=bin[2]^bin[4];
        result[5]=bin[0]^bin[3]^bin[5];
        result[6]=bin[0]^bin[1]^bin[6];
        result[7]=bin[1]^bin[7];
        result[8]=bin[8]^bin[14];
        result[9]=bin[9]^bin[12]^bin[15];
        result[10]=bin[10]^bin[12]^bin[13];
        result[11]=bin[11]^bin[13];
        result[12]=bin[10]^bin[12];
        result[13]=bin[8]^bin[11]^bin[13];
        result[14]=bin[8]^bin[9]^bin[14];
        result[15]=bin[9]^bin[15];
        String binStr2="";
        for(int i=0; i<16; i++){
            binStr2 += String.valueOf(result[i]);
        }
        return From16to4(binStr2);
    }

    public static int[] DeColCon(int[] sm){    //逆列混淆
        String binStr=From4to16(sm);
        int[] bin=new int[16];
        for(int i=0; i<16; i++){
            bin[i]=Integer.valueOf(binStr.substring(i,i+1));
        }
        int[] result=new int[16];
        result[0]=bin[3]^bin[5];
        result[1]=bin[0]^bin[6];
        result[2]=bin[1]^bin[4]^bin[7];
        result[3]=bin[2]^bin[3]^bin[4];
        result[4]=bin[1]^bin[7];
        result[5]=bin[2]^bin[4];
        result[6]=bin[0]^bin[3]^bin[5];
        result[7]=bin[0]^bin[6]^bin[7];
        result[8]=bin[11]^bin[13];
        result[9]=bin[8]^bin[14];
        result[10]=bin[9]^bin[12]^bin[15];
        result[11]=bin[10]^bin[11]^bin[12];
        result[12]=bin[9]^bin[15];
        result[13]=bin[10]^bin[12];
        result[14]=bin[8]^bin[11]^bin[13];
        result[15]=bin[8]^bin[14]^bin[15];
        String binStr2="";
        for(int i=0; i<16; i++){
            binStr2 += String.valueOf(result[i]);
        }
        return From16to4(binStr2);
    }

    public static int[][] KeyExpan(String keyStr){    //密钥扩展
        int[] key=From16to4(keyStr);  //原密钥
        int[][] W=new int[6][2];
        W[0][0]=key[0];
        W[0][1]=key[1];
        W[1][0]=key[2];
        W[1][1]=key[3];
        int[] gw1=G(W[1],RCON1);
        W[2][0]=W[0][0]^gw1[0];
        W[2][1]=W[0][1]^gw1[1];
        W[3][0]=W[2][0]^W[1][0];
        W[3][1]=W[2][1]^W[1][1];
        int[] gw2=G(W[3],RCON2);
        W[4][0]=W[2][0]^gw2[0];
        W[4][1]=W[2][1]^gw2[1];
        W[5][0]=W[4][0]^W[3][0];
        W[5][1]=W[4][1]^W[3][1];
        return W;
    }

    public static String encrypt(String plaintext, String key){
        int[][] W=KeyExpan(key);
        int[] data=From16to4(plaintext);
        data[0]=data[0]^W[0][0];
        data[1]=data[1]^W[0][1];
        data[2]=data[2]^W[1][0];
        data[3]=data[3]^W[1][1];
        data=S_replace(data);
        data=Shift(data);
        data=ColCon(data);
        data[0]=data[0]^W[2][0];
        data[1]=data[1]^W[2][1];
        data[2]=data[2]^W[3][0];
        data[3]=data[3]^W[3][1];
        data=S_replace(data);
        data=Shift(data);
        data[0]=data[0]^W[4][0];
        data[1]=data[1]^W[4][1];
        data[2]=data[2]^W[5][0];
        data[3]=data[3]^W[5][1];
        return From4to16(data);
    }

    public static String decrypt(String ciphertext, String key){
        int[][] W=KeyExpan(key);
        int[] data=From16to4(ciphertext);
        data[0]=data[0]^W[4][0];
        data[1]=data[1]^W[4][1];
        data[2]=data[2]^W[5][0];
        data[3]=data[3]^W[5][1];
        data=DeS_replace(data);
        data=Shift(data);
        data[0]=data[0]^W[2][0];
        data[1]=data[1]^W[2][1];
        data[2]=data[2]^W[3][0];
        data[3]=data[3]^W[3][1];
        data=DeColCon(data);
        data=DeS_replace(data);
        data=Shift(data);
        data[0]=data[0]^W[0][0];
        data[1]=data[1]^W[0][1];
        data[2]=data[2]^W[1][0];
        data[3]=data[3]^W[1][1];
        return From4to16(data);
    }

    // ASCII加密函数
    public static String encryptASCII(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            String binaryChar = String.format("%16s", Integer.toBinaryString(c)).replace(' ', '0');
            String encryptedBinary = encrypt(binaryChar, key);
            int encryptedChar = Integer.parseInt(encryptedBinary, 2);
            ciphertext.append((char) encryptedChar);
        }
        return Base64.getEncoder().encodeToString(ciphertext.toString().getBytes());
    }

    // ASCII解密函数
    public static String decryptASCII(String ciphertext, String key) {
        // 清理Base64输入，去除非法字符
        ciphertext = ciphertext.replaceAll("\\s+", "");

        String decodedText = new String(Base64.getDecoder().decode(ciphertext));
        StringBuilder plaintext = new StringBuilder();
        for (char c : decodedText.toCharArray()) {
            String binaryChar = String.format("%16s", Integer.toBinaryString(c)).replace(' ', '0');
            String decryptedBinary = decrypt(binaryChar, key);
            int decryptedChar = Integer.parseInt(decryptedBinary, 2);
            plaintext.append((char) decryptedChar);
        }
        return plaintext.toString();
    }

}
