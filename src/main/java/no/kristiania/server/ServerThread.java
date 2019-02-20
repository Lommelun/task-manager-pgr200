package no.kristiania.server;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

            String protocol = input.readLine();
            Map<String, String> headers = input.lines()
                    .skip(1)
                    .takeWhile(c -> !c.isEmpty())
                    .map(s -> s.split(": "))
                    .collect(Collectors.toMap(a -> a[0], a -> a[1]));

            output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            client.shutdownInput();

            System.out.println(protocol);
            headers.forEach((a, b) -> System.out.print(a + ": " + b + "\n"));

            String data = input.readLine();
            System.out.println(data);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
