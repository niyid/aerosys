<!DOCTYPE html>

<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<sjm:head jqueryui="true" jquerytheme="flick" />
	    <title><decorator:title default="Dialog" /></title>
		<decorator:head />
		<%@ include file="/common/meta.jsp"%>
	</head>
	<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>>
			<div id="mainDialog" data-role="dialog" data-transition="flip">
				<div data-role="header">
					<h1><decorator:title default="Dialog" /></h1>
				</div>
				<div data-role="content">
					<decorator:body />
				</div>
			</div>
	</body>
</html>