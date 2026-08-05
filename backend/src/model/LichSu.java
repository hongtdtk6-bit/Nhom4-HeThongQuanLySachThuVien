package model;

public class LichSu {
    private String maPhieuMuon;
    private String tenBanDoc;
    private String tenSach;
    private String ngayMuon;
    private String ngayTra;
    private String trangThai;

    public LichSu() {
    }

    public LichSu(
            String maPhieuMuon,
            String tenBanDoc,
            String tenSach,
            String ngayMuon,
            String ngayTra,
            String trangThai
    ) {
        this.maPhieuMuon = maPhieuMuon;
        this.tenBanDoc = tenBanDoc;
        this.tenSach = tenSach;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;

    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public String getTenBanDoc() {
        return tenBanDoc;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public String getTrangThai() {
        return trangThai;
    }
}
