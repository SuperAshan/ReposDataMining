package Files;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
 

public class JsonExporter implements Data.IDataExport
{

    public void ExportToFile()
    {
	try
	{
	    {
		JsonFactory factory = new JsonFactory();
		OutputStream stream = null;
		try
		{
		    stream = new FileOutputStream(FileName);
		} catch (FileNotFoundException e1)
		{
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}

		JsonGenerator generator = null;
		try
		{
		    generator = factory.createJsonGenerator(stream,
			    JsonEncoding.UTF8);
		} catch (IOException e1)
		{
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		try
		{
		    generator.writeStartArray();
		} catch (JsonGenerationException e1)
		{
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (IOException e1)
		{
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		try
		{
		    for (Data.IDictionarySerializable dictionarySerializable : Datas)
			try
			{
			    {
				HashMap<String, Object> map = dictionarySerializable
					.DictSerialize();
				generator.writeObject(map);

			    }
			} catch (Exception e)
			{
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		} catch (Exception e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		try
		{
		    generator.writeEndArray();
		} catch (JsonGenerationException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IOException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		try
		{
		    stream.close();
		} catch (IOException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	    }
	} catch (Exception e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public String FileInput()
    {
	// TODO Auto-generated method stub
	return null;
    }

}
