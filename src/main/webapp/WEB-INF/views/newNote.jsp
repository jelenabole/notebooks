<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new note</title>
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/style.css" />">
</head>

<body>
	<input type="button" name="deleteSession" value="Resetiraj statistiku"
		onclick="window.location.href='<spring:url value="/end-session" />';"
		class="btn btn-success">

	<h1>Unos nove bilješke</h1>
	<form:form method="POST" modelAttribute="newNoteForm">
		<table>
			<tr>
				<td><label for="userId">Korisnik:</label></td>
				<td><spring:bind path="userId">
						<form:select id="userId" path="userId"
							class="form-control ${status.error ? 'has-error' : ''}">
							<option value="" disabled selected>-- odaberi korisnika
								--</option>
							<form:options items="${userList}" itemLabel="fullName"
								itemValue="id" />
						</form:select>
						<form:errors path="userId" />
					</spring:bind></td>
			</tr>
			<tr>
				<td><label for="notebookId">Bilježnica:</label></td>
				<td><spring:bind path="notebookId">
						<form:select id="notebookId" path="notebookId"
							class="form-control ${status.error ? 'has-error' : ''}">

							<option value="" disabled selected>-- odaberi bilježnicu
								--</option>
							<form:options items="${notebookList}" itemLabel="title"
								itemValue="id" />
						</form:select>
						<form:errors path="notebookId" />
					</spring:bind></td>
			</tr>
			<tr>
				<td><label for="header">Naslov:</label></td>
				<td><spring:bind path="header">
						<form:input id="header" path="header"
							class="form-control ${status.error ? 'has-error' : ''}"
							placeholder="Unesi naslov"></form:input>
						<form:errors path="header" />
					</spring:bind></td>
			</tr>
			<tr>
				<td><label for="text">Tekst:</label></td>
				<td><spring:bind path="text">
						<form:textarea id="text" path="text"
							class="form-control ${status.error ? 'has-error' : ''}"
							placeholder="Unesi tekst"></form:textarea>
						<form:errors path="text" />
					</spring:bind></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="saveButton" value="Spremi"
					class="btn btn-success"> <input type="button"
					name="deleteButton"
					onclick="window.location.href='<spring:url value="/deleteForm" />';"
					value="Obriši" class="btn btn-warning"></td>
			</tr>
		</table>
	</form:form>

	<div>
		<table class="table table-sm info-table">
			<thead class="thead-default">
				<tr>
					<th>Bilježnica</th>
					<th>Broj unesenih bilješki</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${counter}" var="entry">
					<tr class="${lastChanged == entry.key ? 'table-warning' : ''}">
						<td>${entry.key}</td>
						<td>${entry.value}</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
	</div>

</body>
</html>