package com.vasworks.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;

import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.tools.MappingTool;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.vasworks.util.CustomMarshaller;

public class BaseSoapService {
	private static final Log log = LogFactory.getLog(BaseSoapService.class);
	private String mappingDirectory = null;
	private CustomMarshaller marshaller;
	
	public void setMarshaller(CustomMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void setMappingDirectory(String mappingDirectory) {
		log.info("Mapping directory: " + mappingDirectory);
		this.mappingDirectory = mappingDirectory;
	}

	public Object unmarshal(String entityXml, String className) throws ClassNotFoundException, MappingException, IOException {
		log.info("ENTITY XML: " + entityXml);
		Class<?> c;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			log.error(e);
			throw e;
		}

		log.info("CLASS_NAME: " + className + " CLASS_FOR_NAME: " + c);

		String mappingPath = className.replaceAll("\\.", "/");
		log.info("Mapping file sub-path: " + mappingPath);

		File mapFile = new File(this.mappingDirectory + "/" + mappingPath + "_mapping.xml");
		log.debug("Map file: " + mapFile.getAbsolutePath() + " Exists?" + mapFile.exists());

		if (!mapFile.exists()) {
			log.info("Setting up MappingTool...");
			MappingTool tool = new MappingTool();
			tool.setForceIntrospection(true);
			tool.setInternalContext(new org.castor.xml.BackwardCompatibilityContext());
			try {
				tool.addClass(c);
			} catch (MappingException e) {
				log.error(e);
				throw e;
			}

			Writer writer;
			try {
				if (!mapFile.getParentFile().exists()) {
					log.info("Creating folder " + mapFile.getParentFile());
					mapFile.getParentFile().mkdirs();
				}
				writer = new OutputStreamWriter(new FileOutputStream(mapFile));
			} catch (FileNotFoundException e) {
				log.error(e);
				throw e;
			}
			tool.write(writer);
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				log.error(e);
				throw e;
			}
			log.info("Mapping file written... Exists?" + mapFile.exists());
		}
		
		Resource mapping = new FileSystemResource(mapFile);
		log.info("Mapping resource: " + mapping.getURI() + "...");

		marshaller.setTargetClass(c);
		marshaller.setMappingLocation(mapping);
		return marshaller.unmarshalStreamSource(new StreamSource(new StringReader(entityXml)));
	}
}
