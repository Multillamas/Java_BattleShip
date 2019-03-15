package network;
/*
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;*/

public class Connection {

	/*private char myRol = ' '; // ( C:Client - S:Server )

	private Client client;

	private final int serverPort = 20608;
	private final int clientPort = 20609;

	public boolean connect(String ip, ServerListener listener) {

		if (myRol != ' ') {
			System.out.println("You must finish the previos connection...");
			return false;
		}

		// Here we are creating a new server in the client to allow the back connection
		// from
		// the main server
		new Server(clientPort, serverPort, listener).start();

		// Here we create the instance of the client and connect it
		this.client = new Client(ip, serverPort);

		// This instance is think to be the client
		myRol = 'C';

		return true;
	}

	public boolean waitConnection(ServerListener listener) {

		if (myRol != ' ') {
			System.out.println("You must finish the previos connection...");
			return false;
		}

		// This instance supposed to be going to be the Server
		myRol = 'S';

		// Start the server - Start data listening -> By a new thread
		new Server(serverPort, clientPort, listener).start();

		return true;
	}

	public void sendData(String val) {

		this.client.sendInformation(val);

	}

	public boolean closeConnection() {

		client.sendInformation("_END_CONN_");

		myRol = ' ';

		return true;
	}

	// *************************************************************************
	// Server class
	// *************************************************************************

	public class Server extends Thread {

		ServerListener listener;
		int portServer = 0;
		int portClient = 0;

		public Server(int portServerver, int portClientent, ServerListener listen) {
			this.listener = listen;
			this.portServer = portServerver;
			this.portClient = portClientent;
		}

		@Override
		public void run() {

			try {

				BufferedReader receiver;

				Socket conn = new ServerSocket(this.portServer).accept();

				System.out.println("Ready for the communication...");

				InputStreamReader isr = new InputStreamReader(conn.getInputStream());

				receiver = new BufferedReader(isr);

				boolean CnctAsClient = true;

				// Infinite loop - See if to break
				while (true) {

					// Validate if the back connection was already created
					// Dont create a new connection if it aready exists
					if (CnctAsClient && client == null) {

						// Get the client IP
						String ipClient = conn.getInetAddress().getHostAddress();

						// Create a back connection to the client with altern port
						client = new Client(ipClient, this.portClient);

						CnctAsClient = false; // Only once
					}

					// Get mesages from the client
					String input = receiver.readLine();

					// System.out.println("Recibe: " + input);

					// Throw the event to the listener
					listener.dataArrives(input);

					// System.out.println("Esto fue lo que vino: " + input);

					if (input == null || input.equals("_END_CONN_")) {
						break;
					}
				}

				receiver.close();
				isr.close();
				conn.close();

				try {
					client.closeConnection();
				} catch (Exception e) {
				}

				System.out.println("Connection was closed");

			} catch (IOException ex) {
				System.out.println("Connection failure: " + ex.toString());
			}

		}

	}

	// *************************************************************************
	// Client class
	// *************************************************************************

	public class Client {

		private DataOutputStream out;

		Socket client;

		public Client(String ip, int portServerver) {

			try {

				client = new Socket(ip, portServerver);

				out = new DataOutputStream(client.getOutputStream());

				// First message: information of the connection
				// out.writeBytes( "Primera impresion" + "\n" );

			} catch (IOException e) {
				System.out.println("Error connecting to server: " + e.toString());
			}

		}

		public void sendInformation(String val) {

			try {

				out.writeBytes(val + "\n");

			} catch (IOException ex) {
				System.out.println("Error sending information: " + ex.toString());
			}
		}

		public void closeConnection() {
			try {
				this.client.close();
			} catch (Exception e) {
			}
		}

	}*/

}

//*************************************************************************
//ServerListener interface. 
//*************************************************************************
interface ServerListener {

	void dataArrives(String msg);
}

