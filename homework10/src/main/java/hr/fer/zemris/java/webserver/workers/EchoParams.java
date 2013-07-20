package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker thread that has to insert all given parameters into HTML table.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");

		try {
			context.write("<html><body>");
			context.write("<table border=\"1\"");
			context.write("<tr> <th> Parameter name </th> <th> Parameter value</th></tr>");

			Set<String> params = context.getParameterNames();

			for (String s : params) {
				context.write("<tr><td>" + s + "</td><td>"
						+ context.getParameter(s) + "</td></tr>");
			}

			context.write("</table></body></html>");
		} catch (IOException ignorable) {
		}

	}

}
