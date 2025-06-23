
function validateLoginForm() {
  const id = document.getElementById("id").value.trim();
  const pw = document.getElementById("pw").value.trim();

  if (!id && !pw) {
    alert("IDとパスワードを入力してください。");
    return false;
  } else if (!id) {
    alert("IDを入力してください。");
    return false;
  } else if (!pw) {
    alert("パスワードを入力してください。");
    return false;
  }
  
   return true; // 問題ないので送信!
  }

