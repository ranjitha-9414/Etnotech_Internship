let is24Hour = false;

// ELEMENTS
const timeEl = document.getElementById("time");
const dateEl = document.getElementById("date");
const greetingEl = document.getElementById("greeting");

const themeBtn = document.getElementById("themeToggle");
const formatBtn = document.getElementById("formatToggle");

const soundSelect = document.getElementById("soundSelect");
const soundInput = document.getElementById("customSound");
const volumeControl = document.getElementById("volumeControl");
const previewBtn = document.getElementById("previewSound");

const alarmBtn = document.getElementById("setAlarmBtn");
const alarmInput = document.getElementById("alarmTime");
const alarmStatus = document.getElementById("alarmStatus");

const stopBtn = document.getElementById("stopAlarm");
const snoozeBtn = document.getElementById("snoozeAlarm");
const alarmControls = document.getElementById("alarmControls");

// THEME TOGGLE
themeBtn.onclick = () => {
  document.body.classList.toggle("light");
  themeBtn.textContent = document.body.classList.contains("light") ? "☀️" : "🌙";
};

// FORMAT TOGGLE
formatBtn.onclick = () => {
  is24Hour = !is24Hour;
  formatBtn.textContent = is24Hour ? "Switch to 12H" : "Switch to 24H";
};

// SOUND MAP
const soundMap = {
  default: "https://actions.google.com/sounds/v1/alarms/alarm_clock.ogg",
  bell: "https://actions.google.com/sounds/v1/cartoon/clang_and_wobble.ogg",
  alarm: "https://actions.google.com/sounds/v1/alarms/digital_watch_alarm_long.ogg"
};

let alarmSound = new Audio(soundMap.default);
alarmSound.loop = true;
let customAudio = null;

// SOUND SELECT
soundSelect.onchange = () => {
  alarmSound = new Audio(soundMap[soundSelect.value]);
  alarmSound.loop = true;
  alarmSound.volume = volumeControl.value;
  customAudio = null;
};

// CUSTOM SOUND
soundInput.onchange = (e) => {
  const file = e.target.files[0];
  if (file) {
    customAudio = new Audio(URL.createObjectURL(file));
    customAudio.loop = true;
    customAudio.volume = volumeControl.value;
  }
};

// VOLUME
volumeControl.oninput = () => {
  if (alarmSound) alarmSound.volume = volumeControl.value;
  if (customAudio) customAudio.volume = volumeControl.value;
};

// PREVIEW
previewBtn.onclick = () => {
  const sound = customAudio || alarmSound;
  sound.currentTime = 0;
  sound.play();
  setTimeout(() => sound.pause(), 3000);
};

// ALARM
let alarmTime = null;
let alarmSet = false;

alarmBtn.onclick = () => {
  alarmTime = alarmInput.value;
  alarmSet = true;

  const s = customAudio || alarmSound;
  s.play().then(() => {
    s.pause();
    s.currentTime = 0;
  });

  alarmStatus.textContent = "Alarm set";
};

// STOP
function stopSound() {
  if (customAudio) customAudio.pause();
  if (alarmSound) alarmSound.pause();
}

stopBtn.onclick = () => {
  stopSound();
  alarmControls.style.display = "none";
};

// SNOOZE
snoozeBtn.onclick = () => {
  stopSound();
  const now = new Date();
  now.setMinutes(now.getMinutes() + 5);

  alarmTime = `${String(now.getHours()).padStart(2,'0')}:${String(now.getMinutes()).padStart(2,'0')}`;
  alarmSet = true;

  alarmControls.style.display = "none";
};

// CLOCK
function updateClock() {
  const now = new Date();

  let h = now.getHours();
  let m = now.getMinutes();
  let s = now.getSeconds();

  const ampm = h >= 12 ? "PM" : "AM";

  greetingEl.textContent =
    h < 12 ? "Good Morning 🌅" :
    h < 18 ? "Good Afternoon ☀️" :
    "Good Evening 🌙";

  let displayH = is24Hour ? h : (h % 12 || 12);

  timeEl.textContent =
    `${String(displayH).padStart(2,'0')}:${String(m).padStart(2,'0')}:${String(s).padStart(2,'0')} ${is24Hour ? "" : ampm}`;

  dateEl.textContent = now.toDateString();

  document.getElementById("indiaTime").textContent =
    new Date().toLocaleTimeString("en-US",{timeZone:"Asia/Kolkata"});

  document.getElementById("nyTime").textContent =
    new Date().toLocaleTimeString("en-US",{timeZone:"America/New_York"});

  document.getElementById("londonTime").textContent =
    new Date().toLocaleTimeString("en-US",{timeZone:"Europe/London"});

  if (alarmSet) {
    const [ah, am] = alarmTime.split(":").map(Number);
    if (now.getHours() === ah && now.getMinutes() === am) {
      const sound = customAudio || alarmSound;
      sound.play();
      alarmControls.style.display = "block";
      alarmSet = false;
    }
  }
}

setInterval(updateClock, 1000);
updateClock();