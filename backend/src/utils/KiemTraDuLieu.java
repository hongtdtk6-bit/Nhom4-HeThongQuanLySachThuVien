package utils;

public class KiemTraDuLieu {
    public static boolean khongRong(String chuoi) {
        return chuoi != null && !chuoi.trim().isEmpty();
    }

    public static boolean soDienThoaiHopLe(String soDienThoai) {
        if (soDienThoai == null) {
            return false;
        }
        return soDienThoai.matches("^0\\d{9}$");
    }

    public static boolean laSoNguyenDuong(int so) {
        return so > 0;
    }

    public static boolean laSoKhongAm(int so) {
        return so >= 0;
    }


}