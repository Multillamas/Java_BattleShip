package views;

import java.util.ArrayList;
import javax.swing.JFrame;
import entities.Coordinates;
import entities.Ship;
import entities.Status;

@SuppressWarnings("serial")
public class Board extends JFrame {

	public Status placeShip(int[][] positions, int type, boolean vertical, int x, int y) {

		Status configure = new Status();

		int[][] validator = new int[positions.length][positions[0].length];

		// We copy the array to work on a validator
		for (int i = 0; i < validator.length; i++) {
			for (int j = 0; j < validator[0].length; j++) {
				validator[j][i] = positions[j][i];
			}
		}

		// We obtain the size of the ship
		int longShip = getSizeType(type);

		if (vertical) {

			for (int i = y; i < (y + longShip); i++) {

				// it validates if it's writing over an already created ship
				if (validator[x][i] != 0) {

					// If it's writing on a ship it sends an error
					configure.error = true;

					// Reestablishes the original board
					configure.board = positions;

					// Returns the original board without changes
					return configure;
				}

				// It sets the type of ship
				validator[x][i] = type;
			}

			// It establishes the new board with the ship on course
			configure.board = validator;

			// Returns the new arrangement with the validated ship and new board
			return configure;
		} 
			// Horizontal
		else {

			for (int i = x; i < (x + longShip); i++) {

				// Validates if it's writing on another ship
				if (validator[i][y] != 0) {

					// it establishes an error flag
					configure.error = true;

					// it establishes the original board
					configure.board = positions;

					// Returns the original ship without changes
					return configure;
				}

				// sets the type of ship on course
				validator[i][y] = type;
			}

			// sets the new board with the new ship
			configure.board = validator;

			// returns the array with the new ship
			return configure;
		}

	}

	private int getSizeType(int type) {

		// type 1 (Porte-avion) = 4 Positions
		// type 2 (Destroyer) = 3 Positions
		// type 3 (Sousmarine) = 3 Positions
		// type 4 (Patrouille ) = 2 Positions

		switch (type) {
		case 1:
			return 4;
		case 2:
			return 3;
		case 3:
			return 3;
		case 4:
			return 2;
		default:
			break;
		}
		return 0;
	}

	// It builds the random board for the computer
	public int[][] setComputerShips(int x, int y) {

		int[][] result = new int[x][y];
		int[][] validator;

		Ship ships[] = new Ship[4];

		ships[0] = new Ship(1); // Type 1 (Porte-avion) = 4 Positions
		ships[1] = new Ship(2); // Type 2 (Destroyer) = 3 Positions
		ships[2] = new Ship(3); // Type 3 (Sousmarine) = 3 Positions
		ships[3] = new Ship(4); // Type 4 (Patrouille ) = 2 Positions
		boolean continues = true;

		while (continues) {

			// Initializes all the positions
			validator = new int[x][y];
			result = new int[x][y];

			// For each ship we build new coordinates
			for (int i = 0; i < ships.length; i++) {
				ships[i] = buildShipComputer(x, y, ships[i]);
			}

			try {

				// to determine if the new combination was found
				boolean inconsistencies = false;

				// it loops on each ship
				for (int shipXs = 0; shipXs < ships.length; shipXs++) {

					Ship ship = ships[shipXs];

					// it loops on each ship
					for (int coor = 0; coor < ship.coordinates.length; coor++) {

						int posX = ship.coordinates[coor].getX();
						int posY = ship.coordinates[coor].getY();

						//
						validator[posX][posY]++;

						result[posX][posY] = ship.type;

						if (validator[posX][posY] > 1) {

							coor = ship.coordinates.length;
							shipXs = ships.length;

							// We have found inconsistencies
							inconsistencies = true;
						}
					}
				}

				// We didn't find inconsistencies and then we can continue
				if (inconsistencies == false) {
					continues = false;
				}

			} 
				// Generates a mistake if the positions are out of bounds
				// Tries a new combination in the while
			catch (Exception e) {
				 System.out.println("Error: " + e.toString());
			}
			
			
			
		}
		
		return result;
	}

	// it constructs randomly the coordinates of the ship that arrives as parameter
	private Ship buildShipComputer(int legthX, int legthY, Ship ship) {

		boolean vertical;

		vertical = (Math.random() > 0.5);

		int X = (int) (Math.random() * legthX);
		int Y = (int) (Math.random() * legthY);

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

	public Coordinates nextComputerTurn(int x, int y, ArrayList<Coordinates> computerHits) {

		Coordinates coor = new Coordinates();

		boolean repited;

		while (true) {

			repited = false;

			int valX = (int) (Math.random() * 10);
			int valY = (int) (Math.random() * 10);

			for (Coordinates prev_coord : computerHits) {

				if (valX == prev_coord.getX() && valY == prev_coord.getY()) {
					repited = true;
				}
			}

			if ((valX >= 0 && valX <= x && valY >= 0 && valY <= y) && !repited) {

				coor.setX(valX);
				coor.setY(valY);

				break;
			}

		}

		return coor;
	}
}

