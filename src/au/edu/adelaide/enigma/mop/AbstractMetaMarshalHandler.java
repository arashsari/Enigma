package au.edu.adelaide.enigma.mop;

/**
 * A handler of message semantics of sent from a baseobject.  The
 * method reifier delegates sending method invocations to each
 * handler.  Each handler wraps the message semantics with new
 * message semantics.
 *
 * @author Darren Webb
 * @version $Id: AbstractMetaMarshalHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public abstract class AbstractMetaMarshalHandler implements MetaMarshalHandler
{
	private MetaMarshalHandler handler;

	/**
	 * Set the next send handler in the chain.
	 */
	public void setNextMarshalHandler(MetaMarshalHandler handler)
	{
		this.handler = handler;
	}

	protected MetaMarshalHandler getNextMarshalHandler()
	{
		return handler;
	}
}
