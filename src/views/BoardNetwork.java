package views;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entities.Coordinates;
import entities.Ship;
import entities.Status;

@SuppressWarnings("serial")
public class BoardNetwork extends JFrame{
	
		public String nomJoueur;
		// Controls
		static JLabel lblInfo = new JLabel();
		static JLabel lblInfoRemote = new JLabel();

		JButton[][] localBoard; // My board
		JButton[][] remoteBoard; // Remote board

		JLabel horizontalLbl;
		JLabel verticalLbl;

		JButton btnHorType1;
		JButton btnHorType2;
		JButton btnHorType3;
		JButton btnHorType4;
		
		JButton btnVerType1;
		JButton btnVerType2;
		JButton btnVerType3;
		JButton btnVerType4;

		JButton reset;

		private ArrayList<Coordinates> computerHits = new ArrayList<>();

		// To define what type of ship was selected and its direction, vertical, or
		// horizontal
		boolean posVertical = false;
		int posTypeShip = 0;

		private int nbLines;
		private int nbCol;

		private int playerBoard[][];
		private int computerBoard[][];

		// Constructor
		public BoardNetwork(int nbLines, int nbCol) {
			super();
			this.nbLines = nbLines;
			this.nbCol = nbCol;

			// Builds the array with the number of columns and rows
			playerBoard = new int[nbCol][nbLines];
			
			nomJoueur = JOptionPane.showInputDialog(null, "Quelle est votre nom?");
			build_UI();
			
			int reponse = 0;
			if (reponse == JOptionPane.CANCEL_OPTION) {
				BoardNetwork battleshipBoard = new BoardNetwork(10, 10);
				battleshipBoard.setVisible(true);
				dispose();
			}
			
		}

		/*************************************************************************/
		public void build_UI() {

			Image image = null;

			try {
				image = ImageIO.read(new File("mediaSources/arrierePlan2.jpg"));
			} catch (IOException e) {

				e.printStackTrace();
			}

			JPanel panelGame = new Background(image);

			// Information label

			JLabel labelInfoRemote = new JLabel(nomJoueur);
			//labelInfoRemote.setText(nomJoueur);
			labelInfoRemote.setBounds(170,20, 100, 100);

			JLabel labelInfo = new JLabel("Ordinateur");
			labelInfo.setBounds(570, 20, 100, 100);

			// Game Board
			JPanel panelPlayer = new JPanel();
			panelPlayer = placeLocalBoard(nbLines, nbCol);
			// panelPlayer.add(placeLocalBoard(nbling, nbcol)); // Local board
			panelPlayer.setBounds(50, 100, 350, 350);

			JPanel panelOppenent = new JPanel();
			panelOppenent = placeRemoteBoard(nbLines, nbCol);
			// panelPlayer.add(placeRemoteBoard(nbling, nbcol)); // Remote board
			panelOppenent.setBounds(480, 100, 350, 350);

			panelGame.add(labelInfoRemote);
			panelGame.add(labelInfo);

			panelGame.add(panelPlayer);
			panelGame.add(panelOppenent);

			JPanel panelConfigurationShips = placeConfigurationShips();
			panelConfigurationShips.setBounds(50, 500, 350, 150);

			JButton resetBtn = resetButton();
			resetBtn.setBounds(600, 630, 80, 50);

			// ajouter un flesh pour retourner a la page pracedente
			JButton bouttonFlech = ajouterBoutton((new ImageIcon("mediaSources/arrow1.png")));
			bouttonFlech.setBounds(700, 600, 150, 100);

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

			panelGame.add(panelConfigurationShips);
			panelGame.add(resetBtn);
			panelGame.add(bouttonFlech);
			panelGame.setLayout(null);
			this.setLayout(null);
			this.setContentPane(panelGame);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// this.setVisible(true);
			this.setSize(900, 1000);
			this.setResizable(false);
			// this.getContentPane().add(panelGame);

		}

		/****************************************************************************/

		/******************************************************************************/

		// Method to place my game

		public JPanel placeLocalBoard(int rows, int columns) {

			JPanel grid = new JPanel(new GridLayout(rows, columns));

			localBoard = new JButton[rows][columns];

			for (int row = 0; row < rows; row++) {

				for (int col = 0; col < columns; col++) {

					final int pos_x = col;
					final int pos_y = row;

					localBoard[col][row] = new JButton();

					// creates the listener for each button with indexes
					localBoard[col][row].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							clicLocalBoard(pos_x, pos_y);
						}
					});

