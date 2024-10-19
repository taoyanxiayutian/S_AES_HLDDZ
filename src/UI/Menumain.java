package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menumain extends JFrame {
    private RoundedButton but1, but2, but3, but4;

    public Menumain() {
        // 设置统一的字体和颜色
        Font labelFont = new Font("楷体", Font.BOLD, 16);
        Font buttonFont = new Font("楷体", Font.BOLD, 16);
        Color primaryColor = Color.decode("#72BF78");
        Color secondaryColor = Color.decode("#FFF6EA");

        setTitle("主菜单");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中显示

        Color panelColor = new Color(240, 240, 240); // 浅灰色背景
        RoundedPanel mainPanel = new RoundedPanel(new GridBagLayout(),15, panelColor); // 使用RoundedPanel
        mainPanel.setLayout(new GridBagLayout()); // 使用GridBagLayout
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置间距

        // 第一行：标题
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // 占据2列
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("S-AES工具表", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24)); // 使用更大的字体
        mainPanel.add(titleLabel, gbc);

        // 第二行：按钮
        gbc.gridwidth = 1; // 每个按钮占据1列
        gbc.gridy = 1;

        // 创建按钮
        but1 = createRoundedButton("普通程序 (s_aes)", buttonFont, primaryColor);
        but2 = createRoundedButton("双重加密 (2s_aes)", buttonFont, primaryColor);
        but3 = createRoundedButton("三重加密 (3s_aes)", buttonFont, primaryColor);
        but4 = createRoundedButton("CBC模式", buttonFont, primaryColor);

        // 添加按钮到面板
        gbc.gridx = 0;
        mainPanel.add(but1, gbc);

        gbc.gridx = 1;
        mainPanel.add(but2, gbc);

        // 第三行：按钮
        gbc.gridy = 2; // 下一行
        gbc.gridx = 0;
        mainPanel.add(but3, gbc);

        gbc.gridx = 1;
        mainPanel.add(but4, gbc);

        // 监听器
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onesaesmain SAES = new onesaesmain();
                SAES.displayWindow();
                PoliceListenEncrypt policeEncrypt = new PoliceListenEncrypt();
                PoliceListenDecrypt policeDecrypt = new PoliceListenDecrypt();
                ClearAllListen clPoliceEncrypt = new ClearAllListen();
                ClearAllListen clPoliceDecrypt = new ClearAllListen();

                SAES.setMyCommandListenerClear(clPoliceEncrypt);
                SAES.setMyCommandListenerClearD(clPoliceDecrypt);
                SAES.setMyCommandListener(policeEncrypt);
                SAES.setMyCommandListenerB(policeDecrypt);
            }
        });

        but2.addActionListener(e -> {
            twosaesmain SAES_2 = new twosaesmain();
            SAES_2.displayWindow();
            PoliceListenEncrypt2 policeEncrypt = new PoliceListenEncrypt2();
            PoliceListenDecrypt2 policeDecrypt = new PoliceListenDecrypt2();
            ClearAllListen clPoliceEncrypt = new ClearAllListen();
            ClearAllListen clPoliceDecrypt = new ClearAllListen();

            SAES_2.setMyCommandListener(policeEncrypt);
            SAES_2.setMyCommandListenerB(policeDecrypt);
            SAES_2.setMyCommandListenerClear(clPoliceEncrypt);
            SAES_2.setMyCommandListenerClearD(clPoliceDecrypt);
        });

        but3.addActionListener(e -> {
            threesaesmain SAES_3 = new threesaesmain();
            SAES_3.displayWindow();
            PoliceListenEncrypt3 policeEncrypt = new PoliceListenEncrypt3();
            PoliceListenDecrypt3 policeDecrypt = new PoliceListenDecrypt3();
            ClearAllListen clPoliceEncrypt = new ClearAllListen();
            ClearAllListen clPoliceDecrypt = new ClearAllListen();

            SAES_3.setMyCommandListener(policeEncrypt);
            SAES_3.setMyCommandListenerB(policeDecrypt);
            SAES_3.setMyCommandListenerClear(clPoliceEncrypt);
            SAES_3.setMyCommandListenerClearD(clPoliceDecrypt);
        });

        but4.addActionListener(e -> {
            CBCmain CBC_mode = new CBCmain();
            CBC_mode.displayWindow();
            PoliceListenEncrypt4 policeEncrypt = new PoliceListenEncrypt4();
            PoliceListenDecrypt4 policeDecrypt = new PoliceListenDecrypt4();
            ClearAllListen clPoliceEncrypt = new ClearAllListen();
            ClearAllListen clPoliceDecrypt = new ClearAllListen();

            CBC_mode.setMyCommandListener(policeEncrypt);
            CBC_mode.setMyCommandListenerB(policeDecrypt);
            CBC_mode.setMyCommandListenerClear(clPoliceEncrypt);
            CBC_mode.setMyCommandListenerClearD(clPoliceDecrypt);
        });
    }

    public static RoundedButton createRoundedButton(String text, Font font, Color backgroundColor) {
        RoundedButton button = new RoundedButton(text);
        button.setFont(font);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

}