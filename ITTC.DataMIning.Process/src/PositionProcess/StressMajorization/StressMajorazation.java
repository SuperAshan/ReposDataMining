/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PositionProcess.StressMajorization;

////import it.uniroma1.dis.wiserver.gexf4j.core.Edge;
//import it.uniroma1.dis.wiserver.gexf4j.core.EdgeType;
//import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
//import it.uniroma1.dis.wiserver.gexf4j.core.Graph;
//import it.uniroma1.dis.wiserver.gexf4j.core.Mode;
//import it.uniroma1.dis.wiserver.gexf4j.core.Node;
//import it.uniroma1.dis.wiserver.gexf4j.core.data.Attribute;
//import it.uniroma1.dis.wiserver.gexf4j.core.data.AttributeClass;
//import it.uniroma1.dis.wiserver.gexf4j.core.data.AttributeList;
//import it.uniroma1.dis.wiserver.gexf4j.core.data.AttributeType;
//import it.uniroma1.dis.wiserver.gexf4j.core.impl.GexfImpl;
//import it.uniroma1.dis.wiserver.gexf4j.core.impl.StaxGraphWriter;
//import it.uniroma1.dis.wiserver.gexf4j.core.impl.data.AttributeListImpl;
//import it.uniroma1.dis.wiserver.gexf4j.core.impl.viz.ColorImpl;
//import it.uniroma1.dis.wiserver.gexf4j.core.impl.viz.PositionImpl;
//import it.uniroma1.dis.wiserver.gexf4j.core.viz.Color;
//import it.uniroma1.dis.wiserver.gexf4j.core.viz.NodeShape;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Data.IPositionProcess;

//import org.apache.hadoop.fs.Path;
//
//import clustering.ClusterNode;
//import clustering.PropertyNode;
//import data.Vectorizable;

/**
 * 
 * @author Tao
 */
public class StressMajorazation extends IPositionProcess
{
//	private static String centreTag = "";
//	private static File filePath;
//	private static String centerUri="";
	private int maxInteration;

	public StressMajorazation()
	{
		this.maxInteration = 0;
	}

//	public static String getCenterUri() {
//		return centerUri;
//	}
//
//	public static void setCenterUri(String centerUri) {
//		StressMajorazation.centerUri = centerUri;
//	}
	
	
	
	@Override
	public void PositionComputeProcess()
	{
		int nodenumber=this.Nodes.size();
		double[][] Input = new double[nodenumber][];
		for (int i = 0; i < nodenumber; i++)
			Input[i] = new double[i + 1];
		
		for(int i=0;i<nodenumber;i++)
		{
			for(int j=-0;j<i;j++)
			{
				Input[i][j]=this.relationMatrix.IsLinked(i, j)?1.0:0;
			}
		}
		
		double[][] InputOriginal = new double[nodenumber][];
		for (int i = 0; i < nodenumber; i++)
			InputOriginal[i] = new double[i + 1];
		for (int i = 0; i < nodenumber; i++)
			System.arraycopy(Input[i], 0, InputOriginal[i], 0, i);

		for (int i = 0; i < nodenumber; i++)
		{
			for (int j = 0; j < i + 1; j++)
			{
				if (i == j)
					Input[i][j] = 0;
				else
				{
					if (Input[i][j] == 0)
						Input[i][j] = 10000;
					else
					{
						double t = edgeLength(i, j, InputOriginal);
						Input[i][j] = t;
					}
				}
			}
		}

		Floyd dij = new Floyd();
		double[][] Dij = new double[nodenumber][];
		for (int i = 0; i < nodenumber; i++)
			Dij[i] = new double[i + 1];
		Dij = dij.getShortedPath(Input);
		OutlierProcess(Dij);
		
		double[][] CoordinateResult=new double[3][];
		for(int i=0;i<3;i++)
		{
			CoordinateResult[i]=new double[nodenumber];
		}

		
		// ////////////////////////////////////////////////////////////////////////////
		// System.out.println("getGraph");
	//	if(dimension==2)
		{
			CoordinateResult=this.DijToCoordinate2D(Dij);
		}
		for(int i=0;i<nodenumber;i++)
		{
			this.Nodes.get(i).setPositionX(CoordinateResult[0][i]*15);
			this.Nodes.get(i).setPositionY(CoordinateResult[1][i]*15);
			this.Nodes.get(i).setPositionZ(CoordinateResult[2][i]*15);
		}
//		if(dimension==3)
//		{
//			CoordinateResult=this.DijToCoordinate3D(Dij);
//		}
//		
		
	
		
		System.out.println("完毕！");
		// String found = IOUtils.toString(new
		// FileReader("target/testStatic.gexf"));
		// String start = stringWriter.toString();
		//
		// Diff myDiff = new Diff(found, start);
		// myDiff.overrideElementQualifier(new ElementNameAndTextQualifier());
		// assertTrue("XML similar " + myDiff.toString(),
		// myDiff.similar());

		// ////////////////////////////////////////////////////////////////////////////

	}
	
