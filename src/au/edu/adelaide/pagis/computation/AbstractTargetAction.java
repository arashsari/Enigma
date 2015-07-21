package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.enigma.mop.MetaLevelObject;
import au.edu.adelaide.enigma.mop.MetaObject;

/**
 *
 *
 * @author Darren Webb
 * @version $Id: AbstractTargetAction.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public abstract class AbstractTargetAction implements TargetAction
{
	protected String label;

	public AbstractTargetAction(String label)
	{
		this.label = label;
	}

	public String getTargetName()
	{
		return label;
	}

	public Object getTarget(Computation computation)
	{
		return computation.getBaseObject(label);
	}
}
