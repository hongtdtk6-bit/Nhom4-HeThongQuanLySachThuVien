package service;

import model.BanDoc;
import model.ChinhSachPhat;
import model.PhatGiangVien;
import model.PhatSinhVienThuong;
import model.PhatSinhVienUuTien;
import model.PhieuMuon;
import model.PhieuTra;
import model.Sach;
import repository.KhoPhieuMuon;
import repository.KhoPhieuTra;
import repository.KhoSach;
import repository.KhoBanDoc;
import utils.SinhMa;
import utils.XuLyNgay;

import java.util.ArrayList;
import java.util.List;

/**
 * Dịch vụ xử lý trả sách.
 */
public class DichVuTraSach {
    private final KhoSach khoSach;
    private final KhoBanDoc khoBanDoc;
    private final KhoPhieuMuon khoPhieuMuon;
    private final KhoPhieuTra khoPhieuTra;

    public DichVuTraSach() {
        khoSach = new KhoSach();
        khoBanDoc = new KhoBanDoc();
        khoPhieuMuon = new KhoPhieuMuon();
        khoPhieuTra = new KhoPhieuTra();
    }

    //Tra Sach

    public String traSach(String maPhieuMuon) {
        // Kiểm tra mã phiếu mượn
        if (maPhieuMuon == null || maPhieuMuon.trim().isEmpty()) {
            return "Mã phiếu mượn không hợp lệ.";
        }

        // Tìm phiếu mượn
        PhieuMuon phieuMuon = khoPhieuMuon.timTheoMa(maPhieuMuon);
        if (phieuMuon == null) {
            return "Không tìm thấy phiếu mượn.";
        }

        // Kiểm tra trạng thái
        if ("Đã trả".equalsIgnoreCase(phieuMuon.getTrangThai())) {
            return "Phiếu mượn này đã được trả.";
        }

        // Tìm bạn đọc
        BanDoc banDoc = khoBanDoc.timTheoMa(phieuMuon.getMaBanDoc());
        if (banDoc == null) {
            return "Không tìm thấy bạn đọc.";
        }

        // Tìm sách
        Sach sach = khoSach.timTheoMa(phieuMuon.getMaSach());

        if (sach == null) {
            return "Không tìm thấy sách.";
        }

        // Lấy ngày trả
        String ngayTra = XuLyNgay.layNgayHienTai();

        // Tính số ngày trễ
        int soNgayTre = Math.max(0, XuLyNgay.tinhSoNgayTre(
                        phieuMuon.getNgayHenTra(),
                        ngayTra
                )
        );

        // Chọn chính sách phạt
        ChinhSachPhat chinhSachPhat;

        switch (banDoc.getLoaiBanDoc()) {

            case "Sinh viên ưu tiên":
                chinhSachPhat = new PhatSinhVienUuTien();
                break;

            case "Giảng viên":
                chinhSachPhat = new PhatGiangVien();
                break;

            case "Sinh viên thường":
            default:
                chinhSachPhat = new PhatSinhVienThuong();
                break;
        }

        // Tính tiền phạt
        double tienPhat = chinhSachPhat.tinhTienPhat(soNgayTre);

        // Sinh mã phiếu trả
        List<String> dsMa = new ArrayList<>();

        for (PhieuTra pt : khoPhieuTra.getDanhSachPhieuTra()) {
            dsMa.add(pt.getMaPhieuTra());
        }

        String maPhieuTra = SinhMa.sinhMa("PT", dsMa);

        // Tạo phiếu trả
        PhieuTra phieuTra = new PhieuTra(
                maPhieuTra,
                maPhieuMuon,
                ngayTra,
                soNgayTre,
                tienPhat
        );

        // Lưu phiếu trả
        khoPhieuTra.them(phieuTra);

        // Cập nhật trạng thái phiếu mượn
        phieuMuon.setTrangThai("Đã trả");
        khoPhieuMuon.capNhat(phieuMuon);

        // Tăng số lượng sách
        sach.setSoLuong(sach.getSoLuong() + 1);
        khoSach.capNhat(sach);

        // Giảm số sách đang mượn của bạn đọc
        banDoc.setSoSachDangMuon(
                Math.max(0, banDoc.getSoSachDangMuon() - 1)
        );
        khoBanDoc.capNhat(banDoc);

        return "Trả sách thành công.\n"
                + "Mã phiếu trả: " + maPhieuTra
                + "\nSố ngày trễ: " + soNgayTre
                + "\nTiền phạt: " + tienPhat + " VNĐ";
    }

    public List<PhieuTra> layDanhSachPhieuTra() {
        return khoPhieuTra.getDanhSachPhieuTra();
    }

}
