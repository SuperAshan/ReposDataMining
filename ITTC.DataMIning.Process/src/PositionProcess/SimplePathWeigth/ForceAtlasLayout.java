package PositionProcess.SimplePathWeigth;

import Data.RelationMatrix;
import PositionProcess.Node;

public class ForceAtlasLayout {
	private Node[] nodes;
	private RelationMatrix relationMatrix;
	// Properties
	public double inertia;
	private double repulsionStrength;
	private double attractionStrength;
	private double maxDisplacement;
	private boolean freezeBalance;
	private double freezeStrength;
	private double freezeInertia;
	private double gravity;
	private double speed;
	private double cooling;
	private boolean outboundAttractionDistribution;
	private boolean adjustSizes;

	public ForceAtlasLayout(int nodeNum) {
		this.nodes = new Node[nodeNum];
		for (int i = 0; i < nodeNum; i++) {
			this.nodes[i] = new Node();
		}
	}

	public void resetPropertiesValues() {
		inertia = 0.1;
		setRepulsionStrength(200d);
		setAttractionStrength(10d);
		setMaxDisplacement(10d);
		setFreezeBalance(false);
		setFreezeStrength(80d);
		setFreezeInertia(0.2);
		setGravity(30d);
		setOutboundAttractionDistribution(false);
		setAdjustSizes(false);
		setSpeed(1d);
		setCooling(1d);
	}

