package test;

import utils.PrepareStatement;

public class TestPrep {
	public static void main(String[] args) {
		String[] wArray	= {"C_FW 423", "C_FW 423", "C_HL 0", "C_HL", "C_FW", "C_FW 5434"};
		
		System.out.println("prepStr:");
		String test = PrepareStatement.prepStr(wArray);
		System.out.println(test);
		
		System.out.println("prepCmds:");
		String[] s = PrepareStatement.prepCmds(test);
		
		System.out.println("desired:");
		for(String a : wArray)
			System.out.println(a);
		
		System.out.println("actual:");
		for(String b : s)
			System.out.println(b);
		
		test = "DNGCLOSE<" + test + ">";
		
		int end = test.indexOf("<");
		String error = test.substring(0, end);
		String emsg = test.substring(end+1, (test.length()-1));
		
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
}
