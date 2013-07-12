<!DOCTYPE html>
<html>
	<head>
		<title><g:layoutTitle default="Grails"/></title>
		<r:require modules="bootstrap"/>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
		<g:layoutBody/>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
