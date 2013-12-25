package PositionProcess.LargeScale;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Combine
{
	private final OpenOrdLayout layout;
	private final Control control;

	public Combine(OpenOrdLayout layout)
	{
		this.layout = layout;
		this.control = layout.getControl();
	}

	public void run()
	{
		Worker worker = layout.getWorker();
		worker.run();
		// Gather positions
		Node[] positions = worker.getPositions();

		// printPositions(positions);

		// Unfix positions if necessary
		if (!control.isRealFixed())
		{
			for (Node n : positions)
			{
				n.fixed = false;
			}
		}

		double totEnergy = getTotEnergy();
		boolean done = !control.udpateStage(totEnergy);
		// System.out.println(done);
		// Params
		control.initWorker(worker);

		// Write positions to nodes
		for (Node n : layout.nodes)
		{
			Node node = positions[n.id];
			n.x = node.x * 10d;
			n.y = node.y * 10d;
		}

		// Finish
		if (!layout.canAlgo() || done)
		{
			worker.setDone(true);
			layout.setRunning(false);
		}

	}

	public void printPositions(Node[] nodes)
	{
		NumberFormat formatter = DecimalFormat.getInstance();
		formatter.setMaximumFractionDigits(2);
		for (int i = 0; i < nodes.length; i++)
		{
			String xStr = formatter.format(nodes[i].x);
			String yStr = formatter.format(nodes[i].y);
			System.out.print("(" + xStr + "-" + yStr + "),");
		}
		System.out.println();
	}

	public double getTotEnergy()
	{
		double totEnergy = layout.getWorker().getTotEnergy();
		return totEnergy;
	}
}
