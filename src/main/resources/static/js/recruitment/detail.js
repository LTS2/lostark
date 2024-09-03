function openModal() {
    document.getElementById('guestbook-modal').style.display = 'flex';
}

function closeModal() {
    document.getElementById('guestbook-modal').style.display = 'none';
}

window.onclick = function(event) {
let modal = document.getElementById("guestbookModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}