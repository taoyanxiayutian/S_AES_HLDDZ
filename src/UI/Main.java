package UI;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		// 显示主菜单
		SwingUtilities.invokeLater(() -> {
			Menumain mainMenu = new Menumain();
			mainMenu.setVisible(true);
		});
	}
}