package test;

import connection.PCConn;

public class BasicMovementTest {
	public static void main(String[] args) {
		PCConn conn = new PCConn();
		
		String[] test1 = {"C_FW;;2500","C_HL;;0","C_FW;;5000", "C_HR;;0", "C_BW;;1000"};
		String[] test2 = {"C_BW;;2500","C_HR;;0","C_FW;;5000", "C_HR;;0", "C_FW;;1000"};
		String[] square = {"C_FW 2500", "C_HL 0", "C_FW 2500", "C_HL 0", "C_FW 2500", "C_HL 0", "C_FW 2500", "C_HL 0"};
		
		System.out.println("Test1:");
		System.out.println("Response: " + conn.sendMsg(test1));
		
		System.out.println("Test2:");
		System.out.println("Response: " + conn.sendMsg(test2));
		
		System.out.println("Square:");
		System.out.println("Response: " + conn.sendMsg(square));
		
		System.out.println("Test done");
		
		System.exit(1);
	}
}
