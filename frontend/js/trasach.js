let returnList = [];
let borrowList = [];
let readerList = [];
let bookList = [];

//LOAD

window.onload = async () => {
    await loadData();
};

//LOAD DATA

async function loadData() {
    try {
        returnList = await layDanhSachPhieuTra();
        borrowList = await layDanhSachPhieuMuon();
        readerList = await layDanhSachBanDoc();
        bookList = await layDanhSachSach();
        loadBorrowSelect();
        renderTable();
        updateStatistic();
    } catch (e) {
        console.error(e);
        showToast("Không tải được dữ liệu!", "error");
    }
}

// HIỂN THỊ BẢNG

function renderTable() {
    const tbody = document.querySelector("#returnTable tbody");
    tbody.innerHTML = "";
    returnList.forEach((item, index) => {
        const phieuMuon = borrowList.find(
            p => p.maPhieuMuon === item.maPhieuMuon
        );
        tbody.innerHTML += `
        <tr>
            <td>${item.maPhieuTra}</td>
            <td>${item.maPhieuMuon}</td>
            <td>${phieuMuon ? getReaderName(phieuMuon.maBanDoc) : ""}</td>
            <td>${phieuMuon ? getBookName(phieuMuon.maSach) : ""}</td>
            <td>${item.ngayTra}</td>
            <td>${item.soNgayTre}</td>
            <td>${item.tienPhat.toLocaleString()} VNĐ</td>
            <td>
                <button class="view-btn"
                    onclick="viewReturn(${index})">
                    <i class="fa-solid fa-eye"></i>
                </button>
            </td>
        </tr>
        `;
    });
}

// THỐNG KÊ

function updateStatistic() {
    document.querySelectorAll(".stat-card h3")[0].innerText =
        returnList.length;
    document.querySelectorAll(".stat-card h3")[1].innerText =
        returnList.filter(x => x.soNgayTre > 0).length;
    document.querySelectorAll(".stat-card h3")[2].innerText =
        returnList.reduce(
            (tong, x) => tong + x.tienPhat,
            0
        ).toLocaleString();
}

//LOAD COMBOBOX PHIẾU MƯỢN

function loadBorrowSelect() {
    const select = document.getElementById("borrow");
    select.innerHTML = "";
    borrowList
        .filter(item => item.trangThai === "Đang mượn")
        .forEach(item => {

            select.innerHTML += `
                <option value="${item.maPhieuMuon}">
                    ${item.maPhieuMuon}
                </option>
            `;
        });
}

// LẤY TÊN BẠN ĐỌC

function getReaderName(maBanDoc) {
    const reader = readerList.find(
        x => x.maNguoiDung === maBanDoc
    );
    return reader ? reader.hoTen : maBanDoc;
}

// LẤY TÊN SÁCH

function getBookName(maSach) {
    const book = bookList.find(
        x => x.maSach === maSach
    );
    return book ? book.tenSach : maSach;
}

// TRẢ SÁCH

async function saveReturn() {
    try {
        const maPhieuMuon =
            document.getElementById("borrow").value;
        if (maPhieuMuon === "") {
            showToast(
                "Vui lòng chọn phiếu mượn!",
                "warning"
            );
            return;
        }
        const message =
            await traSach(maPhieuMuon);
        showToast(message);
        closeModal("returnModal");
        await loadData();
    }
    catch (e) {
        console.error(e);
        showToast(e.message, "error");
    }
}

// XEM CHI TIẾT

function viewReturn(index) {
    const item = returnList[index];
    const phieuMuon = borrowList.find(
        p => p.maPhieuMuon === item.maPhieuMuon
    );
    alert(

        `Mã phiếu trả: ${item.maPhieuTra}

        Mã phiếu mượn: ${item.maPhieuMuon}
        Bạn đọc: ${
        phieuMuon? getReaderName(phieuMuon.maBanDoc): ""
}
        Sách: ${phieuMuon? getBookName(phieuMuon.maSach): ""
}
        Ngày trả: ${item.ngayTra}
        Số ngày trễ: ${item.soNgayTre}
        Tiền phạt: ${item.tienPhat.toLocaleString()} VNĐ`
    );
}
// TÌM KIẾM

function searchReturn() {
    const keyword = document
        .getElementById("searchReturn")
        .value
        .toLowerCase();
    const rows = document.querySelectorAll("#returnTable tbody tr");
    rows.forEach(row => {
        row.style.display =
            row.innerText.toLowerCase().includes(keyword)? "": "none";
    });
}
 
//CLEAR FORM

function clearForm() {
    const select = document.getElementById("borrow");
    if (select.options.length > 0) {
        select.selectedIndex = 0;
    }
}

// REFRESH

const refreshBtn = document.querySelector(".refresh-btn");
if (refreshBtn) {
    refreshBtn.onclick = async () => {
        await loadData();
        showToast("Đã làm mới dữ liệu");
    };
}

//ĐÓNG MODAL

window.onclick = function (event) {
    const modal = document.getElementById("returnModal");
    if (event.target === modal) {
        clearForm();
        closeModal("returnModal");
    }
};

//NÚT LƯU

const saveBtn = document.querySelector(".save-btn");
if (saveBtn) {
    saveBtn.onclick = saveReturn;
}