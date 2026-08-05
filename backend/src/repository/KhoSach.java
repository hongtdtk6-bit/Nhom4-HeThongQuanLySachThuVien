package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KhoSach {
    // Đường dẫn file JSON
    private static final String FILE_PATH = "data/books.json";

    // Danh sách sách
    private final List<model.Sach> danhSachSach;

    /**
     * Constructor.
     */
    public KhoSach() {

        Type type = DocGhiJson.layTypeDanhSach(model.Sach.class);
        List<model.Sach> ds = DocGhiJson.docDanhSach(FILE_PATH, type);
        danhSachSach = (ds != null) ? ds : new ArrayList<>();
    }

    // Lấy toàn bộ danh sách sách.
    public List<model.Sach> getDanhSachSach() {
        return danhSachSach;
    }

    //Tìm sách theo mã.

    public model.Sach timTheoMa(String maSach) {
        if (maSach == null || maSach.trim().isEmpty()) {
            return null;
        }
        for (model.Sach sach : danhSachSach) {
            if (maSach.equals(sach.getMaSach())) {
                return sach;
            }
        }
        return null;
    }

    //Thêm sách.

    public boolean them(model.Sach sach) {
        if (sach == null) {
            return false;
        }
        if (timTheoMa(sach.getMaSach()) != null) {
            return false;
        }
        danhSachSach.add(sach);
        luu();
        return true;
    }

    //Cập nhật sách
    public boolean capNhat(model.Sach sachMoi) {

        if (sachMoi == null) {
            return false;
        }

        for (int i = 0; i < danhSachSach.size(); i++) {
            if (danhSachSach.get(i).getMaSach().equals(sachMoi.getMaSach())) {
                danhSachSach.set(i, sachMoi);
                luu();
                return true;
            }
        }
        return false;
    }

    //Xóa sách

    public boolean xoa(String maSach) {
        model.Sach sach = timTheoMa(maSach);
        if (sach == null) {
            return false;
        }
        danhSachSach.remove(sach);
        luu();
        return true;
    }

    //Lưu dữ liệu xuống file JSON.

    private void luu() {
        DocGhiJson.ghiDanhSach(FILE_PATH, danhSachSach);
    }

}
