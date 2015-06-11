package utils;

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
}
