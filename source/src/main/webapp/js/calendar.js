"use strict";

document.addEventListener('DOMContentLoaded', function () {
  // 月入力変更時にURL遷移
  const monthInput = document.getElementById('monthInput');
  if (monthInput) {
    monthInput.addEventListener('change', function () {
      const ym = this.value;
      if (!ym) return;

      const parts = ym.split('-');
      const year = parts[0];
      const month = parseInt(parts[1]);

      const url = '/D2/CalendarServlet?month=' + month + '&year=' + year;
      window.location.href = url;
    });
  }

  // 健康記録のチェック制御
  const checkMap = {
    cbExercise: 'exercise',
    cbAlcohol: 'alcohol',
    cbSmoke: 'nosmoke',
    cbSleep: 'sleep',
    cbCalorieIntake: 'calorieIntake',
    cbCalorieConsu: 'calorieConsu',
    cbMemo: 'free'
  };

  const healthRecordChk = document.getElementById('cbHealth');

  if (healthRecordChk) {
    for (const [chkId, className] of Object.entries(checkMap)) {
      const checkbox = document.getElementById(chkId);
      if (checkbox) {
        checkbox.addEventListener('change', function () {
          toggleSection(className, this.checked && healthRecordChk.checked);
        });
      }
    }

    healthRecordChk.addEventListener('change', function () {
      const enabled = this.checked;
      document.querySelectorAll('.health-record').forEach(el => {
        el.style.display = enabled ? '' : 'none';
      });

      for (const [chkId, className] of Object.entries(checkMap)) {
        const childChk = document.getElementById(chkId);
        if (childChk) {
          toggleSection(className, enabled && childChk.checked);
        }
      }
    });
  }

  function toggleSection(className, show) {
    document.querySelectorAll('.' + className).forEach(el => {
      el.style.display = show ? '' : 'none';
    });
  }

  // 報酬記録チェックボックス制御
  const rewardChk = document.getElementById('cbReward');
  if (rewardChk) {
    rewardChk.addEventListener('change', function () {
      const show = this.checked;
      document.querySelectorAll('.reward-record').forEach(el => {
        el.style.display = show ? '' : 'none';
      });
    });

    // 初期表示制御
    document.querySelectorAll('.reward-record').forEach(el => {
      el.style.display = rewardChk.checked ? '' : 'none';
    });
  }
});

// ポップアップ制御
function openPopup(date) {
  document.getElementById("popup").style.display = "block";
  document.getElementById("popupDate").value = date;

  fetch('HealthRecordFormServlet?date=' + date)
    .then(response => response.text())
    .then(script => {
      console.log("返ってきたスクリプト:");
      console.log(script);
      eval(script);
    });
}

function closePopup() {
  document.getElementById("popup").style.display = "none";
}



