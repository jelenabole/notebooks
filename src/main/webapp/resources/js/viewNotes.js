// get all notes (with filter):
function filter() {
	var form = {};
	form["orderBy"] = $("#orderBy").val();
	form["orderDirection"] = $(".filterForm input[type='radio']:checked").val()
	form["searchBy"] = $("#searchBy").val();

	// console.log(form);
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
			// console.log("SUCCESS: ", data);
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

// edit (get) one:
function getOne(id) {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "note/edit/" + id,
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS");
			// console.log("SUCCESS: ", data);
			$("#form").html(data);
			showForm();
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

// add new - open form
function addNew() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "note/new",
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS");
			// console.log("SUCCESS: ", data);
			$("#form").html(data);
			showForm();
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

// save (or update):
function saveForm() {
	// prikupi podatke:
	var noteForm = {};
	noteForm["id"] = $("#id").val();
	// user i notebook ID:
	noteForm["user"] = $("#user").val();
	noteForm["notebook"] = $("#notebook").val();

	noteForm["header"] = $("#header").val();
	noteForm["text"] = $("#text").val();
	noteForm["important"] = $(".newForm input[type='checkbox']:checked").val() == undefined ? null
			: "IMPORTANT";
	noteForm["mark"] = $(".newForm input[type='radio']:checked").val() == undefined ? null
			: $(".newForm input[type='radio']:checked").val();

	console.log("OBJECT: ", noteForm);
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "note/save",
		data : JSON.stringify(noteForm),
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS");
			// console.log("SUCCESS: ", data);
			$("#form").html(data);
			// tek nakon validacije se sprema objekt:
			// save(note);
			// deleteForm();
			// i zove funkcija za filter:
//			filter();
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
			filter();
			deleteForm();
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
			filter();
			deleteForm();
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

/** ************* EDIT FORMA ************** */

// ne koristi se:
function fillForm(data) {
	$("#id").val(data.id);
	$("#user").val(data.user.fullname);
	$("#notebook").val(data.notebook.title);
	$("#header").val(data.header);
	$("#text").val(data.text);

	showForm();
}

// nije napisana do kraja:
// provjera pri učitavanju stranice (zbog validacija):
// ovo nije potrebno.. ?? (zbog fragmenata):
var checkForm = function() {
	// ne vrti se na onload - ne postoje id-evi:
	if ($("#id").val() != "" || $("#header").val() != ""
			|| $("#text").val() != "") {
		showForm();
	} else {
		hideForm();
	}
}

// gumb odustani:
var deleteForm = function() {
	$("#id").val("");
	$("#user").val("");
	$("#notebook").val("");
	$("#header").val("");
	$("#text").val("");
	hideForm();
}

// nepotrebne (dohvaća se forma) ??
var hideForm = function() {
	$(".newForm").hide();
	$("#addButton").show();

}
var showForm = function() {
	$(".newForm").show();
	$("#addButton").hide();
}
