package DataVisualization;

import MVVM.FrmState;

public interface IViewManager
{
	// / <summary>
	// / 添加一个界面控件
	// / </summary>
	// / <param name="value">要显示的数据</param>
	// / <param name="thisState">类型</param>
	// / <param name="title">名称</param>
	void AddUserControl(Object value, FrmState thisState, String title);

	void RemoveUserControl(Object value);

}
