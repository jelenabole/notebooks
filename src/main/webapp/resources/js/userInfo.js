// get user info:
function getInfo() {
	$.get("userInfo/get", function(data) {
		console.log("GET TABLE");
		$("#form").html(data);
	});
}

// edit user info:
function editUser() {
	$.get("userInfo/edit", function(data) {
		console.log("EDIT INFO");
		$("#form").html(data);
		$('#username').attr('readonly', true);
	});
}

// update user info:
function saveForm() {
	// get data from form:
	var userForm = getUserInfo();

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "userInfo/save",
		data : JSON.stringify(userForm),
		timeout : 100000,
		success : function(data) {
			console.log("VALIDATED");
			$("#form").html(data);
		}
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
	}
	return userForm;
}

// delete form (cancel bttn):
var deleteForm = function() {
	getInfo();
}