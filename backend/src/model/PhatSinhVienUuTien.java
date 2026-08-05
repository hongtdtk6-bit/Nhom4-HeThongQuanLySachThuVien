package model;

public class PhatSinhVienUuTien implements ChinhSachPhat {
    private static final double TIEN_PHAT_MOI_NGAY = 3000;
    @Override
    public double tinhTienPhat(int soNgayTre) {
        if (soNgayTre <= 0) {
            return 0;
        }
        return soNgayTre * TIEN_PHAT_MOI_NGAY;
    }
}
