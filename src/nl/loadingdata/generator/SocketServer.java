package nl.loadingdata.generator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Generator<Socket> {
    private int port;
    private boolean running;

    public SocketServer(int port) {
        this.port = port;
    }

    public static SocketServer listen(int port) {
    	return new SocketServer(port);
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        running = true;
        try {
            ServerSocket server = new ServerSocket(port);

            while (running) {
                Socket socket = server.accept();
                yield(socket);
            }

            server.close();
        } catch (IOException e) {
            pending = e;
        }
    }

}
