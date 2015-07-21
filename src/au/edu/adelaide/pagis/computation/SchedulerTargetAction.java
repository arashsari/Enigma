package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.enigma.mop.MetaObject;

/**
 *
 *
 * @author Darren Webb
 * @version $Id: SchedulerTargetAction.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public class SchedulerTargetAction extends AbstractTargetAction
{
  public static final SchedulerTargetAction instance = new SchedulerTargetAction();

	public SchedulerTargetAction()
	{
		super("scheduler");
	}
}
