package UI;

import functionalClass.BruteForceListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class mainwindow extends JFrame {
	private JFrame frame;
	private RoundedPanel jpEncrypt, jpDecrypt, jpForce;
	private JLabel jlabEncrypt1, jlabEncrypt2, jlabEncrypt3, jlabDecrypt1, jlabDecrypt2, jlabDecrypt3,jlabplaintext,jlabciphertext,jlabuse,jlabtime;
	private JTabbedPane jtbp;
	private JTextField keyA, keyB, plaintext, ciphertext, plaintextASCII, ciphertextASCII;
	private JTextArea outputEncrypt, outputDecrypt,bruteForceOutputArea;
	private RoundedButton butEncrypt1, butEncrypt2, butDecrypt1, butDecrypt2,butRandomKeyA,butRandomKeyB,bruteForceStartButton;
	private MyCommandListener listener, listenerB, clearlistener;
	private List<JTextField> bruteForceTextFields;
	private List<JCheckBox> bruteForceCheckBoxes;


	//构造函数
	public mainwindow() {
		initializeUI();
	}

	//具体的ui设计
	private void initializeUI() {
		frame = new JFrame("S-DES");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(1000, 600);

		Color panelColor = new Color(240, 240, 240); // 浅灰色背景
		jpEncrypt = new RoundedPanel(new GridBagLayout(), 15, panelColor);//加密界面
		jpDecrypt = new RoundedPanel(new GridBagLayout(), 15, panelColor);//解密界面
		jpForce = new RoundedPanel(new GridBagLayout(), 15, panelColor);//暴力破解界面

		// 设置面板的边距
		jpEncrypt.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 50));
		jpDecrypt.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 50));
		jpForce.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 50));

		// 设置整体主题
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}


		// 设置统一的字体和颜色
		Font labelFont = new Font("楷体", Font.BOLD, 16);
		Font buttonFont = new Font("楷体", Font.BOLD, 16);
		Color primaryColor = Color.decode("#72BF78");
		Color secondaryColor = Color.decode("#FFF6EA");

		// 加密面板组件
		//密钥keyA
		jlabEncrypt1 = new JLabel("二进制密钥(10位)");
		jlabEncrypt1.setFont(labelFont);
		jlabEncrypt1.setForeground(primaryColor);
		jlabEncrypt1.setToolTipText("请输入10位二进制密钥或随机生成");
		keyA = new JTextField(10);
		keyA.setFont(labelFont);
		keyA.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// 加密面板随机生成密钥按钮
		butRandomKeyA = new RoundedButton("随机");
		butRandomKeyA.setFont(buttonFont);
		butRandomKeyA.setBackground(primaryColor);
		butRandomKeyA.setForeground(Color.WHITE);
		butRandomKeyA.setFocusPainted(false);
		butRandomKeyA.setBorderPainted(false);

		//明文
		jlabEncrypt2 = new JLabel("二进制明文(8位)");
		jlabEncrypt2.setFont(labelFont);
		jlabEncrypt2.setForeground(primaryColor);
		jlabEncrypt2.setToolTipText("请输入8位二进制明文");
		plaintext = new JTextField(16);
		plaintext.setFont(labelFont);
		plaintext.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//ASCII明文
		jlabEncrypt3 = new JLabel("ASCII码字符串明文");
		jlabEncrypt3.setFont(labelFont);
		jlabEncrypt3.setForeground(primaryColor);
		jlabEncrypt3.setToolTipText("请输入ASCII明文(符号或\\xXX)");
		plaintextASCII = new JTextField(16);
		plaintextASCII.setFont(labelFont);
		plaintextASCII.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//button
		butEncrypt1 = new RoundedButton("加密");
		butEncrypt1.setFont(buttonFont);
		butEncrypt1.setBackground(primaryColor);
		butEncrypt1.setForeground(Color.WHITE);
		butEncrypt1.setFocusPainted(false);
		butEncrypt1.setBorderPainted(false);

		//button
		butEncrypt2 = new RoundedButton("全部重置");
		butEncrypt2.setFont(buttonFont);
		butEncrypt2.setBackground(primaryColor);
		butEncrypt2.setForeground(Color.WHITE);
		butEncrypt2.setFocusPainted(false);
		butEncrypt2.setBorderPainted(false);

		//加密结果框
		outputEncrypt = new JTextArea(10, 40);
		outputEncrypt.setFont(labelFont);
		outputEncrypt.setBackground(secondaryColor);
		outputEncrypt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// 解密面板组件
		//密钥keyB
		jlabDecrypt1 = new JLabel("二进制密钥(10位)");
		jlabDecrypt1.setFont(labelFont);
		jlabDecrypt1.setForeground(primaryColor);
		jlabDecrypt1.setToolTipText("请输入10位二进制密钥或随机生成");
		keyB = new JTextField(10);
		keyB.setFont(labelFont);
		keyB.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//密文
		jlabDecrypt2 = new JLabel("二进制密文(8位)");
		jlabDecrypt2.setFont(labelFont);
		jlabDecrypt2.setForeground(primaryColor);
		jlabDecrypt2.setToolTipText("请输入8位二进制密文");
		ciphertext = new JTextField(16);
		ciphertext.setFont(labelFont);
		ciphertext.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//ASCII密文
		jlabDecrypt3 = new JLabel("ASCII码字符串密文");
		jlabDecrypt3.setFont(labelFont);
		jlabDecrypt3.setForeground(primaryColor);
		jlabDecrypt3.setToolTipText("请输入ASCII密文(符号或\\xXX)");
		ciphertextASCII = new JTextField(16);
		ciphertextASCII.setFont(labelFont);
		ciphertextASCII.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//button
		butDecrypt1 = new RoundedButton("解密");
		butDecrypt1.setFont(buttonFont);
		butDecrypt1.setBackground(primaryColor);
		butDecrypt1.setForeground(Color.WHITE);
		butDecrypt1.setFocusPainted(false);
		butDecrypt1.setBorderPainted(false);
		butDecrypt2 = new RoundedButton("全部重置");
		butDecrypt2.setFont(buttonFont);
		butDecrypt2.setBackground(primaryColor);
		butDecrypt2.setForeground(Color.WHITE);
		butDecrypt2.setFocusPainted(false);
		butDecrypt2.setBorderPainted(false);

		//结果框
		outputDecrypt = new JTextArea(10, 40);
		outputDecrypt.setFont(labelFont);
		outputDecrypt.setBackground(secondaryColor);
		outputDecrypt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// 解密面板随机生成密钥按钮
		butRandomKeyB = new RoundedButton("随机");
		butRandomKeyB.setFont(buttonFont);
		butRandomKeyB.setBackground(primaryColor);
		butRandomKeyB.setForeground(Color.WHITE);
		butRandomKeyB.setFocusPainted(false);
		butRandomKeyB.setBorderPainted(false);

		//暴力破解组件
		jlabplaintext = new JLabel("二进制明文(8位)");
		jlabplaintext.setFont(labelFont);
		jlabplaintext.setForeground(primaryColor);

		jlabciphertext = new JLabel("二进制密文(8位)");
		jlabciphertext.setFont(labelFont);
		jlabciphertext.setForeground(primaryColor);

		jlabuse = new JLabel("使用");
		jlabuse.setFont(labelFont);
		jlabuse.setForeground(primaryColor);

		bruteForceTextFields = new ArrayList<>();
		bruteForceCheckBoxes = new ArrayList<>();

		for (int i = 0; i < 3; i++) {

			JTextField plaintextField = new JTextField(8);
			plaintextField.setFont(labelFont);
			plaintextField.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(primaryColor),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));

			JTextField ciphertextField = new JTextField(8);
			ciphertextField.setFont(labelFont);
			ciphertextField.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(primaryColor),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));

			JCheckBox checkBox = new JCheckBox();
			bruteForceTextFields.add(plaintextField);
			bruteForceTextFields.add(ciphertextField);
			bruteForceCheckBoxes.add(checkBox);
		}

		bruteForceOutputArea = new JTextArea(10, 40);
		bruteForceOutputArea.setEditable(false);
		bruteForceOutputArea.setFont(labelFont);
		bruteForceOutputArea.setBackground(secondaryColor);
		bruteForceOutputArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		bruteForceStartButton = new RoundedButton("开始破解");
		bruteForceStartButton.setFont(buttonFont);
		bruteForceStartButton.setBackground(primaryColor);
		bruteForceStartButton.setForeground(Color.WHITE);
		bruteForceStartButton.setFocusPainted(false);
		bruteForceStartButton.setBorderPainted(false);
		jlabtime = new JLabel("耗时: 0 秒");
		jlabtime.setFont(labelFont);
		jlabtime.setForeground(primaryColor);
		// 选项卡
		jtbp = new JTabbedPane();

	}

	public void displayWindow() {
		// 使用 GridBagConstraints 设置组件位置
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		// 加密面板布局
		addComponentsToPanel(jpEncrypt, gbc);
		// 解密面板布局
		addComponentsToPanel(jpDecrypt, gbc);
		// 暴力破解布局
		addBruteForceComponentsToPanel(jpForce, gbc);
		// 添加选项卡
		jtbp.addTab("加密", jpEncrypt);
		jtbp.addTab("解密", jpDecrypt);
		jtbp.addTab("暴力解密", jpForce);
		jtbp.setFont(new Font("楷体", Font.BOLD, 15));

		// 设置选项卡外观
		jtbp.setForeground(new Color(50, 100, 0));
		jtbp.setBorder(BorderFactory.createEmptyBorder());

		// 设置内容面板
		frame.setContentPane(jtbp);
		frame.pack();
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	//加解密界面布局
	private void addComponentsToPanel(JPanel panel, GridBagConstraints gbc) {
		// 第一行：密钥输入
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(panel == jpEncrypt ? jlabEncrypt1 : jlabDecrypt1, gbc);

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		panel.add(panel == jpEncrypt ? butRandomKeyA : butRandomKeyB, gbc);

		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panel.add(panel == jpEncrypt ? keyA : keyB, gbc);

		// 第二行：二进制输入
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		panel.add(panel == jpEncrypt ? jlabEncrypt2 : jlabDecrypt2, gbc);

		gbc.gridx = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panel.add(panel == jpEncrypt ? plaintext : ciphertext, gbc);

		// 第三行：ASCII输入
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		panel.add(panel == jpEncrypt ? jlabEncrypt3 : jlabDecrypt3, gbc);

		gbc.gridx = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panel.add(panel == jpEncrypt ? plaintextASCII : ciphertextASCII, gbc);

		// 第四行：按钮
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		panel.add(panel == jpEncrypt ? butEncrypt1 : butDecrypt1, gbc);

		gbc.gridx = 2;
		gbc.gridwidth = 2;
		panel.add(panel == jpEncrypt ? butEncrypt2 : butDecrypt2, gbc);

		// 修改输出区域的布局设置
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 0.3; // 增加垂直方向的权重，使其占据剩余空间
		JTextArea outputArea = panel == jpEncrypt ? outputEncrypt : outputDecrypt;
		outputArea.setRows(10); // 设置行数，确保两个面板的文本区域高度一致
		outputArea.setColumns(50);
		JScrollPane scrollPane = new JScrollPane(outputArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // 总是显示垂直滚动条
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  // 需要时显示水平滚动条
		scrollPane.setPreferredSize(new Dimension(600, 200));  // 设置首选大小
		panel.add(scrollPane, gbc);


	}
	//暴力破解页面布局
	private void addBruteForceComponentsToPanel(JPanel panel, GridBagConstraints gbc) {
		panel.setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		// 标题行
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
		panel.add(jlabplaintext, gbc);
		gbc.gridx = 1;
		panel.add(jlabciphertext, gbc);
		gbc.gridx = 2;
		panel.add(jlabuse, gbc);

		// 输入行
		for (int i = 0; i < 3; i++) {
			gbc.gridy++;
			gbc.gridx = 0;
			gbc.gridwidth = 1;
			panel.add(bruteForceTextFields.get(i * 2), gbc);
			gbc.gridx = 1;
			panel.add(bruteForceTextFields.get(i * 2 + 1), gbc);
			gbc.gridx = 2;
			panel.add(bruteForceCheckBoxes.get(i), gbc);
		}

		// 开始按钮
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		panel.add(bruteForceStartButton, gbc);

		// 时间标签
		gbc.gridy++;
		gbc.gridwidth = 3;
		panel.add(jlabtime, gbc);

		// 结果区域
		gbc.gridy++;
		gbc.gridwidth = 3;
		gbc.weightx = 1.0;
		gbc.weighty = 0.3;
		gbc.fill = GridBagConstraints.BOTH;
		JScrollPane scrollPane = new JScrollPane(bruteForceOutputArea);
		scrollPane.setPreferredSize(new Dimension(600, 200));
		panel.add(scrollPane, gbc);
	}
	public void setMyCommandListener(MyCommandListener listener) {
		this.listener=listener;
		listener.setJTextField1(keyA);
		listener.setJTextField2(plaintext);
		listener.setJTextField3(plaintextASCII);
		listener.setJTextArea(outputEncrypt);
		butEncrypt1.addActionListener(listener);

	}
	public void setMyCommandListenerB(MyCommandListener listener) {
		listenerB=listener;
		listener.setJTextField1(keyB);
		listener.setJTextField2(ciphertext);
		listener.setJTextField3(ciphertextASCII);
		listener.setJTextArea(outputDecrypt);
		butDecrypt1.addActionListener(listener);

	}
	public void setMyCommandListenerClear(MyCommandListener listener) {
		clearlistener=listener;
		listener.setJTextField1(keyA);
		listener.setJTextField2(plaintext);
		listener.setJTextField3(plaintextASCII);
		listener.setJTextArea(outputEncrypt);
		keyA.addActionListener(listener);
		plaintext.addActionListener(listener);
		plaintextASCII.addActionListener(listener);
		butEncrypt2.addActionListener(listener);
		butRandomKeyA.addActionListener(e -> generateRandomKey(keyA));

	}
	public void setMyCommandListenerClearD(MyCommandListener listener) {
		clearlistener=listener;
		listener.setJTextField1(keyB);
		listener.setJTextField2(ciphertext);
		listener.setJTextField3(ciphertextASCII);
		listener.setJTextArea(outputDecrypt);
		keyB.addActionListener(listener);
		ciphertext.addActionListener(listener);
		ciphertextASCII.addActionListener(listener);
		butDecrypt2.addActionListener(listener);
		butRandomKeyB.addActionListener(e -> generateRandomKey(keyB));
	}

	// 生成随机10位二进制密钥
	private void generateRandomKey(JTextField keyField) {
		StringBuilder key = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			key.append(Math.random() < 0.5 ? "0" : "1");
		}
		keyField.setText(key.toString());
	}
	// 添加设置暴力破解监听器的方法
	public void setBruteForceListener(BruteForceListener listener) {
		bruteForceStartButton.addActionListener(e -> {
			List<String> plaintexts = new ArrayList<>();
			List<String> ciphertexts = new ArrayList<>();
			List<Boolean> useFlags = new ArrayList<>();
			boolean hasError = false;
			StringBuilder errorMessage = new StringBuilder("输入错误：\n");

			for (int i = 0; i < 3; i++) {
				String plaintext = bruteForceTextFields.get(i * 2).getText().trim();
				String ciphertext = bruteForceTextFields.get(i * 2 + 1).getText().trim();
				boolean isUsed = bruteForceCheckBoxes.get(i).isSelected();

				plaintexts.add(plaintext);
				ciphertexts.add(ciphertext);
				useFlags.add(isUsed);

				if (isUsed) {
					if (!isValidBinary(plaintext, 8) || !isValidBinary(ciphertext, 8)) {
						errorMessage.append("- 第 ").append(i + 1).append(" 对：明文和密文必须是8位二进制数。\n");
						hasError = true;
					}
				} else if (!plaintext.isEmpty() || !ciphertext.isEmpty()) {
					errorMessage.append("- 第 ").append(i + 1).append(" 对：未选中使用，但输入了数据。\n");
					hasError = true;
				}
			}

			int selectedCount = (int) useFlags.stream().filter(flag -> flag).count();
			if (selectedCount == 0) {
				errorMessage.append("- 请至少选择一对明文-密文对。\n");
				hasError = true;
			}

			if (hasError) {
				JOptionPane.showMessageDialog(this, errorMessage.toString(), "输入错误", JOptionPane.ERROR_MESSAGE);
				return;
			}

			listener.startBruteForce(plaintexts, ciphertexts, useFlags, this::updateBruteForceUI);
		});
	}

	private boolean isValidBinary(String input, int expectedLength) {
		return input.matches("[01]{" + expectedLength + "}");
	}

	// 更新暴力破解ui
	private void updateBruteForceUI(String output, String time) {
		SwingUtilities.invokeLater(() -> {
			bruteForceOutputArea.setText(output);
			jlabtime.setText(time);
		});
	}
}