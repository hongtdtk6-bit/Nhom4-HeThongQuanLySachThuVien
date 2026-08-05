package repository;

import model.PhieuMuon;
import model.PhieuTra;
import utils.DocGhiJson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KhoPhieuTra {
    private static final String FILE_PATH = "data/returnTickets.json";
    private final List<PhieuTra> danhSachPhieuTra;

    public KhoPhieuTra() {
        Type type = DocGhiJson.layTypeDanhSach(PhieuTra.class);
        List<PhieuTra> ds = DocGhiJson.docDanhSach(FILE_PATH, type);
        danhSachPhieuTra = (ds != null) ? ds : new ArrayList<>();
    }

    public List<PhieuTra> getDanhSachPhieuTra() {
        return danhSachPhieuTra;
    }

    public PhieuTra timTheoMa(String maPhieuTra) {
        if (maPhieuTra == null || maPhieuTra.trim().isEmpty()) {
            return null;
        }
        for (PhieuTra phieuTra : danhSachPhieuTra) {
            if (maPhieuTra.equals(phieuTra.getMaPhieuTra())) {
                return phieuTra;
            }
        }
        return null;
    }

    public boolean them(PhieuTra phieuTra) {
        if (phieuTra == null) {
            return false;
        }
        if (timTheoMa(phieuTra.getMaPhieuTra()) != null) {
            return false;
        }
        danhSachPhieuTra.add(phieuTra);
        luu();
        return true;
    }

    //Cap Nhat
    public boolean capNhat(PhieuTra phieuTraMoi) {
        if (phieuTraMoi == null) {
            return false;
        }
        for (int i = 0; i < danhSachPhieuTra.size(); i++) {
            if (danhSachPhieuTra.get(i).getMaPhieuTra()
                    .equals(phieuTraMoi.getMaPhieuTra())) {
                danhSachPhieuTra.set(i, phieuTraMoi);
                luu();
                return true;
            }
        }
        return false;
    }

    //Xoa
    public boolean xoa(String maPhieuTra) {
        PhieuTra phieuTra = timTheoMa(maPhieuTra);
        if (phieuTra == null) {
            return false;
        }
        danhSachPhieuTra.remove(phieuTra);
        luu();
        return true;
    }

    /** dữ liệu xuống file JSON*/
    private void luu() {
        DocGhiJson.ghiDanhSach(FILE_PATH, danhSachPhieuTra);

    }
}
