// 시간 드롭다운 설정
const select = document.getElementById('time');
for (let hour = 0; hour <= 24; hour++) {
    const value = hour === 24 ? '24:00' : (hour < 10 ? '0' + hour : hour) + ':00';
    const option = document.createElement('option');
    option.value = value;
    option.textContent = value;
    select.appendChild(option);
}

// 필터 폼 제출 이벤트 처리
document.getElementById('filterForm').addEventListener('submit', function (event) {
    event.preventDefault();

    // 필터 값 가져오기
    const goal = document.getElementById('goal').value;
    const day = document.getElementById('day').value;
    const time = document.getElementById('time').value;
    const challengeTime = document.getElementById('challengeTime').value;
    const proficiency = document.getElementById('proficiency').value;
    const startDate = document.getElementById('startDate').value;

    // 하위 박스들 필터링
    const boxes = document.querySelectorAll('.box');
    boxes.forEach(function (box) {
        const boxGoal = box.querySelector('.box-details p:nth-of-type(1) span').textContent.trim();
        const boxDay = box.querySelector('.box-details p:nth-of-type(3) span').textContent.trim();
        const boxTime = box.querySelector('.box-details p:nth-of-type(2) span').textContent.trim();
        const boxChallengeTime = box.querySelector('.box-details p:nth-of-type(4) span').textContent.trim();
        const boxProficiency = box.querySelector('.box-details p:nth-of-type(5) span').textContent.trim();
        const boxDate = box.querySelector('.box-details p:nth-of-type(6) span') ? box.querySelector('.box-details p:nth-of-type(6) span').textContent.trim() : '';

        // 필터 조건에 따라 박스 표시 여부 결정
        const filterDate = startDate ? new Date(startDate) : null;
        const boxDateObj = boxDate ? new Date(boxDate) : null;

        if ((goal === "" || goal === boxGoal) &&
            (day === "" || day === boxDay) &&
            (time === "" || time === boxTime) &&
            (challengeTime === "" || challengeTime === boxChallengeTime) &&
            (proficiency === "" || proficiency === boxProficiency) &&
            (!filterDate || (boxDateObj && boxDateObj >= filterDate))) {
            box.style.display = "block";
        } else {
            box.style.display = "none";
        }
    });
});
// 하위 박스 클릭 이벤트 처리
document.querySelectorAll('.box').forEach(box => {
    box.addEventListener('click', function() {
        const url = this.getAttribute('data-url');
        if (url) {
            window.location.href = url;
        }
    });
});
// 모집글 만들기 버튼 클릭 이벤트 처리
document.getElementById('createPostButton').addEventListener('click', function () {
    fetch('/api/user/check-login')
        .then(response => response.json())
        .then(data => {
            console.log('Login status response:', data); // 응답 데이터 로그로 확인
            if (data.loggedIn) {
                window.location.href = '/api/recruitment/create'; // 모집글 작성 페이지로 이동
            } else {
                alert('로그인 후 이용해주세요.'); // 로그인하지 않은 경우 알림창 표시
            }
        })
        .catch(error => {
            console.error('Error checking login status:', error);
            alert('로그인 상태 확인 중 오류가 발생했습니다.'); // 오류 발생 시 알림창 표시
        });
});
