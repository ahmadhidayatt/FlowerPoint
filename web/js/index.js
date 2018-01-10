var currentStep = 1;
var currentLat = "";
var currentLong = "";
var currentaddrs = "";
var dataticket;
var to=2;
var nama;
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
            nama = obj[i]["nama"];
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


// ----------------------------------------------------------------------------------

/**
 * Date: 13-6-10
 * Time: PM6:32
 * To change this template use File | Settings | File Templates.
 */
var waitingMsg = false;
function getMessage() {
    if (!waitingMsg) {
        waitingMsg = true;
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                     $("#chat_room").append(xhr.responseText);
//                    var elem = document.getElementById('message');
//                   insertChat(1,xhr.responseText,0)
//                    elem.innerHTML = xhr.responseText + elem.innerHTML;
                }
                waitingMsg = false;
            }
        };
        xhr.open('get', '../ShoutoutServlet?t=' + new Date(), true);
        xhr.send();
    }
}
setInterval(getMessage, 1000);
function postMessage(text) {
    var xhr = new XMLHttpRequest();
    xhr.open('post', '../ShoutoutServlet', false);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var name = escape(nama);
    var msg = escape(text);
    xhr.send('from=' + id + '&msg=' + msg + '&t=' + new Date() + '&to=' +to + '&nama=' + nama);
}
$(function () {
    $("#addClass").click(function () {
        $('#qnimate').addClass('popup-box-on');
    });

    $("#removeClass").click(function () {
        $('#qnimate').removeClass('popup-box-on');
    });
})
//===================================================================================
var me = {};
me.avatar = "https://lh6.googleusercontent.com/-lr2nyjhhjXw/AAAAAAAAAAI/AAAAAAAARmE/MdtfUmC0M4s/photo.jpg?sz=48";

var you = {};
you.avatar = "https://a11.t26.net/taringa/avatares/9/1/2/F/7/8/Demon_King1/48x48_5C5.jpg";

function formatAMPM(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    return strTime;
}

//-- No use time. It is a javaScript effect.
function insertChat(who, text, time = 0) {
    var control = "";
    var date = formatAMPM(new Date());

    if (who == id) {

        control = '<li style="width:100%">' +
                '<div class="msj macro">' +
                '<div class="avatar"><img class="img-circle" style="width:100%;" src="' + me.avatar + '" /></div>' +
                '<div class="text text-l">' +
                '<p>' + text + '</p>' +
                '<p><small>' + date + '</small></p>' +
                '</div>' +
                '</div>' +
                '</li>';
    } else {
        control = '<li style="width:100%;">' +
                '<div class="msj-rta macro">' +
                '<div class="text text-r">' +
                '<p>' + text + '</p>' +
                '<p><small>' + date + '</small></p>' +
                '</div>' +
                '<div class="avatar" style="padding:0px 0px 0px 10px !important"><img class="img-circle" style="width:100%;" src="' + you.avatar + '" /></div>' +
                '</li>';
    }
    setTimeout(
            function () {
                $("#chat_room").append(control);

            }, time);

}

function resetChat() {
    $("#chat_room").empty();
}

$("#status_message").on("keyup", function (e) {
    if (e.which == 13) {
        var text = $(this).val();
        if (text !== "") {
            insertChat(id, text);
            postMessage(text)
            $(this).val('');
        }
    }
});

//-- Clear Chat
resetChat();

//-- Print Messages
insertChat("me", "Hello Tom...", 0);
insertChat("you", "Hi, Pablo", 1500);
insertChat("me", "What would you like to talk about today?", 3500);
insertChat("you", "Tell me a joke", 7000);
insertChat("me", "Spaceman: Computer! Computer! Do we bring battery?!", 9500);
insertChat("you", "LOL", 12000);