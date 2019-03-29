package com.vasworks.airliner.struts;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vasworks.airliner.model.CrewMember;

public class ProfilePhotoAction extends OperatorAction {
	public static final Log LOG = LogFactory.getLog(ProfilePhotoAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 4138071251332140102L;	
	
	private String contentDisposition;
	
	private InputStream inputStream;
	
	private String contentType;
	
	private Long memberId;
	
	private String fileExtension;

	@Override
	public String execute() {
		LOG.debug("execute()");
		
		StringBuilder b = new StringBuilder("inline;filename=");		
		b.append("\"");
		b.append(System.nanoTime());
		b.append(".");
		b.append(fileExtension != null ? fileExtension : "jpg");
		b.append("\"");
		
		contentDisposition = b.toString();
		
		byte[] rawFileData = getFilePhoto();
		
		if(rawFileData != null) {
			inputStream = new ByteArrayInputStream(rawFileData);
		}
		
		return SUCCESS;
	}

	public byte[] getFilePhoto() {
		LOG.debug("getFilePhoto()");
		byte[] photoData = null;
		if(memberId != null) {
			CrewMember data = (CrewMember) operatorService.find(memberId, CrewMember.class);
			
			contentType = data != null && data.getPhotoMimeType() != null ? data.getPhotoMimeType() : "image/jpeg";
			
			fileExtension = data != null && data.getPhotoFileExtension() != null ? data.getPhotoFileExtension() : ".jpg";
			
			photoData = data.getPhoto();
		}
		
		return photoData;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}
	
	public String getContentType() {
		return contentType;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
