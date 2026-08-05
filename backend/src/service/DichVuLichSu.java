package service;

import java.util.ArrayList;
import java.util.List;

public class DichVuLichSu {
    private final repository.KhoPhieuMuon khoPhieuMuon;
    private final repository.KhoPhieuTra khoPhieuTra;
    private final repository.KhoBanDoc khoBanDoc;
    private final repository.KhoSach khoSach;

    public DichVuLichSu() {
        khoPhieuMuon = new repository.KhoPhieuMuon();
        khoPhieuTra = new repository.KhoPhieuTra();
        khoBanDoc = new repository.KhoBanDoc();
        khoSach = new repository.KhoSach();
    }
    public List<model.LichSu> layLichSu() {
        List<model.LichSu> ketQua = new ArrayList<>();
        for (model.PhieuMuon pm : khoPhieuMuon.getDanhSachPhieuMuon()) {
            String tenBanDoc = pm.getMaBanDoc();
            String tenSach = pm.getMaSach();
            String ngayTra = "";

            // tìm tên bạn đọc
            for (model.BanDoc bd : khoBanDoc.getDanhSachBanDoc()) {

                if (bd.getMaNguoiDung()
                        .equals(pm.getMaBanDoc())) {
                    tenBanDoc = bd.getHoTen();
                    break;
                }

            }

            // tìm tên sách
            for (model.Sach sach : khoSach.getDanhSachSach()) {
                if (sach.getMaSach()
                        .equals(pm.getMaSach())) {
                    tenSach = sach.getTenSach();
                    break;
                }

            }

            // tìm ngày trả
            for (model.PhieuTra pt : khoPhieuTra.getDanhSachPhieuTra()) {
                if (pt.getMaPhieuMuon()
                        .equals(pm.getMaPhieuMuon())) {
                    ngayTra = pt.getNgayTra();
                    break;
                }
            }

            model.LichSu lichSu = new model.LichSu(
                    pm.getMaPhieuMuon(),
                    tenBanDoc,
                    tenSach,
                    pm.getNgayMuon(),
                    ngayTra,
                    pm.getTrangThai()
            );

            ketQua.add(lichSu);
        }

        return ketQua;
    }
}
