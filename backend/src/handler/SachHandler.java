package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Sach;
import service.DichVuSach;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class SachHandler implements HttpHandler {
    private final DichVuSach dichVuSach;
    private final Gson gson;
    public SachHandler() {
        dichVuSach = new DichVuSach();
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // Xử lý OPTIONS
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                layDanhSachSach(exchange);
                break;
            case "POST":
                themSach(exchange);
                break;
            case "PUT":
                capNhatSach(exchange);
                break;
            case "DELETE":
                xoaSach(exchange);
                break;
            default:
                guiPhanHoi(exchange, 405, "Method Not Allowed");
        }
    }

    //GET

    private void layDanhSachSach(HttpExchange exchange) throws IOException {
        List<Sach> danhSach = dichVuSach.layDanhSachSach();
        String json = gson.toJson(danhSach);
        guiPhanHoi(exchange, 200, json);
    }

    // POST /api/sach

    private void themSach(HttpExchange exchange) throws IOException {
        InputStream input = exchange.getRequestBody();
        String body = new String(
                input.readAllBytes(),
                StandardCharsets.UTF_8
        );
        Sach sach = gson.fromJson(body, Sach.class);
        dichVuSach.themSach(sach);
        guiPhanHoi(exchange, 200, gson.toJson(sach));
    }

    private void capNhatSach(HttpExchange exchange) throws IOException {
        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );
        Sach sach = gson.fromJson(body, Sach.class);
        boolean thanhCong = dichVuSach.capNhatSach(sach);

        if (thanhCong) {
            guiPhanHoi(exchange, 200, "Cập nhật thành công");
        } else {
            guiPhanHoi(exchange, 404, "Không tìm thấy sách");
        }
    }

    private void xoaSach(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("maSach=")) {
            guiPhanHoi(exchange, 400, "Thiếu mã sách");
            return;
        }
        String maSach = query.substring("maSach=".length());
        boolean thanhCong = dichVuSach.xoaSach(maSach);
        if (thanhCong) {
            guiPhanHoi(exchange, 200, "Xóa thành công");
        } else {
            guiPhanHoi(exchange, 404, "Không tìm thấy sách");
        }
    }

     //Gửi phản hồi về client.

    private void guiPhanHoi(HttpExchange exchange,
                            int statusCode,
                            String response) throws IOException {
        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json; charset=UTF-8"
        );

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

     //Thiết lập CORS.

    private void thietLapCors(HttpExchange exchange) {
        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin",
                "*"
        );
        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );
        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS"
        );
    }
}