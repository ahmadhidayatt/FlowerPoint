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
    $('.li-nav').click(function () {
        var $targetStep = $($(this).attr('step'));
        currentStep = parseInt($(this).attr('id').substr(7));
        if (!$(this).hasClass('disabled')) {
            $('.li-nav.active').removeClass('active');
            $(this).addClass('active');
            $('.setup-content').hide();
            $targetStep.show();
        }
    });

    $('#navStep1').click();
    $(".placepicker").each(function () {
        var target = this;
        var $collapse = $(this).parents('.form-group').next('.collapse');
        var $map = $collapse.find('.another-map-class');
        var placepicker = $(this).placepicker({
            map: $map.get(0),
            placeChanged: function (place) {
                currentLat = this.getLocation().latitude;
                currentLong = this.getLocation().longitude;
                currentaddrs = place.formatted_address;
                console.log("place changed: ", place.formatted_address, this.getLocation().latitude, this.getLocation().longitude);
            }
        }).data('placepicker');
    });
    $('#tanggal').datepicker();
    $('#submit_form').click(function () {
        var date = $('#tanggal').datepicker({dateFormat: 'yy-MM-dd'}).val();
        var fd = new FormData();
        fd.append("code", "1");
        fd.append("nama", $("#nama").val());
        fd.append("no_hp", $("#no_hp").val());
        fd.append("username", $("#username").val());
        fd.append("password", $("#password").val());
        fd.append("email", $("#email").val());
        fd.append("tanggal", date);
        fd.append("lat", currentLat);
        fd.append("long", currentLong);
        fd.append("level", "supplier");
        fd.append("status", "y");
        fd.append("alamat", currentaddrs);

        $.ajax({
            url: '../helper_pegawai',
            data: fd,
            processData: false,
            contentType: false,
            type: 'POST',
            success: function (data) {
                alert(data);
                location.reload()
            }
        });
    });

});


function step1Next() {
    //You can make only one function for next, and inside you can check the current step
    if (true) {//Insert here your validation of the first step
        currentStep += 1;
        $('#navStep' + currentStep).removeClass('disabled');
        $('#navStep' + currentStep).click();
        $('html, body').animate({scrollTop: 0}, 'slow');
    }
}

function prevStep() {
    //Notice that the btn prev not exist in the first step
    currentStep -= 1;
    $('#navStep' + currentStep).click();
}

function step2Next() {
    if (true) {
        $('#navStep3').removeClass('disabled');
        $('#navStep3').click();
    }
}

function step3Next() {
    if (true) {
        $('#navStep4').removeClass('disabled');
        $('#navStep4').click();
    }
}
