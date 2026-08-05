package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.DichVuMuonSach;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

 //API quản lý mượn sách.

public class MuonSachHandler implements HttpHandler {
    private final DichVuMuonSach dichVuMuonSach;
    private final Gson gson;
    public MuonSachHandler() {
        dichVuMuonSach = new DichVuMuonSach();
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
                layDanhSachPhieuMuon(exchange);
                break;
            case "POST":
                taoPhieuMuon(exchange);
                break;
            default:
                guiPhanHoi(exchange, 405, "Method Not Allowed");
        }
    }

     //GET /api/muon

    private void layDanhSachPhieuMuon(HttpExchange exchange)
            throws IOException {
        String json = gson.toJson(
                dichVuMuonSach.layDanhSachPhieuMuon()
        );
        guiPhanHoi(exchange, 200, json);

    }

     // POST /api/muon

    private void taoPhieuMuon(HttpExchange exchange)
            throws IOException {
        InputStream input = exchange.getRequestBody();
        String body = new String(
                input.readAllBytes(),
                StandardCharsets.UTF_8
        );

        JsonObject json = gson.fromJson(body, JsonObject.class);
        String maBanDoc = json.get("maBanDoc").getAsString();
        String maSach = json.get("maSach").getAsString();
        String ketQua = dichVuMuonSach.muonSach(
                maBanDoc,
                maSach
        );
        guiPhanHoi(exchange, 200, ketQua);
    }

     //Gửi phản hồi

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

    //Thiết lập CORS.

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
