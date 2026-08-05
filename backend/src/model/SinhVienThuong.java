package model;

public class SinhVienThuong extends BanDoc {
    public SinhVienThuong() {
        super();
        setLoaiBanDoc("Sinh Viên Thường");
        setGioiHanMuon(3);

    }
    public SinhVienThuong(String maNguoiDung, 
        String hoTen, 
        String soDienThoai) {
        
            super(maNguoiDung, 
                hoTen, 
                soDienThoai,
                "Sinh Viên Thường",
                soSachDangMuon);

        setGioiHanMuon(3);
        
    }
}
