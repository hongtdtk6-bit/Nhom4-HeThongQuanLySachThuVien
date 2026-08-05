//SIDEBAR ACTIVE
const currentPage = window.location.pathname.split("/").pop();
const menuLinks = document.querySelectorAll(".sidebar a");
menuLinks.forEach((link) => {
  const href = link.getAttribute("href");
  if (href === currentPage) {
    link.parentElement.classList.add("active");
  }
});

// TOGGLE SIDEBAR

const sidebar = document.querySelector(".sidebar");
function toggleSidebar() {
  sidebar.classList.toggle("collapse");
}

//TOAST MESSAGE

function showToast(message, type = "success") {
  const toast = document.createElement("div");
  toast.className = `toast ${type}`;
  toast.innerHTML = message;
  document.body.appendChild(toast);
  setTimeout(() => {
    toast.classList.add("show");
  }, 100);
  setTimeout(() => {
    toast.classList.remove("show");
    setTimeout(() => {
      toast.remove();
    }, 300);
  }, 3000);
}

// CONFIRM DELETE
function confirmDelete(message = "Bạn có chắc muốn xóa?") {
  return confirm(message);
}

//  FORMAT MONEY

function formatMoney(number) {
  return Number(number).toLocaleString("vi-VN") + " đ";
}

//FORMAT DATE

function formatDate(dateString) {
  const date = new Date(dateString);
  return date.toLocaleDateString("vi-VN");
}

//LOADING

function showLoading() {
  let loading = document.querySelector(".loading");
  if (loading) return;
  loading = document.createElement("div");
  loading.className = "loading";
  loading.innerHTML = ` <div class="spinner"></div> `;
  document.body.appendChild(loading);
}
function hideLoading() {
  const loading = document.querySelector(".loading");
  if (loading) {
    loading.remove();
  }
}

//SEARCH TABLE

function searchTable(inputId, tableId) {
  const keyword = document.getElementById(inputId).value.toLowerCase();
  const rows = document.querySelectorAll(`#${tableId} tbody tr`);
  rows.forEach((row) => {
    const text = row.innerText.toLowerCase();
    row.style.display = text.includes(keyword) ? "" : "none";
  });
}

// MODAL
function openModal(id) {
  document.getElementById(id).style.display = "flex";
}
function closeModal(id) {
  document.getElementById(id).style.display = "none";
}

//RANDOM CARD ANIMATION

window.addEventListener("load", () => {
  const cards = document.querySelectorAll(".card");
  cards.forEach((card, index) => {
    card.style.opacity = 0;
    card.style.transform = "translateY(30px)";
    setTimeout(() => {
      card.style.transition = ".5s";
      card.style.opacity = 1;
      card.style.transform = "translateY(0)";
    }, index * 150);
  });
});

// MENU

document.querySelectorAll(".sidebar li").forEach((item) => {
  item.addEventListener("click", () => {
    document
      .querySelectorAll(".sidebar li")
      .forEach((i) => i.classList.remove("active"));

    item.classList.add("active");
  });
});

//SCROLL TOP
const scrollBtn = document.createElement("button");
scrollBtn.className = "scroll-top";
scrollBtn.innerHTML = '<i class="fa-solid fa-arrow-up"></i>';
document.body.appendChild(scrollBtn);
window.addEventListener("scroll", () => {
  if (window.scrollY > 300) {
    scrollBtn.classList.add("show");
  } else {
    scrollBtn.classList.remove("show");
  }
});

scrollBtn.onclick = () => {
  window.scrollTo({
    top: 0,

    behavior: "smooth",
  });
};
