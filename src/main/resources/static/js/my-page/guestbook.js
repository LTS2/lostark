// function submitGuestbook() {
//     const comment = document.getElementById('guestbook-comment').value;
//     const userId = document.getElementById('userId').value;
//     const username = document.getElementById('username').value;
//     const targetUserId = document.getElementById('targetUserId').value;
//     if (!comment) {
//         alert('내용을 입력하세요.');
//         return;
//     }
//
//     fetch('/mypage/guestbook', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify({
//             userId: userId,
//             targetUserId: targetUserId,
//             username: username,
//             comment: comment
//         })
//     })
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('네트워크 오류...');
//             }
//             return response.text();
//         })
//         .then(message => {
//             alert(message);
//             document.getElementById('guestbook-comment').value = "";
//             closeModal();
//         })
//         .catch(error => {
//             console.error('ERROR :: ', error);
//             alert('방명록 등록 중 오류가 발생했습니다.');
//         });
// }