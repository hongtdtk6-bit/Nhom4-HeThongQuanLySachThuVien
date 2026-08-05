package model;

public class PhatSinhVienUuTien implements ChinhSachPhat {
    private static final double TEN_PHAT_MOI_NGAY = 3000.0;
    @Override
    public double tinhTienPhat(int soNgayTre) {
        if (soNgayTre <= 0) {
            return 0.0;
        }
        return soNgayTre * TEN_PHAT_MOI_NGAY;
    }
}
