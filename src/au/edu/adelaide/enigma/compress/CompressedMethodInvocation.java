package au.edu.adelaide.enigma.compress;

import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaResult;

import java.lang.reflect.Method;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 * Reification of a method invocation.  The MetaMethodInvocation reifies 
 * (or makes explicit) the act of invoking a method invocation on an object.
 *
 * @author Darren Webb
 * @version $Id: CompressedMethodInvocation.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class CompressedMethodInvocation implements MetaMethodInvocation
{
	private boolean failedCompress = false;
	private MetaMethodInvocation failedCompressInvocation;
	private transient MetaMethodInvocation uncompressedInvocation;
	private byte[] compressedArray;

	public CompressedMethodInvocation(MetaMethodInvocation uncompressedInvocation)
	{
		/*
		 * the uncompressed invocation is held as a transient instance
		 * variable so it is not copied when serialized -- only the 
		 * compressed data will be.
		 */
		this.uncompressedInvocation = uncompressedInvocation;
		
		try
		{
			/*
			 * Write the object to a byte array then use the deflater to
			 * compress the byte array.
			 *
			 * This could be done much easier, but I wanted to dimension the
			 * intermediate results.
			 */

			ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos1);
			oos.writeObject(uncompressedInvocation);
			oos.close();
			byte[] uncompressedArray = baos1.toByteArray();
			System.out.println("uncompressed array is size "+uncompressedArray.length);

			// if the object isn't big enough, give up now!
			if (uncompressedArray.length < 1300)
			{
				System.out.println("invocation too small to bother compressing...");
				throw new Exception();
			}

			Deflater deflater = new Deflater(Deflater.BEST_SPEED);
			ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			DeflaterOutputStream dos = new DeflaterOutputStream(baos2,deflater);
			dos.write(uncompressedArray);
			dos.close();
			compressedArray = baos2.toByteArray();
			System.out.println("compressed array is size "+compressedArray.length);

			oos.close();
			dos.close();
		}
		catch (Exception e)
		{
			System.out.println("continuing without compression");
			failedCompress = true;
			failedCompressInvocation = uncompressedInvocation;
		}
	}

	protected synchronized MetaMethodInvocation getUncompressedMethodInvocation()
	{
		if (failedCompress)
			return failedCompressInvocation;
		else
		{
			//if (uncompressedInvocation == null)
			{
				try
				{
					// decompress the array
					ByteArrayInputStream bis = new ByteArrayInputStream(compressedArray);
					InflaterInputStream iis = new InflaterInputStream(bis);
					ObjectInputStream ois = new ObjectInputStream(iis);
					uncompressedInvocation = (MetaMethodInvocation)ois.readObject();
					ois.close();
				}
				catch (Exception e)
				{
					System.out.println("failed to decompress method invocation");
					e.printStackTrace();
					throw new IllegalStateException();
				}
			}

			return uncompressedInvocation;
		}
	}

	/**
	 * Get the method to be invoked by this message.
	 */
	public Method getMethod()
	{
		return getUncompressedMethodInvocation().getMethod();
	}

	/**
	 * Return the name of the method associated with this
	 * method invocation.
	 */
	public String getMethodName()
	{
		return getUncompressedMethodInvocation().getMethodName();
	}

	/**
	 * Get the arguments for the method invocation.
	 */
	public Object[] getArguments()
	{
		return getUncompressedMethodInvocation().getArguments();
	}

	/**
	 * Invoke this method on the provided baseObject.
	 */
	public MetaResult invoke(Object baseObject)
	{
		// return a CompressedMetaResult...
		return getUncompressedMethodInvocation().invoke(baseObject);
	}
}
