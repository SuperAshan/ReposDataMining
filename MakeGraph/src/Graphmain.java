import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeSet;

import Config.Conf;


public class Graphmain {
	/**
	 * @param args
	 *            暂时没有参数
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
//		int[][] tes=new int[4][];
//		tes[0]=new int[]{0,1,1,0};
//		tes[1]=new int[]{1,0,1,1};
//		tes[2]=new int[]{1,1,0,1};
//		tes[3]=new int[]{0,1,1,0};
//		for(int i=0;i<4;i++)
//		{
//			for(int j=0;j<4;j++)
//			{
//				if(tes[i][j]==0)
//					tes[i][j]=100;
//			}
//		}
//		int[][] dij=Floyd.getShortedPath(tes);
//		double str=1.222E-4;
//		BigDecimal doubleBigDecimal1 = new BigDecimal(str);
 
		// TODO Auto-generated method stub
		int nodenumber=1000000;
		Graph dbToNodeRelation = new Graph(
				"HaidianUsersNew", nodenumber);
		dbToNodeRelation.Initialize();
		dbToNodeRelation.DataProcessConnectedInternet();
	


}
}
