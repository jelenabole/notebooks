//stara funkcija:
function editNotebook(id, title, description) {
	showForm();
	console.log("funkcija: " + id);
	$("#id").val(id);
	$("#header").val(header);
	$("#text").val(text);
}

// get all notes (with filter):
function filterNotes() {
	var form = {};
	form["orderBy"] = $("#orderBy").val();
	form["orderDirection"] = $(".filterForm input[type='radio']:checked").val()
	form["searchBy"] = $("#searchBy").val();
	
//	console.log(form);
	getNotes(form);
}

// get all notes (on-load):
function getAll() {
	getNotes(form = {
		orderBy : null,
		orderDirection : null
	});
}

function getNotes(form) {
	console.log(form);
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "notes/search",
		data : JSON.stringify(form),
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS");
//			console.log("SUCCESS: ", data);
			$("#results").html(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

function deleteNote(id) {
	$.ajax({
		type : "DELETE",
		contentType : "application/json",
		url : "api/note/" + id,
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			filterNotes();
//			$("#results").replaceWith(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

function changeStatus(id) {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "api/note/changeStatus/" + id,
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			filterNotes();
//			$("#results").replaceWith(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

// get one:
function getNote(id) {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "api/note/" + id,
		data : order,
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			var note = data;

			// $("#id").val(note.id);
			// $("#title").val(notebook.title);
			// $("#description").val(notebook.description);
			// $("#description").val(notebook.description);
			// $("#description").val(notebook.description);
			// $("#description").val(notebook.description);
			// $("#description").val(notebook.description);
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});

	showForm();
}

/*************** EDIT FORMA ***************/

// provjera pri učitavanju stranice (zbog validacija):
var checkForm = function() {
	if ($("#id").val() != "" || $("#header").val() != ""
			|| $("#text").val() != "") {
		showForm();
	} else {
		hideForm();
	}
}

//gumb odustani:
var deleteForm = function() {
	$("#id").val("");
	$("#title").val("");
	$("#description").val("");
	hideForm();
}

var hideForm = function() {
	$(".newForm").hide();
}
var showForm = function() {
	$(".newForm").show();
}

//za prikaz iz JSON-a:
function displayOld(notes) {
	console.log("display");
	
	var result = "";
	for (var i = 0; i < notes.length; i++) {
		result += "<tr>";
		result += "<td sec:authorize=\"hasRole('ROLE_ADMIN')\">" + notes[i].id + "</td>";
		result += "<td sec:authorize=\"hasRole('ROLE_ADMIN')\">" + notes[i].user.name + " " + notes[i].user.surname + "</td>";
		result += "<td>" + notes[i].notebook.title + "</td>";
		result += "<td>" + notes[i].header + "</td>";
		result += "<td>" + notes[i].text + "</td>";
		
		result += "<td class=" + notes[i].mark + ">" + notes[i].importance + "</td>";
//		<td th:class="${note.mark}"><span
//		th:text="${note.importance?.important} ? #{table.yes} : #{table.no}"></span>
//		<span th:if="${note.importance?.important}"
//		class="glyphicon glyphicon-heart"
//		th:style="'color:' + ${note.mark.id}"></span></td>
		
		result += "<td sec:authorize=\"hasRole('ROLE_ADMIN')\">" + notes[i].status + "</td>";
//		<td sec:authorize="hasRole('ROLE_ADMIN')">${note.status.translation}}"></span> <span></span></td>
		
		//gumbi:
		result += "<td><a class=\"btn btn-sm btn-primary\" th:href=\"@{'/newNote/'" + notes[i].id + "\" th:text=\"#{button.edit}\"></a>";
		result += "<a class=\"btn btn-sm btn-warning\" th:href=\"@{'/deleteNote/'" + notes[i].id + "\" th:text=\"#{button.delete}\"></a>";
		result += "<a sec:authorize=\"hasRole('ROLE_ADMIN')\" class=\"btn btn-sm btn-info\" th:href=\"@{'/changeNoteStatus/'" + notes[i].id + "\" th:text=\"#{button.changeStatus}\"></a></td>";
	}

	$("#result tbody").html(result);
}
