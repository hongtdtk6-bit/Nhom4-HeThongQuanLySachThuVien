let books = [];
let editingIndex = -1;

// LOAD
window.onload = async () => {
    await loadBooks();
};

// LOAD DATA

async function loadBooks() {
    try {
        books = await layDanhSachSach();
        renderTable();
        updateStatistic();
    } catch (error) {
        console.error(error);
        alert("Không thể kết nối Backend!");
    }
}

// HIỂN THỊ BẢNG

function renderTable() {
    const tbody = document.querySelector("#bookTable tbody");
    tbody.innerHTML = "";
    books.forEach((book, index) => {
        const status = book.soLuong > 0 ? "Có sẵn" : "Hết sách";
        const statusClass = book.soLuong > 0 ? "available" : "out";
        tbody.innerHTML += `
        <tr>
            <td>${book.maSach}</td>
            <td>${book.tenSach}</td>
            <td>${book.tacGia}</td>
            <td>${book.theLoai}</td>
            <td>${book.soLuong}</td>
            <td>
                <span class="book-status ${statusClass}">
                    ${status}
                </span>
            </td>
            <td>
                <button class="edit-btn" onclick="editBook(${index})">
                    <i class="fa-solid fa-pen"></i>
                </button>
                <button class="delete-btn" onclick="deleteBook(${index})">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </td>
        </tr>
        `;
    });
}

//THỐNG KÊ
function updateStatistic() {
    document.getElementById("totalBook").innerHTML = books.length;
}

//THÊM / SỬA
async function saveBook() {
    const id = document.getElementById("bookId").value.trim();
    const name = document.getElementById("bookName").value.trim();
    const author = document.getElementById("author").value.trim();
    const category = document.getElementById("category").value;
    const quantity = Number(document.getElementById("quantity").value);
    if (
        name === "" ||
        author === "" ||
        isNaN(quantity)
    ) {
        showToast("Vui lòng nhập đầy đủ thông tin!", "warning");
        return;
    }
    try {
        if (editingIndex === -1) {
            const book = {
                tenSach: name,
                tacGia: author,
                theLoai: category,
                nhaXuatBan: "",
                namXuatBan: new Date().getFullYear(),
                soLuong: quantity
            };
            await themSach(book);
            showToast("Thêm sách thành công");
        } else {
            const book = {
                maSach: books[editingIndex].maSach,
                tenSach: name,
                tacGia: author,
                theLoai: category,
                nhaXuatBan: books[editingIndex].nhaXuatBan,
                namXuatBan: books[editingIndex].namXuatBan,
                soLuong: quantity
            };
            await capNhatSach(book);
            editingIndex = -1;
            showToast("Cập nhật thành công");
        }
        await loadBooks();
        clearForm();
        closeModal("bookModal");
    } catch (error) {
        console.error(error);
        alert("Không thể lưu sách!");
    }
}

    //    SỬA

function editBook(index) {
    editingIndex = index;
    const book = books[index];
    document.getElementById("bookId").value = book.maSach;
    document.getElementById("bookName").value = book.tenSach;
    document.getElementById("author").value = book.tacGia;
    document.getElementById("category").value = book.theLoai;
    document.getElementById("quantity").value = book.soLuong;
    openModal("bookModal");
}

    //    XÓA

async function deleteBook(index) {
    if (!confirm("Bạn muốn xóa sách này?")) {
        return;
    }
    try {
        await xoaSach(books[index].maSach);
        await loadBooks();
        showToast("Đã xóa sách");
    } catch (error) {
        console.error(error);
        alert("Không thể xóa sách!");
    }
}

//TÌM KIẾM

function searchBook() {
    const keyword = document
        .getElementById("searchBook")
        .value
        .toLowerCase();
    const rows = document.querySelectorAll("#bookTable tbody tr");
    rows.forEach((row) => {
        row.style.display =
            row.innerText.toLowerCase().includes(keyword)
                ? ""
                : "none";
    });
}

// CLEAR FORM

function clearForm() {
    editingIndex = -1;
    document.getElementById("bookId").value = "";
    document.getElementById("bookName").value = "";
    document.getElementById("author").value = "";
    document.getElementById("quantity").value = "";
    document.getElementById("category").selectedIndex = 0;
}

// REFRESH

document.querySelector(".refresh-btn").onclick = async () => {
    await loadBooks();
    showToast("Đã làm mới dữ liệu");
};

// CLICK NGOÀI MODAL

window.onclick = function (e) {
    const modal = document.getElementById("bookModal");
    if (e.target === modal) {
        clearForm();
        closeModal("bookModal");
    }
};