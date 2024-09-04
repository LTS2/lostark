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

    const goal = document.getElementById('goal').value.trim();
    const recruitmentCount = document.getElementById('recruitmentCount').value.trim();
    const time = document.getElementById('time').value.trim();
    const challengeTime = document.getElementById('challengeTime').value.trim();
    const proficiency = document.getElementById('proficiency').value.trim();
    const startDate = document.getElementById('startDate').value;

    const boxes = document.querySelectorAll('.box');

    boxes.forEach(function (box) {
        const boxGoal = box.querySelector('.box-details p:nth-of-type(1) span').textContent.trim();
        const boxRecruitmentCount = box.querySelector('.box-details p:nth-of-type(3) span').textContent.trim();
        const boxTime = box.querySelector('.box-details p:nth-of-type(2) span').textContent.trim();
        const boxChallengeTime = box.querySelector('.box-details p:nth-of-type(5) span').textContent.trim();
        const boxProficiency = box.querySelector('.box-details p:nth-of-type(4) span').textContent.trim();
        const boxDate = box.querySelector('.box-details p:nth-of-type(6) span') ? box.querySelector('.box-details p:nth-of-type(6) span').textContent.trim() : '';

        const filterDate = startDate ? new Date(startDate) : null;
        const boxDateObj = boxDate ? new Date(boxDate) : null;

        const filterDateString = filterDate ? filterDate.toISOString().split('T')[0] : null;
        const boxDateString = boxDateObj ? boxDateObj.toISOString().split('T')[0] : null;

        const matchesGoal = goal === "" || goal === boxGoal;
        const matchesRecruitmentCount = recruitmentCount === "" || recruitmentCount === boxRecruitmentCount;
        const matchesTime = time === "" || time === boxTime;
        const matchesChallengeTime = challengeTime === "" || challengeTime === boxChallengeTime;
        const matchesProficiency = proficiency === "" || proficiency === boxProficiency;
        const matchesDate = !filterDate || (boxDateString && boxDateString === filterDateString);

        if (matchesGoal && matchesRecruitmentCount && matchesTime && matchesChallengeTime && matchesProficiency && matchesDate) {
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
    fetch('/api/recruitment/check-login')
        .then(response => response.json())
        .then(data => {
            console.log('Login and character check response:', data); // 응답 데이터 로그로 확인
            if (data.loggedIn) {
                if (data.hasCharacter) {
                    window.location.href = '/api/recruitment/create'; // 캐릭터가 등록된 경우 모집글 작성 페이지로 이동
                } else {
                    alert('캐릭터를 먼저 등록해주세요.'); // 캐릭터가 등록되지 않은 경우 알림창 표시
                }
            } else {
                alert('로그인 후 이용해주세요.'); // 로그인하지 않은 경우 알림창 표시
            }
        })
        .catch(error => {
            console.error('Error checking login and character status:', error);
            alert('로그인 및 캐릭터 상태 확인 중 오류가 발생했습니다.'); // 오류 발생 시 알림창 표시
        });
});