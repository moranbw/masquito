package com.moranbw.masquito.common;

import java.io.IOException;
import java.util.concurrent.Executors;

public class ProcessUtil {

    public static void runCommand(DnsMasqCommands aCommand) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(aCommand.getCommand());
        Process process = builder.start();
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        assert exitCode == 0;
    }

}
