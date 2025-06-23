// js/UserRegist.js

function validateUserRegistForm() {
  const id = document.getElementById("id").value.trim();
  const pw = document.getElementById("pw").value.trim();
  const pwConfirm = document.getElementById("pwConfirm").value.trim();

  if (!id || !pw || !pwConfirm) {
    alert("すべての項目を入力してください。");
    return false;
  }

  if (pw !== pwConfirm) {
    alert("パスワードが一致していません。");
    return false;
  }

  return true; // すべてOKなので送信
}