package model;

public class GiangVien extends BanDoc{
    public GiangVien() {
        super();
        setLoaiBanDoc("Giảng viên");
        setGioiHanMuon(10);
    }

    public GiangVien(String maNguoiDung, 
        String hoTen, 
        String soDienThoai, 
        int soSachDangMuon) {
        
        super(maNguoiDung, hoTen,soDienThoai,"Giảng viên",soSachDangMuon);

        setGioiHanMuon(10);
        
    }

} 

