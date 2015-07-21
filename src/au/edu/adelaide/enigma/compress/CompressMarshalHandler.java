package au.edu.adelaide.enigma.compress;

import au.edu.adelaide.enigma.mop.AbstractMetaMarshalHandler;
import au.edu.adelaide.enigma.mop.MetaMessage;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaResult;

/**
 * Compress method invocations delegated from the method reifier.
 * This handler accepts a method invocation and creates a
 * compressed version of the method invocation.
 *
 * @author Darren Webb
 * @version $Id: CompressMarshalHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class CompressMarshalHandler extends AbstractMetaMarshalHandler
{
	/**
	 */
	public MetaResult handleMarshal(MetaMethodInvocation invocation)
	{
		CompressedMethodInvocation compressed = new CompressedMethodInvocation(invocation);
		return getNextMarshalHandler().handleMarshal(compressed);
	}
}
