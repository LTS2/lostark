document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    form.addEventListener("submit", function (event) {
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();

        if (!username || !password) {
            event.preventDefault();
            alert("아이디와 비밀번호를 모두 입력해 주세요.");
        }
    });
});