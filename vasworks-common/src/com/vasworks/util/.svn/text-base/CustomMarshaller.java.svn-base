package com.vasworks.util;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.ResolverException;
import org.exolab.castor.xml.XMLContext;
import org.springframework.core.io.Resource;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.castor.CastorMarshaller;

public class CustomMarshaller extends CastorMarshaller {

	@SuppressWarnings("rawtypes")
	@Override
	public XMLContext createXMLContext(Resource[] arg0, Class[] arg1, String[] arg2) throws MappingException, IOException, ResolverException {
		return super.createXMLContext(arg0, arg1, arg2);
	}

	@Override
	public void marshalDomResult(Object graph, DOMResult domResult) throws XmlMappingException {
		// TODO Auto-generated method stub
		super.marshalDomResult(graph, domResult);
	}

	@Override
	public void marshalSaxResult(Object graph, SAXResult saxResult) throws XmlMappingException {
		// TODO Auto-generated method stub
		super.marshalSaxResult(graph, saxResult);
	}

	@Override
	public void marshalStaxResult(Object arg0, Result arg1) throws XmlMappingException {
		// TODO Auto-generated method stub
		super.marshalStaxResult(arg0, arg1);
	}

	@Override
	public void marshalStreamResult(Object graph, StreamResult streamResult) throws XmlMappingException, IOException {
		// TODO Auto-generated method stub
		super.marshalStreamResult(graph, streamResult);
	}

	@Override
	public Object unmarshalDomSource(DOMSource arg0) throws XmlMappingException {
		// TODO Auto-generated method stub
		return super.unmarshalDomSource(arg0);
	}

	@Override
	public Object unmarshalSaxSource(SAXSource arg0) throws XmlMappingException, IOException {
		// TODO Auto-generated method stub
		return super.unmarshalSaxSource(arg0);
	}

	@Override
	public Object unmarshalStaxSource(Source arg0) throws XmlMappingException {
		// TODO Auto-generated method stub
		return super.unmarshalStaxSource(arg0);
	}

	@Override
	public Object unmarshalStreamSource(StreamSource streamSource) throws XmlMappingException, IOException {
		// TODO Auto-generated method stub
		return super.unmarshalStreamSource(streamSource);
	}
	
}
