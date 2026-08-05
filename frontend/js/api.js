const API_URL = "http://localhost:8080/api";

/* =========================
        SÁCH
========================= */

// Lấy danh sách sách
async function layDanhSachSach() {

    const response = await fetch(`${API_URL}/sach`);

    if (!response.ok) {
        throw new Error("Không lấy được danh sách sách");
    }

    return await response.json();
}

// Thêm sách
async function themSach(book) {

    const response = await fetch(`${API_URL}/sach`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(book)

    });

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.json();
}

// Cập nhật sách
async function capNhatSach(book) {

    const response = await fetch(`${API_URL}/sach`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(book)

    });

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.text();
}

// Xóa sách
async function xoaSach(maSach) {

    const response = await fetch(
        `${API_URL}/sach?maSach=${encodeURIComponent(maSach)}`,
        {
            method: "DELETE"
        }
    );

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.text();
}

/* =========================
        BẠN ĐỌC
========================= */

// Lấy danh sách bạn đọc
async function layDanhSachBanDoc() {

    const response = await fetch(`${API_URL}/bandoc`);

    if (!response.ok) {
        throw new Error("Không lấy được danh sách bạn đọc");
    }

    return await response.json();
}

// Thêm bạn đọc
async function themBanDoc(banDoc) {

    const response = await fetch(`${API_URL}/bandoc`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(banDoc)

    });

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.text();

}

// Cập nhật bạn đọc
async function capNhatBanDoc(banDoc) {

    const response = await fetch(`${API_URL}/bandoc`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(banDoc)

    });

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.text();
}

// Xóa bạn đọc
async function xoaBanDoc(maBanDoc) {

    const response = await fetch(
        `${API_URL}/bandoc?maBanDoc=${encodeURIComponent(maBanDoc)}`,
        {
            method: "DELETE"
        }
    );

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.text();
}
/* =========================
        PHIẾU MƯỢN
========================= */

// Lấy danh sách phiếu mượn
async function layDanhSachPhieuMuon() {

    const response = await fetch(`${API_URL}/muon`);

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.json();

}
/* =========================
        MƯỢN SÁCH
========================= */
async function muonSach(maBanDoc, maSach) {

    console.log({
        maBanDoc,
        maSach
    });

    const response = await fetch(`${API_URL}/muon`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            maBanDoc,
            maSach
        })

    });

    console.log(response.status);

    const text = await response.text();

    console.log(text);

    if (!response.ok) {
        throw new Error(text);
    }

    return text;

}

/* =========================
        TRẢ SÁCH
========================= */

async function traSach(maPhieuMuon) {

    const response = await fetch(`${API_URL}/tra`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            maPhieuMuon: maPhieuMuon
        })

    });

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.text();
}
/* =========================
PHIẾU TRẢ
========================= */

async function layDanhSachPhieuTra() {

    const response = await fetch(`${API_URL}/tra`);

    if (!response.ok) {
        throw new Error("Không lấy được danh sách phiếu trả");
    }

    return await response.json();
}
/* =========================
   LỊCH SỬ
========================= */

// Lấy danh sách lịch sử
async function layDanhSachLichSu() {

    const response = await fetch(`${API_URL}/lichsu`);

    if (!response.ok) {
        throw new Error("Không lấy được lịch sử");
    }

    return await response.json();
}