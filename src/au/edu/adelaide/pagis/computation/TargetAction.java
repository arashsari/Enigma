package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.enigma.mop.MetaLevelObject;
import au.edu.adelaide.enigma.mop.MetaObject;

/**
 *
 *
 * @author Darren Webb
 * @version $Id: TargetAction.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public interface TargetAction extends MetaLevelObject
{
	public String getTargetName();
	public Object getTarget(Computation computation);
}
