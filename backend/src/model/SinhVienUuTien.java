package model;

public class SinhVienUuTien extends BanDoc {
    public SinhVienUuTien() {
        super();
        setLoaiBanDoc("Sinh Viên Ưu Tiên");
        setGioiHanMuon(5);

    }
    public SinhVienUuTien(String maNguoiDung, 
        String hoTen, 
        String soDienThoai,
        int soSachDangMuon) {
        
            super(maNguoiDung, 
                hoTen, 
                soDienThoai,
                "Sinh Viên Ưu Tiên",
                soSachDangMuon);

        setGioiHanMuon(5);
        
    }
}
