// 캐릭터와 목표 드롭다운 엘리먼트 가져오기
const characterSelect = document.getElementById('character');
const goalSelect = document.getElementById('goal');
const recruitmentCountSelect = document.getElementById('recruitmentCount');
const timeSelect = document.getElementById('time');

// 목표 레벨 정보를 설정 (서버에서 전달된 데이터)
const goalLevels = {
    "에기르 (하드1680)": 1680,
    "에기르 (노말1660)": 1660,
    "베히모스 (노말1640)": 1640,
    "카멘 (하드1630)": 1630,
    "카멘 (노말1610)": 1610,
    "상아탑 (하드1620)": 1620,
    "상아탑 (노말1600)": 1600,
    "카양겔 (하드1580)": 1580,
    "카양겔 (노말1540)": 1540
};

// 캐릭터 선택 시 목표 업데이트
characterSelect.addEventListener('change', function() {
    const selectedOption = this.options[this.selectedIndex];
    let characterLevel = selectedOption.getAttribute('data-level');

    // 쉼표가 포함된 경우 제거
    if (characterLevel) {
        characterLevel = characterLevel.replace(/,/g, '');
    }

    characterLevel = parseInt(characterLevel, 10);

    if (isNaN(characterLevel)) {
        console.error('캐릭터 레벨을 가져오는 데 실패했습니다.');
        return;
    }
    console.log("목표 필터링")
    // 캐릭터의 레벨을 확인하여 목표를 필터링
    updateGoalOptions(characterLevel);
});

// 목표 드롭다운 업데이트
function updateGoalOptions(level) {
    goalSelect.innerHTML = '<option value="">선택하세요</option>'; // 초기화
    let goalAdded = false;

    Object.keys(goalLevels).forEach(goal => {
        const goalLevel = goalLevels[goal];
        if (goalLevel <= level) {
            const option = document.createElement('option');
            option.value = goal;
            option.textContent = goal;
            goalSelect.appendChild(option);
            goalAdded = true;
        }
    });

    if (!goalAdded) {
        // 목표를 추가할 수 없으면 메시지 표시
        alert("캐릭터 레벨이 낮아 모집글을 작성할 수 없습니다.");
        goalSelect.disabled = true;
    } else {
        goalSelect.disabled = false;
    }
}


// 목표 변경 시 모집 인원 업데이트
goalSelect.addEventListener('change', function() {
    const selectedGoal = goalSelect.value;

    if (selectedGoal) {
        recruitmentCountSelect.innerHTML = ''; // 기존 옵션들 제거
        let maxCount = selectedGoal.includes('베히모스') ? 16 : 8;

        for (let i = 1; i <= maxCount; i++) {
            const option = document.createElement('option');
            option.value = i;
            option.textContent = i + '명';
            recruitmentCountSelect.appendChild(option);
        }
    }
});

// 시간 드롭다운 설정
for (let hour = 0; hour <= 24; hour++) {
    const value = hour === 24 ? '24:00' : (hour < 10 ? '0' + hour : hour) + ':00';
    const option = document.createElement('option');
    option.value = value;
    option.textContent = value;
    timeSelect.appendChild(option);
}

// 날짜 검증
document.getElementById('createPostForm').addEventListener('submit', function(event) {
    const startDate = document.getElementById('startDate').value;
    const today = new Date().toISOString().split('T')[0]; // 오늘 날짜를 "YYYY-MM-DD" 형식으로 가져옴

    if (startDate < today) {
        alert("과거 날짜는 선택할 수 없습니다.");
        event.preventDefault(); // 폼 제출을 중지
        return;
    }
});