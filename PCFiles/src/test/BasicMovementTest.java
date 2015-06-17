package test;

import comm.PCConn;

public class BasicMovementTest {
	public static void main(String[] args) {
		PCConn conn = new PCConn();
		
//		String[] test1 = {"C_FW;;6000","C_HL;;0","C_FW;;5000", "C_HR;;0", "C_BW;;1000"};
//		String[] test2 = {"C_BW;;2500","C_HR;;0","C_FW;;5000", "C_HR;;0", "C_FW;;1000"};
//		String[] square = {"C_FW;;100", "C_FW;;200", "C_FW;;5000", "C_HL;;0", "C_FW;;2500", "C_HL;;0", "C_FW;;2500", "C_FW;;100", "C_HL;;0", "C_BW;;234", "C_HL;;0", "C_FW;;2500", "C_HL;;0"};
		
//		String[] test1 = {"C_FW;;200", "C_BW;;200", "C_FW;;200", "C_BW;;200", "C_FW;;200", "C_BW;;200", "C_FW;;200", "C_BW;;200", };
//		String[] test2 = {"C_BW;;100", "C_BW;;100", "C_BW;;100", "C_BW;;100", "C_BW;;100", "C_BW;;100"};
//		String[] square = {"C_HL;;0", "C_HL;;0", "C_HL;;0", "C_HL;;0", "C_HL;;0", "C_HL;;0", "C_HL;;0"};
		
//		System.out.println("Test1:");
//		System.out.println("Response: " + conn.sendMsg(test1));
//		
//		System.out.println("Test2:");
//		System.out.println("Response: " + conn.sendMsg(test2));
//		
//		System.out.println("Square:");
//		System.out.println("Response: " + conn.sendMsg(square));
		
		String a = "C_FW;;500";
		String b = "C_HL;;0";
		
		String[] test01 = {a,b};
		String[] test02 = {a,b,a,b};
		String[] test03 = {a,b,a,b,a,b};
		String[] test04 = {a,b,a,b,a,b,a,b};
		String[] test05 = {a,b,a,b,a,b,a,b,a,b};
		String[] test06 = {a,b,a,b,a,b,a,b,a,b,a,b};
		String[] test07 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test08 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test09 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test10 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test11 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test12 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test13 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test14 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test15 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
//		String[] test16 = {a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b,a,b};
		
		System.out.println("Running...");
		System.out.println(1);
		System.out.println("Response: " + conn.sendMsg(test01));
		System.out.println(2);
		System.out.println("Response: " + conn.sendMsg(test02));
		System.out.println(3);
		System.out.println("Response: " + conn.sendMsg(test03));
		System.out.println(4);
		System.out.println("Response: " + conn.sendMsg(test04));
		System.out.println(5);
		System.out.println("Response: " + conn.sendMsg(test05));
		System.out.println(6);
		System.out.println("Response: " + conn.sendMsg(test06));
		System.out.println(7);
		System.out.println("Response: " + conn.sendMsg(test07));
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
