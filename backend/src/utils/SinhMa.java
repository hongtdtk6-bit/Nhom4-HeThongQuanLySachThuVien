package utils;

import java.util.List;

public class SinhMa {

    public static String sinhMa(String tienTo, List<String> danhSachMa) {
        int max = 0;
        for (String ma : danhSachMa) {
            if (ma != null && ma.startsWith(tienTo)) {
                try {
                    int so = Integer.parseInt(ma.substring(tienTo.length()));
                    if (so > max) {
                        max = so;
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua mã không hợp lệ
                }
            }
        }
        return String.format("%s%03d", tienTo, max + 1);
    }

}