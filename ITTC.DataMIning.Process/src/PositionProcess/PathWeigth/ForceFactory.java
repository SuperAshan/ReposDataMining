package PositionProcess.PathWeigth;

import PositionProcess.Node;

public class ForceFactory {
	public static ForceFactory builder = new ForceFactory();

	private ForceFactory() {
	}

	public RepulsionForce buildRepulsion(boolean adjustBySize,
			double coefficient) {
		if (adjustBySize) {
			return new linRepulsion_antiCollision(coefficient);
		} else {
			return new linRepulsion(coefficient);
		}
	}

	public RepulsionForce getStrongGravity(double coefficient) {
		return new strongGravity(coefficient);
	}

	public AttractionForce buildAttraction(boolean logAttraction,
			boolean distributedAttraction, boolean adjustBySize,
			double coefficient) {
		if (adjustBySize) {
			if (logAttraction) {
				if (distributedAttraction) {
					return new logAttraction_degreeDistributed_antiCollision(
							coefficient);
				} else {
					return new logAttraction_antiCollision(coefficient);
				}
			} else {
				if (distributedAttraction) {
					return new linAttraction_degreeDistributed_antiCollision(
							coefficient);
				} else {
					return new linAttraction_antiCollision(coefficient);
				}
			}
		} else {
			if (logAttraction) {
				if (distributedAttraction) {
					return new logAttraction_degreeDistributed(coefficient);
				} else {
					return new logAttraction(coefficient);
				}
			} else {
				if (distributedAttraction) {
					return new linAttraction_massDistributed(coefficient);
				} else {
					return new linAttraction(coefficient);
				}
			}
		}
	}

	public abstract class AttractionForce {

		public abstract void apply(Node n1, Node n2, double e); // Model for
																// node-node
																// attraction (e
																// is for edge
																// weight if
																// needed)
	}

	public abstract class RepulsionForce {

		public abstract void apply(Node n1, Node n2); // Model for node-node
														// repulsion

		public abstract void apply(Node n, Region r); // Model for Barnes Hut
														// approximation

		public abstract void apply(Node n, double g); // Model for gravitation
														// (anti-repulsion)
	}

	/*
	 * Repulsion force: Linear
	 */
	private class linRepulsion extends RepulsionForce {

		private double coefficient;

		public linRepulsion(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2) {

			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();
			double distance = Math.sqrt(xDist * xDist + yDist * yDist);

			if (distance > 0) {
				// factor = force / distance
				double factor = coefficient * n1.getMass() * n2.getMass() / distance
						/ distance;

				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);
			}
		}

		@Override
		public void apply(Node n, Region r) {
			// Get the distance
			double xDist = n.getX() - r.getMassCenterX();
			double yDist = n.getY() - r.getMassCenterY();
			double distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);

