package NetworkGenerate;

import java.util.ArrayList;
import java.util.Random;

public class BaModel {
	private int initionDegree;
	private int Degree;

	private int ScaleSize;

	public float[][] Nodes;

	public BaModel(int scaleSize, int degree, int initiondegree) {
		this.ScaleSize = scaleSize;
		this.Degree = degree;
		this.initionDegree = initiondegree;
	}

	public BaModel(int scaleSize) {
		this.ScaleSize = scaleSize;
	}

	public float[][] CreatBaModel() {
		this.Nodes = new float[this.ScaleSize][];
		for (int i = 0; i < this.ScaleSize; i++) {
			this.Nodes[i] = new float[i + 1];
		}

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < this.initionDegree; i++) {
			// this.Nodes[this.Degree][i] = 1; // tempDist;
			list.add(i);
		}

		for (int k = this.initionDegree; k < this.ScaleSize; k++) {
			// int t = this.initionDegree+(k-this.initionDegree)*2*this.Degree;
			for (int i = 0; i < this.Degree; i++) {
				Random rand = new Random(0);
				int h = rand.nextInt(list.size() - 1);
				while (this.Nodes[k][(int) list.get(h)] == 1.0f) {
					h = rand.nextInt(list.size() - 1);
				}
				list.add((int) list.get(h));
				list.add(k);
				this.Nodes[k][(int) list.get(h)] = 1.0f;
			}
		}
		return this.Nodes;
	}
}
