package omok_02;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class RunGame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container c;
	MapSize size = new MapSize();
	GameMethod gm = new GameMethod();
	

	public RunGame(String title) {
		
		setTitle(title);
		createMenu();
		setBounds(200, 20, 650, 750);
		c = getContentPane();
		c.setLayout(null);
		ShowMap m = new ShowMap(size, gm);
		setContentPane(m);
		MouseAction Mc = new MouseAction(gm, size, m, this);
		addMouseListener(Mc);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd = e.getActionCommand();
			
			switch (cmd) { // �޴� �������� ���� ����
			
			case "2��":
				gm.setGameMode(2);
				gm.init();
				break;
			case "3��":
				gm.setGameMode(3);
				gm.init();
				break;
				
			case "4��":
				gm.setGameMode(4);
				gm.init();
				break;	
				
			}
		}
	}
	
	class ReAction implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				switch (i) {  // ���� �ٽý���
				case "�ٽý���":
					gm.init();
					break;
				}					
			}
	}
	
	public void createMenu() {
		
		JMenuBar mb = new JMenuBar(); // �޴��� ����
		JMenuItem[] menuItem = new JMenuItem[3];
		String[] itemTitle = { "2��", "3��", "4��" }; //�ĺ��޴� ����
		JMenu screenMenu = new JMenu("����� ��");
		JMenuItem[] reItem = new JMenuItem[1];
		String[] reTitle = {"�ٽý���"};
		JMenu replay = new JMenu("���� �޴�");
		MenuActionListener listener = new MenuActionListener();		
		for (int i = 0; i < menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(listener);
			screenMenu.add(menuItem[i]);
		}
		
		ReAction restart = new ReAction();
		
		for(int j = 0; j < reItem.length; j++) {
			reItem[j] = new JMenuItem(reTitle[j]);
			reItem[j].addActionListener(restart);
			replay.add(reItem[j]);
		}
		
		mb.add(screenMenu);
		mb.add(replay);
		setJMenuBar(mb);
	}
}