package UI;
import functionalClass.Decrypt;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
//解密界面的一些监听器设置
public class PoliceListenDecrypt implements MyCommandListener {
	JTextField keyB, ciphertext, ciphertextASCII;
	JTextArea outputDecrypt;

	public void setJTextField1(JTextField text) {
		keyB = text;
	}

	public void setJTextField2(JTextField text) {
		ciphertext = text;
	}

	public void setJTextField3(JTextField text) {
		ciphertextASCII = text;
	}

	public void setJTextArea(JTextArea area) {
		outputDecrypt = area;
	}

	public void actionPerformed(ActionEvent e) {
		String key = keyB.getText();
		String binaryCiphertext = ciphertext.getText();
		String asciiCiphertext = ciphertextASCII.getText();

		// 检查密钥
		if (!isValidBinary(key, 10)) {
			showError("密钥必须是10位二进制数。");
			return;
		}

		// 检查并处理输入
		if (!binaryCiphertext.isEmpty() && asciiCiphertext.isEmpty()) {
			// 处理二进制密文
			if (!isValidBinary(binaryCiphertext, 8)) {
				showError("二进制密文必须是8位二进制数。");
				return;
			}
			int[] cipherBinary = StringToBinary(binaryCiphertext);
			int[] plaintext = Decrypt.decryptData(cipherBinary, key);
			outputDecrypt.append("二进制密文解密结果: " + Arrays.toString(plaintext) + "\n");
		} else if (binaryCiphertext.isEmpty() && !asciiCiphertext.isEmpty()) {
			// 处理ASCII密文
			String normalizedCiphertext = normalizeASCIIInput(asciiCiphertext);
			String plaintextASCII = Decrypt.decryptASCII(normalizedCiphertext, key);
			outputDecrypt.append("ASCII密文解密结果: " + displayString(plaintextASCII) + "\n");
		} else if (!binaryCiphertext.isEmpty() && !asciiCiphertext.isEmpty()) {
			showError("请只在二进制密文或ASCII密文中输入一种。");
			return;
		} else {
			showError("请输入密文（二进制或ASCII）。");
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

	private String displayString(String input) {
		StringBuilder result = new StringBuilder();
		for (char c : input.toCharArray()) {
			result.append(displayChar(c));
		}
		return result.toString();
	}

	private String displayChar(char c) {
		if (c >= 32 && c <= 126) {
			// 可打印ASCII字符
			return String.valueOf(c);
		} else {
			// 不可打印字符，使用特殊表示
			return String.format("\\x%02X", (int) c);
		}
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
		JOptionPane.showMessageDialog(null, message, "warning", JOptionPane.ERROR_MESSAGE);
	}

	public static int[] StringToBinary(String str) {
		int[] binary = new int[8];
		for (int i = 0; i < 8; i++) {
			binary[i] = str.charAt(i) - '0';
		}
		return binary;
	}
}