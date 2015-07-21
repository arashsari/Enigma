package au.edu.adelaide.enigma.mop;

/**
 * Reification of an value resulting from the execution
 * of a method invocation.
 *
 * @author Darren Webb
 * @version $Id: MetaValueResult.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class MetaValueResult implements MetaResult
{
	private Object value;

	public MetaValueResult(Object value)
	{
		this.value = value;
	}

	public Object getValue()
	{
		return value;
	}
}