	private double[][] DijToCoordinate2D(double[][] Dij)
	{
		int nodenumber=Dij.length;
		double[][] CoordinateInition=new double[2][];
		for(int i=0;i<2;i++)
		{
			CoordinateInition[i]=new double[nodenumber];
			CoordinateInition[i]=productMatrix(nodenumber);
		}
		
		double[][] CoordinateResult=new double[3][];
		for(int i=0;i<3;i++)
		{
			CoordinateResult[i]=new double[nodenumber];
		}
		double StressFormer, StressLatter;
		StressFormer = StressCompute2D(CoordinateInition[0],CoordinateInition[1], Dij);
		

		double[] bx = new double[nodenumber];
		double[] by = new double[nodenumber];
		double[][] Lw = new double[nodenumber][];
		for (int i = 0; i < nodenumber; i++)
			Lw[i] = new double[i + 1];
		double[][] Lz = new double[nodenumber][];
		for (int i = 0; i < nodenumber; i++)
			Lz[i] = new double[i + 1];
		Lw = LwProduct(Dij, nodenumber);
		Lz = LzUpdate2D(CoordinateInition[0], CoordinateInition[1], Dij);

		CG cg = new CG();
		bx = cg.Multiply(Lz, CoordinateInition[0]);
		by = cg.Multiply(Lz, CoordinateInition[1]);
		

		CG cgx = new CG(CoordinateInition[0], bx, Lw);
		CG cgy = new CG(CoordinateInition[1], by, Lw);
		CoordinateInition=null;
		
		CoordinateResult[0] = cgx.SolveEqutions();
		CoordinateResult[1] = cgy.SolveEqutions();
		StressLatter = StressCompute2D(CoordinateResult[0], CoordinateResult[1],Dij);
		double distance = (StressFormer - StressLatter) / StressFormer;
		double deadline = 0.0001;
		while (Math.abs(distance) > deadline && StressLatter > 1e-15
				&& Math.abs(StressFormer - StressLatter) > 1e-15
				&& this.maxInteration != 1000)
		{
			this.maxInteration++;
			System.out.println(Integer.toString(maxInteration));
			System.out.println(Double.toString(StressLatter));
			StressFormer = StressLatter;
			Lz = LzUpdate2D(CoordinateResult[0], CoordinateResult[1], Dij);

			bx = cg.Multiply(Lz, CoordinateResult[0]);
			by = cg.Multiply(Lz, CoordinateResult[1]);

			CG cgxI = new CG(CoordinateResult[0], bx, Lw);
			CG cgyI = new CG(CoordinateResult[1], by, Lw);
			CoordinateResult[0] = cgxI.SolveEqutions();
			CoordinateResult[1] = cgyI.SolveEqutions();
			StressLatter = StressCompute2D(CoordinateResult[0], CoordinateResult[1],Dij);
			distance = (StressFormer - StressLatter) / StressFormer;
		}
		for(int i=0;i<nodenumber;i++)
			CoordinateResult[2][i]=0.0f;
		return CoordinateResult;
	}
	
