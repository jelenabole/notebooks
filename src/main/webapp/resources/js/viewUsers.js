// get users (on-load):
function getAll() {
	getUsers(form = {
		orderBy : null,
		orderDirection : null
	});
}

// get users (with filter):
function filter() {
	var form = {};
	form["orderBy"] = $("#orderBy").val();
	form["orderDirection"] = $(".filterForm input[type='radio']:checked").val()
	form["searchBy"] = $("#searchBy").val();

	getUsers(form);
}

// get users - ajax:
function getUsers(form) {
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "users/search",
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
	$.get("user/edit/" + id, function(data) {
		console.log("GET FOR EDIT");
		$("#form").html(data);
		$("#password").hide();
		$('#username').attr('readonly', true);
		showForm();
	});
}

// add new - new form
function addNew() {
	$.get("user/new", function(data) {
		console.log("CREATE NEW");
		$("#form").html(data);
		showForm();
	});
}

// save (or update):
function saveForm() {
	// get data from form:
	var userForm = getUserInfo();

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "user/save",
		data : JSON.stringify(userForm),
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

// delete user:
function deleteUser(id) {
	$.ajax({
		type : "DELETE",
		url : "api/user/" + id,
		success : function(data) {
			console.log("DELETED ID: ", id);
			deleteForm();
			filter();
		}
	});
}

// change enabled (Spring Security):
function changeStatus(id) {
	$.get("api/user/changeStatus/" + id, function(data) {
		console.log("CHANGE STATUS FOR: ", id);
		deleteForm();
		filter();
	});
}

// get info from form:
var getUserInfo = function() {
	var userForm = {
		id : $("#id").val(),
		name : $("#name").val(),
		surname : $("#surname").val(),
		username : $("#username").val(),
		email : $("#email").val(),
		newPassword : $("#newPassword").val(),
		roles : $(".newForm input[type='checkbox']:checked").map(function() {
			return this.value;
		}).get()
	}
	return userForm;
}

// delete form (cancel bttn):
var deleteForm = function() {
	$.get("user/removeForm", function(data) {
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
