/**
 * vasworks-common-web.struts Oct 22, 2009
 */
package com.vasworks.struts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vasworks.struts.webfile.ServerFile;

/**
 * Action allows for browsing, managing and downloading files from a specified server-side directory
 * 
 * @author developer
 */
@SuppressWarnings("serial")
public class DirectoryFilesAction extends FilesAction {

	public DirectoryFilesAction() {
	}

	private static final Log LOG = LogFactory.getLog(DirectoryFilesAction.class);
	private File rootDirectory;

	/**
	 * Construct a DirectoryFilesAction using a base directory
	 */
	public DirectoryFilesAction(String directory) {
		this.rootDirectory = new File(directory);
		if (!this.rootDirectory.exists())
			LOG.warn("Directory '" + directory + "' does not exist");
		if (!this.rootDirectory.isDirectory())
			LOG.warn("'" + directory + "' is not a directory");
		if (!this.rootDirectory.canRead())
			LOG.warn("Directory '" + directory + "' cannot be read");
	}

	/**
	 * @see com.vasworks.struts.FilesAction#getBrowserTitle()
	 */
	@Override
	public String getBrowserTitle() {
		return "application files";
	}

	/**
	 * @see com.vasworks.struts.FilesAction#getFiles(java.lang.String)
	 */
	@Override
	public List<ServerFile> getFiles(String subDirectory) {
		try {
			return ServerFile.getServerFiles(this.rootDirectory, subDirectory);
		} catch (IOException e) {
			addActionError(e.getMessage());			
		}
		return null;
	}

	/**
	 * @see com.vasworks.struts.FilesAction#getId()
	 */
	@Override
	public Long getId() {
		return null;
	}

	/**
	 * @see com.vasworks.struts.FilesAction#getRootDirectory()
	 */
	@Override
	protected File getRootDirectory() throws FileNotFoundException {
		return this.rootDirectory;
	}

	/**
	 * @see com.vasworks.struts.FilesAction#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		
	}

	/**
	 * @see com.vasworks.struts.FileDownloadAction#getServerFile(java.lang.String, java.lang.String)
	 */
	@Override
	public ServerFile getServerFile(String subDirectory, String fileName) throws IOException {
		return ServerFile.getServerFile(this.rootDirectory, subDirectory, fileName);
	}

}
