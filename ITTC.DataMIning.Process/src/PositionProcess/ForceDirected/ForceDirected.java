package PositionProcess.ForceDirected;

import Data.IPositionProcess;

 ;

public class ForceDirected extends IPositionProcess {
	double[][] PositionCalculate(Data.RelationMatrix relationMatrix) {
		RepTenAutoLayout autoLayout = new RepTenAutoLayout(relationMatrix);
		autoLayout.resetPropertiesValues();
		autoLayout.Autolayout();
		int nodeNum = relationMatrix.getSize();
		double[] xcoor = autoLayout.getX();
		double[] ycoor = autoLayout.getY();
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
