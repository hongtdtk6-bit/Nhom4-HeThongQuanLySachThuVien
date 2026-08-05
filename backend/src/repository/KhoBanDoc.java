package repository;

import model.BanDoc;
import utils.DocGhiJson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class KhoBanDoc {
    private static final String FILE_PATH = "data/readers.json";
    private final List<BanDoc> danhSachBanDoc;

    public KhoBanDoc() {
        Type type = DocGhiJson.layTypeDanhSach(BanDoc.class);
        List<BanDoc> ds = DocGhiJson.docDanhSach(FILE_PATH, type);
        danhSachBanDoc = (ds != null) ? ds : new ArrayList<>();
    }

    public List<BanDoc> getDanhSachBanDoc() {
        return danhSachBanDoc;
    }

    public BanDoc timTheoMa(String maNguoiDung) {
        if (maNguoiDung == null || maNguoiDung.trim().isEmpty()) {
            return null;
        }
        for (BanDoc banDoc : danhSachBanDoc) {
            if (maNguoiDung.equals(banDoc.getMaNguoiDung())) {
                return banDoc;
            }
        }
        return null;
    }

    public boolean themBanDoc(BanDoc banDoc) {
        if (banDoc == null) {
            return false;
        }
        if (timTheoMa(banDoc.getMaNguoiDung()) != null) {
            return false;
        }
        danhSachBanDoc.add(banDoc);
        luu();
        return true;
    }
    public boolean capNhat(BanDoc banDoc) {
        if (banDoc == null) {
            return false;
        }
        for (int i = 0; i < danhSachBanDoc.size(); i++) {
            if (danhSachBanDoc.get(i).getMaNguoiDung().equals(banDocMoi.getMaNguoiDung())) {
                danhSachBanDoc.set(i, banDocMoi);
                luu();
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maNguoiDung) {
        BanDoc banDoc = timTheoMa(maNguoiDung);
        if (banDoc == null) {
            return false;
        }
        danhSachBanDoc.remove(banDoc);
        luu();
        return true;
    }

    private void luu() {
        DocGhiJson.ghiDanhSach(FILE_PATH, danhSachBanDoc);
    }
}
