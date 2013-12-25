package CoreClass;

import java.io.Serializable;

import org.jabsorb.JSONRPCBridge;
import org.jabsorb.JSONRPCServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class RPCServer
{

	public int Port = 1989;
	Server server;
	Context context;
	public String ServiceName = "RPCSservice";

	/**
	 * Encapsulate Jetty hosting server initialization so that we could start it
	 * only once during the test run
	 */

	public RPCServer()
	{

	}

	public void Begin(Serializable service) throws Exception
	{

		JSONRPCBridge.getGlobalBridge().registerObject(ServiceName, service);
		server = new Server(Port);
		context = new Context(server, JABSORB_CONTEXT, Context.SESSIONS);
		ServletHolder jsonRpcServlet = new ServletHolder(new JSONRPCServlet());
		// Based on the patch by http://code.google.com/u/cameron.taggart/
		// located at
		// http://code.google.com/p/json-rpc-client/issues/detail?id=1
		jsonRpcServlet.setInitParameter("auto-session-bridge", "0");
		context.addServlet(jsonRpcServlet, "/*");
		server.start();
	}

	static final String JABSORB_CONTEXT = "/jabsorb-trunk";

	public String getServiceRootURL()
	{
		return "http://localhost:" + Port + JABSORB_CONTEXT;
	}
}
