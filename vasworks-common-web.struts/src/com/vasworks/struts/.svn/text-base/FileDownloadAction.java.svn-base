/**
 * projecttask.Struts Sep 17, 2009
 */
package com.vasworks.struts;

import java.io.IOException;

import com.vasworks.struts.webfile.ServerFile;

/**
 * Any file downloader that wants to use the common donwloading
 * 
 * @author developer
 */
public interface FileDownloadAction {

	/**
	 * Set file to download
	 * 
	 * @param downloadFileName the downloadFileName to set
	 */
	public abstract void setFile(String fileName);

	/**
	 * @param downloadDirectory the downloadDirectory to set
	 */
	public abstract void setDirectory(String directory);

	/**
	 * Download file action
	 * 
	 * @return
	 */
	public abstract String download();

	/**
	 * Get the ServerFile to download. This file will opened and transferred to the client.
	 * @param string2 
	 * @param string 
	 * 
	 * @return
	 * @throws IOException 
	 */
	public abstract ServerFile getServerFile(String subDirectory, String fileName) throws IOException;
}