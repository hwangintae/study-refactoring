package org.intaehwang.chapter06.splitPhase;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JavaExampleCode {
    public static void main(String[] args) {
        try {
            System.out.println(run(args));
        } catch(Exception ignore) {}
    }

    public static long run(String[] args) throws IOException {
        if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");
        CommandLine commandLine = new CommandLine();
        commandLine.onlyCountReady = Stream.of(args).anyMatch(arg -> "-r".equals(arg));
        commandLine.fileName = args[args.length - 1];


        return countOrders(commandLine, args);
    }

    private static long countOrders(CommandLine commandLine, String[] args) throws IOException {
        File input = Paths.get(commandLine.fileName).toFile();
        ObjectMapper mapper = new ObjectMapper();
        Order[] orders = mapper.readValue(input, Order[].class);

        if (commandLine.onlyCountReady) {
            return Stream.of(orders)
                    .filter(o -> "ready".equals(o.status()))
                    .count();
        } else return orders.length;
    }

    private static  class CommandLine {
        boolean onlyCountReady;
        String fileName;
    }
}
