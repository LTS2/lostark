// 시간 드롭다운 설정
const timeSelect = document.getElementById('time');
for (let hour = 0; hour <= 24; hour++) {
    const value = hour === 24 ? '24:00' : (hour < 10 ? '0' + hour : hour) + ':00';
    const option = document.createElement('option');
    option.value = value;
    option.textContent = value;
    timeSelect.appendChild(option);
}