window.onload = function () {
    document.getElementById("search_addr").addEventListener("click", function () { //주소입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function (data) { //선택시 입력값 세팅
                document.getElementById("address_1st").value = data.address; // 주소 넣기
                document.getElementById("address_2st").value = data.sido;
                document.getElementById("address_3st").value = data.sigungu;
                document.getElementById("address_4st").value = data.bname;
                document.getElementById("address_detail").value = '';
                document.getElementById("address_detail").focus(); //상세입력 포커싱
            }
        }).open();

    });
}

function btnPwdActive() {
    const target1 = document.getElementById('password1');
    const target2 = document.getElementById('password2');
    const target3 = document.getElementById('password3');
    if (target1.disabled == false && target2.disabled == false && target3.disabled == false) {
        target1.disabled = true;
        target2.disabled = true;
        target3.disabled = true;
    } else {
        target1.disabled = false;
        target2.disabled = false;
        target3.disabled = false;
    }
}

function btnPhoneActive() {
    const target1 = document.getElementById('phoneNumber');
    if (target1.disabled == false) {
        target1.disabled = true;
    } else {
        target1.disabled = false;
    }
}

function btnAddressActive() {
    const target1 = document.getElementById('address_1st');
    const target2 = document.getElementById('address_2st');
    const target3 = document.getElementById('address_3st');
    const target4 = document.getElementById('address_4st');
    const target5 = document.getElementById('address_detail');
    const target6 = document.getElementById('search_addr');
    if (target1.disabled == false && target2.disabled == false && target3.disabled == false && target4.disabled == false && target5.disabled == false && target6.disabled == false) {
        target1.disabled = true;
        target2.disabled = true;
        target3.disabled = true;
        target4.disabled = true;
        target5.disabled = true;
        target6.disabled = true;
        console.log(target1.value);
    } else {
        target1.disabled = false;
        target2.disabled = false;
        target3.disabled = false;
        target4.disabled = false;
        target5.disabled = false;
        target6.disabled = false;
    }
}

function info_submit(form) {
    const nickname = document.getElementById('nickname');
    const address_1st = document.getElementById('address_1st');
    const address_2st = document.getElementById('address_2st');
    const address_3st = document.getElementById('address_3st');
    const address_4st = document.getElementById('address_4st');
    if (address_1st.value.trim().length == 0 && address_1st.disabled == false &&
        address_2st.value.trim().length == 0 && address_2st.disabled == false &&
        address_3st.value.trim().length == 0 && address_3st.disabled == false &&
        address_4st.value.trim().length == 0 && address_4st.disabled == false) {
        alert('주소를 입력해주세요.');
        return;
    }
    if (nickname.value.trim().length < 3) {
        console.log(nickname.value);
        alert('사용자 닉네임 길이는 3자 이상 25자 이하 이여야 합니다.');
        return;
    }
    // alert('정보가 저장 되었습니다.');
    form.submit();
    return;
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
function pwd_check() {
    let pwd2 = $("#password2").val();
    let pwd3 = $("#password3").val();
    if (pwd2 == pwd3) {
        if ($("#pwdcheck").length === 0) {
            $("#pwdcheck").prepend("<span id='pwd_check'>비밀번호가 일치합니다.</span>");
        } else {
            $("#pwdcheck").text("비밀번호가 일치합니다.");
        }
    } else {
        if ($("#pwdcheck").length === 0) {
            $("#pwdcheck").prepend("<span id='pwd_check'>비밀번호가 일치하지 않습니다.</span>");
        } else {
            $("#pwdcheck").text("비밀번호가 일치하지 않습니다.");
        }
    }
}