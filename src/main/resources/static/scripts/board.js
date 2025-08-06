function idcheck() {
    var userid = document.join.userid.value.trim();
    if (userid == "") {
        alert("아이디를 입력하세요");
        return;
    }

    var opt = "width=500, height=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no";
    window.open("idcheck?userid="+userid, "idcheck", opt);
}

function deleteMember(userid) {
    if ( confirm("저장된 회원정보가 모두 삭제됩니다. 탈퇴하시겠습니까?") ) {
        location.href = "deleteMember?userid="+userid;
    }
}

function selectImg() {
    var opt = "width=450,height=200,toolbar=no,menubar=no,resizable=no";
    window.open("selectimg", "selectimg", opt);
}

function deleteBoard(num) {
    if ( confirm("게시물을 삭제하시겠습니까?") ) {
        var pass = prompt("삭제 비밀번호를 입력하세요","");
        location.href = "deleteBoard?num="+num+"&pass="+pass;
    }
}

function replyCheck() {
    if ( document.addRep.content.value == "" ) {
        alert("내용을 입력하세요.");
        return false;
    }
    return true;
}

function deleteReply(replynum, boardnum) {
    if ( confirm("댓글을 삭제하시겠습니까?") ) {
        location.href = "deleteReply?replynum="+replynum+"&boardnum="+boardnum;
    }
}

function updatePass(num) {
    var opt = "width=500, height=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no";
    window.open("updatePassOpenWindow?num="+num, "updatePass", opt);
}