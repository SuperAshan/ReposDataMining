package AlgorithmInteractive;
 

public interface IDrawCanvasMethod
{

	public enum CanvasType
	{
		Local,

		RemoteJsonRPC,

		RemoteWCF
	}

	CanvasType MyCanvasType = CanvasType.Local;
	Data.IPositionProcess GlobalPositionProcess = null;
}
