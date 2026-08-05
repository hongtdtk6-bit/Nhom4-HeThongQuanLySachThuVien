let bookList = [];
let readerList = [];
let borrowList = [];
let returnList = [];

window.onload = async () => {
    await loadDashboard();
};

async function loadDashboard(){
    try{
        bookList = await layDanhSachSach();
        readerList = await layDanhSachBanDoc();
        borrowList = await layDanhSachPhieuMuon();
        returnList = await layDanhSachPhieuTra();
        updateCards();
        renderBookTable();
        updateStatistic();
        renderActivity();
    }catch(e){
        console.error(e);
        showToast(
            "Không tải được dữ liệu Dashboard!",
            "error"
        );
    }
}
//CARD

function updateCards(){
    document.getElementById("totalReaders").innerText =
        readerList.length;
    document.getElementById("totalBooks").innerText =
        bookList.length;
    document.getElementById("totalBorrowing").innerText =
        borrowList.filter(
            x=>x.trangThai==="Đang mượn"
        ).length;
    const tongTienPhat =
        returnList.reduce(
            (tong,item)=>tong + item.tienPhat,0);
    document.getElementById("totalFine").innerText =
        tongTienPhat.toLocaleString()+ " VNĐ";
}

//HIỂN THỊ BẢNG SÁCH

function renderBookTable(){
    const tbody =
        document.querySelector(
            "#bookTable tbody"
        );
    tbody.innerHTML = "";
    bookList.slice(0,5).forEach(book=>{
        tbody.innerHTML += `
        <tr>
            <td>${book.maSach}</td>
            <td>${book.tenSach}</td>
            <td>${book.tacGia}</td>
            <td>${book.soLuong}</td>
        </tr>
       `;
    });
}

//THỐNG KÊ
function updateStatistic(){
    const tongSach =
        bookList.reduce(
            (tong,sach)=> tong + sach.soLuong,  0 );
    const dangMuon =
        borrowList.filter(item=>item.trangThai==="Đang mượn" ).length;
    const tyLe = tongSach===0?0:Math.round( dangMuon/tongSach*100);
    const circle = document.querySelector( ".circle span" );
    if(circle){circle.innerText =tyLe + "%";   }
}
function renderActivity(){
    const activity =
        document.getElementById("activityList");
    activity.innerHTML = `
        <li>📚 ${borrowList.length} phiếu mượn</li>
        <li>📖 ${returnList.length} phiếu trả</li>
        <li>👤 ${readerList.length} bạn đọc</li>
        <li>📕 ${bookList.length} đầu sách</li>
    `;
}