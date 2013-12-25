package PositionProcess.ForceDirected;

import Data.RelationMatrix;

 
public class RepTenAutoLayout {
	private double[] x;
	private double[] y;
	private Data.RelationMatrix relationMatrix;
	private int[] Mass;
	private double[] xForce;
	private double[] yForce;
	private double balanceCondition;
	private double elasticityCoefficient;
	private double optimalLength;
	private double gravity;
		
	public RepTenAutoLayout (RelationMatrix relationMatrix) {	
		this.relationMatrix = relationMatrix;
		int nodeNum = relationMatrix.getSize();
		x = new double[nodeNum];
		y = new double[nodeNum];
		Mass = new int[nodeNum];
		for(int i = 0;i < nodeNum;i++)
		{
			x[i] = (0.01 + Math.random()) * 1000 - 500;
			y[i] = (0.01 + Math.random()) * 1000 - 500;
			Mass[i] = 1;
		}
		xForce = new double[nodeNum];
		yForce = new double[nodeNum];
	}
	
	public void resetPropertiesValues() {
		this.elasticityCoefficient = 1;
		this.optimalLength = 50;
		this.gravity = optimalLength * optimalLength;
	}
		
	public void Autolayout()
	{  
		int nodeNum = x.length;
		for (int i = 0; i < nodeNum; i++) {
			for (int j = 0; j < nodeNum; j++) {
				if (relationMatrix.IsLinked(i, j)) {
					Mass[i]++;
				}				
			}			
		}	
		boolean isBalance = false;
		setBalanceCondition((double)nodeNum / 10.0);
		while(!isBalance) {   
			for(int i = 0;i < nodeNum; i++){
				xForce[i] = 0;
				yForce[i] = 0;				
			}
			double Tension, xTension, yTension, xRepulsion, yRepulsion;
			for (int i = 0; i < nodeNum; i++) {
				for (int j = 0; j < i; j++) {
					if (relationMatrix.IsLinked(i, j)) {
						double dist = Math.sqrt((x[j] - x[i]) * (x[j] - x[i]) + 
								(y[j] - y[i]) * (y[j] - y[i]));
						if(dist > optimalLength);
						{
							Tension = elasticityCoefficient * (dist - optimalLength);//Tension
							xTension = Tension * ((x[j] - x[i]) / dist);
							yTension = Tension * ((y[j] - y[i]) / dist);					
							xForce[i] -= xTension;
							yForce[i] += yTension;			
							xForce[j] += xTension;
							yForce[j] -= yTension;
						}			
					}
				}
			}
			for(int i = 0; i < nodeNum; i++) {   
				for(int j = 0; j < nodeNum; j++) {
					if (i != j) {
						double distance = Math.sqrt((x[j] - x[i]) * (x[j] - x[i]) + 
								(y[j] - y[i]) * (y[j] - y[i]));		
						double distCube = Math.pow(distance, 3);
						xRepulsion =  gravity * Mass[i] * Mass[j] * (x[j] - x[i]) / distCube;
						yRepulsion =  gravity * Mass[i] * Mass[j] * (y[j] - y[i]) / distCube;
						xForce[i] += xRepulsion;
						yForce[i] -= yRepulsion;
					}
				}	
			}
					    			
			for(int i = 0; i < nodeNum; i++) {
				double xDisplacement = Math.min(4, Math.abs(xForce[i]) / Mass[i]);
				if(xForce[i] > 0){
					x[i] = x[i] - xDisplacement;
				}
				else {
					x[i] = x[i] + xDisplacement;
				}	
				double yDisplacement = Math.min(4, Math.abs(yForce[i]) / Mass[i]);
				if(yForce[i] > 0) {
					y[i] = y[i] + yDisplacement; 
				}
				else {
					 y[i] = y[i] - yDisplacement;
				}		
			}
			isBalance = true;
			for (int i = 0; isBalance && i < nodeNum; i++) {
				if (Math.abs(xForce[i] / Mass[i]) > balanceCondition || Math.abs(yForce[i] / Mass[i]) > balanceCondition) {
					isBalance = false;
				}
			}
		}
	}	

	
    public double[] getX() {
		return x;
	}
    
	public double[] getY() {
		return y;
	}
	
	public int[] getMass() {
		return Mass;
	}
	
	public void setBalanceCondition(double balanceCondition) {
		this.balanceCondition = balanceCondition;
	}
		
	public double getK() {
		return elasticityCoefficient;
	}
	
	public void setK(double elasticityCoefficient) {
		this.elasticityCoefficient = elasticityCoefficient;
	}
		
	public double getOptimalLength() {
		return optimalLength;
	}
	
	public void setOptimalLength(double optimalLength) {
		this.optimalLength = optimalLength;
	}
	
	public double getG() {
		return gravity;
	}
	
	public void setG(double gravity) {
		this.gravity = gravity;
	}
}
