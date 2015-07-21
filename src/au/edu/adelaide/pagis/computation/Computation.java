package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.enigma.mop.MetaLevelObject;
import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;

import au.edu.adelaide.pna.system.*;

import java.util.Map;

public interface Computation extends MetaLevelObject
{
	public void setBaseObject(String name,Object baseObject);
	public Object getBaseObject(String name);
	public Map getDirectory();
  public void setStrategy(ProcessThreadStrategy strategy);
  public void apply(Reconfiguration reconfiguration);
	public MetaResult invoke(TargetAction target,MetaMethodInvocation invocation);
}
