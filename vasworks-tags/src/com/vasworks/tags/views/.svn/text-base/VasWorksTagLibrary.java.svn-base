/**
 * vasworks-tags Jun 5, 2009
 */
package com.vasworks.tags.views;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.TagLibrary;
import org.apache.struts2.views.velocity.components.HeadDirective;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.views.freemarker.tags.VasWorksModels;
import com.vasworks.tags.views.velocity.components.AutocompleterDirective;
import com.vasworks.tags.views.velocity.components.CollapseDirective;
import com.vasworks.tags.views.velocity.components.DateDirective;
import com.vasworks.tags.views.velocity.components.DatePickerDirective;
import com.vasworks.tags.views.velocity.components.DiskSizeDirective;
import com.vasworks.tags.views.velocity.components.FileDropZoneDirective;
import com.vasworks.tags.views.velocity.components.FileUploadDirective;
import com.vasworks.tags.views.velocity.components.TextDirective;

public class VasWorksTagLibrary implements TagLibrary {

    public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req,
        HttpServletResponse res) {
        return new VasWorksModels(stack, req, res);
    }

    @SuppressWarnings("rawtypes")
	public List<Class> getVelocityDirectiveClasses() {
        Class[] directives = new Class[] { HeadDirective.class, DatePickerDirective.class, AutocompleterDirective.class, TextDirective.class, FileUploadDirective.class, FileDropZoneDirective.class, CollapseDirective.class, DiskSizeDirective.class, DateDirective.class };
        return Arrays.asList(directives);
    }

}
