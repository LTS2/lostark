
document.getElementById("current-profile-image").addEventListener("click", function() {
    document.getElementById("profile-image").click();
});

// 파일 선택 시 이미지 미리보기 업데이트
document.getElementById("profile-image").addEventListener("change", function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById("current-profile-image").src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
});

document.getElementById("profile-btn").addEventListener("click", function() {
    const fileInput = document.getElementById("profile-image");
    if (fileInput.files.length > 0) {
        document.getElementById("profile-form").submit();
    } else {
        alert("프로필 이미지를 선택해주세요.");
    }
});