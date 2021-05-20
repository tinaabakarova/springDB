package ru.otus.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class ConsoleIoService implements IoService {
    private final PrintStream out;

    public ConsoleIoService(PrintStream out) {
        this.out = out;
    }

    @Override
    public void out(String message) {
        out.println(message);
    }
}
