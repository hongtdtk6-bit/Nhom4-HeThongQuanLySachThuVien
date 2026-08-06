package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class XuLyNgay {

    private static final DateTimeFormatter DINH_DANG =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static String layNgayHienTai() {
        return LocalDate.now().format(DINH_DANG);
    }

    public static String tinhNgayHenTra(int soNgayMuon) {
        return LocalDate.now()
                .plusDays(soNgayMuon)
                .format(DINH_DANG);
    }

    public static int tinhSoNgayTre(String ngayHenTra, String ngayTra) {
        LocalDate henTra = LocalDate.parse(ngayHenTra, DINH_DANG);
        LocalDate tra = LocalDate.parse(ngayTra, DINH_DANG);
        long soNgay = ChronoUnit.DAYS.between(henTra, tra);
        return (int) Math.max(0, soNgay);
    }

    public static boolean quaHan(String ngayHenTra) {
        LocalDate henTra = LocalDate.parse(ngayHenTra, DINH_DANG);
        return LocalDate.now().isAfter(henTra);
    }

}