function openModal() {
    document.getElementById('guestbook-modal').style.display = 'flex';
}

function closeModal() {
    document.getElementById('guestbook-modal').style.display = 'none';
}

window.onclick = function(event) {
    let modal = document.getElementById("guestbook-modal");
    let chModal = document.getElementById("character-modal");
    if (event.target == modal || event.target == chModal) {
        modal.style.display = "none";
    }
}

/* 모달 창 보이기 */
function showCharacterModal() {
    document.getElementById("character-modal").style.display = "block";
}

/* 모달 창 닫기 */
function closeCharacterModal() {
    document.getElementById("character-modal").style.display = "none";
}

/* 코멘트 작성 */
function submitGuestbook() {
    const comment = document.getElementById('guestbook-comment').value;
    const userId = document.getElementById('userId').value;
    const username = document.getElementById('username').value;
    const targetUserId = document.getElementById('targetUserId').value;
    const recruitmentId = document.getElementById('recruitmentId').value;

    if (!comment) {
        alert('내용을 입력하세요.');
        return;
    }

    fetch('/mypage/guestbook', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: userId,
            targetUserId: targetUserId,
            username: username,
            comment: comment,
            recruitmentId: recruitmentId
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 오류...');
            }
            return response.text();
        })
        .then(message => {
            alert(message);
            document.getElementById('guestbook-comment').value = "";
            closeModal();
        })
        .catch(error => {
            console.error('ERROR :: ', error);
            alert('방명록 등록 중 오류가 발생했습니다.');
        });
}

function applyCharacter() {
    let selectedCharacter = document.getElementById("character-select").value;
    let recruitmentId = document.getElementById("recruitment-id").value;
    if (selectedCharacter) {
        document.getElementById("selected-character").value = selectedCharacter;
        document.getElementById("character-form").submit();
    } else {
        alert("캐릭터를 선택하세요.");
    }
}