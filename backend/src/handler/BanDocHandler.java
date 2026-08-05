package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.BanDoc;
import service.DichVuBanDoc;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class BanDocHandler implements HttpHandler {
    private final DichVuBanDoc dichVuBanDoc;
    private final Gson gson;
    public BanDocHandler() {
        dichVuBanDoc = new DichVuBanDoc();
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        thietLapCors(exchange);
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        switch (exchange.getRequestMethod()) {
            case "GET":
                layDanhSachBanDoc(exchange);
                break;
            case "POST":
                themBanDoc(exchange);
                break;
            case "PUT":
                capNhatBanDoc(exchange);
                break;
            case "DELETE":
                xoaBanDoc(exchange);
                break;
            default:
                guiPhanHoi(exchange, 405, "Method Not Allowed");
        }
    }

    private void layDanhSachBanDoc(HttpExchange exchange)
            throws IOException {
        List<BanDoc> danhSach = dichVuBanDoc.layDanhSachBanDoc();
        guiPhanHoi(exchange, 200, gson.toJson(danhSach));
    }

    //POST /api/bandoc

    private void themBanDoc(HttpExchange exchange)
            throws IOException {

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        BanDoc banDoc = gson.fromJson(body, BanDoc.class);
        boolean thanhCong = dichVuBanDoc.themBanDoc(banDoc);
        if (thanhCong) {
            guiPhanHoi(exchange, 201, "Thêm bạn đọc thành công");
        } else {
            guiPhanHoi(exchange, 400, "Mã bạn đọc đã tồn tại");
        }
    }

    // PUT /api/bandoc

    private void capNhatBanDoc(HttpExchange exchange)

            throws IOException {
        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );
        BanDoc banDoc = gson.fromJson(body, BanDoc.class);
        boolean thanhCong = dichVuBanDoc.capNhatBanDoc(banDoc);

        if (thanhCong) {
            guiPhanHoi(exchange, 200, "Cập nhật bạn đọc thành công");
        } else {
            guiPhanHoi(exchange, 404, "Không tìm thấy bạn đọc");
        }
    }

     // DELETE

    private void xoaBanDoc(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();

        if (query == null || !query.startsWith("maBanDoc=")) {
            guiPhanHoi(exchange, 400, "Thiếu mã bạn đọc");
            return;
        }

        String maBanDoc = query.substring("maBanDoc=".length());
        boolean thanhCong = dichVuBanDoc.xoaBanDoc(maBanDoc);

        if (thanhCong) {
            guiPhanHoi(exchange, 200, "Xóa bạn đọc thành công");
        } else {
            guiPhanHoi(exchange, 404, "Không tìm thấy bạn đọc");
        }
    }

    // Gửi phản hồi.

    private void guiPhanHoi(HttpExchange exchange,
                            int statusCode,
                            String response)
            throws IOException {

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

    // Thiết lập CORS.

    private void thietLapCors(HttpExchange exchange) {
        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );
        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );
        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS"
        );
    }
}
