package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.DichVuTraSach;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TraSachHandler implements HttpHandler {
    private final DichVuTraSach dichVuTraSach;
    private final Gson gson;
    public TraSachHandler() {
        dichVuTraSach = new DichVuTraSach();
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        thietLapCors(exchange);
        // Xử lý preflight request từ frontend
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        switch (exchange.getRequestMethod()) {
            case "GET":
                layDanhSachPhieuTra(exchange);
                break;
            case "POST":
                taoPhieuTra(exchange);
                break;
            default:
                guiPhanHoi(exchange, 405, "Method Not Allowed");
                break;
        }
    }

    // GET /api/tra

    private void layDanhSachPhieuTra(HttpExchange exchange)
            throws IOException {
        String json = gson.toJson(
                dichVuTraSach.layDanhSachPhieuTra()
        );
        guiPhanHoi(exchange, 200, json);

    }

    private void taoPhieuTra(HttpExchange exchange)
            throws IOException {

        InputStream input = exchange.getRequestBody();

        String body = new String(
                input.readAllBytes(),
                StandardCharsets.UTF_8
        );

        JsonObject json = gson.fromJson(
                body,
                JsonObject.class
        );

        String maPhieuMuon = json
                .get("maPhieuMuon")
                .getAsString();

        String ketQua = dichVuTraSach.traSach(
                maPhieuMuon
        );

        guiPhanHoi(exchange, 200, ketQua);
    }

    //Gửi phản hồi JSON

    private void guiPhanHoi(HttpExchange exchange,
                            int statusCode,
                            String response)
            throws IOException {

        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json; charset=UTF-8"
        );

        byte[] bytes = response.getBytes(
                StandardCharsets.UTF_8
        );

        exchange.sendResponseHeaders(
                statusCode,
                bytes.length
        );
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    //Thiết lập CORS

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
