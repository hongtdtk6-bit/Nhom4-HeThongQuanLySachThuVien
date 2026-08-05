package service;

import model.BanDoc;
import model.GiangVien;
import model.SinhVienThuong;
import model.SinhVienUuTien;
import repository.KhoBanDoc;
import utils.SinhMa;

import java.util.List;
import java.util.ArrayList;

public class DichVuBanDoc {
    private final KhoBanDoc khoBanDoc;
    public DichVuBanDoc(){
        khoBanDoc = new KhoBanDoc();
    }

    public List<BanDoc> layDanhSachBanDoc() {
        return khoBanDoc.getDanhSachBanDoc();
    }

    public boolean themBanDoc(BanDoc banDoc) {

        List<BanDoc> dsMa = new ArrayList<>();
        for (BanDoc bd : khoBanDoc.getDanhSachBanDoc()) {
            dsMa.add(bd.getMaNguoiDung());
        }
    
    String ma = SinhMa.sinhMa("BD", dsMa);
    BanDoc banDocMoi;
    switch (banDoc.getLoaiBanDoc()) {
        case "Sinh Viên Ưu Tiên":
            banDocMoi = new SinhVienUuTien();
            break;
        case "Giảng Viên":
            banDocMoi = new GiangVien();
            break;
        default:
            banDocMoi = new SinhVienThuong();
    }

    banDocMoi.setMaNguoiDung(ma);
    banDocMoi.setHoTen(banDoc.getHoTen());
    banDocMoi.setSoDienThoai(banDoc.getSoDienThoai());
    banDocMoi.setLoaiBanDoc(banDoc.getLoaiBanDoc());
    banDocMoi.setSoSachDangMuon(0);

    khoBanDoc.them(banDocMoi);
    return khoBanDoc.themBanDoc(banDocMoi);
}
public boolean capNhatBanDoc(BanDoc banDoc) {
    return khoBanDoc.capNhat(banDoc);
}
public boolean xoaBanDoc(String maBanDoc) {
    return khoBanDoc.xoaBanDoc(maBanDoc);
}


}
