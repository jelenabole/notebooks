<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Note</title>
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/style.css" />">
</head>
<body>

	<h1>${note.header}</h1>
	<div>
		<p>${note.text}</p>
	</div>
	<div>
		<p>
			Napisao: <span>${note.user.fullName}</span>, korisničko ime: <span>${note.user.username}</span>
		</p>
		<p>
			Nalazite se u bilježnici <span title="${note.notebook.description}">
				${note.notebook.name}</span>
		</p>
	</div>

	<a href='<spring:url value="/newNote" />'>Povratak</a>
	<a href="${pageContext.servletContext.contextPath}/newNote">
		Povratak 2 </a>

	<spring:url value="/newNote" context="labos1" var="url" />
	<a href="${url}">Povratak 3</a>

</body>
</html>