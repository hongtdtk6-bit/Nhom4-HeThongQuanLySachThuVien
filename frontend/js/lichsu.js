let historyList = [];

//LOAD
window.onload = async () => {
    await loadHistory();
};

//LOAD DATA

async function loadHistory(){
    try {
        historyList = await layDanhSachLichSu();
        renderTable();
        updateStatistic();
    } catch(e){
        console.error(e);
        showToast(
            "Không tải được dữ liệu lịch sử!",
            "error"
        );
    }
}

//HIỂN THỊ BẢNG

function renderTable(){
    const tbody =
        document.querySelector("#historyTable tbody");
    tbody.innerHTML = "";
    historyList.forEach((item,index)=>{
        let type = "Mượn";
        let typeClass = "borrow";
        if(item.ngayTra != null && item.ngayTra !== ""){
            type = "Trả";
            typeClass = "return";
        }

        tbody.innerHTML += `
        <tr>
            <td>
                ${item.maPhieuMuon}
            </td>
            <td>
                ${item.tenBanDoc}
            </td>
            <td>
                ${item.tenSach}
            </td>
            <td>
                ${
                    item.ngayTra 
                    ? item.ngayTra
                    : item.ngayMuon
                }
            </td>
            <td>
                <span class="status success">
                    ${item.trangThai}
                </span>
            </td>
            <td>
                <button class="view-btn"
                onclick="viewHistory(${index})">
                    <i class="fa-solid fa-eye"></i>
                </button>
            </td>
        </tr>
        `;
    });
}

//THỐNG KÊ

function updateStatistic(){
    const cards =
    document.querySelectorAll(
        ".stat-card h3"
    );
    if(cards.length < 3)
        return;
    cards[0].innerText =
        historyList.length;

    cards[1].innerText =
        historyList.filter(
            item =>
            item.ngayMuon != null
            &&
            item.ngayMuon !== ""
        ).length;

    cards[2].innerText =
        historyList.filter(
            item =>
            item.ngayTra != null
            &&
            item.ngayTra !== ""
        ).length;
}

//XEM CHI TIẾT

function viewHistory(index){
    const item =
        historyList[index];
    alert(
`
Mã phiếu mượn: ${item.maPhieuMuon}
Bạn đọc: ${item.tenBanDoc}
Sách: ${item.tenSach}
Ngày mượn: ${item.ngayMuon}
Ngày trả: ${item.ngayTra || "Chưa trả"}
Trạng thái: ${item.trangThai}
`
 );

}

//TÌM KIẾM

const searchInput =
document.getElementById("searchHistory");

if(searchInput){
    searchInput.addEventListener(
        "keyup",
        function(){
            const keyword =
            this.value.toLowerCase();
            const rows =
            document.querySelectorAll(
                "#historyTable tbody tr"
            );
            rows.forEach(row=>{
                row.style.display =
                row.innerText
                .toLowerCase()
                .includes(keyword)? "" : "none";
            });
        }
    );
}

//REFRESH

const refreshBtn =
document.querySelector(".refresh-btn");

if(refreshBtn){
    refreshBtn.onclick = async()=>{
        await loadHistory();
        showToast(
            "Đã làm mới dữ liệu"
        );
    };
}