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
		if(gm.endGame(w)==true) {  // 우승한 돌의 메세지 찾기
			String ms;
			if(w.getColor()==1) {  
				ms="검은돌이 승리!";
			}
			else if(w.getColor()==2) {
				ms="백돌이 승리!";
			}
			else if(w.getColor()==3) {
				ms="청돌이 승리!";
			}
			else {
				ms="적돌승리!";
			}
			showWin(ms);
			gm.init();
		}
	}
	public void showWin(String msg) { // 우승한 돌의 메세지를 나타낸다
		JOptionPane.showMessageDialog(g, msg, "",JOptionPane.INFORMATION_MESSAGE);
	}
}