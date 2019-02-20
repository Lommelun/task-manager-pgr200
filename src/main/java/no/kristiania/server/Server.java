package no.kristiania.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static boolean running = true;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting HTTP server..");
        ExecutorService executor = Executors.newCachedThreadPool();

        try (ServerSocket socket = new ServerSocket(3000)) {
            System.out.println("Http server running on port " + socket.getLocalPort());

            while (running) {
                try {
                    Socket client = socket.accept();
                    System.out.println(client.getInetAddress() + " connected");

                    executor.execute(new ServerThread(client));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
