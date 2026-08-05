package service;

import java.util.ArrayList;
import java.util.List;

public class DichVuSach {
    private final repository.KhoSach khoSach;

    public DichVuSach() {
        khoSach = new repository.KhoSach();
    }
    public List<model.Sach> layDanhSachSach() {
        return khoSach.getDanhSachSach();
    }

    public void themSach(model.Sach sach) {
        // Sinh mã sách tự động
        List<String> dsMa = new ArrayList<>();
        for (model.Sach s : khoSach.getDanhSachSach()) {
            dsMa.add(s.getMaSach());
        }

        String maSach = utils.SinhMa.sinhMa("S", dsMa);
        sach.setMaSach(maSach);
        khoSach.them(sach);
    }

    // Cập nhật sách
    public boolean capNhatSach(model.Sach sach) {
        return khoSach.capNhat(sach);
    }

    //Xóa sách
    public boolean xoaSach(String maSach) {
        return khoSach.xoa(maSach);
    }
}
