const value = document.getElementById("value");
const increaseBtn = document.querySelector(".increase");
const decreaseBtn = document.querySelector(".decrease");
const resetBtn = document.querySelector(".reset");
const historyList = document.getElementById("history");
const toggle = document.getElementById("themeToggle");

// Load from localStorage
let count = localStorage.getItem("count")
  ? parseInt(localStorage.getItem("count"))
  : 0;

// Update UI
function updateUI() {
  value.textContent = count;

  localStorage.setItem("count", count);

  value.classList.remove("positive", "negative", "zero");

  if (count > 0) value.classList.add("positive");
  else if (count < 0) value.classList.add("negative");
  else value.classList.add("zero");
}

// Animation
function animateValue() {
  value.classList.add("animate");
  setTimeout(() => value.classList.remove("animate"), 200);
}

// History
function updateHistory(action) {
  const li = document.createElement("li");
  li.textContent = `${action}: ${count}`;
  historyList.prepend(li);

  if (historyList.children.length > 5) {
    historyList.removeChild(historyList.lastChild);
  }
}

// Buttons
increaseBtn.addEventListener("click", () => {
  count++;
  animateValue();
  updateUI();
  updateHistory("Increased");
});

decreaseBtn.addEventListener("click", () => {
  count--;
  animateValue();
  updateUI();
  updateHistory("Decreased");
});

resetBtn.addEventListener("click", () => {
  count = 0;
  animateValue();
  updateUI();
  updateHistory("Reset");
});

// Keyboard support
document.addEventListener("keydown", (e) => {
  if (e.key === "ArrowUp") count++;
  else if (e.key === "ArrowDown") count--;
  else if (e.key === "0") count = 0;

  animateValue();
  updateUI();
});

// Theme toggle
toggle.addEventListener("click", () => {
  document.body.classList.toggle("light");
});

// Init
updateUI();