function passwordFind() {
    let email = $("#email").val();
    let username = $("#username").val();
    let submit = $("#submit").val();
    let certificateKeyText = $("#certificateKeyText");
    let certificateKey = $("#certificateKey").hide();
    let certificateButton = $("#certificateButton").html();

    $("#certificateKeyText").removeAttr('hidden');
    $("#certificateKey").removeAttr('hidden');
    $("#certificateKeyCheck").removeAttr('hidden');
    $("#certificateKey").removeAttr('style');
    console.log(certificateButton);
    $.ajax({
        url: "/user/password/find", // 클라이언트가 HTTP 요청을 보낼 서버의 URL 주소
        data: {email: email,username:username},  // HTTP 요청과 함께 서버로 보낼 데이터
        method: "GET",   // HTTP 요청 메소드(GET, POST 등)
        dataType: "html" // 서버에서 보내줄 데이터의 타입
    })
        // HTTP 요청이 성공하면 요청한 데이터가 done() 메소드로 전달됨.
        .done(function (json) {
            if (json.length == 0){
            } else {

                alert(json);
            }

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
function KeyCheck(){
    let email = $("#email").val();
    let username = $("#username").val();
    let certificateKey = $("#certificateKey").val();


    console.log(certificateKey);
    $.ajax({
        url: "/user/password/check", // 클라이언트가 HTTP 요청을 보낼 서버의 URL 주소
        data: {email: email,username:username, certificateKey:certificateKey},  // HTTP 요청과 함께 서버로 보낼 데이터
        method: "GET",   // HTTP 요청 메소드(GET, POST 등)
        dataType: "html" // 서버에서 보내줄 데이터의 타입
    })
        // HTTP 요청이 성공하면 요청한 데이터가 done() 메소드로 전달됨.
        .done(function (json) {
            if (json == "인증이 되었습니다."){
                alert(json);
                $("#submit").removeAttr('hidden');
                $("#password2").removeAttr('hidden');
                $("#password3").removeAttr('hidden');
                $("#password2Text").removeAttr('hidden');
                $("#password3Text").removeAttr('hidden');
            } else {
                alert(json);
            }
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