	private double[][] DijToCoordinate3D(double[][] Dij)
	{
		int nodenumber=Dij.length;
		double[][] CoordinateInition=new double[3][];
		for(int i=0;i<3;i++)
		{
			CoordinateInition[i]=new double[nodenumber];
			CoordinateInition[i]=productMatrix(nodenumber);
		}
		
		double[][] CoordinateResult=new double[3][];
		for(int i=0;i<3;i++)
		{
			CoordinateResult[i]=new double[nodenumber];
		}
		double StressFormer, StressLatter;
		StressFormer = StressCompute(CoordinateInition[0],CoordinateInition[1],CoordinateInition[2], Dij);
		

		double[] bx = new double[nodenumber];
		double[] by = new double[nodenumber];
		double[] bz = new double[nodenumber];
		double[][] Lw = new double[nodenumber][];
		for (int i = 0; i < nodenumber; i++)
			Lw[i] = new double[i + 1];
		double[][] Lz = new double[nodenumber][];
		for (int i = 0; i < nodenumber; i++)
			Lz[i] = new double[i + 1];
		Lw = LwProduct(Dij, nodenumber);
		Lz = LzUpdate(CoordinateInition[0], CoordinateInition[1],CoordinateInition[2], Dij);

		CG cg = new CG();
		bx = cg.Multiply(Lz, CoordinateInition[0]);
		by = cg.Multiply(Lz, CoordinateInition[1]);
		bz = cg.Multiply(Lz, CoordinateInition[2]);

		CG cgx = new CG(CoordinateInition[0], bx, Lw);
		CG cgy = new CG(CoordinateInition[1], by, Lw);
		CG cgz = new CG(CoordinateInition[2], bz, Lw);
		CoordinateInition=null;
		CoordinateResult[0] = cgx.SolveEqutions();
		CoordinateResult[1] = cgy.SolveEqutions();
		CoordinateResult[2] = cgz.SolveEqutions();
		StressLatter = StressCompute(CoordinateResult[0], CoordinateResult[1], CoordinateResult[2],Dij);
		double distance = (StressFormer - StressLatter) / StressFormer;
		double deadline = 0.0001;
		while (Math.abs(distance) > deadline && StressLatter > 1e-15
				&& Math.abs(StressFormer - StressLatter) > 1e-15
				&& this.maxInteration != 1000)
		{
			this.maxInteration++;
			System.out.println(Integer.toString(maxInteration));
			System.out.println(Double.toString(StressLatter));
			StressFormer = StressLatter;
			Lz = LzUpdate(CoordinateResult[0], CoordinateResult[1],CoordinateResult[2], Dij);

			bx = cg.Multiply(Lz, CoordinateResult[0]);
			by = cg.Multiply(Lz, CoordinateResult[1]);
			bz = cg.Multiply(Lz, CoordinateResult[2]);

			CG cgxI = new CG(CoordinateResult[0], bx, Lw);
			CG cgyI = new CG(CoordinateResult[1], by, Lw);
			CG cgzI = new CG(CoordinateResult[2], bz, Lw);
			CoordinateResult[0] = cgxI.SolveEqutions();
			CoordinateResult[1] = cgyI.SolveEqutions();
			CoordinateResult[2] = cgzI.SolveEqutions();
			StressLatter = StressCompute(CoordinateResult[0], CoordinateResult[1],CoordinateResult[2], Dij);
			distance = (StressFormer - StressLatter) / StressFormer;
		}
		return CoordinateResult;
	}

