package handler;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import service.DichVuLichSu;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LichSuHandler implements HttpHandler {
    private final DichVuLichSu dichVuLichSu;
    private final Gson gson;
    public LichSuHandler(){
        dichVuLichSu = new DichVuLichSu();
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        // CORS
        exchange.getResponseHeaders()
                .add(
                        "Access-Control-Allow-Origin",
                        "*"
                );

        exchange.getResponseHeaders()
                .add(
                        "Access-Control-Allow-Headers",
                        "Content-Type"
                );

        exchange.getResponseHeaders()
                .add(
                        "Access-Control-Allow-Methods",
                        "GET, OPTIONS"
                );

        // OPTIONS
        if(exchange.getRequestMethod()
                .equalsIgnoreCase("OPTIONS")){
            exchange.sendResponseHeaders(
                    204,
                    -1
            );
            return;
        }

        String method = exchange.getRequestMethod();
        switch(method){
            case "GET":
                layLichSu(exchange);
                break;
            default:
                guiPhanHoi(
                        exchange,
                        405,
                        "Method Not Allowed"
                );
        }

    }
    private void layLichSu(HttpExchange exchange)
            throws IOException {

        String json =
                gson.toJson(
                        dichVuLichSu.layLichSu()
                );

        guiPhanHoi(
                exchange,
                200,
                json
        );

    }

    private void guiPhanHoi(
            HttpExchange exchange,
            int status,
            String response
    ) throws IOException {

        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );

        byte[] data =
                response.getBytes(
                        StandardCharsets.UTF_8
                );

        exchange.sendResponseHeaders(
                status,
                data.length
        );

        OutputStream os = exchange.getResponseBody();
        os.write(data);
        os.close();
    }
}