			if (distance > 0) {
				// factor = force / distance
				double factor = coefficient * n.getMass() * r.getMass() / distance
						/ distance;

				n.setDx(n.getDx() + xDist * factor);
				n.setDy(n.getDy() + yDist * factor);
			}
		}

		@Override
		public void apply(Node n, double g) {
			// Get the distance
			double xDist = n.getX();
			double yDist = n.getY();
			double distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);

			if (distance > 0) {
				// factor = force / distance
				double factor = coefficient * n.getMass() * g / distance;

				n.setDx(n.getDx() - xDist * factor);
				n.setDy(n.getDy() - yDist * factor);
			}
		}
	}

	/*
	 * Repulsion force: Strong Gravity (as a Repulsion Force because it is
	 * easier)
	 */
	private class linRepulsion_antiCollision extends RepulsionForce {

		private double coefficient;

		public linRepulsion_antiCollision(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();
			double distance = Math.sqrt(xDist * xDist + yDist * yDist)
					- n1.getSize() - n2.getSize();

			if (distance > 0) {
				// factor = force / distance
				double factor = coefficient * n1.getMass() * n2.getMass() / distance
						/ distance;

				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);

			} else if (distance < 0) {
				double factor = 100 * coefficient * n1.getMass() * n2.getMass();

				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);
			}
		}

		@Override
		public void apply(Node n, Region r) {
			// Get the distance
			double xDist = n.getX() - r.getMassCenterX();
			double yDist = n.getY() - r.getMassCenterY();
			double distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);

			if (distance > 0) {
				// factor = force / distance
				double factor = coefficient * n.getMass() * r.getMass() / distance
						/ distance;

				n.setDx(n.getDx() + xDist * factor);
				n.setDy(n.getDy() + yDist * factor);
			} else if (distance < 0) {
				double factor = -coefficient * n.getMass() * r.getMass() / distance;

				n.setDx(n.getDx() + xDist * factor);
				n.setDy(n.getDy() + yDist * factor);
			}
		}

		@Override
		public void apply(Node n, double g) {
			// Get the distance
			double xDist = n.getX();
			double yDist = n.getY();
			double distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);

			if (distance > 0) {
				// factor = force / distance
				double factor = coefficient * n.getMass() * g / distance;

				n.setDx(n.getDx() - xDist * factor);
				n.setDy(n.getDy() - yDist * factor);
			}
		}
	}

	private class strongGravity extends RepulsionForce {

		private double coefficient;

		public strongGravity(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2) {
			// Not Relevant
		}

		@Override
		public void apply(Node n, Region r) {
			// Not Relevant
		}

		@Override
		public void apply(Node n, double g) {
			// Get the distance
			double xDist = n.getX();
			double yDist = n.getY();
			double distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);

			if (distance > 0) {
				// factor = force / distance
				double factor = coefficient * n.getMass() * g;

				n.setDx(n.getDx() - xDist * factor);
				n.setDy(n.getDy() - yDist * factor);
			}
		}
	}

	/*
	 * Attraction force: Linear
	 */
	private class linAttraction extends AttractionForce {

		private double coefficient;

		public linAttraction(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2, double e) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();

			// factor = force / distance
			double factor = -coefficient * e;

			n1.setDx(n1.getDx() + xDist * factor);
			n1.setDy(n1.getDy() + yDist * factor);
			
			n2.setDx(n2.getDx() - xDist * factor);
			n2.setDy(n2.getDy() - yDist * factor);
		}
	}

	/*
	 * Attraction force: Linear, distributed by mass (typically, degree)
	 */
	private class linAttraction_massDistributed extends AttractionForce {

		private double coefficient;

		public linAttraction_massDistributed(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2, double e) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();

			// factor = force / distance
			double factor = -coefficient * e / n1.getMass();

			n1.setDx(n1.getDx() + xDist * factor);
			n1.setDy(n1.getDy() + yDist * factor);
			
			n2.setDx(n2.getDx() - xDist * factor);
			n2.setDy(n2.getDy() - yDist * factor);
		}
	}

	/*
	 * Attraction force: Logarithmic
	 */
	private class logAttraction extends AttractionForce {

		private double coefficient;

		public logAttraction(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2, double e) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();
			double distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);

			if (distance > 0) {

				// factor = force / distance
				double factor = -coefficient * e * Math.log(1 + distance)
						/ distance;

				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);
			}
		}
	}

	/*
	 * Attraction force: Linear, distributed by Degree
	 */
	private class logAttraction_degreeDistributed extends AttractionForce {

		private double coefficient;

		public logAttraction_degreeDistributed(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2, double e) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();
			double distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);

			if (distance > 0) {

				// factor = force / distance
				double factor = -coefficient * e * Math.log(1 + distance)
						/ distance / n1.getMass();

				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);
			}
		}
	}

	/*
	 * Attraction force: Linear, with Anti-Collision
	 */
	private class linAttraction_antiCollision extends AttractionForce {

		private double coefficient;

		public linAttraction_antiCollision(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2, double e) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();
			double distance = Math.sqrt(xDist * xDist + yDist * yDist)
					- n1.getSize() - n2.getSize();

			if (distance > 0) {
				// factor = force / distance
				double factor = -coefficient * e;

				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);
			}
		}
	}

	/*
	 * Attraction force: Linear, distributed by Degree, with Anti-Collision
	 */
	private class linAttraction_degreeDistributed_antiCollision extends
			AttractionForce {

		private double coefficient;

		public linAttraction_degreeDistributed_antiCollision(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2, double e) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();
			double distance = Math.sqrt(xDist * xDist + yDist * yDist)
					- n1.getSize() - n2.getSize();

			if (distance > 0) {
				// factor = force / distance
				double factor = -coefficient * e / n1.getMass();

				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);
			}
		}
	}

	/*
	 * Attraction force: Logarithmic, with Anti-Collision
	 */
	private class logAttraction_antiCollision extends AttractionForce {

		private double coefficient;

		public logAttraction_antiCollision(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2, double e) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getY() - n2.getY();
			double distance = Math.sqrt(xDist * xDist + yDist * yDist)
					- n1.getSize() - n2.getSize();

			if (distance > 0) {

				// factor = force / distance
				double factor = -coefficient * e * Math.log(1 + distance)
						/ distance;

				
				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);
			}
		}
	}

	/*
	 * Attraction force: Linear, distributed by Degree, with Anti-Collision
	 */
	private class logAttraction_degreeDistributed_antiCollision extends
			AttractionForce {

		private double coefficient;

		public logAttraction_degreeDistributed_antiCollision(double c) {
			coefficient = c;
		}

		@Override
		public void apply(Node n1, Node n2, double e) {
			// Get the distance
			double xDist = n1.getX() - n2.getX();
			double yDist = n1.getX() - n2.getY();
			double distance = Math.sqrt(xDist * xDist + yDist * yDist)
					- n1.getSize() - n2.getSize();

			if (distance > 0) {

				// factor = force / distance
				double factor = -coefficient * e * Math.log(1 + distance)
						/ distance / n1.getMass();

				n1.setDx(n1.getDx() + xDist * factor);
				n1.setDy(n1.getDy() + yDist * factor);
				
				n2.setDx(n2.getDx() - xDist * factor);
				n2.setDy(n2.getDy() - yDist * factor);
			}
		}
	}
}
