package model;

public class PhatGiangVien implements ChinhSachPhat{
    private static final double TIEN_PHAT_MOI_NGAY = 2000;
    @Override
    public double tinhTienPhat(int soNgayTre) {
        if (soNgayTre <= 0) {
            return 0;
        }
        return soNgayTre * TIEN_PHAT_MOI_NGAY;
    }
}
