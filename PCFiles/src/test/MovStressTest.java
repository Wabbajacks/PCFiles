package test;

import comm.PCConn;

public class MovStressTest {
	public MovStressTest() {
		PCConn conn = null;
		
		try {
			conn = new PCConn("00165304789F");
			
			Thread.sleep(1);
			
			System.out.println("Starting test...");
			
			conn.sendCommand("fw");
			
			Thread.sleep(400);
			
			conn.sendCommand("br");
			
			Thread.sleep(200);
			
			conn.sendCommand("bw");
			
			Thread.sleep(300);
			
			conn.sendCommand("END");
			
			System.out.println("Trying to close NXT connection...");
			
			if(conn.sendCommand("END").equals("END"))
				System.out.println("NXT connection closed...");
			else
				System.out.println("ERROR! NXT is NOT aware of closed link!");
			
			System.out.println("Closing resources...");
			
			conn.closeConn();
			
			System.out.println("Resources closed.");
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new MovStressTest();
	}
}
