// get notebooks (on-load):
function getAll() {
	getNotebooks(form = {
		orderBy : null,
		orderDirection : null
	});
}

// get notebooks (with filter):
function filter() {
	var form = {};
	form["orderBy"] = $("#orderBy").val();
	form["orderDirection"] = $(".filterForm input[type='radio']:checked").val()
	form["searchBy"] = $("#searchBy").val();

	getNotebooks(form);
}

// get notebooks - ajax:
function getNotebooks(form) {
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "notebooks/search",
		data : JSON.stringify(form),
		timeout : 100000,
		success : function(data) {
			console.log("GET ALL");
			deleteForm();
			$("#table").html(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
}

// edit (get) one:
function getOne(id) {
	$.get("notebook/edit/" + id, function(data) {
		console.log("GET FOR EDIT");
		$("#form").html(data);
		showForm();
	});
}

// add new - new form
function addNew() {
	$.get("notebook/new", function(data) {
		console.log("CREATE NEW");
		$("#form").html(data);
		showForm();
	});
}

// save (or update):
function saveForm() {
	// get data from form:
	var notebookForm = getNotebookInfo();

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "notebook/save",
		data : JSON.stringify(notebookForm),
		timeout : 100000,
		success : function(data) {
			console.log("VALIDATED");
			$("#form").html(data);

			if (data == "<div></div>") {
				console.log("SAVED");
				deleteForm();
				filter();
			} else {
				console.log("ERRORS");
			}
		}
	});
}

// delete notebook:
function deleteNotebook(id) {
	$.ajax({
		type : "DELETE",
		url : "api/notebook/" + id,
		success : function(data) {
			console.log("DELETED ID: ", id);
			deleteForm();
			filter();
		}
	});
}

// change DB status:
function changeStatus(id) {
	$.get("api/notebook/changeStatus/" + id, function(data) {
		console.log("CHANGE STATUS FOR: ", id);
		deleteForm();
		filter();
	});
}

// get info from form:
var getNotebookInfo = function() {
	var notebookForm = {
		id : $("#id").val(),
		title : $("#title").val(),
		description : $("#description").val()
	};

	return notebookForm;
}

// delete form (cancel bttn):
var deleteForm = function() {
	$.get("notebook/removeForm", function(data) {
		console.log("REMOVE FORM");
		$("#form").html(data);
		hideForm();
	});
}

/** ************* SKRIVANJE FORME ************** */

var hideForm = function() {
	// $(".newForm").hide();
	$("#addButton").show();

}
var showForm = function() {
	// $(".newForm").show();
	$("#addButton").hide();
}
