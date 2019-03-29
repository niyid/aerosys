<div class="collapse
<#if parameters.cssClass?if_exists != "">
 ${parameters.cssClass?html}
</#if>
<#if parameters.collapsed>
collapsed
</#if>
" 
<#if parameters.cssStyle?if_exists != "">
style="${parameters.cssStyle?html}"
</#if>
id="${parameters.id}">
	<div class="collapse-heading-collapsed"><a onclick="javascript: return VasWorks.Collapse.show(this);">${parameters.closedHeading}</a></div>
	<div class="collapse-heading"><a onclick="javascript: return VasWorks.Collapse.collapse(this);">${parameters.heading}</a></div>
	<div class="collapse-content" id="${parameters.id}_content">