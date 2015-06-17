package comm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import utils.PrepareStatement;
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
	
	public PCConn() {
		new PCConn(null);
	}
	
	/**
	 * Class constructor. Will establish connection to a given address (MAC).<br><br>
	 * 
	 * Note: if no address is given the PC will try and establish a connection to the first found/nearest NXT brick.
	 * 
	 * @param address Address to NXT brick (MAC-address format).
	 * @throws NXTCommException If any communication error occurs between PC and NXT brick.
	 * @throws IOException If in-/output stream error occurs.
	 */
	public PCConn(String address) {
		this.address = address;
	}

	
	/**
	 * Send a message to the NXT brick.<br>
	 * The brick will return a response. This response is the method's return value.<br><br>
	 * 
	 * A message (command) consists of:<br>
	 * <ul>
	 * <li>1. a command</li>
	 * <li>2. a timeframe (milliseconds)</li>
	 * </ul>
	 * 
	 * The two elements are separated with two semicolons (;;).<br><br>
	 * 
	 * E.g.:<br>
	 * "C_FW;;450"	= COMMAND FORWARD for 450 MILLISECONDS.<br>
	 * "C_BW"		= COMMAND BACKWARDS for INFINITE (not possible, see bellow)<br><br>
	 * 
	 * If no timeframe is given "0" will automatically be applied.<br>
	 * The NXT brick will keep doing the command until a "DANGER CLOSE" event occurs (via UltraSonic Sensor).<br>
	 * Since the UltraSonic sensor only is mounted on the front, any other command than "C_FW" will be ignored,<br>
	 * unless a timeframe has been given (for now - if more UltraSonic sensors are mounted update will follow.)
	 * 
	 * 
	 * @param msgs The message to be sent. Can only contain commands in the format ||command;;timeframe.
	 * @return Response from NXT brick.
	 * @throws Exception 
	 */
	public String sendMsg(String[] msgs) throws Exception {
		conn = new NXTConnector();
		
		// Connect to an NXT brick via bluetooth
		
		boolean connected;
		
		if(this.address == null) connected = conn.connectTo("btspp://");
		else connected = conn.connectTo("btspp://");
		
		// If connection can't be established exit and print error.
		if (!connected) {
			System.err.println("Failed to connect to NXT, exiting.");
			System.exit(1);
		}
		
		// Open streams for communication
		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		out = new DataOutputStream(conn.getOutputStream());
		
		try {	
			// Write message to NXT brick
			this.out.writeBytes(PrepareStatement.prepStr(msgs) + "\n");
			this.out.flush();
			
			// Wait for response
			while((response = in.readLine()).equals(null)) System.out.println("Waiting for response...");
			
			// Handle response if any error was encountered
			if(!response.equals("COMPLETE")) {
				int end = response.indexOf("<");
				String error = response.substring(0, end);
				String emsg = response.substring(end+1, (response.length()-1));
				
				switch(error) {
					case "ERROR":
						System.out.println("Error occured on brick.");
						System.out.println("Error message: " + emsg);
					case "DNGCLOSE":
						System.out.println("DANGER CLOSE! Brick has been stopped.");
						System.out.println("Remaining commands: " + PrepareStatement.prettyStr(emsg));
						break;
					case "INCOMPLETE":
						System.out.println("Cannot complete task! Brick has been stopped.");
						System.out.println("Remaining commands: " + PrepareStatement.prettyStr(emsg));
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.writeBytes("END\n");
				out.flush();
				
				this.out.close();
				this.in.close();
				this.conn.close();
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// Minor fix
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}