package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.enigma.mop.MetaObject;

/**
 *
 *
 * @author Darren Webb
 * @version $Id: OutputPortTargetAction.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public class OutputPortTargetAction extends AbstractTargetAction
{
	public OutputPortTargetAction(int portNum)
	{
		super("outputport"+portNum);
	}
}
