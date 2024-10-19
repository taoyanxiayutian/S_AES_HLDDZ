package UI;

import javax.swing.*;
import java.awt.*;

public class onesaesmain extends JFrame {
	private JFrame frame;
	private RoundedPanel jpEncrypt, jpDecrypt;
	private JLabel jlabEncrypt1, jlabEncrypt2, jlabEncrypt3, jlabDecrypt1, jlabDecrypt2, jlabDecrypt3,jlabplaintext,jlabciphertext,jlabuse,jlabtime;
	private JTabbedPane jtbp;
	private JTextField keyA, keyB, plaintext, ciphertext, plaintextASCII, ciphertextASCII;
	private JTextArea outputEncrypt, outputDecrypt,bruteForceOutputArea;
	private RoundedButton butEncrypt1, butEncrypt2, butDecrypt1, butDecrypt2,butRandomKeyA,butRandomKeyB,bruteForceStartButton;
	private MyCommandListener listener, listenerB, clearlistener;



	//构造函数
	public onesaesmain() {
		initializeUI();
	}

	//具体的ui设计
	private void initializeUI() {
		frame = new JFrame("S-AES");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1000, 600);

		Color panelColor = new Color(240, 240, 240); // 浅灰色背景
		jpEncrypt = new RoundedPanel(new GridBagLayout(), 15, panelColor);//加密界面
		jpDecrypt = new RoundedPanel(new GridBagLayout(), 15, panelColor);//解密界面

		// 设置面板的边距
		jpEncrypt.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 50));
		jpDecrypt.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 50));


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
		jlabEncrypt1 = new JLabel("二进制密钥(16位)");
		jlabEncrypt1.setFont(labelFont);
		jlabEncrypt1.setForeground(primaryColor);
		jlabEncrypt1.setToolTipText("请输入16位二进制密钥或随机生成");
		keyA = new JTextField(10);
		keyA.setFont(labelFont);
		keyA.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// 加密面板随机生成密钥按钮
		butRandomKeyA = Menumain.createRoundedButton("随机", buttonFont, primaryColor);

		//明文
		jlabEncrypt2 = new JLabel("二进制明文(16位)");
		jlabEncrypt2.setFont(labelFont);
		jlabEncrypt2.setForeground(primaryColor);
		jlabEncrypt2.setToolTipText("请输入16位二进制明文");
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
		butEncrypt1 = Menumain.createRoundedButton("加密", buttonFont, primaryColor);
		butEncrypt2 = Menumain.createRoundedButton("全部重置", buttonFont, primaryColor);

		//加密结果框
		outputEncrypt = new JTextArea(10, 40);
		outputEncrypt.setFont(labelFont);
		outputEncrypt.setBackground(secondaryColor);
		outputEncrypt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// 解密面板组件
		//密钥keyB
		jlabDecrypt1 = new JLabel("二进制密钥(16位)");
		jlabDecrypt1.setFont(labelFont);
		jlabDecrypt1.setForeground(primaryColor);
		jlabDecrypt1.setToolTipText("请输入16位二进制密钥或随机生成");
		keyB = new JTextField(10);
		keyB.setFont(labelFont);
		keyB.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//密文
		jlabDecrypt2 = new JLabel("二进制密文(16位)");
		jlabDecrypt2.setFont(labelFont);
		jlabDecrypt2.setForeground(primaryColor);
		jlabDecrypt2.setToolTipText("请输入16位二进制密文");
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
		butDecrypt1 = Menumain.createRoundedButton("解密", buttonFont, primaryColor);
		butDecrypt2 = Menumain.createRoundedButton("全部重置", buttonFont, primaryColor);

		//结果框
		outputDecrypt = new JTextArea(10, 40);
		outputDecrypt.setFont(labelFont);
		outputDecrypt.setBackground(secondaryColor);
		outputDecrypt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(primaryColor),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// 解密面板随机生成密钥按钮
		butRandomKeyB = Menumain.createRoundedButton("随机", buttonFont, primaryColor);

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

		// 添加选项卡
		jtbp.addTab("加密", jpEncrypt);
		jtbp.addTab("解密", jpDecrypt);
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
	public static void generateRandomKey(JTextField keyField) {
		StringBuilder key = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			key.append(Math.random() < 0.5 ? "0" : "1");
		}
		keyField.setText(key.toString());
	}

}
