package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.enigma.mop.MetaObject;

/**
 *
 *
 * @author Darren Webb
 * @version $Id: ProcessTargetAction.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public class ProcessTargetAction extends AbstractTargetAction
{
  public static final ProcessTargetAction instance = new ProcessTargetAction();

	public ProcessTargetAction()
	{
		super("process");
	}
}
