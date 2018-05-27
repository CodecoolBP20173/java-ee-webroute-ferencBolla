package com.codecool.bolla;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Server {

    private HttpServer server;

    public void initServer() {
        try {

            createServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
        createContent();
        startServer();
    }


    private void createServer() throws Exception {
        int port = 8080;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
    }

    private void startServer() {
        server.setExecutor(null);
        server.start();
    }

    private void createContent() {
        server.createContext("/", new RequestHandler());
    }

}
