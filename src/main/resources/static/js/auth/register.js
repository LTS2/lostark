document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('register-form');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirm-password');
    const usernameError = document.getElementById('username-error');
    const passwordError = document.getElementById('password-error');
    const confirmPasswordError = document.getElementById('confirm-password-error');
    const checkUsernameButton = document.getElementById('check-username');
    let isUsernameAvailable = false; // 중복 검사 결과 저장

    // 아이디 중복 검사
    checkUsernameButton.addEventListener('click', function() {
        const username = usernameInput.value;
        if (username) {
            fetch(`/api/user/check-username?username=${encodeURIComponent(username)}`)
                .then(response => response.json())
                .then(data => {
                    if (!data.available) {
                        usernameError.textContent = '이미 사용 중인 아이디입니다.';
                        usernameError.style.color = 'red';
                        isUsernameAvailable = false;
                    } else {
                        usernameError.textContent = '사용 가능한 아이디입니다.';
                        usernameError.style.color = 'green';
                        isUsernameAvailable = true;
                    }
                })
                .catch(error => {
                    usernameError.textContent = '아이디 중복 검사 중 오류가 발생했습니다.';
                    isUsernameAvailable = false;
                });
        } else {
            usernameError.textContent = '아이디를 입력해 주세요.';
            isUsernameAvailable = false;
        }
    });

    // 폼 유효성 검사
    form.addEventListener('submit', function(event) {
        let valid = true;

        // 이전 오류 메시지 지우기
        usernameError.textContent = '';
        passwordError.textContent = '';
        confirmPasswordError.textContent = '';

        // 아이디 중복 검사 여부 확인
        if (!isUsernameAvailable) {
            valid = false;
            usernameError.textContent = '아이디 중복 검사를 먼저 진행해 주세요.';
        }

        // 비밀번호 확인 검사
        if (passwordInput.value !== confirmPasswordInput.value) {
            confirmPasswordError.textContent = '비밀번호가 일치하지 않습니다.';
            valid = false;
        }

        // 유효하지 않으면 폼 제출 방지
        if (!valid) {
            event.preventDefault();
        }
    });
});