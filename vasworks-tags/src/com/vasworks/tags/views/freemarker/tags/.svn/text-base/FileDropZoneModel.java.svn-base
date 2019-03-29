package com.vasworks.tags.views.freemarker.tags;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.freemarker.tags.TagModel;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.FileDropZone;

public class FileDropZoneModel extends TagModel {

    public FileDropZoneModel(ValueStack stack, HttpServletRequest req,
        HttpServletResponse res) {
        super(stack, req, res);
    }

    @Override
    protected Component getBean() {
        return new FileDropZone(this.stack, this.req, this.res);
    }

}
