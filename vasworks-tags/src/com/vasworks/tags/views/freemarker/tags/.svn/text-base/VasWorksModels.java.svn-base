package com.vasworks.tags.views.freemarker.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class VasWorksModels {
	private ValueStack stack;
	private HttpServletRequest req;
	private HttpServletResponse res;
	private DatePickerModel datepicker;
	private AutocompleterModel autocompleter;
	private TextModel text;

	public VasWorksModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		this.stack = stack;
		this.req = req;
		this.res = res;
	}

	public DatePickerModel getDatepicker() {
		if (this.datepicker == null) {
			this.datepicker = new DatePickerModel(this.stack, this.req, this.res);
		}

		return this.datepicker;
	}

	public AutocompleterModel getAutocompleter() {
		if (this.autocompleter == null) {
			this.autocompleter = new AutocompleterModel(this.stack, this.req, this.res);
		}

		return this.autocompleter;
	}
	
	public TextModel getText() {
		if (this.text==null) {
			this.text=new TextModel(this.stack, this.req, this.res);
		}
		
		return this.text;
	}
}
