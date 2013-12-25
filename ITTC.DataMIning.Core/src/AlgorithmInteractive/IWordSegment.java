package AlgorithmInteractive;

import java.util.List;

import Data.ISegwordable;

public interface IWordSegment
{

    List<ISegwordable> MySegmentSource =null;

 

 

    List<String> GetSegment(String source);

    boolean InitProcess();

    /// <summary>
    /// 过滤停用词
    /// </summary>
    boolean IsFilterStopWord=false;

 
}
