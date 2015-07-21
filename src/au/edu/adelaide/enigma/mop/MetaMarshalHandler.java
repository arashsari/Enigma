package au.edu.adelaide.enigma.mop;

/**
 * A handler of messages sent from a baseobject.  The
 * method reifier delegates sending method invocations to each
 * handler.  Each handler wraps the message semantics with new
 * message semantics.
 *
 * @author Darren Webb
 * @version $Id: MetaMarshalHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaMarshalHandler extends MetaLevelObject
{
	/**
	 * Set the next marshal handler in the chain.
	 */
	public void setNextMarshalHandler(MetaMarshalHandler handler);

	/**
	 * Wrap the send semantics with new send semantics to the
	 * baseobject.
	 */
	public MetaResult handleMarshal(MetaMethodInvocation invocation);
}
