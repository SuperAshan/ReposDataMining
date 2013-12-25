package Files;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.*;
 
public class ExcelExporter implements Data.IDataExport
{

    public void ExportToFile()
    {

	// 创建Excel文档

	HSSFWorkbook hwb = new HSSFWorkbook();

	// sheet 对应一个工作页

	HSSFSheet sheet = hwb.createSheet("sheet1");

	HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
	Set<String> titles = Datas.get(0).DictSerialize().keySet();
	int rowLength = Datas.size();
	int CountColumnNum = titles.size();
	HSSFCell[] firstcell = new HSSFCell[CountColumnNum];

	List<String> names = new ArrayList<String>();
	for (String string : Datas.get(0).DictSerialize().keySet())
	{
	    names.add(string);
	}

	for (int j = 0; j < CountColumnNum; j++)
	{

	    firstcell[j] = firstrow.createCell(j);

	    firstcell[j].setCellValue(new HSSFRichTextString(names.get(j)));

	}

	// create titles

	// 得到要插入的每一条记录
	int rowIndex = 1;
	int collumIndex = 0;
	for (Data.IDictionarySerializable data : Datas)
	{

	    HSSFRow row = sheet.createRow(rowIndex);

	    HashMap<String, Object> map = data.DictSerialize();
	    collumIndex = 0;
	    for (String key : map.keySet())
	    {
		HSSFCell xh = row.createCell(collumIndex);
		xh.setCellValue(map.get(key).toString());
		collumIndex++;
	    }

	    rowIndex++;

	}

	// 创建文件输出流，准备输出电子表格

	OutputStream out;
	try
	{
	    out = new FileOutputStream(FileName);
	    try
	    {
		hwb.write(out);
	    } catch (IOException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    try
	    {
		out.close();
	    } catch (IOException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	} catch (FileNotFoundException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	System.out.println("Excel file export successful");

    }

    public String FileInput()
    {
	// TODO Auto-generated method stub
	return null;
    }

}
