let username = document.getElementById('username').value;
let stompClient = "";

/* STOMP 연결 */
function connect() {
    let socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/sub/chatroom', function (message) {
            const receivedMessage = JSON.parse(message.body);
            if (receivedMessage.type === 'ENTER' || receivedMessage.type === 'EXIT') {
                updateParticipants(receivedMessage.message);
                showMessage(receivedMessage);
            } else {
                showMessage(receivedMessage);
            }
        });

        stompClient.send("/pub/chat.addUser", {}, JSON.stringify({sender: username, type: 'ENTER'}));
        document.getElementById("message-input").value = '';
    }, function(error) {
        console.log('STOMP 오류: ' + error);
    });
}


/* 참여자 목록 업데이트 */
function updateParticipants(participants) {
    let participantList = document.getElementById("participant-list");
    participantList.innerHTML = '';

    let cleanedParticipants = participants.replace(/[\[\]']+/g, '');
    let participantsArr = cleanedParticipants.split(',').map(participant => participant.trim()).filter(participant => participant); // 쉼표로 분리하고 공백 제거 및 빈 문자열 필터링

    participantsArr.forEach(function(participant) {
        let participantElement = document.createElement('li');
        participantElement.textContent = participant;
        participantList.appendChild(participantElement);
    });
}



/* 채팅방 메세지 전송 */
function sendMessage() {
    let messageContent = document.getElementById("message-input").value;
    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            message: messageContent,
            type: 'TALK'
        };
        stompClient.send("/pub/chat.sendMessage", {}, JSON.stringify(chatMessage));
        document.getElementById("message-input").value = '';
    } else {
        console.log('stompClient가 초기화되지 않았습니다');
    }
}

/* 채팅방 메세지 출력 */
function showMessage(message) {
    let chatContainer = document.querySelector(".wrap");

    let chatElement = document.createElement('div');
    chatElement.className = 'chat';

    if (message.type === 'ENTER') {
        chatElement.className += ' enter-message';
        chatElement.textContent = message.sender + " 님이 입장하셨습니다.";
    } else if (message.type === 'EXIT') {
        chatElement.className += ' exit-message';
        chatElement.textContent = message.sender + " 님이 퇴장하셨습니다.";
    } else {
        chatElement.className += ' ' + (message.sender === username ? 'ch2' : 'ch1');

        let iconContainer = document.createElement('div');
        iconContainer.className = 'icon-container';

        let iconElement = document.createElement('div');
        iconElement.className = 'icon';
        iconElement.innerHTML = '<i class="fa-solid fa-user"></i>';

        let senderElement = document.createElement('div');
        senderElement.className = 'sender-name';
        senderElement.textContent = message.sender;

        iconContainer.appendChild(iconElement);
        iconContainer.appendChild(senderElement);

        let textElement = document.createElement('div');
        textElement.className = 'textbox';
        textElement.textContent = message.message;

        chatElement.appendChild(iconContainer);
        chatElement.appendChild(textElement);
    }

    chatContainer.appendChild(chatElement);
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

/* 채팅방 나가기 함수 */
function exitChat() {
    if (stompClient) {
        let chatMessage = {
            sender: username,
            message: '',
            type: 'EXIT'
        };
        stompClient.send("/pub/chat.sendMessage", {}, JSON.stringify(chatMessage));
        stompClient.disconnect(() => {
            window.location.href = "/";
        });
    } else {
        window.location.href = "/";
    }
}

/* 채팅방 나가기 event */
document.getElementById("exit-button").addEventListener("click", () => {
    const confirmed = confirm('정말 채팅방을 나가시겠습니까?');
    if (confirmed) {
        exitChat();
    }
});

/* Enter키 이벤트 */
document.addEventListener('DOMContentLoaded', function() {
    connect();

    let messageInput = document.getElementById("message-input");
    if (messageInput) {
        messageInput.addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                sendMessage();
            }
        });
    }
});