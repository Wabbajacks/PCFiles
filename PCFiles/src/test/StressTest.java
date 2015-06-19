package test;

import java.util.ArrayList;

import comm.PCConn;

public class StressTest {
	public static void main(String[] args) {
		PCConn conn = null;
		
		try {
			conn = new PCConn("00165304789F");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Long> avg = new ArrayList<Long>();
		
		for(int i = 0; i < 500; i++) {
			long a = System.currentTimeMillis();
			
			System.out.println(conn.sendCommand("n: " +i));
			
			long b = System.currentTimeMillis();
			
			System.out.println("respond time: " + (b - a));
			
			if(i != 0) avg.add((b-a));
		}
		
		int s = 0;
		
		for(long l : avg)
			s += l;
		
		long min = (s/avg.size());
		long max = 0;
		
		for(int i = 0; i < avg.size(); i++) {
			if(avg.get(i) > max) max = avg.get(i);
			if(avg.get(i) < min) min = avg.get(i);
		}
		
		int bad = 0;
		
		for(int i = 0; i < avg.size(); i++)
			if(avg.get(i) > (max - (max-min)/2))
				bad++;
		
		System.out.println("min time: " + min);
		System.out.println("avg time: " + (s/avg.size()));
		System.out.println("max time: " + max);
		System.out.println("bad time: " + bad);
		
		conn.closeConn();
		System.exit(1);
	}
}
