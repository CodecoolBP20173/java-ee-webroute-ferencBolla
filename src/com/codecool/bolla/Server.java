package com.codecool.bolla;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Server {

    private HttpServer server;

    //    @WebRoute()
    public void initServer() {
        try {

            createServer();

        } catch (Exception e) {
            e.printStackTrace();
        }

        startServer();
    }

    private void startServer() {
        server.setExecutor(null);
        server.start();
    }

    private void createServer() throws Exception {
        this.server = HttpServer.create(new InetSocketAddress(8000), 0);
    }

}
