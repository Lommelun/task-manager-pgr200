package no.kristiania.server;

import no.kristiania.server.http.RequestHandler;

import java.io.*;
import java.net.Socket;

class ServerThread implements Runnable {
    private Socket client;

    ServerThread(Socket client) {
        this.client = client;
        System.out.println("ServerThread:Handling request for " + client.getInetAddress());
    }

    @Override
    public void run() {
        System.out.println("ServerThread:run:Running...");

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            OutputStream output = this.client.getOutputStream();

            RequestHandler request = new RequestHandler(input);

            output.write(request.reply());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
