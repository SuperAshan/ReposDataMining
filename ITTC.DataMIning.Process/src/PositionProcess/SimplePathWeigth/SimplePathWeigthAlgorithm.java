package PositionProcess.SimplePathWeigth;

import Data.IPositionProcess;
import Data.RelationMatrix;
 

public class SimplePathWeigthAlgorithm extends IPositionProcess{
	double[][] PositionCalculate(RelationMatrix relationMatrix) {
		int nodeNum = relationMatrix.getSize();
		ForceAtlasLayout forceAtlas = new ForceAtlasLayout(nodeNum);
		forceAtlas.resetPropertiesValues();
		forceAtlas.initAlgo(relationMatrix);
	    for (int i = 0; i < nodeNum / 2 && forceAtlas.canAlgo(); i++) {
	    	forceAtlas.goAlgo();
	    }  
	    forceAtlas.endAlgo();
	  	
	    double[] xcoor = forceAtlas.getX();
	    double[] ycoor = forceAtlas.getY();  
	   	
	  	double[][] coordinate = new double[2][nodeNum];
	  	for (int i = 0; i < nodeNum; i++) {
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
