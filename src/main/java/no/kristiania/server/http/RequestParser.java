package no.kristiania.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

class RequestParser {
    private BufferedReader input;

    RequestParser(BufferedReader input) throws IOException {
        this.input = input;
    }

    Request parse() throws IOException, IllegalArgumentException {
        String statusLine = input.readLine();
        if (!statusLine.matches("^(GET|HEAD|POST|PUT|DELETE|CONNECT|OPTIONS|TRACE) (/.*) (HTTP/[0-9](.[0-9])?)$")) {
            throw new IllegalArgumentException("This header status is malformed or not supported on this server");
        }

        // Read HTTP Headers
        Map<String, String> headers = input.lines()
                .skip(1)
                .takeWhile(c -> !c.isEmpty())
                .map(s -> s.split(": "))
                .collect(Collectors.toMap(a -> a[0], a -> a[1]));

        // Read HTTP BodyDTO
        StringBuilder body = new StringBuilder();
        if (headers.get("Content-Length") != null) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            for (int i = 0; i < contentLength; i++) {
                body.append((char) input.read());
            }
        }

        return new Request(statusLine.split(" "), headers, body.toString());
    }
}