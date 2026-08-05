let fineList = [
  {
    id: "PT001",
    reader: "Nguyễn Văn A",
    book: "Lập trình Java",
    lateDays: 3,
    fine: 15000,
    status: "Chưa thanh toán",
  },

  {
    id: "PT002",
    reader: "Trần Thị B",
    book: "HTML CSS JavaScript",
    lateDays: 2,
    fine: 10000,
    status: "Đã thanh toán",
  },

  {
    id: "PT003",
    reader: "Lê Văn C",
    book: "Python Cơ Bản",
    lateDays: 5,
    fine: 25000,
    status: "Chưa thanh toán",
  },
];

window.onload = () => {
  renderTable();

  updateStatistic();
};

function renderTable() {
  const tbody = document.querySelector("#fineTable tbody");

  tbody.innerHTML = "";

  fineList.forEach((item, index) => {
    const statusClass = item.status === "Đã thanh toán" ? "paid" : "unpaid";

    tbody.innerHTML += `

        <tr>

            <td>${item.id}</td>

            <td>${item.reader}</td>

            <td>${item.book}</td>

            <td>${item.lateDays}</td>

            <td>${formatMoney(item.fine)}</td>

            <td>

                <span class="status ${statusClass}">
                    ${item.status}
                </span>

            </td>

            <td>

                ${
                  item.status === "Chưa thanh toán"
                    ? `
                <button
                    class="pay-btn"
                    onclick="payFine(${index})">

                    <i class="fa-solid fa-money-check-dollar"></i>

                </button>
                `
                    : ""
                }

                <button
                    class="view-btn"
                    onclick="viewFine(${index})">

                    <i class="fa-solid fa-eye"></i>

                </button>

            </td>

        </tr>

        `;
  });
}

function updateStatistic() {
  document.querySelectorAll(".stat-card h3")[0].innerText = fineList.length;

  document.querySelectorAll(".stat-card h3")[1].innerText = fineList.filter(
    (x) => x.status === "Đã thanh toán",
  ).length;

  document.querySelectorAll(".stat-card h3")[2].innerText = fineList.filter(
    (x) => x.status === "Chưa thanh toán",
  ).length;
}

function viewFine(index) {
  const item = fineList[index];

  document.getElementById("fineId").value = item.id;

  document.getElementById("readerName").value = item.reader;

  document.getElementById("bookName").value = item.book;

  document.getElementById("lateDays").value = item.lateDays;

  document.getElementById("money").value = formatMoney(item.fine);

  openModal("fineModal");
}

function payFine(index) {
  if (!confirm("Xác nhận thanh toán khoản tiền phạt này?")) return;

  fineList[index].status = "Đã thanh toán";

  renderTable();

  updateStatistic();

  showToast("Thanh toán thành công!");
}

const searchInput = document.getElementById("searchFine");

if (searchInput) {
  searchInput.addEventListener("keyup", function () {
    const keyword = this.value.toLowerCase();

    const rows = document.querySelectorAll("#fineTable tbody tr");

    rows.forEach((row) => {
      row.style.display = row.innerText.toLowerCase().includes(keyword)
        ? ""
        : "none";
    });
  });
}

const refreshBtn = document.querySelector(".refresh-btn");

if (refreshBtn) {
  refreshBtn.onclick = () => {
    renderTable();
    updateStatistic();
    showToast("Đã làm mới dữ liệu!");
  };
}

window.onclick = function (e) {
  const modal = document.getElementById("fineModal");
  if (e.target === modal) {
    closeModal("fineModal");
  }
};
