import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

import nl.loadingdata.generator.Fibo;
import nl.loadingdata.generator.For;
import nl.loadingdata.generator.Generator;
import nl.loadingdata.generator.LineReader;
import nl.loadingdata.generator.ListIterator;
import nl.loadingdata.generator.Range;
import nl.loadingdata.generator.SocketServer;

public class Tester {

    public static void main(String[] args) {
        System.out.println("for Range");
        for (Integer i : Range.of(0, 100)) {
            System.out.println(i);
        }

        System.out.println("Range.forEach");
        Range.of(0, 10).inclusive()	
            .forEach(System.out::println);

        System.out.println("For");
        For.of(0, i -> i + 1, i -> i < 10)
            .forEach(System.out::println);

        System.out.println("Fibo");
        Fibo.until(1000)
            .forEach(System.out::println);

        System.out.println("Nested");
        squarer(downcounter(10))
            .forEach(System.out::println);

        System.out.println("List");
        ListIterator.of(Arrays.asList("one", "two", "three"))
            .forEach(System.out::println);

        System.out.print("Read input ('bye' to end)\n> ");
        for (String line : LineReader.read(System.in)) {
            if (line.equals("bye")) break;
            System.out.println(line);
        }

        System.out.println("SocketServer (port 8888)");
        for (Socket socket : SocketServer.listen(8888)) {
            fork(socket);
        }
    }

    private static void fork(Socket socket) {
        System.out.println("Fork: " + socket.getRemoteSocketAddress());
        new Thread(() -> {
            try {
                lineWriter(
                    socket.getOutputStream(),

                    Generator.create(gen -> {
                        gen.yield("Welcome to Echo ('bye' to end)\r\n> ");
                        try {
                            for (String line : LineReader.read(socket.getInputStream())) {
                                gen.yield(line + "\r\n> ");
                                if (line.equals("bye")) break;
                            }
                        } catch (IOException e) {
                            gen.pending = e;
                        }
                    })
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static Iterable<Integer> downcounter(int start) {
        return Generator.create(gen -> {
            for (int i = start; i > 0; i--) gen.yield(i);
        });
    }

    private static Iterable<Integer> squarer(Iterable<Integer> in) {
        return Generator.create(gen -> {
            in.forEach(i -> gen.yield(i * i));
        });
    }

    private static void lineWriter(OutputStream os, Iterable<String> it) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os));
        it.forEach(line -> {
            try {
                out.write(line);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        out.close();
    }
}
