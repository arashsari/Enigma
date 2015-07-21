package au.edu.adelaide.enigma.mop;

/**
 * A handler of transmission semantics of messages sent at the metalevel.
 * The metaobject delegates transmission to each handler to wrap the default
 * transmission semantics with new transmission semantics.
 *
 * @author Darren Webb
 * @version $Id: AbstractMetaTransmissionHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public abstract class AbstractMetaTransmissionHandler implements MetaTransmissionHandler
{
	private MetaTransmissionHandler handler;

	/**
         * Set the next transmission handler in the chain.
	 */
	public void setNextTransmissionHandler(MetaTransmissionHandler handler)
	{
		this.handler = handler;
	}

	protected MetaTransmissionHandler getNextTransmissionHandler()
	{
		return handler;
	}
}
