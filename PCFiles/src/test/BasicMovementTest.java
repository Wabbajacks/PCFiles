package test;

import communication.PCConn;

public class BasicMovementTest {
	public static void main(String[] args) {
		PCConn conn = new PCConn();
		
		String[] test1 = {"C_FW;;6000","C_HL;;0","C_FW;;5000", "C_HR;;0", "C_BW;;1000"};
		String[] test2 = {"C_BW;;2500","C_HR;;0","C_FW;;5000", "C_HR;;0", "C_FW;;1000"};
		String[] square = {"C_FW;;100", "C_FW;;200", "C_FW;;5000", "C_HL;;0", "C_FW;;2500", "C_HL;;0", "C_FW;;2500", "C_FW;;100", "C_HL;;0", "C_BW;;234", "C_HL;;0", "C_FW;;2500", "C_HL;;0"};
		
//		String[] test1 = {"C_FW;;200", "C_BW;;200", "C_FW;;200", "C_BW;;200", "C_FW;;200", "C_BW;;200", "C_FW;;200", "C_BW;;200", };
//		String[] test2 = {"C_BW;;100", "C_BW;;100", "C_BW;;100", "C_BW;;100", "C_BW;;100", "C_BW;;100"};
//		String[] square = {"C_HL;;0", "C_HL;;0", "C_HL;;0", "C_HL;;0", "C_HL;;0", "C_HL;;0", "C_HL;;0"};
		
		System.out.println("Test1:");
		System.out.println("Response: " + conn.sendMsg(test1));
		
		System.out.println("Test2:");
		System.out.println("Response: " + conn.sendMsg(test2));
		
		System.out.println("Square:");
		System.out.println("Response: " + conn.sendMsg(square));
		
//		String a = "C_FW;;500";
		
//		String[] test01 = {a};
//		String[] test02 = {a,a};
//		String[] test03 = {a,a,a};
//		String[] test04 = {a,a,a,a};
//		String[] test05 = {a,a,a,a,a};
//		String[] test06 = {a,a,a,a,a,a};
//		String[] test07 = {a,a,a,a,a,a,a};
//		String[] test08 = {a,a,a,a,a,a,a,a};
//		String[] test09 = {a,a,a,a,a,a,a,a,a};
//		String[] test10 = {a,a,a,a,a,a,a,a,a,a};
//		String[] test11 = {a,a,a,a,a,a,a,a,a,a,a};
//		String[] test12 = {a,a,a,a,a,a,a,a,a,a,a,a};
//		String[] test13 = {a,a,a,a,a,a,a,a,a,a,a,a,a};
//		String[] test14 = {a,a,a,a,a,a,a,a,a,a,a,a,a,a};
//		String[] test15 = {a,a,a,a,a,a,a,a,a,a,a,a,a,a,a};
//		String[] test16 = {a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a};
		
		System.out.println("Running...");
//		System.out.println(1);
//		System.out.println("Response: " + conn.sendMsg(test01));
//		System.out.println(2);
//		System.out.println("Response: " + conn.sendMsg(test02));
//		System.out.println(3);
//		System.out.println("Response: " + conn.sendMsg(test03));
//		System.out.println(4);
//		System.out.println("Response: " + conn.sendMsg(test04));
//		System.out.println(5);
//		System.out.println("Response: " + conn.sendMsg(test05));
//		System.out.println(6);
//		System.out.println("Response: " + conn.sendMsg(test06));
//		System.out.println(7);
//		System.out.println("Response: " + conn.sendMsg(test07));
//		System.out.println(8);
//		System.out.println("Response: " + conn.sendMsg(test08));
//		System.out.println(9);
//		System.out.println("Response: " + conn.sendMsg(test09));
//		System.out.println(10);
//		System.out.println("Response: " + conn.sendMsg(test10));
//		System.out.println(11);
//		System.out.println("Response: " + conn.sendMsg(test11));
//		System.out.println(12);
//		System.out.println("Response: " + conn.sendMsg(test12));
//		System.out.println(13);
//		System.out.println("Response: " + conn.sendMsg(test13));
//		System.out.println(14);
//		System.out.println("Response: " + conn.sendMsg(test14));
//		System.out.println(15);
//		System.out.println("Response: " + conn.sendMsg(test15));
//		System.out.println(16);
//		System.out.println("Response: " + conn.sendMsg(test16));
		
		
		System.out.println("Test done");
		
		System.exit(1);
	}
}
