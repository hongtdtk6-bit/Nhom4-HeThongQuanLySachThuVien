let readers = [];
let editingIndex = -1;

//LOAD

window.onload = async () => {
    await loadReaders();
};

//LOAD DATA
async function loadReaders() {
    try {
        readers = await layDanhSachBanDoc();
        renderTable();
        updateStatistic();
    } catch (e) {
        console.error(e);
        alert("Không kết nối được Backend!");
    }
}

//HIỂN THỊ BẢNG

function renderTable() {
    const tbody = document.querySelector("#readerTable tbody");
    tbody.innerHTML = "";
    readers.forEach((reader, index) => {
        tbody.innerHTML += `
        <tr>
            <td>${reader.maNguoiDung}</td>
            <td>${reader.hoTen}</td>
            <td>${reader.soDienThoai}</td>
            <td>${reader.loaiBanDoc}</td>
            <td>${reader.soSachDangMuon}</td>
            <td>
                <span class="status active">
                    Hoạt động
                </span>
            </td>
            <td>
                <button class="edit-btn"
                    onclick="editReader(${index})">
                    <i class="fa-solid fa-pen"></i>
                </button>
                <button class="delete-btn"
                    onclick="deleteReader('${reader.maNguoiDung}')">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </td>
        </tr>
        `;
    });
}

//THỐNG KÊ

function updateStatistic() {
    document.querySelectorAll(".stat-card h3")[0].innerText = readers.length;
    document.querySelectorAll(".stat-card h3")[1].innerText =
        readers.filter(r => r.loaiBanDoc !== "Giảng viên").length;
    document.querySelectorAll(".stat-card h3")[2].innerText =
        readers.filter(r => r.loaiBanDoc === "Giảng viên").length;
}

// THÊM / SỬA

async function saveReader() {
    const id = document.querySelectorAll(".form-group input")[0].value.trim();
    const name = document.querySelectorAll(".form-group input")[1].value.trim();
    const phone = document.querySelectorAll(".form-group input")[2].value.trim();
    const type = document.querySelector(".form-group select").value;
    if (name === "" || phone === "") {
        showToast("Vui lòng nhập đầy đủ thông tin", "warning");
        return;
    }
    try {
        if (editingIndex === -1) {
            const reader = {
                hoTen: name,
                soDienThoai: phone,
                loaiBanDoc: type,
                soSachDangMuon: 0
            };
            await themBanDoc(reader);
            showToast("Thêm thành công");
        } else {
            const reader = {
                maNguoiDung: readers[editingIndex].maNguoiDung,
                hoTen: name,
                soDienThoai: phone,
                loaiBanDoc: type,
                soSachDangMuon: readers[editingIndex].soSachDangMuon
            };
            await capNhatBanDoc(reader);
            editingIndex = -1;
            showToast("Cập nhật thành công");
        }
        await loadReaders();
        clearForm();
        closeModal("readerModal");
    } catch (e) {
        console.error(e);
        alert("e.message");
    }
}
// SỬA

function editReader(index) {
    editingIndex = index;
    const reader = readers[index];
    document.querySelectorAll(".form-group input")[0].value = reader.maNguoiDung;
    document.querySelectorAll(".form-group input")[1].value = reader.hoTen;
    document.querySelectorAll(".form-group input")[2].value = reader.soDienThoai;
    document.querySelector(".form-group select").value = reader.loaiBanDoc;
    openModal("readerModal");
}

//XÓA
async function deleteReader(maNguoiDung) {
    if (!confirm("Bạn muốn xóa bạn đọc này?")) {
        return;
    }
    try {
        await xoaBanDoc(maNguoiDung);
        await loadReaders();
        showToast("Đã xóa bạn đọc");
    } catch (e) {
        console.error(e);
        alert("Không thể xóa bạn đọc!");
    }
}
//TÌM KIẾM

function searchReader() {
    const keyword = document
        .getElementById("searchReader")
        .value
        .toLowerCase();
    const rows = document.querySelectorAll("#readerTable tbody tr");
    rows.forEach((row) => {
        row.style.display =
            row.innerText.toLowerCase().includes(keyword)? "": "none";
    });
}

//CLEAR FORM

function clearForm() {
    editingIndex = -1;
    document.querySelectorAll(".form-group input")[0].value = "";
    document.querySelectorAll(".form-group input")[1].value = "";
    document.querySelectorAll(".form-group input")[2].value = "";
    document.querySelector(".form-group select").selectedIndex = 0;
}
//REFRESH

document.querySelector(".refresh-btn").onclick = async () => {
    await loadReaders();
    showToast("Đã làm mới dữ liệu");
};

//ĐÓNG MODAL

window.onclick = function(event) {
    const modal = document.getElementById("readerModal");
    if (event.target === modal) {
        clearForm();
        closeModal("readerModal");
    }
};

// NÚT LƯU
document.querySelector(".save-btn").onclick = saveReader;