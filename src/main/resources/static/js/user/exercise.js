$(document).ready(function () {
    var now = new Date();
    var year = now.getFullYear();
    var mon = (now.getMonth() + 1) > 9 ? '' + (now.getMonth() + 1) : '0' + (now.getMonth() + 1);
    var day = (now.getDate()) > 9 ? '' + (now.getDate()) : '0' + (now.getDate());
    var DayOfWeek = ['월','화','수','목','금','토','일'];
    var Intensity = ['강','중','하'];

    //년도 selectbox만들기
    for (var i = 2022; i <= year; i++) {
        $('#year').append('<option value="' + i + '">' + i + '년</option>');
    }
    // 강도별 selectbox 만들기
    for (var i = 0; i <= 2; i++) {
        $('#Intensity').append('<option>'+ Intensity[i] + '</option>');
    }

    // 요일별 selectbox 만들기
    for (var i = 0; i <= 7; i++) {
        $('#DayOfWeek').append('<option>'+ DayOfWeek[i] + '요일</option>');
    }

    // 월별 selectbox 만들기
    for (var i = 1; i <= 12; i++) {
        var mm = i > 9 ? i : "0" + i;
        $('#month').append('<option value="' + mm + '">' + mm + '월</option>');
    }

    // 일별 selectbox 만들기
    for (var i = 1; i <= 31; i++) {
        var dd = i > 9 ? i : "0" + i;
        $('#day').append('<option value="' + dd + '">' + dd + '일</option>');
    }
    $("#month  > option[value=" + mon + "]").attr("selected", "true");
    $("#day  > option[value=" + day + "]").attr("selected", "true");

})