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
  
   if (exerciseCount === 0) addExercise();
  if (alcoholCount === 0) addAlcohol();

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

function generateCalendar(year, month) {
    const firstDay = new Date(year, month - 1, 1);
    const lastDay = new Date(year, month, 0).getDate();
    const startDay = firstDay.getDay();

    const calendarBody = document.getElementById("calendar-body");
    calendarBody.innerHTML = "";
    let row = document.createElement("tr");

    for (let i = 0; i < startDay; i++) {
        row.appendChild(document.createElement("td"));
    }

    for (let day = 1; day <= lastDay; day++) {
        if ((startDay + day - 1) % 7 === 0 && day !== 1) {
            calendarBody.appendChild(row);
            row = document.createElement("tr");
        }

        const dateStr = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
        const cell = document.createElement("td");
        cell.addEventListener("click", function () {
            openPopup(dateStr);});
        cell.innerHTML = `<strong>${day}</strong><br>`;

        if (rwList[dateStr]) {
            cell.classList.add("has-reward");
            rwList[dateStr].forEach(item => {
                cell.innerHTML += `<div class="reward-record">${item}</div>`;
            });
        }

        if (hwList[dateStr]) {
            cell.classList.add("has-health-record");
            cell.innerHTML += `<div class="health-record">${hwList[dateStr].nowWeight}kg</div>`;
            cell.innerHTML += `<div class="health-record calorieIntake">${hwList[dateStr].calorieIntake}kcal</div>`;
            cell.innerHTML += `<div class="health-record calorieConsu">${hwList[dateStr].calorieConsu}kcal</div>`;
            cell.innerHTML += `<div class="health-record nosmoke">${hwList[dateStr].nosmoke} 喫煙</div>`;
            cell.innerHTML += `<div class="health-record sleep">${hwList[dateStr].sleepHours} 時間</div>`;
            cell.innerHTML += `<div class="health-record free">${hwList[dateStr].free}</div>`;
        }

        if (heList[dateStr]) {
            cell.classList.add("has-health-exercise");
            heList[dateStr].forEach(item => {
                cell.innerHTML += `<div class="health-record exercise">${item.exerciseType}</div>`;
                cell.innerHTML += `<div class="health-record exercise">${item.exerciseTime}分</div>`;
                cell.innerHTML += `<div class="health-record exercise">${item.calorieConsu}kcal</div>`;
            });
        }

        if (haList[dateStr]) {
            cell.classList.add("has-health-alcohol");
            haList[dateStr].forEach(item => {
                cell.innerHTML += `<div class="health-record alcohol">${item.pureAlcoholConsumed}g</div>`;
                cell.innerHTML += `<div class="health-record alcohol">${item.alcoholContent}%</div>`;
                cell.innerHTML += `<div class="health-record alcohol">${item.alcoholConsumed}ml</div>`;
            });
        }

        row.appendChild(cell);
    }

    calendarBody.appendChild(row);
}

generateCalendar(year, month);

