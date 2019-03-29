<input type="hidden" name="${parameters.name}" id="${parameters.id}_value" value="${parameters.value?default("")}" />
<input type="text" id="${parameters.id}"
  <#if parameters.submitTextTo?if_exists != "">name="${parameters.submitTextTo}"</#if> 
  class="autocompleted <#if parameters.cssClass?if_exists != "">${parameters.cssClass?html}</#if>"
  <#if parameters.cssStyle?if_exists != "">style="${parameters.cssStyle?html}"</#if>
  value="${parameters.displayValue?default("")}" 
  onfocus="if (this.autocompleter==null) this.autocompleter=new Ajax.AutocompleterJSON(this, $('${parameters.id}_upd'), '${parameters.url}', { contentType: 'application/json-rpc', methodName: '${parameters.method?default("autocomplete")}', listKey: '${parameters.listKey}', listValue: '${parameters.listValue}', textHadChanged: function() { $('${parameters.id}_value').value=''; }, updateElement: function(se) { $('${parameters.id}_value').value=se.getAttribute('key'); } });"
/>
<div id="${parameters.id}_upd" class="autocomplete" style="display: none;"></div>