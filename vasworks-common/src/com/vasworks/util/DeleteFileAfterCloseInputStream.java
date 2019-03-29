/**
 * inventory.Struts Apr 22, 2009
 */
package com.vasworks.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author developer
 *
 */
public class DeleteFileAfterCloseInputStream extends FileInputStream {

	private File file;

	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public DeleteFileAfterCloseInputStream(File file) throws FileNotFoundException {
		super(file);
		this.file=file;
	}

	/**
	 * @see java.io.FileInputStream#close()
	 */
	@Override
	public void close() throws IOException {
		super.close();
		
		// delete file!
		System.err.println("Deleting file: " + file.getAbsolutePath());
		file.delete();
	}
}
