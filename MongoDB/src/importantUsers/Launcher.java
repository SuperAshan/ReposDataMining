package importantUsers;

import java.io.IOException;
import java.util.Date;

public class Launcher
{

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub
		long start = new Date().getTime();
		// FollowerandFriends follandFri = new FollowerandFriends();
		// follandFri.FollFri("FollFri.txt", 1000000);
		ImportantUsers iUsers = new ImportantUsers();
		iUsers.getImportantUsers("ImportantUsers.txt");
		long end = new Date().getTime();
		System.out.print("Time(ms) " + (end - start));
	}

}
