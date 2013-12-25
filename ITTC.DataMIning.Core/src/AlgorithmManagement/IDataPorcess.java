package AlgorithmManagement;

import DataVisualization.IViewManager;
import Process.IProcess;

public interface IDataPorcess extends IProcess
{

	String DataCollectionName = "";

	boolean IsDataProcessInvoked = true;

	boolean ShouldReCalculated = true;

	
	IDataManager SysDataManager = null;

	IProcessManager SysProcessManager = null;

	IViewManager SysViewManager = null;

}
