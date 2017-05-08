<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Preview Note</title>
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/style.css" />">
</head>
<body>

	<table class="table table-bordered info-table">
		<tr>
			<td>Korisnik:</td>
			<td>${note.user.fullName}</td>
		</tr>
		<tr>
			<td>Bilježnica:</td>
			<td>${note.notebook.title}</td>
		</tr>
		<tr>
			<td>Naslov:</td>
			<td>${note.header}</td>
		</tr>
		<tr>
			<td>Tekst:</td>
			<td>${note.text}</td>
		</tr>
		<tr>
			<td><input type="button" name="changeNote"
				value="Izmijeni bilješku"
				onclick="window.location.href='<spring:url value="/newNote" />';"
				class="btn btn-warning"></td>
			<td>

				<form action="saveNote" method="POST">
					<form:hidden path="note" />
					<input type="submit" name="saveNote" value="Spremi bilješku"
						class="btn btn-success">
				</form>
			</td>
		</tr>
	</table>
</body>
</html>