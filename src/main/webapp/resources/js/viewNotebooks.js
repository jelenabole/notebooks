//stara funkcija:
function editNotebook(id, title, description) {
	showForm();
	console.log("funkcija: " + id);
	$("#id").val(id);
	$("#title").val(title);
	$("#description").val(description);
}

// get notebook (for editing):
function getNotebook(id) {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "api/notebook/" + id,
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			var notebook = data;

			$("#id").val(notebook.id);
			$("#title").val(notebook.title);
			$("#description").val(notebook.description);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			display(e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});

	showForm();
}

//provjera pri učitavanju stranice (zbog validacija):
var checkForm = function() {
	if ($("#id").val() != "" || $("#title").val() != ""
			|| $("#description").val() != "") {
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
