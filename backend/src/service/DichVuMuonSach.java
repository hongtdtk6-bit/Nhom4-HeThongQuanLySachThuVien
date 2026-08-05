package service;

import model.BanDoc;
import model.PhieuMuon;
import model.Sach;
import repository.KhoBanDoc;
import repository.KhoPhieuMuon;
import repository.KhoSach;
import utils.SinhMa;
import utils.XuLyNgay;

import java.util.ArrayList;
import java.util.List;

public class DichVuMuonSach {
    private final KhoSach khoSach;
    private final KhoBanDoc khoBanDoc;
    private final KhoPhieuMuon khoPhieuMuon;

    public DichVuMuonSach() {
        khoSach = new KhoSach();
        khoBanDoc = new KhoBanDoc();
        khoPhieuMuon = new KhoPhieuMuon();
    }

    public String muonSach(String maBanDoc, String maSach) {
        // Kiểm tra dữ liệu đầu vào
        if (maBanDoc == null || maBanDoc.trim().isEmpty()) {
            return "Mã bạn đọc không hợp lệ.";
        }
        if (maSach == null || maSach.trim().isEmpty()) {
            return "Mã sách không hợp lệ.";
        }

        // Tìm bạn đọc
        BanDoc banDoc = khoBanDoc.timTheoMa(maBanDoc);
        if (banDoc == null) {
            return "Không tìm thấy bạn đọc.";
        }

        // Tìm sách
        Sach sach = khoSach.timTheoMa(maSach);

        if (sach == null) {
            return "Không tìm thấy sách.";
        }

        // Kiểm tra số lượng sách
        if (sach.getSoLuong() <= 0) {
            return "Sách đã hết.";
        }

        // Kiểm tra giới hạn mượn
        if (banDoc.getSoSachDangMuon() >= banDoc.getGioiHanMuon()) {
            return "Bạn đọc đã đạt giới hạn mượn.";
        }

        // Kiểm tra đã mượn cuốn sách này chưa
        for (PhieuMuon pm : khoPhieuMuon.getDanhSachPhieuMuon()) {
            if (pm.getMaBanDoc().equals(maBanDoc)
                    && pm.getMaSach().equals(maSach)
                    && pm.getTrangThai().equalsIgnoreCase("Đang mượn")) {

                return "Bạn đọc đang mượn cuốn sách này.";
            }
        }

        // Sinh mã phiếu mượn
        List<String> dsMa = new ArrayList<>();
        for (PhieuMuon pm : khoPhieuMuon.getDanhSachPhieuMuon()) {
            dsMa.add(pm.getMaPhieuMuon());
        }

        String maPhieuMuon = SinhMa.sinhMa("PM", dsMa);
        
        // Tạo phiếu mượn
        PhieuMuon phieuMuon = new PhieuMuon(
                maPhieuMuon,
                maBanDoc,
                maSach,
                XuLyNgay.layNgayHienTai(),
                XuLyNgay.tinhNgayHenTra(7),
                "Đang mượn"
        );

        // Lưu phiếu mượn
        khoPhieuMuon.them(phieuMuon);

        // Giảm số lượng sách
        sach.setSoLuong(sach.getSoLuong() - 1);
        khoSach.capNhat(sach);

        // Tăng số sách đang mượn
        banDoc.setSoSachDangMuon(
                banDoc.getSoSachDangMuon() + 1
        );
        khoBanDoc.capNhat(banDoc);

        return "Mượn sách thành công.\n"
                + "Mã phiếu mượn: " + maPhieuMuon
                + "\nNgày hẹn trả: " + phieuMuon.getNgayHenTra();
    }
    /**
     * Lấy danh sách phiếu mượn.
     */
    public List<PhieuMuon> layDanhSachPhieuMuon() {
        return khoPhieuMuon.getDanhSachPhieuMuon();
    }
}