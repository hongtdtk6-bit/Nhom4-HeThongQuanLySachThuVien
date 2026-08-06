package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DocGhiJson {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> List<T> docDanhSach(String duongDan, Type type) {
        try {
            File file = new File(duongDan);
            // Nếu file chưa tồn tại
            if (!file.exists()) {
                File thuMuc = file.getParentFile();
                if (thuMuc != null && !thuMuc.exists()) {
                    thuMuc.mkdirs();
                }
                file.createNewFile();
                return new ArrayList<>();
            }
            try (InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(file),
                    StandardCharsets.UTF_8)) {
                List<T> danhSach = GSON.fromJson(reader, type);
                if (danhSach == null) {
                    return new ArrayList<>();
                }
                return danhSach;
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file JSON: " + duongDan);
            return new ArrayList<>();
        }
    }

    //Ghi danh sách xuống file JSON.

    public static <T> void ghiDanhSach(String duongDan, List<T> danhSach) {
        try {
            File file = new File(duongDan);
            File thuMuc = file.getParentFile();
            if (thuMuc != null && !thuMuc.exists()) {
                thuMuc.mkdirs();
            }
            try (OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(file),
                    StandardCharsets.UTF_8)) {

                GSON.toJson(danhSach, writer);
            }

        } catch (IOException e) {
            System.out.println("Lỗi ghi file JSON: " + duongDan);
            e.printStackTrace();
        }
    }

    public static <T> Type layTypeDanhSach(Class<T> clazz) {
        return TypeToken
                .getParameterized(List.class, clazz)
                .getType();

    }
}
