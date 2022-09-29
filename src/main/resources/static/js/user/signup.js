$(document).ready(function () {
    var now = new Date();
    var year = now.getFullYear();
    var mon = (now.getMonth() + 1) > 9 ? '' + (now.getMonth() + 1) : '0' + (now.getMonth() + 1);
    var day = (now.getDate()) > 9 ? '' + (now.getDate()) : '0' + (now.getDate());
    //년도 selectbox만들기
    for (var i = 1900; i <= year; i++) {
        $('#year').append('<option value="' + i + '">' + i + '년</option>');
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
    $("#year  > option[value=" + year + "]").attr("selected", "true");
    $("#month  > option[value=" + mon + "]").attr("selected", "true");
    $("#day  > option[value=" + day + "]").attr("selected", "true");

})

window.onload = function () {
    document.getElementById("search_addr").addEventListener("click", function () { //주소입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function (data) { //선택시 입력값 세팅
                document.getElementById("동네").value = data.address; // 주소 넣기
                document.getElementById("광역시").value = data.sido;
                document.getElementById("시군구").value = data.sigungu;
                document.getElementById("동읍면리").value = data.bname;
                document.getElementById("상세 주소").focus(); //상세입력 포커싱
            }
        }).open();

    });
}
function id_check() {
    let nickname = $("#nickname").val();
    console.log(nickname);
    $.ajax({
        url: "/user/information/check", // 클라이언트가 HTTP 요청을 보낼 서버의 URL 주소
        data: {nickname: nickname},  // HTTP 요청과 함께 서버로 보낼 데이터
        method: "GET",   // HTTP 요청 메소드(GET, POST 등)
        dataType: "html" // 서버에서 보내줄 데이터의 타입
    })
        // HTTP 요청이 성공하면 요청한 데이터가 done() 메소드로 전달됨.
        .done(function (json) {
            if ($("#duplicate").length === 0) {
                $("#check").prepend("<div id='duplicate'>" + json + "</div>");
            } else {
                $("#duplicate").text(json);
            }
            // $("<div class=\"content\">").html(json.html).appendTo("body");
        })
        // HTTP 요청이 실패하면 오류와 상태에 관한 정보가 fail() 메소드로 전달됨.
        .fail(function (xhr, status, errorThrown) {
            $("#text").html("오류가 발생했다.<br>")
                .append("오류명: " + errorThrown + "<br>")
                .append("상태: " + status);
        })
        //
        .always(function (xhr, status) {
            $("#text").html("요청이 완료되었습니다!");
        });
}