package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

/**
 * Class constructor. Will establish connection to a given address (MAC).
 * 
 * @param address Address to NXT brick (MAC format).
 * @throws NXTCommException If any communication error occurs between PC and NXT brick.
 * @throws IOException If in-/output stream error occurs.
 */

public class PCConn {
	private static BufferedReader in;
	private static DataOutputStream out;
	
	public static void main(String[] args) {
		NXTConnector conn = new NXTConnector();
	
		conn.addLogListener(new NXTCommLogListener() {

			public void logEvent(String message) {
				System.out.println("BTSend Log.listener: "+message);
				
			}

			public void logEvent(Throwable throwable) {
				System.out.println("BTSend Log.listener - stack trace: ");
				 throwable.printStackTrace();
				
			}
			
		});
		
		// Connect to any NXT over Bluetooth
		boolean connected = conn.connectTo("btspp://");
		
		if (!connected) {
			System.err.println("Failed to connect to any NXT");
			System.exit(1);
		}
		
		try {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			out = new DataOutputStream(conn.getOutputStream());
			
			String s = null;
			int n = 0;

			do {
				System.out.println("Sending: " + n);
				out.writeBytes(String.valueOf(n) + "\n");
				out.flush();
				
				while((s = in.readLine()).equals(null)) {
					System.out.println("Waiting....");
				}
				
				System.out.println("Received: " + s);
				
				n++;
			} while(n < 1000);
			
			out.writeBytes("END\n");
			out.flush();
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				out.close();
				in.close();
				conn.close();
				
				System.out.println("Done.");
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
