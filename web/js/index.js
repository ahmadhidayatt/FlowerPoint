var currentStep = 1;
var currentLat = "";
var currentLong = "";
var currentaddrs = "";
var dataticket;
var id_atm;
var id_masalah;
var id;
var level;
$(document).ready(function () {
    $.get("../index", function (data, status) {
        var result = $.trim(data);
        if (result === "true") {
            return;
        } else {

            window.location.href = "login.html";
        }
        ;
    });
    $.post("../getSession", function (returnedData) {
        var obj = JSON.parse(returnedData);
        console.log(returnedData);
        jQuery.each(obj, function (i, val) {
            id = obj[i]["id"];
            level = obj[i]["level"];
            console.log(id);
            if (level !== null) {
                if (level === "admin") {
                    return;
                } else if (level === "supplier") {
                    document.getElementById("regist_supp").style.display = "none";
                    return;
                } else {
                    alert("tidak ada jabatan harap login dahulu");
                    window.location.href = "login.html";
                }
            }
        });
    });
});