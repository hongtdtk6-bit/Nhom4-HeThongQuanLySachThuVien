let borrowList = [];
let readerList = [];
let bookList = [];
let editingIndex = -1;

//KHỞI TẠO
window.onload = async function () {
    await loadData();
};

// LOAD DỮ LIỆU

async function loadData() {
    try {
        console.log("1. Đang lấy phiếu mượn...");
        borrowList = await layDanhSachPhieuMuon();
        console.log(borrowList);
        console.log("2. Đang lấy bạn đọc...");
        readerList = await layDanhSachBanDoc();
        console.log(readerList);
        console.log("3. Đang lấy sách...");
        bookList = await layDanhSachSach();
        console.log(bookList);
        console.log("4. Load select...");
        loadReaderSelect();
        loadBookSelect();
        console.log("5. Render...");
        renderTable();
        console.log("6. Statistic...");
        updateStatistic();
        console.log("Hoàn thành.");
    } catch (e) {
        alert(
            "Lỗi: " +
            e.message +
            "\n\n" +
            e.stack
        );
    }
}

function loadReaderSelect() {
    const select = document.getElementById("reader");
    select.innerHTML = "";
    readerList.forEach(reader => {
        select.innerHTML += `
            <option value="${reader.maNguoiDung}">
                ${reader.hoTen}
            </option>
        `;
    });
}

function loadBookSelect() {
    const select = document.getElementById("book");
    select.innerHTML = "";
    bookList.forEach(book => {
        select.innerHTML += `
            <option value="${book.maSach}">
                ${book.tenSach}
            </option>
        `;
    });
}
function getReaderName(maBanDoc) {
    const reader = readerList.find(
        x => x.maNguoiDung === maBanDoc
    );
    return reader ? reader.hoTen : maBanDoc;
}

function getBookName(maSach) {
    const book = bookList.find(x => x.maSach === maSach);
    return book ? book.tenSach : maSach;
}

// HIỂN THỊ DANH SÁCH

function renderTable() {
    const tbody = document.querySelector("#borrowTable tbody");
    tbody.innerHTML = "";
    borrowList.forEach((item, index) => {
        let statusClass = "";
        if (item.trangThai === "Đang mượn") {
            statusClass = "borrowing";
        } else if (item.trangThai === "Đã trả") {
            statusClass = "returned";
        } else {
            statusClass = "overdue";
        }
        tbody.innerHTML += `
        <tr>
            <td>${item.maPhieuMuon}</td>
            <td>${getReaderName(item.maBanDoc)}</td>
            <td>${getBookName(item.maSach)}</td>
            <td>${item.ngayMuon}</td>
            <td>${item.ngayHenTra}</td>
            <td>
                <span class="status ${statusClass}">
                    ${item.trangThai}
                </span>
            </td>
            <td>
                <button class="view-btn"onclick="viewBorrow(${index})">
                    <i class="fa-solid fa-eye"></i>
                </button>
                <button class="edit-btn" onclick="editStatus(${index})">
                    <i class="fa-solid fa-pen"></i>
                </button>
            </td>
        </tr>
       `;
   });
}
// THỐNG KÊ

function updateStatistic() {
    const total = borrowList.length;
    const borrowing =
        borrowList.filter(x => x.trangThai === "Đang mượn").length;
    const returned =
        borrowList.filter(x => x.trangThai === "Đã trả").length;
    document.querySelectorAll(".stat-card h3")[0].innerText = total;
    document.querySelectorAll(".stat-card h3")[1].innerText = borrowing;
    document.querySelectorAll(".stat-card h3")[2].innerText = returned;
}

// LƯU
async function saveBorrow() {
    try {
        const maBanDoc =
            document.getElementById("reader").value;
        const maSach =
            document.getElementById("book").value;
        if (maBanDoc === "" || maSach === "") {
            showToast("Vui lòng chọn đầy đủ thông tin!", "warning");
            return;
        }
        const message =await muonSach(maBanDoc, maSach);
        showToast(message);
        closeModal("borrowModal");
        await loadData();
    }
    catch (e) {
    console.error(e);
    alert(e);
    alert(e.message);
    showToast(e.message, "error");
    }
}

//XEM

function viewBorrow(index) {
    const item = borrowList[index];
    alert(
`Mã phiếu: ${item.maPhieuMuon}
Bạn đọc: ${getReaderName(item.maBanDoc)}
Sách: ${getBookName(item.maSach)}
Ngày mượn: ${item.ngayMuon}
Hạn trả: ${item.ngayHenTra}
Trạng thái: ${item.trangThai}`
    );
}

async function editStatus(index) {

    const item = borrowList[index];

    let trangThai = prompt(
        "Nhập trạng thái mới:\nĐang mượn hoặc Đã trả",
        item.trangThai
    );

    if (trangThai == null) return;

    trangThai = trangThai.trim();

    if (
        trangThai !== "Đang mượn" &&
        trangThai !== "Đã trả"
    ) {
        showToast("Trạng thái không hợp lệ!", "error");
        return;
    }

    try {

        const response = await fetch(
            "http://localhost:8080/api/muon",
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    maPhieuMuon: item.maPhieuMuon,
                    trangThai: trangThai
                })
            }
        );

        const message = await response.text();

        showToast(message);

        await loadData();

    } catch (e) {
        console.error(e);
        showToast("Không thể cập nhật!", "error");
    }
}

// CLEAR FORM

function clearForm() {
    document.getElementById("reader").selectedIndex = 0;
    document.getElementById("book").selectedIndex = 0;
}

//REFRESH

const refreshBtn =
    document.querySelector(".refresh-btn");
if (refreshBtn) {
    refreshBtn.onclick = async () => {
        await loadData();
        showToast("Đã tải lại dữ liệu!");
    };
}

//TÌM KIẾM

const searchInput =
    document.getElementById("searchBorrow");
if (searchInput) {
    searchInput.addEventListener("keyup", function () {
        const keyword =
            this.value.toLowerCase();
        const rows =
            document.querySelectorAll("#borrowTable tbody tr");
        rows.forEach(row => {
            row.style.display =
                row.innerText.toLowerCase().includes(keyword) ? "" : "none";
        });
    });
}