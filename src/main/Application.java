package main;

import java.io.IOException;
import javax.swing.SwingUtilities;

import views.Menu;
import sounds.Sound;

public class Application {

	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			
			@SuppressWarnings("unused")
			@Override
			public void run() {

				try {
					Menu battleshipBoard = new Menu();
					Sound sound =new  Sound();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
			}
		});

	}

}
