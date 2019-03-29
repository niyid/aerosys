<div class="gears-only "><div id="${parameters.id}_dp" class="file-drop-zone" 
	vasworks:upload-log="${parameters.uploadLog!parameters.id+'_log'}" vasworks:destination="${parameters.destination}<#if parameters.queryParams??>?${parameters.queryParams}</#if>" vasworks:root="${parameters.root}" 
<#if parameters.cssClass?if_exists != "">
class="${parameters.cssClass?html}"
</#if>
<#if parameters.cssStyle?if_exists != "">
style="${parameters.cssStyle?html}"
</#if>
>