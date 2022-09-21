$(document).ready(function () {
    alert('부가정보를 입력하여 회원가입을 해주세요.');
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