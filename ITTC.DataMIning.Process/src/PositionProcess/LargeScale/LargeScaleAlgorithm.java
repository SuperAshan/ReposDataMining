package PositionProcess.LargeScale;

import Data.IPositionProcess;
import Data.RelationMatrix;
 

 
public class LargeScaleAlgorithm extends IPositionProcess
{
	double[][] PositionCalculate(RelationMatrix relationMatrix)
	{
		int nodeNum = relationMatrix.getSize();
		OpenOrdLayout openOrdLayout = new OpenOrdLayout(nodeNum);
		openOrdLayout.resetPropertiesValues();
		openOrdLayout.initAlgo(relationMatrix);
		for (int i = 0; i < openOrdLayout.getNumIterations()
				&& openOrdLayout.canAlgo(); i++)
		{
			openOrdLayout.goAlgo();
		}
		openOrdLayout.endAlgo();
		double[] xcoor = openOrdLayout.getX();
		double[] ycoor = openOrdLayout.getY();

		double[][] coordinate = new double[2][nodeNum];
		for (int i = 0; i < nodeNum; i++)
		{
			coordinate[0][i] = xcoor[i];
			coordinate[1][i] = ycoor[i];
		}
		return coordinate;
	}

	@Override
	public void PositionComputeProcess()
	{
	    // TODO Auto-generated method stub
	    
	}
}
