package model;

public abstract class NguoiDung {
    private String maNguoiDung;
    private String hoTen;
    private String soDienThoai;
    public NguoiDung(){

    }
    public NguoiDung(String maNguoiDung, String hoTen, String soDienThoai) {
        this.maNguoiDung = maNguoiDung;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
    }
    public String getMaNguoiDung() {
        return maNguoiDung;
    }
    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }
    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    @Override
    public String toString() {
        return "NguoiDung{" +
                "maNguoiDung='" + maNguoiDung + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                '}';
    }
}
