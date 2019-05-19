package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame obj=new JFrame();
		GamePlay gameplay=new GamePlay();
		obj.setBounds(10,10,700,620);
		obj.setTitle("Breakout Ball");
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
		obj.setVisible(true);
		
	}

}
