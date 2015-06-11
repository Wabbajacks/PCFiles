package utils;

import java.util.ArrayList;

public class PrepareStatement {
	/**
	 * Takes an array of commands with their respective timeframes and converts them into a string.<br><br>
	 * 
	 * The layout of array must be command followed by the timeframe, separated by a space.
	 * 
	 * E.g.
	 * <ul>
	 * <li><code>[0] -> cmd timeframe</code></li>
	 * <li><code>[1] -> cmd timeframe</code></li>
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
			if(tmp.length == 1) {
				list += "0";
			}
			else {
				list += tmp[1];
			}
			list += "||";
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
	 * The returned array is given as command with the timeframe in the same index, separated by a space.<br>
	 * E.g.:
	 * 
	 * <ul>
	 * <li>[0] -> "C_FW 5000"</li>
	 * <li>[1] -> "C_HL 0"</li>
	 * </ul>
	 * 
	 * 
	 * @param msg String containing the commands and their timeframes.
	 * @return An array containing the command and its timeframe in the the same index, separated by a space.
	 */
	public static String[] prepCmds(String msg) {
		String[] res;
		
		String s = msg.substring(2, msg.length()-2);
		
		res = s.split("\\|\\|");
		
		for(int i = 0; i < res.length; i++) res[i] = res[i].replace(";;", " ");
		
		return res;
	}

}
