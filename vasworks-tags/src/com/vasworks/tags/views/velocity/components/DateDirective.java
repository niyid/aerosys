package com.vasworks.tags.views.velocity.components;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.Date;

/**
 * @see DateTimePicker
 */
public class DateDirective extends VasWorksAbstractDirective {

    @Override
    protected Component getBean(ValueStack stack, HttpServletRequest req,
        HttpServletResponse res) {
        return new Date(stack);
    }

    @Override
    public String getBeanName() {
        return "date";
    }
}