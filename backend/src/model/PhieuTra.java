package model;

public class PhieuTra {
    private String maPhieuTra;
    private String maPhieuMuon;
    private String ngayTra;
    private int soNgayTre;
    private double tienPhat;

    public PhieuTra() {
    }

    public PhieuTra(String maPhieuTra, String maPhieuMuon, String ngayTra, int soNgayTre, double tienPhat) {
        this.maPhieuTra = maPhieuTra;
        this.maPhieuMuon = maPhieuMuon;
        this.ngayTra = ngayTra;
        this.soNgayTre = soNgayTre;
        this.tienPhat = tienPhat;
    }

    public String getMaPhieuTra() {
        return maPhieuTra;
    }

    public void setMaPhieuTra(String maPhieuTra) {
        this.maPhieuTra = maPhieuTra;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getSoNgayTre() {
        return soNgayTre;
    }

    public void setSoNgayTre(int soNgayTre) {
        this.soNgayTre = soNgayTre;
    }

    public double getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(double tienPhat) {
        this.tienPhat = tienPhat;
    }

    @Override
    public String toString() {
        return "PhieuTra{" +
                "maPhieuTra='" + maPhieuTra + '\'' +
                ", maPhieuMuon='" + maPhieuMuon + '\'' +
                ", ngayTra='" + ngayTra + '\'' +
                ", soNgayTre=" + soNgayTre +
                ", tienPhat=" + tienPhat +
                '}';
    }
}