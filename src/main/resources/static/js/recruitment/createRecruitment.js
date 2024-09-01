// 시간 드롭다운 설정
const timeSelect = document.getElementById('time');
for (let hour = 0; hour <= 24; hour++) {
    const value = hour === 24 ? '24:00' : (hour < 10 ? '0' + hour : hour) + ':00';
    const option = document.createElement('option');
    option.value = value;
    option.textContent = value;
    timeSelect.appendChild(option);
}

// 모집 인원 설정
const goalSelect = document.getElementById('goal');
const recruitmentCountSelect = document.getElementById('recruitmentCount');

goalSelect.addEventListener('change', function() {
    const selectedGoal = goalSelect.value;
    recruitmentCountSelect.innerHTML = ''; // 기존 옵션들 제거

    let maxCount = selectedGoal.includes('베히모스') ? 16 : 8;

    for (let i = 1; i <= maxCount; i++) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = i + '명';
        recruitmentCountSelect.appendChild(option);
    }
});

// 날짜 검증
document.getElementById('createPostForm').addEventListener('submit', function(event) {
    var startDate = document.getElementById('startDate').value;
    var today = new Date().toISOString().split('T')[0]; // 오늘 날짜를 "YYYY-MM-DD" 형식으로 가져옴

    if (startDate < today) {
        alert("과거 날짜는 선택할 수 없습니다.");
        event.preventDefault(); // 폼 제출을 중지
    }
});