	public void initAlgo(RelationMatrix relationMatrix)  {
		for (Node n : nodes) {
			n.resetNode();
		}
		this.relationMatrix = relationMatrix;
		int[] degree = new int[nodes.length]; /* degree of each node */
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes.length; j++) {
				if(relationMatrix.IsLinked(i, j))
					degree[i]++;
			}
		}
		for (int i = 0; i < nodes.length; i++) {
			this.nodes[i].setMass(degree[i] + 1);
		}
	}

	public void goAlgo() {
		for (Node n : nodes) {
			n.setOld_dx(n.getDx());
			n.setOld_dy(n.getDy());
			n.mulDx(inertia);
			n.mulDy(inertia);
		}
		// repulsion
		if (isAdjustSizes()) {
			for (Node n1 : nodes) {
				for (Node n2 : nodes) {
					if (n1 != n2) {
						ForceVectorUtils.fcBiRepulsor_noCollide(
								n1,
								n2,
								getRepulsionStrength() * n1.getMass()
										* n2.getMass());
					}
				}
			}
		} else {
			for (Node n1 : nodes) {
				for (Node n2 : nodes) {
					if (n1 != n2) {
						ForceVectorUtils.fcBiRepulsor(
								n1,
								n2,
								getRepulsionStrength() * n1.getMass()
										* n2.getMass());
					}
				}
			}
		}
		// attraction
		if (isAdjustSizes()) {
			if (isOutboundAttractionDistribution()) {
				for (int i = 0; i < nodes.length; i++) {
					for (int j = 0; j < i; j++) {
						if (relationMatrix.IsLinked(i, j)) {
							Node nf = nodes[i];
							Node nt = nodes[j];
							double bonus = 1.0;
							bonus *= getWeight(0);
							ForceVectorUtils.fcBiAttractor_noCollide(nf, nt, bonus
									* getAttractionStrength() / nf.getMass());
						}
					}
				}
			} else {
				for (int i = 0; i < nodes.length; i++) {
					for (int j = 0; j < i; j++) {
						if (relationMatrix.IsLinked(i, j)) {
							Node nf = nodes[i];
							Node nt = nodes[j];
							double bonus = 1.0;
							bonus *= getWeight(0);
							ForceVectorUtils.fcBiAttractor_noCollide(nf, nt, bonus
									* getAttractionStrength());
						}
					}
				}
			}
		} else {
			if (isOutboundAttractionDistribution()) {
				for (int i = 0; i < nodes.length; i++) {
					for (int j = 0; j < i; j++) { //nodes.length
						if (relationMatrix.IsLinked(i, j)) {
							Node nf = nodes[i];
							Node nt = nodes[j];
							double bonus = 1.0;
							bonus *= getWeight(0);
							ForceVectorUtils.fcBiAttractor(nf, nt, bonus
									* getAttractionStrength() / nf.getMass());
						}
					}
				}
			} else {
				for (int i = 0; i < nodes.length; i++) {
					for (int j = 0; j < i; j++) {
						if (relationMatrix.IsLinked(i, j)) {
							Node nf = nodes[i];
							Node nt = nodes[j];
							double bonus = 1.0;
							bonus *= getWeight(0);
							ForceVectorUtils.fcBiAttractor(nf, nt, bonus
									* getAttractionStrength());
						}
					}
				}
			}
		}
		// gravity
		for (Node n : nodes) {
			double nx = n.getX();
			double ny = n.getY();
			double d = 0.0001 + Math.sqrt(nx * nx + ny * ny);
			double gf = 0.0001 * getGravity() * d;
			n.addDx(-gf * nx / d);
			n.addDy(-gf * ny / d);
		}
		// speed
		if (isFreezeBalance()) {
			for (Node n : nodes) {
				n.mulDx(getSpeed() * 10.0);
				n.mulDy(getSpeed() * 10.0);
			}
		} else {
			for (Node n : nodes) {
				n.mulDx(getSpeed());
				n.mulDy(getSpeed());
			}
		}
		// apply forces
		for (Node n : nodes) {
			double d = 0.0001 + Math.sqrt(n.getDx() * n.getDx() + n.getDy() * n.getDy());
			double ratio;
			if (isFreezeBalance()) {
				n.setFreeze(getFreezeInertia() * n.getFreeze()
						+ (1 - getFreezeInertia())	* 0.1 * getFreezeStrength()
						* (Math.sqrt(Math.sqrt((n.getOld_dx() - n.getDx()) * (n.getOld_dx() - n.getDx()) + (n.getOld_dy() - n.getDy())
								* (n.getOld_dy() - n.getDy())))));
				ratio = Math.min((d / (d * (1f + n.getFreeze()))),
						getMaxDisplacement() / d);
			} else {
				ratio = Math.min(1, getMaxDisplacement() / d);
			}

			n.mulDx(ratio / getCooling());
			n.mulDy(ratio / getCooling());

			n.addX(n.getDx());
			n.addY(n.getDy());
		}
	}

	public void endAlgo() {

	}

	public boolean canAlgo() {
		return true;
	}

	public double[] getX() {
		double[] xcorr = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			xcorr[i] = nodes[i].getX();
		}
		return xcorr;
	}

	public double[] getY() {
		double[] ycorr = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			ycorr[i] = nodes[i].getY();
		}
		return ycorr;
	}

	private double getWeight(int i) {
		return 1.;
	}

	public void setInertia(Double inertia) {
		this.inertia = inertia;
	}

	public Double getInertia() {
		return inertia;
	}

	/**
	 * @return the repulsionStrength
	 */
	public Double getRepulsionStrength() {
		return repulsionStrength;
	}

	/**
	 * @param repulsionStrength
	 *            the repulsionStrength to set
	 */
	public void setRepulsionStrength(Double repulsionStrength) {
		this.repulsionStrength = repulsionStrength;
	}

	/**
	 * @return the attractionStrength
	 */
	public Double getAttractionStrength() {
		return attractionStrength;
	}

	/**
	 * @param attractionStrength
	 *            the attractionStrength to set
	 */
	public void setAttractionStrength(Double attractionStrength) {
		this.attractionStrength = attractionStrength;
	}

	/**
	 * @return the maxDisplacement
	 */
	public Double getMaxDisplacement() {
		return maxDisplacement;
	}

	/**
	 * @param maxDisplacement
	 *            the maxDisplacement to set
	 */
	public void setMaxDisplacement(Double maxDisplacement) {
		this.maxDisplacement = maxDisplacement;
	}

	/**
	 * @return the freezeBalance
	 */
	public Boolean isFreezeBalance() {
		return freezeBalance;
	}

	/**
	 * @param freezeBalance
	 *            the freezeBalance to set
	 */
	public void setFreezeBalance(Boolean freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	/**
	 * @return the freezeStrength
	 */
	public Double getFreezeStrength() {
		return freezeStrength;
	}

	/**
	 * @param freezeStrength
	 *            the freezeStrength to set
	 */
	public void setFreezeStrength(Double freezeStrength) {
		this.freezeStrength = freezeStrength;
	}

	/**
	 * @return the freezeInertia
	 */
	public Double getFreezeInertia() {
		return freezeInertia;
	}

	/**
	 * @param freezeInertia
	 *            the freezeInertia to set
	 */
	public void setFreezeInertia(Double freezeInertia) {
		this.freezeInertia = freezeInertia;
	}

	/**
	 * @return the gravity
	 */
	public Double getGravity() {
		return gravity;
	}

	/**
	 * @param gravity
	 *            the gravity to set
	 */
	public void setGravity(Double gravity) {
		this.gravity = gravity;
	}

	/**
	 * @return the speed
	 */
	public Double getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	/**
	 * @return the cooling
	 */
	public Double getCooling() {
		return cooling;
	}

	/**
	 * @param cooling
	 *            the cooling to set
	 */
	public void setCooling(Double cooling) {
		this.cooling = cooling;
	}

	/**
	 * @return the outboundAttractionDistribution
	 */
	public Boolean isOutboundAttractionDistribution() {
		return outboundAttractionDistribution;
	}

	/**
	 * @param outboundAttractionDistribution
	 *            the outboundAttractionDistribution to set
	 */
	public void setOutboundAttractionDistribution(
			Boolean outboundAttractionDistribution) {
		this.outboundAttractionDistribution = outboundAttractionDistribution;
	}

	/**
	 * @return the adjustSizes
	 */
	public Boolean isAdjustSizes() {
		return adjustSizes;
	}

	/**
	 * @param adjustSizes
	 *            the adjustSizes to set
	 */
	public void setAdjustSizes(Boolean adjustSizes) {
		this.adjustSizes = adjustSizes;
	}
}
