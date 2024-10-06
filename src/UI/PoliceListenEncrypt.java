//解密界面的一些监听器设置
package UI;
import functionalClass.Encrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class PoliceListenEncrypt implements MyCommandListener {
	JTextField keyA,plaintext,plaintextASCII;
	JTextArea outputEncrypt;
	
	public void setJTextField1(JTextField text) {
		keyA=text;
	}
	public void setJTextField2(JTextField text) {
		plaintext=text;
	}
	public void setJTextField3(JTextField text) {
		plaintextASCII=text;
	}
	public void setJTextArea(JTextArea area) {
		outputEncrypt=area;
	}

	public void actionPerformed(ActionEvent e) {
		String key = keyA.getText();
		String binaryPlaintext = plaintext.getText();
		String asciiPlaintext = plaintextASCII.getText();

		// 检查密钥
		if (!isValidBinary(key, 10)) {
			showError("密钥必须是10位二进制数。");
			return;
		}

		// 检查并处理输入
		if (!binaryPlaintext.isEmpty() && asciiPlaintext.isEmpty()) {
			// 处理二进制明文
			if (!isValidBinary(binaryPlaintext, 8)) {
				showError("二进制明文必须是8位二进制数。");
				return;
			}
			int[] plainBinary = StringToBinary(binaryPlaintext);
			int[] ciphertext = Encrypt.encryptData(plainBinary, key);
			outputEncrypt.append("二进制明文加密结果: " + Arrays.toString(ciphertext) + "\n");
		} else if (binaryPlaintext.isEmpty() && !asciiPlaintext.isEmpty()) {
			// 处理ASCII明文
			String normalizedPlaintext = normalizeASCIIInput(asciiPlaintext);
			String ciphertextASCII = Encrypt.encryptASCII(normalizedPlaintext, key);
			outputEncrypt.append("ASCII明文加密结果: " + displayString(ciphertextASCII) + "\n");
		} else if (!binaryPlaintext.isEmpty() && !asciiPlaintext.isEmpty()) {
			showError("请只在二进制明文或ASCII明文中输入一种。");
			return;
		} else {
			showError("请输入明文（二进制或ASCII）。");
			return;
		}
	}

	private String normalizeASCIIInput(String input) {
		StringBuilder normalized = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '\\' && i + 3 < input.length() && input.charAt(i + 1) == 'x') {
				// 处理 \xXX 格式
				String hex = input.substring(i + 2, i + 4);
				try {
					int charCode = Integer.parseInt(hex, 16);
					normalized.append((char) charCode);
					i += 3; // 跳过已处理的字符
				} catch (NumberFormatException e) {
					// 如果不是有效的十六进制，保持原样
					normalized.append(input.charAt(i));
				}
			} else {
				normalized.append(input.charAt(i));
			}
		}
		return normalized.toString();
	}
	private boolean isValidBinary(String str, int length) {
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

	// 显示字符串，将不可打印字符转换为可见表示
	private String displayString(String input) {
		StringBuilder result = new StringBuilder();
		for (char c : input.toCharArray()) {
			result.append(displayChar(c));
		}
		return result.toString();
	}

	// 显示单个字符，将不可打印字符转换为可见表示，表示形式为\xXX
	private String displayChar(char c) {
		if (c >= 32 && c <= 126) {
			// 可打印ASCII字符
			return String.valueOf(c);
		} else {
			// 不可打印字符，使用特殊表示
			return String.format("\\x%02X", (int) c);
		}
	}
	public static int[] StringToBinary(String str) {
        int[] binary = new int[8];
        for (int i = 0; i < 8; i++) {
    		Character ch = str.charAt(i);
    		binary[i] = Integer.parseInt(ch.toString());
    		}

        return binary;
    }

}
