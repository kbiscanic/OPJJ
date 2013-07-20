package hr.fer.zemris.java.webserver;

/**
 * Interface determining one method that every web worker must have.
 * 
 */
public interface IWebWorker {

	/**
	 * Method used to process request sent by client to our server.
	 * 
	 * @param context
	 *            {@link RequestContext} for this request.
	 */
	public void processRequest(RequestContext context);
}
