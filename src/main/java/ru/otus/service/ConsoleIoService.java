package ru.otus.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

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