	public static void PrintNode(double[][] node, String s)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < node.length; i++)
		{
			for (int j = 0; j < i + 1; j++)
			{
				sb.append(Double.toString(node[i][j]));
				sb.append(" ");
			}
		}
		File file = new File(s);
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			} catch (IOException ex)
			{
				// Logger.getLogger(Coordinategeneration.class.getName()).log(
				// Level.SEVERE, null, ex);
			}
		}
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(file);
		} catch (FileNotFoundException ex)
		{
			// Logger.getLogger(Coordinategeneration.class.getName()).log(
			// Level.SEVERE, null, ex);
		}
		writer.write(sb.toString());
		writer.flush();
		writer.close();
	}

//	public static void setCentreTag(String tag)
//	{
//		centreTag = tag;
//	}
//	public static void setFilePath(File path)
//	{
//		filePath = path;
//	}

	private void OutlierProcess(double[][] NodeMatrix)
	{
		int n = NodeMatrix.length;
		double Maxdistance = 0;
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < i; j++)
			{
				if (NodeMatrix[i][j] > Maxdistance && NodeMatrix[i][j] < 10000)
					Maxdistance = NodeMatrix[i][j];
			}
		}

		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < i; j++)
			{
				if (NodeMatrix[i][j] > 9999)
					NodeMatrix[i][j] = Maxdistance + 8;
			}
		}

	}

	/*
	 * public double[][] readtxt() throws IOException { double[][] Input=new
	 * double[this.nodenumber][]; for(int i=0;i<this.nodenumber;i++)
	 * Input[i]=new double[i+1]; BufferedReader br=new BufferedReader(new
	 * FileReader("NodeMatrix.txt")); String r=br.readLine(); String[]
	 * each=r.split(" "); int k=0; for(int i=0;i<this.nodenumber;i++) { for(int
	 * j=0;j<i+1;j++) { Input[i][j]=Double.parseDouble(each[k]); k++; } }
	 * br.close(); return Input; }
	 */
	public void Print(double[] x, String s)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x.length; i++)
		{
			sb.append(Double.toString(x[i]));
			sb.append(" ");
		}
		File file = new File(s);
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			} catch (IOException ex)
			{
				// Logger.getLogger(Coordinategeneration.class.getName()).log(
				// Level.SEVERE, null, ex);
			}
		}
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(file);
		} catch (FileNotFoundException ex)
		{
			// Logger.getLogger(Coordinategeneration.class.getName()).log(
			// Level.SEVERE, null, ex);
		}
		writer.write(sb.toString());
		writer.flush();
		writer.close();
	}

	public double StressCompute(double[] x, double[] y,double[] z, double[][] Dij)
	{
		/* compute the overall stress */
		int n = x.length;
		int i, j;
		double sum, dist;
		sum = 0;
		for (i = 0; i < n; i++)
		{
			for (j = 0; j < i; j++)
			{
				dist = 0;
				dist = (x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
						* (y[i] - y[j])+(z[i] - z[j]) * (z[i] - z[j]);
				dist = (double) Math.sqrt(dist);
				sum += (Dij[i][j] - dist) * (Dij[i][j] - dist)
						/ (Dij[i][j] * Dij[i][j]);
			}
		}

		return sum;

	}

	public double StressCompute2D(double[] x, double[] y, double[][] Dij)
	{
		/* compute the overall stress */
		int n = x.length;
		int i, j;
		double sum, dist;
		sum = 0;
		for (i = 0; i < n; i++)
		{
			for (j = 0; j < i; j++)
			{
				dist = 0;
				dist = (x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
						* (y[i] - y[j]);
				dist = (double) Math.sqrt(dist);
				sum += (Dij[i][j] - dist) * (Dij[i][j] - dist)
						/ (Dij[i][j] * Dij[i][j]);
			}
		}

		return sum;

	}
	public double[][] LwProduct(double[][] Dij, int n)
	{
		double[][] Lw = new double[n][];
		for (int i = 0; i < n; i++)
			Lw[i] = new double[i + 1];
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < i; j++)
			{
				Lw[i][j] = -(1 / (Dij[i][j] * Dij[i][j]));
			}
		}
		for (int i = 0; i < n; i++)
		{
			for (int k = 0; k < i; k++)
				Lw[i][i] += (-Lw[i][k]);
			for (int k = i + 1; k < n; k++)
				Lw[i][i] += (-Lw[k][i]);
		}

		for (int j = 0; j < n; j++)
		{
			Lw[j][0] = 0;
		}

		return Lw;
	}

	public double[][] LzUpdate(double[] x, double[] y,double[] z, double[][] Dij)
	{
		int n = x.length;
		double[][] Lz = new double[n][];
		for (int i = 0; i < n; i++)
			Lz[i] = new double[i + 1];
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < i; j++)
			{
				{
					double dist = (x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
							* (y[i] - y[j])+(z[i] - z[j]) * (z[i] - z[j]);
					dist = Math.sqrt(dist);
					Lz[i][j] = -1 / (Dij[i][j] * dist);
				}
			}
		}
		for (int i = 0; i < n; i++)
		{
			Lz[i][i] = 0;
			for (int k = 0; k < i; k++)
				Lz[i][i] += (-Lz[i][k]);
			for (int k = i + 1; k < n; k++)
				Lz[i][i] += (-Lz[k][i]);

		}
		for (int j = 0; j < n; j++)
		{
			Lz[j][0] = 0;
		}
		return Lz;
	}
	
	public double[][] LzUpdate2D(double[] x, double[] y, double[][] Dij)
	{
		int n = x.length;
		double[][] Lz = new double[n][];
		for (int i = 0; i < n; i++)
			Lz[i] = new double[i + 1];
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < i; j++)
			{
				{
					double dist = (x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
							* (y[i] - y[j]);
					dist = Math.sqrt(dist);
					Lz[i][j] = -1 / (Dij[i][j] * dist);
				}
			}
		}
		for (int i = 0; i < n; i++)
		{
			Lz[i][i] = 0;
			for (int k = 0; k < i; k++)
				Lz[i][i] += (-Lz[i][k]);
			for (int k = i + 1; k < n; k++)
				Lz[i][i] += (-Lz[k][i]);

		}
		for (int j = 0; j < n; j++)
		{
			Lz[j][0] = 0;
		}
		return Lz;
	}

	public static double edgeLength(int i, int j, double[][] Input)
	{
		ArrayList degree1 = new ArrayList();
		ArrayList degree2 = new ArrayList();
		int nodenumber = Input.length;
		for (int k = 0; k < nodenumber; k++)
		{
			if (k < j + 1)
			{
				if (Input[i][k] == 1 || Input[j][k] == 1)
					degree1.add(k);
				if (Input[i][k] == 1 && Input[j][k] == 1)
					degree2.add(k);
			} else
			{
				if (k < i + 1)
				{
					if (Input[k][j] == 1 || Input[i][k] == 1)
						degree1.add(k);
					if (Input[k][j] == 1 && Input[i][k] == 1)
						degree2.add(k);
				} else
				{
					if (Input[k][j] == 1 || Input[k][i] == 1)
						degree1.add(k);
					if (Input[k][j] == 1 && Input[k][i] == 1)
						degree2.add(k);
				}
			}
		}
		return (degree1.size() - degree2.size());

	}

	public static double[] productMatrix(int nodeNumber)
	{
		double[] matrix = new double[nodeNumber];
		for (int i = 0; i < nodeNumber; i++)
		{
			if (i == 0)
			{
				matrix[i] = 0;
			} else
			{
				matrix[i] = new Random().nextDouble() * 10;

			}
		}
		return matrix;
	}

	public double getDistance(double x0, double y0, double x1, double y1)
	{
		return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
	}

	
}
