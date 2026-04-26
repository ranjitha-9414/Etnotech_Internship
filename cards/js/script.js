/* HERO IMAGES (DIFFERENT FROM GALLERY) */
const heroImages = [
  "https://images.unsplash.com/photo-1497366216548-37526070297c",
  "https://images.unsplash.com/photo-1503387762-592deb58ef4e",
  "https://images.unsplash.com/photo-1512917774080-9991f1c4c750",
  "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85"
];

/* GALLERY DATA */
const data = [
  {
    name: "Modern Villa",
    img: "https://images.unsplash.com/photo-1600585154340-be6161a56a0c",
    desc: "Modern architecture with clean lines"
  },
  {
    name: "City Tower",
    img: "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab",
    desc: "Urban skyscraper"
  },
  {
    name: "Luxury Interior",
    img: "https://images.unsplash.com/photo-1618221195710-dd6b41faaea6",
    desc: "Interior design"
  },
  {
    name: "Minimal House",
    img: "https://images.unsplash.com/photo-1570129477492-45c003edd2be",
    desc: "Minimal architecture"
  }
];

/* HERO SLIDER */
const heroSlider = document.getElementById("heroSlider");

if (heroSlider) {
  heroImages.forEach((src, i) => {
    const img = document.createElement("img");
    img.src = src;

    if (i === 0) img.classList.add("active");

    heroSlider.appendChild(img);
  });

  const slides = document.querySelectorAll(".hero-slider img");
  let index = 0;

  setInterval(() => {
    slides[index].classList.remove("active");
    index = (index + 1) % slides.length;
    slides[index].classList.add("active");
  }, 4000);
}

/* GALLERY */
const container = document.getElementById("container");
const title = document.getElementById("infoTitle");
const desc = document.getElementById("infoDesc");
const panel = document.getElementById("infoPanel");

if (container) {
  data.forEach((item, i) => {
    const card = document.createElement("div");
    card.className = "card";
    card.style.backgroundImage = `url(${item.img})`;

    card.innerHTML = `<h3>${item.name}</h3>`;

    card.onclick = () => activate(i);

    container.appendChild(card);
  });

  function activate(i) {
    document.querySelectorAll(".card").forEach(c => c.classList.remove("active"));
    document.querySelectorAll(".card")[i].classList.add("active");

    title.innerText = data[i].name;
    desc.innerText = data[i].desc;

    panel.classList.add("active");
  }

  /* AUTO EXPAND */
  let current = 0;

  setInterval(() => {
    current = (current + 1) % data.length;
    activate(current);
  }, 3000);
}