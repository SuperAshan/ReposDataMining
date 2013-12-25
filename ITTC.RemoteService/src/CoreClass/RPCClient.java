package CoreClass;

import org.jabsorb.client.Client;
import org.jabsorb.client.HTTPSession;
import org.jabsorb.client.TransportRegistry;

public class RPCClient
{

	public String IPAddress = "localhost";
	public String ServiceName = "Service1";
	public int Port = 1989;

	Client client = null;

	public Object Connect(Class proxy)
	{
		String str = String.format("http://{0}:{1}/{2}/", IPAddress, Port,
				ServiceName);
		HTTPSession proxiedSession = newHTTPSession("http://192.168.2.109:1938/DataMiningService/");

		proxiedSession.getHostConfiguration().setProxy(IPAddress, Port);
		Client client = new Client(proxiedSession);
		Object proxyObject = client.openProxy(null, proxy);
		return proxyObject;
	}

	TransportRegistry registry;

	TransportRegistry getRegistry()
	{
		if (registry == null)
			registry = new TransportRegistry(); // Standard registry by default
		return registry;
	}

	HTTPSession newHTTPSession(String url)
	{
		try
		{
			TransportRegistry reg = getRegistry();
			// Note: HTTPSession is not registered by default. Normally you
			// would
			// register during initialization. In this test, we are testing
			// different
			// states of the registry, hence we register it here and clean up
			// afterwards
			HTTPSession.register(reg);
			// Note: will not work without registering HTTPSession, see #setUp()
			return (HTTPSession) getRegistry().createSession(url);
		} finally
		{
			// Modified the registry; let's clean up after ourselves. Next call
			// to getRegistry will create a new one
			registry = null;
		}
	}

}
