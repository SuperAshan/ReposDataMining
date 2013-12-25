package Data;

import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.hydromatic.linq4j.function.Function2;
 

 
 

public class RelationMatrix
{

    public static final double threshold = 0.000001f;

    public float Accuracy = 0.000001f;

    public float[][] Matrix;
    protected TreeMap<Integer, TreeSet<Integer>> SlimLinkedTable;

    public RelationMatrix(int size, CompressType compressType)
    {
	this.compresstype = compressType;
	this.size = size;
	if (size > 20000)
	{
	    compressType = CompressType.LinkedTable;
	}
	if (compressType == CompressType.Matrix)
	{
	    this.Matrix = new float[this.size][];
	    for (int i = 0; i < this.size; i++)
	    {
		this.Matrix[i] = new float[this.size];
	    }
	} else if (compressType == compresstype.LinkedTable)
	{
	    this.LinkedTable = new TreeMap<SuperPoint, Float>();
	} else
	{
	    this.SlimLinkedTable = new TreeMap<Integer, TreeSet<Integer>>();
	}
    }

    private CompressType compresstype;

    public CompressType getCompressType()
    {
	return compresstype;
    }

    private void setCompressType(CompressType compressType)
    {
	compresstype = compressType;
     }

    private int RelationCount;

    private int getRelationCount()
    {
	if (this.compresstype == CompressType.LinkedTable)
	    return this.LinkedTable.size();
	else
	{
	    return 0;
	}
    }

    private void setRelationCount(int relationCount)
    {
	RelationCount = relationCount;
    }

    private int size;

    public int getSize()
    {
	return this.size;
    }

    private SortedMap<SuperPoint, Float> LinkedTable;

    public void Export(String path)
     {
	  FileOutputStream outSTr = null;   
	        BufferedOutputStream Buff = null;   
	        
	        String tab = "  ";   
	        String enter = "\r\n";   
	    
	        final StringBuffer write =new StringBuffer();  
	        try {   
	            outSTr = new FileOutputStream(new File(path));   
	            Buff = new BufferedOutputStream(outSTr);  
	             
	            for (int i = 0; i < size; i++)
	                {
	                 
	        	write.append(i);
	        	write.append(": ");
	                 this.TraversalExecuteX(i, new Function2<Integer, Double, Double>()
			{

			    public Double apply(Integer arg0, Double arg1)
			    {
				   write.append(arg0+"="+ Helpers.MathHelper.roundNumber(arg1, 3)+" ");
				return arg1;   
			                  
			    }
			});
	                 write.append("\r\n");
	                }
	     
	            byte[] midbytes=write.toString().getBytes("UTF8");
	           Buff.write(midbytes);
	                  
	            Buff.flush();   
	            Buff.close();   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        } finally {   
	            try {   
	                Buff.close();   
	                outSTr.close();   
	            } catch (Exception e) {   
	                e.printStackTrace();   
	            }   
	        }   

     }

    public float GetR(int a, int b)
    {
	float data;
	if (compresstype == CompressType.Matrix)
	{
	    return this.Matrix[a][b];
	} else if (compresstype == CompressType.LinkedTable)
	{
	    return this.LinkedTable.get(new SuperPoint(a, b));
	} else if (compresstype == CompressType.SlimLinkedTable)
	{
	    return this.SlimLinkedTable.get(a).contains(b) ? 1 : 0;
	} else
	{
	    return 0;
	}

    }

    public Boolean IsLinked(int a, int b)
    {
	float r = this.GetR(a, b);
	return Math.abs(r) > this.Accuracy;
    }

    public void SetR(int a, int b, float relation)
    {
	if (compresstype == CompressType.Matrix)
	{
	    Matrix[a][b] = relation;
	} else if (compresstype == CompressType.LinkedTable)
	{
	    LinkedTable.put(new SuperPoint(a, b), relation);

	} else if (compresstype == CompressType.SlimLinkedTable)
	{
	    if (this.SlimLinkedTable.containsKey(a))
	    {
		SortedSet<Integer> set = this.SlimLinkedTable.get(a);
		set.add(b);
	    } else
	    {
		TreeSet newSet = new TreeSet<Integer>();
		this.SlimLinkedTable.put(a, newSet);
	    }
	}
    }

    public void TraversalExecute(Function2<Point, Double, Double> action)
    {

	switch (this.compresstype)
	{
	case Matrix:
	    for (int i = 0; i < this.Matrix.length; i++)
	    {
		for (int j = 0; j < this.Matrix[i].length; j++)
		{
		    if (i == j)
		    {
			continue;
		    }
		    double d = this.Matrix[i][j];
		    if (d>0.01)
		    {
			action.apply(new Point(i, j), d);
		    }
		}
	    }

	    break;
	case LinkedTable:
	    Set<SuperPoint> key = LinkedTable.keySet();
	    for (SuperPoint superPoint : key)
	    {
		Point point = superPoint.getPoint();
		float d = LinkedTable.get(superPoint);
		if (d != 0)
		{
		    action.apply(point, (double) d);
		}
	    }

	    break;
	default:
	    break;
	}
    }

    // / <summary>
    // / 对轴每个关系进行遍历
    // / </summary>
    // / <param name="x"> </param>
    // / <param name="action"></param>
    public void TraversalExecuteX(int x,
	    Function2<Integer, Double, Double> action)
    {
	switch (this.compresstype)
	{
	case Matrix:
	    for (int i = 0; i < this.Matrix[x].length; i++)
	    {
		if (x == i)
		    continue;
		double d = this.Matrix[x][i];
                if(d!=0)
		   action.apply(i, d);
	    }
	    break;
	case LinkedTable:

	    Set<SuperPoint> key = LinkedTable.keySet();
	    for (SuperPoint superPoint : key)
	    {
		if (superPoint.getX() != x)
		    continue;

		Point point = superPoint.getPoint();
		float d = LinkedTable.get(superPoint);
		if (d != 0)
		{
		    action.apply((int) superPoint.getY(), (double) d);
		}
	    }
	    break;

	}
    }
}
