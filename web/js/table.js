/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
            console.log(level);
            getprod(id);
            console.log(level + " " + "admin");
            if (level !== null) {
                if (level === "admin") {
                    return;
                } else if (level === "supplier") {
                    document.getElementById("regist_supp").style.display = "none";
                    document.getElementById("all_table").style.display = "none";

                    return;
                } else {
                    alert("tidak ada jabatan harap login dahulu");
                    window.location.href = "login.html";
                }
            }
        });
    });

    $.post("../helper_pegawai", {code: 0}, function (data) {
        console.log(data);
        //  var response = jQuery.parseJSON(data);

        $("#myTable").dataTable({

            "data": JSON.parse(data),
            "columns": [{
                    "data": "id"
                }, {
                    "data": "nama"
                }, {
                    "data": "alamat"
                }, {
                    "data": "tgl"
                }, {
                    "data": "lat"
                }, {
                    "data": "longi"
                }, {
                    "data": "level"
                }, {
                    "data": "username"
                }, {
                    "data": "password"
                }, {
                    "data": "status"
                }, {
                    "data": "email"
                }

            ]
        });
        var detailRows = [];
        var dt = $("#myTable").DataTable();


        // On each draw, loop over the `detailRows` array and show any child rows

    });
// end post
    $.post("../helper_product", {code: 3}, function (data) {
        console.log(data);
        var obj = JSON.parse(data);
        var dataSet = [];
        jQuery.each(obj, function (i, val) {
            console.log(obj[i]);
            dataSet.push(["<td>" + obj[i]["id"] + "</td>"
                        , "<td>" + obj[i]["id_user"] + "</td>"
                        , "<td>" + obj[i]["nama"] + "</td>"
                        , "<td>" + obj[i]["jenis"] + "</td>"
                        , "<td>" + obj[i]["harga"] + "</td>"
                        , "<img src='https://www.datatables.net/media/images/nav-dt.png' style='height:30px; width:30px' id='gambar_" + obj[i]["id"] + "'>"
            ]);
//            console.log(i+" "+'gambar_'+ obj[i]["id"] );
            if ((i + 1) === obj.length) {
                jQuery.each(obj, function (a, val) {
                    setTimeout(getImage(obj[a]["id"]), 500);
                });
            }
        });
        $("#myTable_all").dataTable({
            "data": dataSet
        });
        var detailRows = [];
        var dt = $("#myTable_all").DataTable();
    });
    //end post


});
function getprod(id) {
    $.post("../helper_product", {
        id_user: id,
        code: 5,

    }, function (data) {
        console.log(data);
        var obj = JSON.parse(data);
        var dataSet = [];
        jQuery.each(obj, function (i, val) {
            console.log(obj[i]);
            dataSet.push(["<td>" + obj[i]["id"] + "</td>"
                        , "<td>" + obj[i]["id_user"] + "</td>"
                        , "<td>" + obj[i]["nama"] + "</td>"
                        , "<td>" + obj[i]["jenis"] + "</td>"
                        , "<td>" + obj[i]["harga"] + "</td>"
                        , "<img src='https://www.datatables.net/media/images/nav-dt.png' style='height:30px; width:30px' id='gambar_id_" + obj[i]["id"] + "'>"
            ]);
//            console.log(i+" "+'gambar_'+ obj[i]["id"] );
            if ((i + 1) === obj.length) {
                jQuery.each(obj, function (a, val) {
                    setTimeout(getImages(obj[a]["id"]), 500);
                });
            }
        });
        $("#myTable_your").dataTable({
            "data": dataSet
        });
        var detailRows = [];
        var dt = $("#myTable_your").DataTable();


        // On each draw, loop over the `detailRows` array and show any child rows

    });
}
function getImage(id_produk) {
    var fd = new FormData();
    fd.append("code", "3");
    fd.append("nik", id_produk);
    $.post("../helper_image", {code: "3", id: id_produk}, function (data) {
        var outputImg = document.getElementById('gambar_' + id_produk);
        outputImg.src = "data:image/png;base64," + data.toString();
        $('#gambar_' + id_produk).attr('width', '60px');
        $('#gambar_' + id_produk).attr('height', '60px');
        console.log(data.toString());
    });
}
function getImages(id_produk) {
    var fd = new FormData();
    fd.append("code", "3");
    fd.append("nik", id_produk);
    $.post("../helper_image", {code: "3", id: id_produk}, function (data) {
        var outputImg = document.getElementById('gambar_id_' + id_produk);
        outputImg.src = "data:image/png;base64," + data.toString();
        $('#gambar_id_' + id_produk).attr('width', '60px');
        $('#gambar_id_' + id_produk).attr('height', '60px');
        console.log(data.toString());
    });
}
