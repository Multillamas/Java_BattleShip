package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Instructions extends JFrame{
	JTextArea instructionLabel;
	
	public Instructions() {
			super();
			/***********************************/

			// set arriere plan
			instructionLabel = new JTextArea();
			Font fonte = new Font("Chiller", 0, 30);
			Image image = null;

			try {
				image = ImageIO.read(new File("mediaSources/arrierePlan.jpg"));
			} catch (IOException e) {

				e.printStackTrace();
			}
			JPanel panel = new Background(image);

			/**************************************/
			// set le titre de le page
			JLabel labelInstruction = ajouterLabel(new ImageIcon("mediaSources/instructions.png"));
			labelInstruction.setBounds(0, 0, 500, 200);

			/***********************************/
			// set le text d'instruction
			instructionLabel.setText("Le  joueur doit placer des « navires » sur une grille"
					+ " \ntenue secrète et tenter de « toucher » les navires adverses."
					+ " \nLe gagnant est celui qui parvient à couler"
					+ " \ntous les navires de l'adversaire avant que tous les siens ne le soient."
					+ "\nOn dit qu'un navire est coulé si chacune de ses cases"
					+ " \na été touchées par un coup de l'adversaire.");

			instructionLabel.setBackground(new Color(0, true));
			instructionLabel.setBounds(20, 150, 600, 300);
			instructionLabel.setFont(fonte);

			/**************************************/
			// ajouter un panel avec des images des bateaux
			JPanel panelImage = new JPanel(new GridLayout(3, 4));
			panelImage.setBounds(100, 400, 350, 150);
			panelImage.setBackground(new Color(0, true));

			panelImage.add(new JLabel("Porte avion"));
			panelImage.add(new JLabel("Destroyer"));
			panelImage.add(new JLabel("Sousmarin"));
			panelImage.add(new JLabel("Pratouille"));
			
			panelImage.add(ajouterLabel(new ImageIcon("mediaSources/PorteAvionH.png")));
			panelImage.add(ajouterLabel(new ImageIcon("mediaSources/DestroyerH.png")));
			panelImage.add(ajouterLabel(new ImageIcon("mediaSources/SousmarineH.png")));
			panelImage.add(ajouterLabel(new ImageIcon("mediaSources/PratouilleH.png")));
			
			panelImage.add(new JLabel("4 cases"));
			panelImage.add(new JLabel("3 cases"));
			panelImage.add(new JLabel("3 cases"));
			panelImage.add(new JLabel("2 cases"));
			
			/****************************************/
			// ajouter un flesh pour retourner a la page pracedente
			JButton bouttonFlech = ajouterBoutton((new ImageIcon("mediaSources/arrow1.png")));
			bouttonFlech.setBounds(50, 600, 150, 100);

			bouttonFlech.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					Menu menu;
					try {
						menu = new Menu();
						dispose();
						// menu.setVisible(true);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});
			/**********************************************/
			// Add all the content to panel
			panel.add(instructionLabel);

			panel.add(labelInstruction);
			panel.add(panelImage);
			panel.add(bouttonFlech);

			setContentPane(panel);
			panel.setLayout(null);

			setVisible(true);
			setSize(900, 1000);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);

		}

		// Method to add an image button
		public JButton ajouterBoutton(ImageIcon img) {
			JButton boutton = new JButton();
			boutton.setIcon(img);
			boutton.setOpaque(false);
			boutton.setBackground(new Color(0, true));
			boutton.setBorderPainted(false);
			boutton.setContentAreaFilled(false);
			boutton.setFocusPainted(false);

			return boutton;
		}

		// Method to add a label with an image

		public JLabel ajouterLabel(ImageIcon img) {

			JLabel label = new JLabel();
			label.setIcon(img);
			label.setOpaque(false);
			label.setBackground(new Color(0, true));
			
			return label;
		}
}
