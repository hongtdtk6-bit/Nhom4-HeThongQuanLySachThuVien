package model;

public class GiangVien extends NguoiDung {
    public GiangVien() {
        super();
        setLoaiBanDoc("Giảng Viên");
        setGioiHanMuon(10);
    }

    public GiangVien(String maNguoiDung, 
        String hoTen, 
        String soDienThoai, 
        int soSachDangMuon) {
        
            super(maNguoiDung, 
                hoTen, 
                soDienThoai,
                "Giảng Viên",
                soSachDangMuon,);
                setGioiHanMuon(10);
        
    }

} 

