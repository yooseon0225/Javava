package omok_02;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
public class MouseAction extends MouseAdapter{
	private GameMethod gm; 
	private MapSize m; 
	private ShowMap sm;
	private RunGame g;
	
	public MouseAction(GameMethod gm,MapSize m, ShowMap mm,RunGame g) {
		this.gm=gm;
		this.m=m;
		this.sm=mm;
		this.g=g;
	}
	@Override
	public void mousePressed(MouseEvent me) {
		int x = (int)Math.round(me.getX()/(double) m.getCell())-1;
		int y = (int)Math.round(me.getY()/(double) m.getCell())-2;
		
		if(gm.checkInput(y, x) == false) {
			return;
		}
		
		Word w = new Word(y,x,gm.getCun_GamePlayer());
		gm.inputWord(w);
		gm.nextPlayer(gm.getCun_GamePlayer());
		sm.repaint();
		if(gm.endGame(w)==true) {  // ����� ���� �޼��� ã��
			String ms;
			if(w.getColor()==1) {  
				ms="�������� �¸�!";
			}
			else if(w.getColor()==2) {
				ms="�鵹�� �¸�!";
			}
			else if(w.getColor()==3) {
				ms="û���� �¸�!";
			}
			else {
				ms="�����¸�!";
			}
			showWin(ms);
			gm.init();
		}
	}
	public void showWin(String msg) { // ����� ���� �޼����� ��Ÿ����
		JOptionPane.showMessageDialog(g, msg, "",JOptionPane.INFORMATION_MESSAGE);
	}
}