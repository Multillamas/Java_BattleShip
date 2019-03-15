package entities;

public class Ship {
	public int type = 0;
	 public Coordinates[] coordinates;
	private int size;

	public Ship(int tipo_in) {

		this.type = tipo_in;

		switch (type) {
		case 1:
			this.coordinates = new Coordinates[4];
			break;
		case 2:
			this.coordinates = new Coordinates[3];
			break;
		case 3:
			this.coordinates = new Coordinates[3];
			break;
		case 4:
			this.coordinates = new Coordinates[2];
			break;
		default:
			break;
		}

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
