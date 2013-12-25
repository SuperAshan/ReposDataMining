package PositionProcess.SimplePathWeigth;

import PositionProcess.Node;

public class ForceVectorUtils {

    public static double distance(Node n1, Node n2) {
        return Math.hypot(n1.getX() - n2.getX(), n1.getY() - n2.getY());
    }

    public static void fcBiRepulsor(Node n1, Node n2, double c) {
        double xDist = n1.getX() - n2.getX();	// distance en x entre les deux noeuds
        double yDist = n1.getY() - n2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist);	// distance tout court

        if (dist > 0) {
            double f = repulsion(c, dist);
            
            n1.setDx(n1.getDx() + xDist / dist * f);
            n1.setDy(n1.getDy() + yDist / dist * f);

            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - yDist / dist * f);
        }
    }

    public static void fcBiRepulsor_y(Node n1, Node n2, double c, double verticalization) {
        double xDist = n1.getX() - n2.getX();	// distance en x entre les deux noeuds
        double yDist = n1.getY() - n2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist);	// distance tout court

        if (dist > 0) {
            double f = repulsion(c, dist);

            n1.setDx(n1.getDx() + xDist / dist * f);
            n1.setDy(n1.getDy() + verticalization * yDist / dist *f);
            
            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - verticalization * yDist / dist *f);
           
        }
    }

    public static void fcBiRepulsor_noCollide(Node n1, Node n2, double c) {
        double xDist = n1.getX() - n2.getX();	// distance en x entre les deux noeuds
        double yDist = n1.getY() - n2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist) - n1.getSize() - n2.getSize();	// distance (from the border of each node)

        if (dist > 0) {
            double f = repulsion(c, dist);

            n1.setDx(n1.getDx() + xDist / dist * f);
            n1.setDy(n1.getDy() + yDist / dist * f);

            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - yDist / dist * f);
        } else if (dist != 0) {
            double f = -c;	//flat repulsion

            n1.setDx(n1.getDx() + xDist / dist * f);
            n1.setDy(n1.getDy() + yDist / dist * f);

            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - yDist / dist * f);
        }
    }

    public static void fcUniRepulsor(Node n1, Node n2, double c) {
        double xDist = n1.getX() - n2.getX();	// distance en x entre les deux noeuds
        double yDist = n1.getY() - n2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist);	// distance tout court

        if (dist > 0) {
            double f = repulsion(c, dist);
            
            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - yDist / dist * f);
        }
    }

    public static void fcBiAttractor(Node n1, Node n2, double c) {
        double xDist = n1.getX() - n2.getX();	// distance en x entre les deux noeuds
        double yDist = n1.getY() - n2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist);	// distance tout court

        if (dist > 0) {
            double f = attraction(c, dist);

            n1.setDx(n1.getDx() + xDist / dist * f);
            n1.setDy(n1.getDy() + yDist / dist * f);

            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - yDist / dist * f);
        }
    }

    public static void fcBiAttractor_noCollide(Node n1, Node n2, double c) {
        double xDist = n1.getX() - n2.getX();	// distance en x entre les deux noeuds
        double yDist = n1.getY() - n2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist) - n1.getSize() - n2.getSize();	// distance (from the border of each node)

        if (dist > 0) {
            double f = attraction(c, dist);

            n1.setDx(n1.getDx() + xDist / dist * f);
            n1.setDy(n1.getDy() + yDist / dist * f);

            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - yDist / dist * f);
        }
    }

    public static void fcBiFlatAttractor(Node n1, Node n2, double c) {
        double xDist = n1.getX() - n2.getX();	// distance en x entre les deux noeuds
        double yDist = n1.getY() - n2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist);	// distance tout court

        if (dist > 0) {
            double f = -c;

            n1.setDx(n1.getDx() + xDist / dist * f);
            n1.setDy(n1.getDy() + yDist / dist * f);

            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - yDist / dist * f);
        }
    }

    public static void fcUniAttractor(Node n1, Node n2, double c) {
        double xDist = n1.getX() - n2.getX();	// distance en x entre les deux noeuds
        double yDist = n1.getY() - n2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist);	// distance tout court

        if (dist > 0) {
            double f = attraction(c, dist);

            n2.setDx(n2.getDx() - xDist / dist * f);
            n2.setDy(n2.getDy() - yDist / dist * f);
        }
    }

    protected static double attraction(double c, double dist) {
        return 0.01 * -c * dist;
    }

    protected static double repulsion(double c, double dist) {
        return 0.001 * c / dist;
    }
}
