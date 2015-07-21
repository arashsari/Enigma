package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.enigma.mop.MetaObject;

/**
 *
 *
 * @author Darren Webb
 * @version $Id: InputPortTargetAction.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public class InputPortTargetAction extends AbstractTargetAction
{
	public InputPortTargetAction(int portNum)
	{
		super("inputport"+portNum);
	}
}
