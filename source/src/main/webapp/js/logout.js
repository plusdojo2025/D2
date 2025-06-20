function confirmLogout() {
    const userConfirmed = confirm("本当にログアウトしますか？");
    if (userConfirmed) {
        // ログアウト処理へリダイレクト
        window.location.href = "LogoutServlet";
        alert("ログアウトしました。本日もお疲れさまでした。");
    } else {
        alert("ログアウトをキャンセルしました。");
    }
}