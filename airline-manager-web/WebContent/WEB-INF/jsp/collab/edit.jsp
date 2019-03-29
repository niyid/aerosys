<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<html>
<head>
<title>Collab editor</title>
</head>
<body>
<p><a href=".">Collab editing test</a></p>
<table class="inputform">
<tr><td>Text</td><td>
<FCK:editor instanceName="area"></FCK:editor>
<%--
<textarea id="area" style="height: 150px;">
Ut convallis; dui at consequat commodo, nisl neque mattis velit, nec imperdiet quam sapien vitae erat. Quisque in lorem diam, non viverra sapien. Cras a justo nisl, sit amet dignissim nulla. Cras in diam eu felis placerat tempor. Vestibulum ultrices, quam nec mollis porttitor, urna mauris pharetra nunc, in bibendum nulla nisl id nulla. Curabitur eu ipsum erat. Suspendisse sit amet leo magna, eu dapibus diam. Pellentesque pellentesque lobortis quam, sit amet gravida magna consectetur sed. Maecenas ac quam magna. Duis commodo dictum elit at eleifend.
</textarea> --%>
</td>
</tr><tr><td /><td>
	<input type="button" onclick="javascript: edit.checkForUpdates();" value="Check for updates" />
</td></tr>
</table>
<div id="LOG">
<div />
</div>
<script src="<c:url value="/" />/script/collab.js"></script>
<script type="text/javascript">
var edit=new VasWorks.CollabEdit($("area"), '/stub/ajax/collaborate.jspx');
</script>
</body>
</html>