package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class PCConnectionOld {
	private String address;
	private DataOutputStream out;
	private BufferedReader in;
	
	// private final String SHEO_ADDRESS = "00:16:53:0A:71:1B";
	
	/**
	 * Class constructor. Will establish connection to a given address (MAC).
	 * 
	 * @param address Address to NXT brick (MAC format).
	 * @throws NXTCommException If any communication error occurs between PC and NXT brick.
	 * @throws IOException If in-/output stream error occurs.
	 */
	public PCConnectionOld(String address) {
		this.address = address;
		System.out.println("Trying to establish connection...");
		System.out.println("Address:\t" + this.address);
		
		try {
			NXTComm ncom = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);		// Define communication
			
			NXTInfo ninf = new NXTInfo(NXTCommFactory.BLUETOOTH, "M8", this.address);	// What to connect to
			
			if(ncom.open(ninf)) {														// Open connection to defined brick
				System.out.println("Connected to " + this.address);						// Print info
				
				out = new DataOutputStream(ncom.getOutputStream());						// Open stream FROM the brick
				in = new BufferedReader(new InputStreamReader(ncom.getInputStream()));	// Open stream TO the brick
				
				String s = null;														// Instantiate a new string (will hold data FROM the brick)

				while((s = in.readLine()) != "END") {									// Keep reading FROM the brick 'till we receive "END"
					if(s.equals("GO"))	{
						System.out.println("Starting...");								// We're live and receiving data
						out.writeBytes("READY\n");										// ... and make brick aware of the situation (we're ready)
					}
					else {
						System.out.println(s);											// Print whatever we receive
						out.writeBytes(s);												// Write input back to brick
					}
				}
			}
		} catch (NXTCommException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				in.close();																// Closing ressources
				out.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		new PCConnectionOld("00:16:53:0a:71:1b");
	}
}