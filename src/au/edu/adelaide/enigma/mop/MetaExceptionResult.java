package au.edu.adelaide.enigma.mop;

/**
 * Reification of an exception resulting from the execution
 * of a method invocation.
 *
 * @author Darren Webb
 * @version $Id: MetaExceptionResult.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class MetaExceptionResult implements MetaResult
{
	private Throwable throwable;

	public MetaExceptionResult(Throwable throwable)
	{
		this.throwable = throwable;
	}

	public Throwable getException()
	{
		return throwable;
	}
}