					// Adds a button to the view
					grid.add(localBoard[col][row]);
					localBoard[col][row].setBackground(new Color(132, 17, 12));
				}

			}

			return grid;
		}

		/*******************************************************************************/

		private void clicLocalBoard(int x, int y) {

			setPlayerShip(x, y);

		}

		/*******************************************************************************/

		// Method to place the remote board

		public JPanel placeRemoteBoard(int rows, int columns) {

			JPanel grid = new JPanel(new GridLayout(rows, columns));
			remoteBoard = new JButton[rows][columns];

			for (int row = 0; row < rows; row++) {

				for (int col = 0; col < columns; col++) {

					final int pos_x = col;
					final int pos_y = row;

					remoteBoard[col][row] = new JButton();

					// It creates the listener for each button
					remoteBoard[col][row].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							clicTableroRemoto(pos_x, pos_y);
						}
					});

					remoteBoard[col][row].setEnabled(false);

					grid.add(remoteBoard[col][row]);
				}
			}

			return grid;
		}

		/*******************************************************************************/

		private void clicTableroRemoto(int x, int y) {

			if (computerBoard[x][y] <= 0) { // debe ser <= 0
				computerBoard[x][y] = -1; // esto fue un disparo que no acerto
			} 
			else {
				computerBoard[x][y] = computerBoard[x][y] + 10; // Esto fue un disparo que si acerto
			}

			updateComputerBoard();

			boolean iWon = true;

			// Validate if i won
			for (int i = 0; i < computerBoard.length; i++) {
				for (int j = 0; j < computerBoard[0].length; j++) {
					if (computerBoard[j][i] > 0 && computerBoard[j][i] < 10) {
						iWon = false;
					}
				}
			}

			if (iWon) {
				JOptionPane.showMessageDialog(null, "Vous avez gagnée!!!");
				finishGame();
			} else {

				// Computer turn

				Coordinates coor = new Board().nextComputerTurn(nbCol, nbLines, computerHits);

				computerHits.add(coor);

				int valX = coor.getX();
				int valY = coor.getY();

				if (playerBoard[valX][valY] == 0) {
					playerBoard[valX][valY] = -1;
				} else {
					playerBoard[valX][valY] = playerBoard[valX][valY] + 10;
				}

				updatePlayerBoard();

				boolean compWon = true;

				// Validate if the computer won
				for (int i = 0; i < computerBoard.length; i++) {
					for (int j = 0; j < computerBoard[0].length; j++) {
						if (playerBoard[j][i] > 0 && playerBoard[j][i] < 10) {
							compWon = false;
						}
					}
				}

				if (compWon) {

					JOptionPane.showMessageDialog(null, "Vous avez perdu !!!");
					finishGame();

				}

			}
		}

		/******************************************************************************************************/

		private void finishGame() {

			for (int i = 0; i < localBoard.length; i++) {
				for (int j = 0; j < localBoard[0].length; j++) {
					localBoard[j][i].setEnabled(false);
					remoteBoard[j][i].setEnabled(false);
				}
			}

		}

		/******************************************************************************************************/

		// Method to put the buttons for different ships

		public JPanel placeConfigurationShips() {

			// Panel to set the buttons
			JPanel pnlButtons = new JPanel(new GridLayout(3, 4)); // rows, cols

			// Type 1 (Porte-avion) = 4 Positions
			// Type 2 (Destroyer) = 3 Positions
			// Type 3 (Sousmarine) = 3 Positions
			// Type 4 (Pratouille ) = 2 Positionss

			// horizontalLbl = new JLabel("Horizontal");
			// verticalLbl = new JLabel("Vertical");

			JLabel labelPortAvion = new JLabel("Porte-avion");
			JLabel labelDestroyer = new JLabel("Destroyer");
			JLabel labelSousmarine = new JLabel("Sousmarine");
			JLabel labelPratouille = new JLabel("Pratouille");

			btnHorType1 = ajouterBoutton(new ImageIcon("mediaSources/PorteAvionH.png"));
			btnHorType2 = ajouterBoutton(new ImageIcon("mediaSources/DestroyerH.png"));
			btnHorType3 = ajouterBoutton(new ImageIcon("mediaSources/SousmarineH.png"));
			btnHorType4 = ajouterBoutton(new ImageIcon("mediaSources/PratouilleH.png"));

			btnVerType1 = ajouterBoutton(new ImageIcon("mediaSources/PorteAvionV.png"));
			btnVerType2 = ajouterBoutton(new ImageIcon("mediaSources/DestroyerV.png"));
			btnVerType3 = ajouterBoutton(new ImageIcon("mediaSources/SousmarineV.png"));
			btnVerType4 = ajouterBoutton(new ImageIcon("mediaSources/PratouilleV.png"));

			// Listener Horizontal
			btnHorType1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					selectsShip(false, 1);
				}
			});
			btnHorType2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					selectsShip(false, 2);
				}
			});
			btnHorType3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					selectsShip(false, 3);
				}
			});
			btnHorType4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					selectsShip(false, 4);
				}
			});

			// Listener Vertical
			btnVerType1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					selectsShip(true, 1);
				}
			});
			btnVerType2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					selectsShip(true, 2);
				}
			});
			btnVerType3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					selectsShip(true, 3);
				}
			});
			btnVerType4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					selectsShip(true, 4);
				}
			});

			// First row controls

			pnlButtons.add(labelPortAvion);
			pnlButtons.add(labelDestroyer);
			pnlButtons.add(labelSousmarine);
			pnlButtons.add(labelPratouille);

			// pnlButtons.add(horizontalLbl);

			pnlButtons.add(btnHorType1);
			pnlButtons.add(btnHorType2);
			pnlButtons.add(btnHorType3);
			pnlButtons.add(btnHorType4);

			// second row controls

			// pnlButtons.add(verticalLbl);

			pnlButtons.add(btnVerType1);
			pnlButtons.add(btnVerType2);
			pnlButtons.add(btnVerType3);
			pnlButtons.add(btnVerType4);

			pnlButtons.setBackground(new Color(0, true));

			return pnlButtons;
		}

		/******************************************************************************************************/

		private void selectsShip(boolean vertical, int tipoBarco) {
			this.posVertical = vertical;
			this.posTypeShip = tipoBarco;
		}

		private void setPlayerShip(int x, int y) {

			int tab[][] = playerBoard.clone();

			Status con;

			try {

				// Tries to set the new boat
				con = new Board().placeShip(tab, posTypeShip, posVertical, x, y);
			} catch (Exception e) {

				// Catch the error when there's not enough space to set the ship

				JOptionPane.showMessageDialog(null, "Espace Insuffisant pour placer le bateau!!");

				return;
			}

			// if there were no msitakes
			if (!con.error) {

				playerBoard = con.board;

				// update the board with the new ship
				updatePlayerBoard();

				// unables all the buttons so we cannot add another ship at the same moment
				switch (posTypeShip) {
				case 1:
					btnHorType1.setEnabled(false);
					btnVerType1.setEnabled(false);
					break;
				case 2:
					btnHorType2.setEnabled(false);
					btnVerType2.setEnabled(false);
					break;
				case 3:
					btnHorType3.setEnabled(false);
					btnVerType3.setEnabled(false);
					break;
				case 4:
					btnHorType4.setEnabled(false);
					btnVerType4.setEnabled(false);
					break;
				default:
					break;
				}

				boolean est1 = btnHorType1.isEnabled();
				boolean est2 = btnHorType2.isEnabled();
				boolean est3 = btnHorType3.isEnabled();
				boolean est4 = btnHorType4.isEnabled();

				posVertical = false;
				posTypeShip = 0;

				// it validates if the ships are ready to start playing
				if (!est1 && !est2 && !est3 && !est4) {

					startGame();

				}

			} else {
				JOptionPane.showMessageDialog(null, "Le bateau existe deja");
				return;
			}

		}

		/******************************************************************************************************/

		private void startGame() {

			// Clear the computer shots
			computerHits.clear();

			// The buttons for the local board are desactivated (MyBoard)
			// the buttons of the remote board are activated (OpponentsBoard)
			for (int i = 0; i < localBoard.length; i++) {
				for (int j = 0; j < localBoard[0].length; j++) {
					localBoard[j][i].setEnabled(false);
					remoteBoard[j][i].setEnabled(true);
				}
			}

			computerBoard = new Board().setComputerShips(this.nbCol, this.nbLines);

			//
			updateComputerBoard();

		}

		/******************************************************************************************************/

		private void updatePlayerBoard() {

			for (int pos_y = 0; pos_y < localBoard.length; pos_y++) {
				for (int pos_x = 0; pos_x < localBoard[0].length; pos_x++) {

					switch (playerBoard[pos_x][pos_y]) {
					case 1:
					case 2:
					case 3:
					case 4:
						localBoard[pos_x][pos_y].setBackground(new Color(169,160,65));

						break;
					case -1:
						localBoard[pos_x][pos_y].setBackground(new Color(192,192,192));

						break;
					case 11:
					case 12:
					case 13:
					case 14:
						localBoard[pos_x][pos_y].setBackground(Color.red);

						break;
					case 0:
						localBoard[pos_x][pos_y].setBackground(reset.getBackground());

						break;
					default:
						break;
					}

				}
			}
		}

		/******************************************************************************************************/

		private JButton resetButton() {

			reset = ajouterBoutton(new ImageIcon("mediaSources/reset.png"));

			JPanel pnlButtons = new JPanel(new GridLayout(1, 2)); // rows, cols

			pnlButtons.add(reset);

			reset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					resetGame();
				}
			});

			return reset;
		}

		/******************************************************************************************************/

		private void resetGame() {

			playerBoard = new int[this.nbCol][this.nbLines];
			computerBoard = new int[this.nbCol][this.nbLines];

			updatePlayerBoard();
			updateComputerBoard();

			for (int pos_y = 0; pos_y < computerBoard.length; pos_y++) {
				for (int pos_x = 0; pos_x < computerBoard[0].length; pos_x++) {
					localBoard[pos_x][pos_y].setEnabled(true);
					remoteBoard[pos_x][pos_y].setEnabled(false);
					localBoard[pos_x][pos_y].setBackground(new Color(132, 17, 12));
					 
				}
			}

			btnHorType1.setEnabled(true);
			btnHorType2.setEnabled(true);
			btnHorType3.setEnabled(true);
			btnHorType4.setEnabled(true);

			btnVerType1.setEnabled(true);
			btnVerType2.setEnabled(true);
			btnVerType3.setEnabled(true);
			btnVerType4.setEnabled(true);

			posVertical = false;
			posTypeShip = 0;

			computerHits = new ArrayList<>();
			validate();
			repaint();
		}

		/******************************************************************************************************/

		private void updateComputerBoard() {

			for (int pos_y = 0; pos_y < computerBoard.length; pos_y++) {
				for (int pos_x = 0; pos_x < computerBoard[0].length; pos_x++) {

					switch (computerBoard[pos_x][pos_y]) {
					case 1:
					case 2:
					case 3:
					case 4:
						remoteBoard[pos_x][pos_y].setBackground(reset.getBackground());

						break;
					case -1:
						remoteBoard[pos_x][pos_y].setBackground(Color.GREEN);

						break;
					case 11:
					case 12:
					case 13:
					case 14:
						remoteBoard[pos_x][pos_y].setBackground(Color.red);

						break;
					case 0:
						remoteBoard[pos_x][pos_y].setBackground(reset.getBackground());

						break;
					default:
						break;
					}
				}
			}
		}

		/******************************************************************************************************/

		public int[][] buildBoardComputer(int x, int y) {

			int[][] result = new int[x][y];
			int[][] validator;

			Ship ships[] = new Ship[4];

			ships[0] = new Ship(1); // Tipo 1 (Portavion) = 4 Posiciones
			ships[1] = new Ship(2); // Tipo 2 (Destroyer) = 3 Posiciones
			ships[2] = new Ship(3); // Tipo 3 (Submarino) = 3 Posiciones
			ships[3] = new Ship(4); // Tipo 4 (Patrulla ) = 2 Posiciones
			boolean continua = true;

			while (continua) {

				// Initializes all the positions
				validator = new int[x][y];
				result = new int[x][y];

				// For each ship we build new coordinates
				for (int i = 0; i < ships.length; i++) {
					ships[i] = construirShipComputador(x, y, ships[i]);
				}

				try {

					// to determine if the new combination was found
					boolean inconsistencies = false;

					for (int shipXs = 0; shipXs < ships.length; shipXs++) {

						Ship shipr = ships[shipXs];

						for (int coor = 0; coor < shipr.coordinates.length; coor++) {

							int posX = shipr.coordinates[coor].getX();
							int posY = shipr.coordinates[coor].getY();

							validator[posX][posY]++;

							result[posX][posY] = shipr.type;

							// if the position is already occupy it annulates the generated board
							if (validator[posX][posY] > 1) {

								// it initializes the variables at the loop for
								coor = shipr.coordinates.length;
								shipXs = ships.length;

								// there were inconsistencies found
								inconsistencies = true;
							}
						}
					}

					// There were no inconsistencies and the search can end
					if (inconsistencies == false) {
						continua = false;
					}

				} //
					// It generates an error if the positions are out of Bounds
					// tries a new combination in the while loop
				catch (Exception e) {
					// System.out.println("Error: " + e.toString());
				}
			}

			return result;
		}

		/******************************************************************************************************/

		private Ship construirShipComputador(int length, int lengthY, Ship ship) {

			boolean vertical;

			vertical = (Math.random() > 0.5);

			int X = (int) (Math.random() * length);
			int Y = (int) (Math.random() * lengthY);

			ship.coordinates[0] = new Coordinates();

			ship.coordinates[0].setX(X);
			ship.coordinates[0].setY(Y);

			for (int i = 1; i < ship.coordinates.length; i++) {

				if (vertical) {
					Y++;
				} else {
					X++;
				}

				ship.coordinates[i] = new Coordinates();

				ship.coordinates[i].setX(X);
				ship.coordinates[i].setY(Y);

			}

			return ship;
		}

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
