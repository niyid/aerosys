<#if uploadLog??><#else><div id="${parameters.id}_log"></div></#if></div></div>
<div class="gears-missing"><form method="post" enctype="multipart/form-data" action="${parameters.destination}<#if parameters.queryParams??>?${parameters.queryParams}</#if>"><input type="file" name="${parameters.name}"
<#if parameters.cssClass?if_exists != "">
class="${parameters.cssClass?html}"
</#if>
<#if parameters.cssStyle?if_exists != "">
style="${parameters.cssStyle?html}"
</#if>
 /><br /><input type="submit" value="${parameters.value?default("Upload")}"
<#if parameters.disabled??>
	disabled="true"
</#if>
<#if parameters.tabindex??>
	tabindex="${parameters.tabindex}"
</#if>
/></form></div>