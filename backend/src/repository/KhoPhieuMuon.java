package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KhoPhieuMuon {
    private static final String FILE_PATH = "data/borrowTickets.json";
    private final List<model.PhieuMuon> danhSachPhieuMuon;
    public KhoPhieuMuon() {
        Type type = utils.DocGhiJson.layTypeDanhSach(model.PhieuMuon.class);
        List<model.PhieuMuon> ds = utils.DocGhiJson.docDanhSach(FILE_PATH, type);
        danhSachPhieuMuon = (ds != null) ? ds : new ArrayList<>();
    }
    public List<model.PhieuMuon> getDanhSachPhieuMuon() {
        return danhSachPhieuMuon;
    }
    public model.PhieuMuon timTheoMa(String maPhieuMuon) {
        if (maPhieuMuon == null || maPhieuMuon.trim().isEmpty()) {
            return null;
        }
        for (model.PhieuMuon phieuMuon : danhSachPhieuMuon) {
            if (maPhieuMuon.equals(phieuMuon.getMaPhieuMuon())) {
                return phieuMuon;
            }
        }
        return null;
    }

    //Them phieu muon
    public boolean them(model.PhieuMuon phieuMuon) {
        if (phieuMuon == null) {
            return false;
        }
        if (timTheoMa(phieuMuon.getMaPhieuMuon()) != null) {
            return false;
        }
        danhSachPhieuMuon.add(phieuMuon);
        luu();
        return true;
    }

    //Cập nhật phiếu mượn.

    public boolean capNhat(model.PhieuMuon phieuMuonMoi) {
        if (phieuMuonMoi == null) {
            return false;
        }

        for (int i = 0; i < danhSachPhieuMuon.size(); i++) {
            if (danhSachPhieuMuon.get(i).getMaPhieuMuon().equals(phieuMuonMoi.getMaPhieuMuon())) {
                danhSachPhieuMuon.set(i, phieuMuonMoi);
                luu();
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maPhieuMuon) {
        model.PhieuMuon phieuMuon = timTheoMa(maPhieuMuon);
        if (phieuMuon == null) {
            return false;
        }
        danhSachPhieuMuon.remove(phieuMuon);
        luu();
        return true;
    }

    private void luu() {
        utils.DocGhiJson.ghiDanhSach(FILE_PATH, danhSachPhieuMuon);
    }
}
