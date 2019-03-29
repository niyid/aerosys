<input name="${parameters.name}" id="${parameters.id}" value="${parameters.formattedValue?default("")}"
  <#if parameters.cssClass?if_exists != "">
     class="date-picker ${parameters.cssClass?html}"
  <#else>
  	class="date-picker"
  </#if>
  <#if parameters.cssStyle?if_exists != "">
     style="${parameters.cssStyle?html}"
  </#if> autocomplete="off"
onFocus="javascript: return VasWorks.DatePicker.show(this, '${parameters.format}', new Date(${parameters.rfcTime?default("")}));" />