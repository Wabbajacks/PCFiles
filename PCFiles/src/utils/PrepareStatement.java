package utils;

import java.util.ArrayList;

public class PrepareStatement {
	/**
	 * Takes an array of commands with their respective timeframes and converts them into a string.<br><br>
	 * 
	 * The layout of the array must be commands in the even index numbers, while the trailing odd index is<br>
	 * the command's timeframe.<br><br>
	 * 
	 * E.g.
	 * <ul>
	 * <li><code>[0] -> cmd, [1] -> timeframe</code></li>
	 * <li><code>[2] -> cmd, [2] -> timeframe</code></li>
	 * </ul>
	 * 
	 * The returned string must be with the format:<br><br>
	 * <code>"||cmd;;timeframe||cmd;;timeframe||etc...||"</code><br><br>
	 * The string will always start with "||" and end with "||" and a command and its timeframe will always be separated by ";;".
	 * 
	 * @param cmdList An array containing the commands and their respective timeframes.
	 * @param start The start index of the given array.
	 * @return A string with the commands and their timeframes.
	 */
	public static String prepStr(String[] cmdList) {
		
		String list = "||";
		for(int i = 0; i < cmdList.length; i++){
			String[] tmp;
			tmp = cmdList[i].split(" ");
			list += tmp[0];
			list += ";;";
			list += tmp[1];
			list += "||";
			i++;
		}
		
		return list;
	}
	/**
	 * Takes a string and converts it into a list of commands and timeframes.<br>
	 * "||" is used as delimiter. ";;" is used to recognize the command from the timeframe.<br><br>
	 * 
	 * The string format must be:<br>
	 * "||cmd;;timeframe||cmd;;timeframe||etc...||"<br>
	 * The string must start with "||" and end with "||"<br><br>
	 * 
	 * The returned array is given as a command in the even index numbers<br>
	 * with the timeframe for the command in the following odd index number.<br>
	 * E.g.:
	 * 
	 * <ul>
	 * <li>[0] -> "C_FW", [1] -> "5000"</li>
	 * <li>[2] -> "C_HL", [3] -> "0"</li>
	 * </ul>
	 * 
	 * @param msg String containing the commands and their timeframes.
	 * @return 
	 * @return An array containing the command and its timeframe in the following array index.
	 */
	public static String[] prepCmds(String msg) {
		ArrayList<String> res = new ArrayList<String>();
		
		int start = 0;
		
		while(start < msg.lastIndexOf("||")) {
			int i = msg.indexOf(";;", start);
			int j = msg.indexOf("||", i);
			
			if(i != -1) {
				res.add(msg.substring((start+2), i));
				res.add(msg.substring((i+2), j));
				
				start = msg.indexOf("||", i);
			}
		}
		
		return res.toArray(new String[res.size()]);
	}

}
