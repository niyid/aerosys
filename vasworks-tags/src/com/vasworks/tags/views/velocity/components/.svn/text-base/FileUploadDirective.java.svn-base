package com.vasworks.tags.views.velocity.components;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.FileUpload;

/**
 * @see DateTimePicker
 */
public class FileUploadDirective extends VasWorksAbstractDirective {

    @Override
    protected Component getBean(ValueStack stack, HttpServletRequest req,
        HttpServletResponse res) {
        return new FileUpload(stack, req, res);
    }

    @Override
    public String getBeanName() {
        return "fileupload";
    }
}