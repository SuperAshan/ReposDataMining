package PositionProcess.PathWeigth;

import Data.IPositionProcess;
import Data.RelationMatrix;
 
public class PathWeightAlgorithm  extends IPositionProcess {
	double[][] PositionCalculate(RelationMatrix relationMatrix)
	{
		int nodeNum = relationMatrix.getSize();
		ForceAtlas2 forceAtlas2 = new ForceAtlas2(nodeNum);
		forceAtlas2.resetPropertiesValues();
		forceAtlas2.setScalingRatio(500.0);		
		forceAtlas2.initAlgo(relationMatrix);
        for (int i = 0; i < nodeNum * 3 && forceAtlas2.canAlgo(); i++) {
        	forceAtlas2.goAlgo();
        }    
        forceAtlas2.endAlgo();
		forceAtlas2.endAlgo();
		double[] xcoor = forceAtlas2.getX();
		double[] ycoor = forceAtlas2.getY();

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
