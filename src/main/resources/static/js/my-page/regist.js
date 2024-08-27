// /js/my-page/regist.js
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('regist-form');
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // 기본 폼 제출 동작 방지

        const username = document.getElementById('username').value;
        const resultDiv = document.getElementById('result');
        const errorDiv = document.getElementById('error');
        const charactersSection = document.getElementById('characters-section');
        const charactersTbody = document.getElementById('characters-tbody');

        // 결과 초기화
        resultDiv.style.display = 'none';
        errorDiv.style.display = 'none';
        charactersSection.style.display = 'none';
        charactersTbody.innerHTML = '';

        fetch('/api/user/lostArk/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'username': username
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.character) {
                    // 캐릭터 정보가 있을 경우
                    document.getElementById('server-name').textContent = data.character.serverName;
                    document.getElementById('item-max-level').textContent = data.character.itemMaxLevel;
                    document.getElementById('character-level').textContent = data.character.characterLevel;
                    document.getElementById('character-name').textContent = data.character.characterName;
                    document.getElementById('character-name-hidden').value = data.character.characterName;
                    resultDiv.style.display = 'block';
                } else {
                    // 캐릭터 정보가 없을 경우
                    errorDiv.style.display = 'block';
                }

                if (data.characters && data.characters.length > 0) {
                    // 캐릭터 목록이 있을 경우
                    data.characters.forEach(character => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td>${character.serverName}</td>
                        <td>${character.characterClassName}</td>
                        <td>${character.characterName}</td>
                        <td>${character.itemMaxLevel}</td>
                    `;
                        charactersTbody.appendChild(row);
                    });
                    charactersSection.style.display = 'block';
                }
            })
            .catch(error => console.error('Error:', error));
    });
});
