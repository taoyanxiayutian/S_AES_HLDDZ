package UI;

import functionalClass.AESMiddleAttack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class twosaesmain extends JFrame {
    private JFrame frame;
    private JTabbedPane jtbp_2;
    private RoundedPanel jpEncrypt, jpDecrypt, jpMinddleAttack;
    private JLabel jlabEncrypt1, jlabEncrypt2, jlabEncrypt3;
    private JLabel jlabDecrypt1, jlabDecrypt2, jlabDecrypt3;
    private JLabel jlabuse,jlabtime,jlabplaintext,jlabciphertext;
    private JTextField keyA1, keyA2, plaintext;
    private JTextField keyB1, keyB2, ciphertext;
    private JTextArea outputEncrypt, outputDecrypt,outputMiddleAttack;
    private RoundedButton butEncrypt1, butEncrypt2, butDecrypt1, butDecrypt2;
    private RoundedButton butrandomA1, butrandomA2;
    private RoundedButton butrandomB1, butrandomB2;
    private RoundedButton StartButton;
    private MyCommandListener listener, listenerB,clearlistener;
    // 中间相遇攻击组件
    private long startTime; // 用于计时

    private List<JTextField> MiddleAttckTextFields;
    private List<JCheckBox> MiddleAttckCheckBoxes;

    //构造函数
    public twosaesmain() {
        initializeUI();
    }

    //具体的ui设计
    private void initializeUI() {
        frame = new JFrame("2 S-AES");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color panelColor = new Color(240, 240, 240); // 浅灰色背景
        jpEncrypt = new RoundedPanel(new GridBagLayout(), 15, panelColor);//加密界面
        jpDecrypt = new RoundedPanel(new GridBagLayout(), 15, panelColor);//解密界面
        jpMinddleAttack=new RoundedPanel(new GridBagLayout(), 15, panelColor);//解密界面

        // 设置面板的边距
        jpEncrypt.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 50));
        jpDecrypt.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 50));
        jpMinddleAttack.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 50));

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
        jlabEncrypt1 = new JLabel("二进制密钥1(16位)");
        jlabEncrypt1.setFont(labelFont);
        jlabEncrypt1.setForeground(primaryColor);
        jlabEncrypt1.setToolTipText("请输入16位二进制密钥或随机生成");
        keyA1 = new JTextField(10);
        keyA1.setFont(labelFont);
        keyA1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // 加密面板随机生成密钥按钮
        butrandomA1 = Menumain.createRoundedButton("随机", buttonFont, primaryColor);

        //明文
        jlabEncrypt2 = new JLabel("二进制密钥2(16位)");
        jlabEncrypt2.setFont(labelFont);
        jlabEncrypt2.setForeground(primaryColor);
        keyA2 = new JTextField(16);
        keyA2.setFont(labelFont);
        keyA2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        butrandomA2 = Menumain.createRoundedButton("随机", buttonFont, primaryColor);

        jlabEncrypt3 = new JLabel("二进制明文(16位)");
        jlabEncrypt3.setFont(labelFont);
        jlabEncrypt3.setForeground(primaryColor);
        plaintext = new JTextField(16);
        plaintext.setFont(labelFont);
        plaintext.setBorder(BorderFactory.createCompoundBorder(
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
        jlabDecrypt1 = new JLabel("二进制密钥1(16位)");
        jlabDecrypt1.setFont(labelFont);
        jlabDecrypt1.setForeground(primaryColor);
        keyB1 = new JTextField(10);
        keyB1.setFont(labelFont);
        keyB1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        butrandomB1 = Menumain.createRoundedButton("随机", buttonFont, primaryColor);

        //key2
        jlabDecrypt2 = new JLabel("二进制密钥2(16位)");
        jlabDecrypt2.setFont(labelFont);
        jlabDecrypt2.setForeground(primaryColor);
        keyB2 = new JTextField(16);
        keyB2.setFont(labelFont);
        keyB2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        butrandomB2 = Menumain.createRoundedButton("随机", buttonFont, primaryColor);

        jlabDecrypt3 = new JLabel("二进制密文(16位)");
        jlabDecrypt3.setFont(labelFont);
        jlabDecrypt3.setForeground(primaryColor);
        ciphertext = new JTextField(16);
        ciphertext.setFont(labelFont);
        ciphertext.setBorder(BorderFactory.createCompoundBorder(
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

        //中间相遇攻击组件
        jlabplaintext = new JLabel("二进制明文(16位)");
        jlabplaintext.setFont(labelFont);
        jlabplaintext.setForeground(primaryColor);

        jlabciphertext = new JLabel("二进制密文(16位)");
        jlabciphertext.setFont(labelFont);
        jlabciphertext.setForeground(primaryColor);

        jlabuse = new JLabel("使用");
        jlabuse.setFont(labelFont);
        jlabuse.setForeground(primaryColor);

        MiddleAttckTextFields = new ArrayList<>();
        MiddleAttckCheckBoxes = new ArrayList<>();

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
            MiddleAttckTextFields.add(plaintextField);
            MiddleAttckTextFields.add(ciphertextField);
            MiddleAttckCheckBoxes.add(checkBox);
        }

        outputMiddleAttack = new JTextArea(10, 40);
        outputMiddleAttack.setEditable(false);
        outputMiddleAttack.setFont(labelFont);
        outputMiddleAttack.setBackground(secondaryColor);
        outputMiddleAttack.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        StartButton = new RoundedButton("开始破解");
        StartButton.setFont(buttonFont);
        StartButton.setBackground(primaryColor);
        StartButton.setForeground(Color.WHITE);
        StartButton.setFocusPainted(false);
        StartButton.setBorderPainted(false);
        jlabtime = new JLabel("耗时: 0 秒");
        jlabtime.setFont(labelFont);
        jlabtime.setForeground(primaryColor);


        StartButton.addActionListener(e -> {
            outputMiddleAttack.setText("");  // 清空输出框
            startTime = System.currentTimeMillis(); // 记录开始时间

            List<String[]> selectedPairs = new ArrayList<>(); // 用于存储选中的明密文对

            // 遍历所有选择的明密文对
            for (int i = 0; i < MiddleAttckTextFields.size(); i += 2) {
                // 只处理选中的明密文对
                if (MiddleAttckCheckBoxes.get(i / 2).isSelected()) {
                    String plaintext = MiddleAttckTextFields.get(i).getText();  // 获取输入的明文
                    String ciphertext = MiddleAttckTextFields.get(i + 1).getText(); // 获取输入的密文

                    // 验证输入的明文和密文是否为二进制
                    if (!plaintext.matches("[01]{16}") || !ciphertext.matches("[01]{16}")) {
                        outputMiddleAttack.append("明文和密文必须为16位二进制字符串。\n");
                        continue; // 跳过无效输入
                    }

                    // 将有效的明密文对添加到列表中
                    selectedPairs.add(new String[]{plaintext, ciphertext});
                }
            }

            // 执行中间相遇攻击
            if (!selectedPairs.isEmpty()) {
                String result = AESMiddleAttack.performMiddleAttack(selectedPairs);
                outputMiddleAttack.append("攻击结果:\n" + result + "\n"); // 添加了换行符
            } else {
                outputMiddleAttack.append("未选择任何有效的明密文对。\n");
            }

            // 计算并显示耗时
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            jlabtime.setText("耗时: " + (duration / 1000.0) + " 秒");
        });

        // 选项卡
        jtbp_2 = new JTabbedPane();

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
        //中间相遇面板布局
        addMiddleAttackToPanel(jpMinddleAttack, gbc);
        // 添加选项卡
        jtbp_2.addTab("加密", jpEncrypt);
        jtbp_2.addTab("解密", jpDecrypt);
        jtbp_2.addTab("中间相遇攻击", jpMinddleAttack);
        jtbp_2.setFont(new Font("楷体", Font.BOLD, 15));

        // 设置选项卡外观
        jtbp_2.setForeground(new Color(50, 100, 0));
        jtbp_2.setBorder(BorderFactory.createEmptyBorder());

        // 设置内容面板
        frame.setContentPane(jtbp_2);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    //加解密界面布局
    private void addComponentsToPanel(JPanel panel, GridBagConstraints gbc) {
        // 第一行：keyA1/B1
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(panel == jpEncrypt ? jlabEncrypt1 : jlabDecrypt1, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(panel == jpEncrypt ? butrandomA1 : butrandomB1, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(panel == jpEncrypt ? keyA1 : keyB1, gbc);

        // 第二行：keyA2/B2
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(panel == jpEncrypt ? jlabEncrypt2 : jlabDecrypt2, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(panel == jpEncrypt ? butrandomA2 : butrandomB2, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(panel == jpEncrypt ? keyA2 : keyB2, gbc);

        // 第三行：明密文输入
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
        panel.add(panel == jpEncrypt ? plaintext : ciphertext, gbc);

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
    private void addMiddleAttackToPanel(JPanel panel, GridBagConstraints gbc) {
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
            panel.add(MiddleAttckTextFields.get(i * 2), gbc);
            gbc.gridx = 1;
            panel.add(MiddleAttckTextFields.get(i * 2 + 1), gbc);
            gbc.gridx = 2;
            panel.add(MiddleAttckCheckBoxes.get(i), gbc);
        }

        // 开始按钮
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        panel.add(StartButton, gbc);

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
        JScrollPane scrollPane = new JScrollPane(outputMiddleAttack);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        panel.add(scrollPane, gbc);
    }
    public void setMyCommandListener(MyCommandListener listener) {
        this.listener=listener;
        listener.setJTextField1(keyA1);
        listener.setJTextField2(keyA2);
        listener.setJTextField3(plaintext);
        listener.setJTextArea(outputEncrypt);
        butEncrypt1.addActionListener(listener);
    }
    public void setMyCommandListenerB(MyCommandListener listener) {
        listenerB=listener;
        listener.setJTextField1(keyB1);
        listener.setJTextField2(keyB2);
        listener.setJTextField3(ciphertext);
        listener.setJTextArea(outputDecrypt);
        butDecrypt1.addActionListener(listener);
    }
    public void setMyCommandListenerClear(MyCommandListener listener) {
        clearlistener=listener;
        listener.setJTextField1(keyA1);
        listener.setJTextField2(keyA2);
        listener.setJTextField3(plaintext);
        listener.setJTextArea(outputEncrypt);
        keyA1.addActionListener(listener);
        keyA2.addActionListener(listener);
        plaintext.addActionListener(listener);
        butrandomA1.addActionListener(e -> generateRandomKey(keyA1));
        butrandomA2.addActionListener(e -> generateRandomKey(keyA2));
        butEncrypt2.addActionListener(listener);
    }
    public void setMyCommandListenerClearD(MyCommandListener listener) {
        clearlistener=listener;
        listener.setJTextField1(keyB1);
        listener.setJTextField2(keyB2);
        listener.setJTextField3(ciphertext);
        listener.setJTextArea(outputDecrypt);
        keyB1.addActionListener(listener);
        keyB2.addActionListener(listener);
        ciphertext.addActionListener(listener);
        butrandomA1.addActionListener(e -> generateRandomKey(keyB1));
        butrandomA2.addActionListener(e -> generateRandomKey(keyB2));
        butDecrypt2.addActionListener(listener);
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
