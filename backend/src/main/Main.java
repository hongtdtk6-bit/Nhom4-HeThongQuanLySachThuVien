package main;

import com.sun.net.httpserver.HttpServer;
import handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );

        // API sách
        server.createContext(
                "/api/sach",
                new SachHandler()
        );

        // API bạn đọc
        server.createContext(
                "/api/bandoc",
                new BanDocHandler()
        );

        // API mượn sách
        server.createContext(
                "/api/muon",
                new MuonSachHandler()
        );

        // API trả sách
        server.createContext(
                "/api/tra",
                new TraSachHandler()
        );
        //API lichsu
        server.createContext(
                "/api/lichsu",
                new LichSuHandler()
        );

        server.setExecutor(null);

        server.start();

        System.out.println("SERVER ĐÃ KHỞI ĐỘNG");
        System.out.println("Port: 8080");

        System.out.println("GET  http://localhost:8080/api/sach");
        System.out.println("GET  http://localhost:8080/api/bandoc");
        System.out.println("POST http://localhost:8080/api/muon");
        System.out.println("POST http://localhost:8080/api/tra");
        System.out.println("POST http://localhost:8080/api/lichsu");

    }

}
