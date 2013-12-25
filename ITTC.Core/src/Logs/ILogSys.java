package Logs;

/// <summary>
/// 系统的调试接口
/// </summary>
public interface ILogSys
{

	void Debug(String message);

	void Debug(String message, Exception exception);

	void Error(String message);

	void Error(String message, Exception exception);

	void Info(String message);

	void Info(String message, Exception exception);

}
