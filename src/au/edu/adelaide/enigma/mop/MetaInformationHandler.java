package au.edu.adelaide.enigma.mop;

/**
 * The interface to observe change in metainformation of a metaobject.
 *
 * @author Darren Webb
 * @version $Id: MetaInformationHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaInformationHandler extends MetaLevelObject
{
	public void metaInformationChanged(MetaInformationEvent event);
}
