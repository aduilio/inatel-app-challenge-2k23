package com.aduilio.appchallenge.netcap.connector.handler;

import com.aduilio.appchallenge.netcap.connector.observer.SocketConnectionObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Performs the Socket connection.
 */
@Slf4j
public class SocketConnectionHandler extends Thread {

    private final SocketConnectionObserver observer;

    private Socket socket;

    public SocketConnectionHandler(final String host,
                                   final int port,
                                   final SocketConnectionObserver observer) throws IOException {
        this.observer = observer;

        socket = new Socket(host, port);

        log.debug("Connecting to " + host + " using the port " + port + ".");
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];

            int bytesRead;
            StringBuilder data = new StringBuilder();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                data.append(new String(buffer, 0, bytesRead));
                //System.out.println("\n\nDATA 1: " + data);
                if (data.toString().contains(">>")) {
                    String content = data.substring(0, data.indexOf(">>"));
                    observer.traffic(content);

                    //System.out.println("\n\nCONTENT: " + content);
                    if (data.length() <= 2) {
                        data = new StringBuilder();
                    } else {
                        data = new StringBuilder(data.substring(data.indexOf(">>") + 2));
                    }
                    //System.out.println("\n\nDATA 2: " + data);
                }
            }
        } catch (IOException e) {
            log.error("Failed to connect to the socket server.", e);
        }
    }
}
