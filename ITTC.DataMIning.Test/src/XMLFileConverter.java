

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class XMLFileConverter
{
	private static String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><gexf xmlns=\"http://www.gexf.net/1.1draft\" version=\"1.1\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.1draft http://www.gexf.net/1.1draft/gexf.xsd\">";

	public void Convert(File from, File to) throws IOException
	{
		RandomAccessFile file = new RandomAccessFile(from, "r");
		file.seek(95);
		byte[] buffer = new byte[100];
		int len = 0;
		OutputStreamWriter out = new OutputStreamWriter(
				new FileOutputStream(to), "UTF-8");
		out.append(head);
		while ((len = file.read(buffer, 0, 100)) != -1)
		{
			out.append(new String(buffer, 0, len, "utf-8"));
		}
		out.close();
		file.close();
		from.deleteOnExit();
	}
}
