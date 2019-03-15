package views;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Menu extends JFrame {

	public Menu() throws IOException {

		// Ajouter une image au fond
		Image image = null;

		try {
			image = ImageIO.read(new File("mediaSources/arrierePlan.jpg"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		JPanel panel = new Background(image);

		JLabel battleshipLabel = new JLabel();
		battleshipLabel.setIcon(new ImageIcon("mediaSources/battleship1.png"));
		battleshipLabel.setOpaque(false);
		battleshipLabel.setBackground(new Color(0, true));
		battleshipLabel.setBounds(20, 20, 800, 250);

		// Ajouter le boutton jouer
		JButton lancerPartie = ajouterBoutton(new ImageIcon("mediaSources/jouerBtn.png"));
		lancerPartie.setBounds(100, 300, 350, 100);
		lancerPartie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BoardNetwork battleshipBoard = new BoardNetwork(10, 10);
				battleshipBoard.setVisible(true);
				dispose();
			}
		});

		// Ajouter boutton instruction
		JButton instruction = ajouterBoutton(new ImageIcon("mediaSources/instructionsBtn.png"));
		instruction.setBounds(100, 450, 350, 100);
		instruction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Instructions instruction = new Instructions();
				instruction.setVisible(true);
				dispose();

			}
		});

		// Ajouter boutton quitter
		JButton quitter = ajouterBoutton(new ImageIcon("mediaSources/quitterBtn.png"));
		quitter.setBounds(100, 600, 350, 100);
		quitter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int reponse = JOptionPane.showConfirmDialog(null, "Voulez vous vraimment quitter", "Attention",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

				if (reponse == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		panel.add(battleshipLabel);
		panel.add(lancerPartie);
		panel.add(instruction);
		panel.add(quitter);

		setContentPane(panel);
		panel.setLayout(null);
		// setLocationRelativeTo(null);
		setVisible(true);
		setSize(900, 1000);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	// Methode pour ajouter un boutton
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

}

