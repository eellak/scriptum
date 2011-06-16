package gr.scriptum.eprotocol.diavgeia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;

/**
 * Helper class that DEBUGS HttpRequests
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */

public class HttpDispatcher {

	private boolean debug = false;

	// Debugging Helpers
	void debugResponse(InputStream is) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		String line;
		StringBuffer response = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		debug(response.toString());
	}

	void debugResponse(String comment, HttpMethod method) {
		debug("--------------------------------------");
		debug("- HTTP RESPONSE                      -");
		debug("--------------------------------------");
		debug("Caller: " + comment);
		debug("Status code " + method.getStatusCode());
		debug("Status text " + method.getStatusText());
		Header headers[] = method.getResponseHeaders();
		for (int i = 0; i < headers.length; i++)
			debug("Header [" + headers[i].getName() + "] value ["
					+ headers[i].getValue() + "]");
		debug("--------------------------------------");
	}

	void debugRequest(String comment, HttpMethod method) {
		debug("--------------------------------------");
		debug("- HTTP REQUEST                       -");
		debug("--------------------------------------");
		debug("Caller: " + comment);
		debug("Type  : " + method.getName());
		debug("Target: " + method.getPath());
		debug("Query : " + method.getQueryString());
		debug("--------------------------------------");
	}

	void debug(String s) {
		if (debug)
			System.out.println(s);
	}

	void setDebug(boolean d) {
		this.debug = d;
	}
}
