const bmiValue = document.getElementById("bmiValue");
const statusText = document.getElementById("status");
const feedback = document.getElementById("feedback");
const historyList = document.getElementById("historyList");
const usernameInput = document.getElementById("username");
const userSelect = document.getElementById("userSelect");

const metricBtn = document.getElementById("metricBtn");
const imperialBtn = document.getElementById("imperialBtn");

const metricInputs = document.getElementById("metricInputs");
const imperialInputs = document.getElementById("imperialInputs");

let isMetric = true;
let chart;

// Toggle
metricBtn.onclick = () => {
  isMetric = true;
  metricBtn.classList.add("active");
  imperialBtn.classList.remove("active");
  metricInputs.style.display = "block";
  imperialInputs.style.display = "none";
};

imperialBtn.onclick = () => {
  isMetric = false;
  imperialBtn.classList.add("active");
  metricBtn.classList.remove("active");
  metricInputs.style.display = "none";
  imperialInputs.style.display = "block";
};

// Storage
function getUsers() {
  return JSON.parse(localStorage.getItem("users")) || {};
}

function saveUsers(users) {
  localStorage.setItem("users", JSON.stringify(users));
}

// Populate dropdown
function populateUsers() {
  const users = getUsers();
  userSelect.innerHTML = '<option value="">Select User</option>';

  Object.keys(users).forEach(user => {
    const option = document.createElement("option");
    option.value = user;
    option.textContent = user;
    userSelect.appendChild(option);
  });
}

// Select user
userSelect.addEventListener("change", (e) => {
  const user = e.target.value;
  if (!user) return;

  const users = getUsers();
  usernameInput.value = user;

  renderHistory(users[user]);
  drawChart(users[user]);
});

// Calculate BMI
document.getElementById("calculateBtn").onclick = () => {
  const name = usernameInput.value.trim();
  if (!name) return alert("Enter name");

  let users = getUsers();
  if (!users[name]) users[name] = [];

  const weight = parseFloat(document.getElementById("weight").value);
  if (!weight) return alert("Enter weight");

  let bmi;

  if (isMetric) {
    const cm = parseFloat(document.getElementById("heightCm").value);
    const m = parseFloat(document.getElementById("heightM").value);

    let h = cm ? cm / 100 : m;
    if (!h) return alert("Enter height");

    bmi = weight / (h * h);
  } else {
    const ft = parseFloat(document.getElementById("feet").value) || 0;
    const inch = parseFloat(document.getElementById("inches").value) || 0;

    const totalInches = ft * 12 + inch;
    if (!totalInches) return alert("Enter height");

    bmi = (weight / (totalInches ** 2)) * 703;
  }

  bmi = parseFloat(bmi.toFixed(1));
  bmiValue.textContent = bmi;

  users[name].push({
    bmi,
    date: new Date().toLocaleDateString()
  });

  saveUsers(users);
  localStorage.setItem("lastUser", name);

  populateUsers();
  renderHistory(users[name]);
  drawChart(users[name]);
  updateStatus(bmi, name, users[name]);
};

// Status
function updateStatus(bmi, name, history) {
  let advice = "";
  let trend = "";

  if (bmi < 18.5) {
    statusText.textContent = "Underweight";
    advice = "Increase calories & strength training.";
  } else if (bmi < 25) {
    statusText.textContent = "Normal";
    advice = "Maintain your lifestyle.";
  } else if (bmi < 30) {
    statusText.textContent = "Overweight";
    advice = "Add cardio & balanced diet.";
  } else {
    statusText.textContent = "Obese";
    advice = "Start structured fitness plan.";
  }

  if (history.length >= 2) {
    let last = history[history.length - 1].bmi;
    let prev = history[history.length - 2].bmi;

    if (last > prev) trend = "⚠️ BMI increasing";
    else if (last < prev) trend = "✅ BMI improving";
    else trend = "➡️ No change";
  }

  feedback.innerHTML = `
    🤖 <b>AI Insight</b><br>
    ${name}, ${advice}<br>
    ${trend}
  `;
}

// History
function renderHistory(data) {
  historyList.innerHTML = "";

  data.slice(-5).reverse().forEach(item => {
    const li = document.createElement("li");
    li.textContent = `${item.date} - BMI ${item.bmi}`;
    historyList.appendChild(li);
  });
}

// Chart
function drawChart(data) {
  const ctx = document.getElementById("bmiChart");

  if (chart) chart.destroy();

  chart = new Chart(ctx, {
    type: "line",
    data: {
      labels: data.map(d => d.date),
      datasets: [{
        label: "BMI Progress",
        data: data.map(d => d.bmi),
        borderWidth: 2,
        tension: 0.3
      }]
    }
  });
}

// Load on refresh
window.addEventListener("load", () => {
  populateUsers();

  const users = getUsers();
  const lastUser = localStorage.getItem("lastUser");

  if (lastUser && users[lastUser]) {
    usernameInput.value = lastUser;
    userSelect.value = lastUser;

    renderHistory(users[lastUser]);
    drawChart(users[lastUser]);
  }
});