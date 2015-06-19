package comm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.pc.comm.NXTConnector;

/**
 * Controls the connection between the PC and the NXT brick.
 * 
 * @autor Kristin Hansen
 */

public class PCConn {
	private BufferedReader in;
	private DataOutputStream out;
	private NXTConnector conn;
	private String address;
	private String response = null;
	private boolean running = false;
	
	/**
	 * Class constructor. Will establish connection to a given address (MAC).<br><br>
	 * 
	 * Note: if no address is given the PC will try and establish a connection to the first found/nearest NXT brick.<br><br>
	 * 
	 * @throws NXTCommException If any communication error occurs between PC and NXT brick.
	 * @throws IOException If in-/output stream error occurs.
	 */
	public PCConn() throws Exception {
		new PCConn(null);
	}
	
	/**
	 * Class constructor. Will establish connection to a given address (MAC).<br><br>
	 * 
	 * @param address Address to NXT brick (MAC-address format).
	 * @throws Exception If any of the I/O streams fail to open.
	 * @throws NXTCommException If any communication error occurs between PC and NXT brick.
	 * @throws IOException If I/O stream error occurs.
	 */
	public PCConn(String address) throws Exception {
		this.address = address;
		
		conn = new NXTConnector();
		
		// Connect to an NXT brick via bluetooth
		boolean connected;
		
		if(this.address == null) connected = conn.connectTo("btspp://");
		else connected = conn.connectTo("btspp://" + address);
		
		// If connection can't be established exit and print error.
		if (!connected) {
			System.err.println("Failed to connect to NXT, exiting.");
			System.exit(1);
		}
		
		// Open streams for communication
		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		out = new DataOutputStream(conn.getOutputStream());
		
		if(in == null) throw new Exception("Input error. Exiting...");
		if(out == null) throw new Exception("Output error. Exiting...");
	}
	
	/**
	 * 
	 * @param cmd
	 * @return
	 */
	public String sendCommand(String cmd) {
		try {
			out.writeBytes(cmd + "\n");
			out.flush();
			
			while((response = in.readLine()).equals(null)) System.out.println("Waiting for response...");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return response;
	}
	
	/**
	 * Closes current active connection to the NXT.
	 */
	public void closeConn() {
		try {
			out.writeBytes("END\n");
			out.flush();
			
			out.close();
			in.close();
			conn.close();
			
			running  = false;
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/* GETTERS/SETTERS */
	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}

	public NXTConnector getConn() {
		return conn;
	}

	public void setConn(NXTConnector conn) {
		this.conn = conn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
