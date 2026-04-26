const balance = document.getElementById("balance");
const income = document.getElementById("income");
const expense = document.getElementById("expense");
const list = document.getElementById("list");
const form = document.getElementById("form");
const text = document.getElementById("text");
const amount = document.getElementById("amount");
const modal = document.getElementById("modal");
const addBtn = document.getElementById("addBtn");

const lastExpense = document.getElementById("lastExpense");
const maxIncome = document.getElementById("maxIncome");
const monthTotal = document.getElementById("monthTotal");

const menuItems = document.querySelectorAll("#menu li");
const pages = document.querySelectorAll(".page");
const pageTitle = document.getElementById("pageTitle");

let transactions = JSON.parse(localStorage.getItem("transactions")) || [];
let chart, analyticsChart;

// Navigation
menuItems.forEach(item => {
  item.addEventListener("click", () => {
    menuItems.forEach(i => i.classList.remove("active"));
    item.classList.add("active");

    pages.forEach(p => p.classList.add("hidden"));
    document.getElementById(item.dataset.page).classList.remove("hidden");

    pageTitle.innerText = item.innerText;
  });
});

// Modal
addBtn.onclick = () => modal.style.display = "flex";
window.onclick = e => { if (e.target === modal) modal.style.display = "none"; };

// Add transaction
form.addEventListener("submit", e => {
  e.preventDefault();

  const transaction = {
    id: Date.now(),
    text: text.value,
    amount: +amount.value,
    date: new Date()
  };

  transactions.push(transaction);
  updateLocalStorage();
  init();

  modal.style.display = "none";
  form.reset();
});

// Init
function init() {
  list.innerHTML = "";

  if (transactions.length === 0) {
    list.innerHTML = "<p>No transactions yet</p>";
  }

  transactions.forEach(addTransactionDOM);
  updateValues();
  updateStats();
  drawChart();
  drawAnalyticsChart();
}

// DOM
function addTransactionDOM(t) {
  const li = document.createElement("li");
  li.classList.add(t.amount < 0 ? "minus" : "plus");

  li.innerHTML = `${t.text} <span>${formatMoney(t.amount)}</span>`;
  list.appendChild(li);
}

// Values
function updateValues() {
  const amounts = transactions.map(t => t.amount);
  const total = amounts.reduce((a,b)=>a+b,0);
  const inc = amounts.filter(a=>a>0).reduce((a,b)=>a+b,0);
  const exp = amounts.filter(a=>a<0).reduce((a,b)=>a+b,0);

  balance.innerText = formatMoney(total);
  income.innerText = formatMoney(inc);
  expense.innerText = formatMoney(Math.abs(exp));
}

// Stats
function updateStats() {
  const expenses = transactions.filter(t=>t.amount<0);
  const incomes = transactions.filter(t=>t.amount>0);

  lastExpense.innerText = expenses.length ?
    "Last Expense: "+formatMoney(expenses.slice(-1)[0].amount) : "Last Expense: ₹0";

  maxIncome.innerText = incomes.length ?
    "Highest Income: "+formatMoney(Math.max(...incomes.map(t=>t.amount))) : "₹0";

  const month = new Date().getMonth();
  const monthly = transactions.filter(t=>new Date(t.date).getMonth()===month);
  const total = monthly.reduce((a,b)=>a+b.amount,0);

  monthTotal.innerText = "This Month: "+formatMoney(total);
}

// Charts
function drawChart() {
  const ctx = document.getElementById("chart");
  if (chart) chart.destroy();

  const incomeTotal = transactions.filter(t=>t.amount>0).reduce((a,b)=>a+b.amount,0);
  const expenseTotal = transactions.filter(t=>t.amount<0).reduce((a,b)=>a+b.amount,0);

  chart = new Chart(ctx, {
    type: "doughnut",
    data: {
      labels: ["Income","Expense"],
      datasets: [{
        data: [incomeTotal, Math.abs(expenseTotal)],
        backgroundColor: ["#22c55e","#ef4444"]
      }]
    },
    options: { maintainAspectRatio:false, cutout:"70%" }
  });
}

function drawAnalyticsChart() {
  const ctx = document.getElementById("analyticsChart");
  if (analyticsChart) analyticsChart.destroy();

  const incomeTotal = transactions.filter(t=>t.amount>0).reduce((a,b)=>a+b.amount,0);
  const expenseTotal = transactions.filter(t=>t.amount<0).reduce((a,b)=>a+b.amount,0);

  analyticsChart = new Chart(ctx, {
    type: "bar",
    data: {
      labels: ["Income","Expense"],
      datasets: [{
        data: [incomeTotal, Math.abs(expenseTotal)],
        backgroundColor: ["#22c55e","#ef4444"]
      }]
    }
  });
}

// Helpers
function formatMoney(amount) {
  return new Intl.NumberFormat("en-IN", {
    style: "currency",
    currency: "INR"
  }).format(amount);
}

function updateLocalStorage() {
  localStorage.setItem("transactions", JSON.stringify(transactions));
}

function resetApp() {
  localStorage.clear();
  location.reload();
}

// Init
init();