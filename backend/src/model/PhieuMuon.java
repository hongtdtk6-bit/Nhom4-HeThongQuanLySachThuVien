package model;

public class PhieuMuon {
    private String maPhieuMuon;
    private String maBanDoc;
    private String maSach;
    private String ngayMuon;
    private String ngayHenTra;
    private String trangThai;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPhieuMuon, String maBanDoc, String maSach, String ngayMuon, String ngayHenTra, String trangThai) {
        this.maPhieuMuon = maPhieuMuon;
        this.maBanDoc = maBanDoc;
        this.maSach = maSach;
        this.ngayMuon = ngayMuon;
        this.ngayHenTra = ngayHenTra;
        this.trangThai = trangThai;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }
    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }
    public String getMaBanDoc() {
        return maBanDoc;
    }
    public void setMaBanDoc(String maBanDoc) {
        this.maBanDoc = maBanDoc;
    }
    public String getMaSach() {
        return maSach;
    }
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }
    public String getNgayMuon() {
        return ngayMuon;
    }
    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }
    public String getNgayHenTra() {
        return ngayHenTra;
    }
    public void setNgayHenTra(String ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    @Override
    public String toString() {
        return "PhieuMuon{" +
                "maPhieuMuon='" + maPhieuMuon + '\'' +
                ", maBanDoc='" + maBanDoc + '\'' +
                ", maSach='" + maSach + '\'' +
                ", ngayMuon='" + ngayMuon + '\'' +
                ", ngayHenTra='" + ngayHenTra + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }

}
