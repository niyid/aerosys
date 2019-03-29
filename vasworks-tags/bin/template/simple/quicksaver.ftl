<div>
<#if parameters.value??>
	<span id="${parameters.id}">${parameters.value}</span>
	<script type="text/javascript">
		 new Ajax.InPlaceEditor(
		 	'${parameters.id}', 
		 	'${parameters.destination}?id=${parameters.entityId}&typeId=${parameters.entityClass}&property=${parameters.property}',  
		 	{rows:5,cols:40,highlightcolor:'#FDF5E6'}
		 );
	</script>
<#else>					
	<span id="${parameters.id}"><i>Enter text...</></span>
	<script type="text/javascript">
		 new Ajax.InPlaceEditor(
		 	'${parameters.id}', 
		 	'${parameters.destination}?id=${parameters.entityId}&typeId=${parameters.entityClass}&property=${parameters.property}',  
		 	{rows:10,cols:80,highlightcolor:'#FDF5E6'}
		 );
	</script>
</#if>
</div>