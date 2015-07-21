package au.edu.adelaide.enigma.mop;

/**
 * A handler of transmission semantics of messages sent at the metalevel.
 * The metaobject delegates transmission to each handler to wrap the default
 * transmission semantics with new transmission semantics.
 *
 * @author Darren Webb
 * @version $Id: MetaTransmissionHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaTransmissionHandler extends MetaLevelObject
{
	/**
	 * Set the next transmission handler in the chain.
	 */
	public void setNextTransmissionHandler(MetaTransmissionHandler handler);

	/**
	 * Trap to introduce new transmission behaviour to the baselevel.  The
	 * metalevel programmer implements this method to introduce new
	 * behaviour before and a message is sent to the metaobject of a 
	 * baseobject.  The actual message is delegated to the next transmission
	 * handler.
	 */
	public MetaResult handleTransmission(MetaMessage message);
}
