package model;

public class BanDoc extends NguoiDung {
    private String loaiBanDoc;
    private int soSachDangMuon;
    protected int gioiHanMuon;
    public BanDoc() {

    }
    public BanDoc(String maNguoiDung, String hoTen, String soDienThoai, String loaiBanDoc, int soSachDangMuon, int gioiHanMuon) {
        super(maNguoiDung, hoTen, soDienThoai);
        this.loaiBanDoc = loaiBanDoc;
        this.soSachDangMuon = soSachDangMuon;
        this.gioiHanMuon = gioiHanMuon;
    }
    public String getLoaiBanDoc() {
        return loaiBanDoc;
    }
    public void setLoaiBanDoc(String loaiBanDoc) {
        this.loaiBanDoc = loaiBanDoc;
    }
    public int getSoSachDangMuon() {
        return soSachDangMuon;
    }
    public void setSoSachDangMuon(int soSachDangMuon) {
        this.soSachDangMuon = soSachDangMuon;
    }
    public int getGioiHanMuon() {
        return gioiHanMuon;
    }
    
    protected void setGioiHanMuon(int gioiHanMuon) {
        this.gioiHanMuon = gioiHanMuon;
    }
    @Override
    public String toString() {
        return "BanDoc{" +
        "maNguoiDung='" + getMaNguoiDung() + '\'' +
        ", hoTen='" + getHoTen() + '\'' +
        ", soDienThoai='" + getSoDienThoai() + '\'' +
        ", loaiBanDoc='" + loaiBanDoc + '\'' +
        ", soSachDangMuon=" + soSachDangMuon +
        ", gioiHanMuon=" + gioiHanMuon +
        '}';
    }

}
