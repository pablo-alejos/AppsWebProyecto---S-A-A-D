var formData;
var frm = $('#loginForm');
frm.submit(getAjax);

function getAjax() {
    var userElement = document.getElementById('username');
    var passwordElement = document.getElementById('password');

    var user = userElement.value;
    var password = passwordElement.value;
    var formData = JSON.stringify({ username: user, password: password });
    //var tokenElement = document.getElementById('token');
    //formData = JSON.stringify($("#loginForm").serializeArray());
    console.log("Request data:" + formData);
    //console.log(JSON.parse(formData));
    $.ajax({
        type: "POST",
        url: "/login_auth",
        contentType: 'application/json',
        dataType: 'json',
        data: formData,
        success: function(data) {
            console.log(typeof data + " " + data.jwt)
            localStorage.removeItem("jwt");
            localStorage.setItem("jwt", data.jwt);
            document.getElementById("token").innerHTML = localStorage.getItem("jwt");
            //window.location.href = "/";
            window.location.replace("/");
        },
    });
    return false;
}

function loadContentWithToken(url) {
    $.ajax({
        type: "GET",
        url: url,
        headers: {
            "Authorization": "Bearer" + localStorage.getItem("jwt")
        },
        success: function(data) {
            alert('Success Response!');
        }
    });
}

function logOut() {
    localStorage.removeItem("jwt");
}