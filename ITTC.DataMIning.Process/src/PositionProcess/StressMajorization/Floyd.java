/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PositionProcess.StressMajorization;

import java.sql.Date;
import java.text.SimpleDateFormat;

//import sun.security.util.Length;

/**
 * 
 * @author Tao
 */
public class Floyd
{

//	public static int[][] getShortedPath(int[][] G)
//	{
//		int n = G.length;
//		for (int i = 0; i < n; i++)
//		{
//			for (int j = 0; j < n; j++)
//			{
//				for (int k = 0; k < j; k++)
//				{
//					Boolean flag1, flag2;
//					flag1 = j > i;
//					flag2 = i > k;
//					if (flag1 && flag2)
//					{
//						G[j][k] = min(G[j][k], G[j][i] + G[i][k]);
//					} else
//					{
//						if (flag1 && !flag2)
//						{
//							G[j][k] = min(G[j][k], G[j][i] + G[k][i]);
//						} else
//						{
//							if (!flag1 && flag2)
//							{
//								G[j][k] = min(G[j][k], G[i][j] + G[i][k]);
//							} else
//							{
//								G[j][k] = min(G[j][k], G[i][j] + G[k][i]);
//							}
//						}
//					}
//				}
//			}
//		}
//		return G;
//	}

	public static int[][] getShortedPath(int[][] G)
	{
		
		int n = G.length;
		int[][] result=new int[n][];
		for(int i=0;i<n;i++)
			result[i]=new int[n];
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				for (int k = 0; k < n; k++)
				{
//					System.out.println("G[j][k]:"+Integer.toString(G[j][k]));
//					System.out.println("G[j][i]:"+Integer.toString(G[j][i]));
//					System.out.println("G[i][k]:"+Integer.toString(G[i][k]));
					result[j][k] = min(G[j][k], G[j][i] + G[i][k]);

				}
			}
			G=clone(result);
			SimpleDateFormat df = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 设置日期格式
			System.out.println(df.format(new Date(System
					.currentTimeMillis()))+"第"+Integer.toString(i)+"次迭代");
	//		System.out.println("第"+Integer.toString(i)+"次迭代");
		}
		return result;
	}
	
	public static int[][] clone(int[][] data)
	{
		int n=data.length;
		int[][] result=new int[n][];
		for(int i=0;i<n;i++)
			result[i]=new int[n];
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				result[i][j]=data[i][j];
			}
		}
		return result;
	}

	public static int min(int x, int y)
	{
		return x < y ? x : y;
	}

	public double[][] getShortedPath(double[][] G)
	{
		int n = G.length;
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				for (int k = 0; k < j; k++)
				{
					Boolean flag1, flag2;
					flag1 = j > i;
					flag2 = i > k;
					if (flag1 && flag2)
					{
						G[j][k] = this.min(G[j][k], G[j][i] + G[i][k]);
					} else
					{
						if (flag1 && !flag2)
						{
							G[j][k] = this.min(G[j][k], G[j][i] + G[k][i]);
						} else
						{
							if (!flag1 && flag2)
							{
								G[j][k] = this.min(G[j][k], G[i][j] + G[i][k]);
							} else
							{
								G[j][k] = this.min(G[j][k], G[i][j] + G[k][i]);
							}
						}
					}
				}
			}
		}
		return G;
	}

	public double min(double x, double y)
	{
		return x < y ? x : y;
	}

}
