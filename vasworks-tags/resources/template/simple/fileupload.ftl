<div class="gears-only"><input type="button" id="${parameters.id}_dp" class="file-gears-browse" value="${parameters.value}" 
	vasworks:upload-log="${parameters.uploadLog!parameters.id+'_log'}" vasworks:destination="${parameters.destination}<#if parameters.queryParams??>?${parameters.queryParams}</#if>" vasworks:root="${parameters.root}" 
<#if parameters.disabled??>
	disabled="true"
</#if>
<#if parameters.cssClass?if_exists != "">
class="${parameters.cssClass?html}"
</#if>
<#if parameters.cssStyle?if_exists != "">
style="${parameters.cssStyle?html}"
</#if>
/><div id="${parameters.id}_log"></div></div>
<div class="gears-missing"><form method="post" enctype="multipart/form-data" action="${parameters.destination}<#if parameters.queryParams??>?${parameters.queryParams}</#if>"><input type="file" name="${parameters.name}"
<#if parameters.cssClass?if_exists != "">
class="${parameters.cssClass?html}"
</#if>
<#if parameters.cssStyle?if_exists != "">
style="${parameters.cssStyle?html}"
</#if>
 /><br /><input type="submit" value="${parameters.value}"
<#if parameters.disabled??>
	disabled="true"
</#if>
<#if parameters.tabindex??>
	tabindex="${parameters.tabindex}"
</#if>
/></form></div>