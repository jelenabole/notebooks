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

	<div>
		<p>
			Napisao: <span>${note.user.fullName} (${note.user.username})</span>
			<br />
			Bilježnica: <span> ${note.notebook.name} -
				${note.notebook.description}</span>
		</p>
	</div>
	
	<h1>${note.header}</h1>
	<div>
		<p class="centered">${note.text}</p>
	</div>

	<a href='<spring:url value="/newNote" />'>Povratak</a>
</body>
</html>