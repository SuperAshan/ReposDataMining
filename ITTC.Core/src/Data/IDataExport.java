package Data;

import java.io.FileNotFoundException;
import java.util.List;

public interface IDataExport
{

 

    List<IDictionarySerializable> Datas=null;

    String ExtentFileName=null;

    String FileName =null;

 
    void ExportToFile() ;
    String FileInput();
}
