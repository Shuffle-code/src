package HW3_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        Printer p = new Printer();
        new Thread(() -> p.print('A', 'B')).start(); // вариант  с реализацией интерфейса Runnable, через анонимный внутренний класс и лямбду.
        new Thread(() -> p.print('B', 'C')).start();
        new Thread(() -> p.print('C', 'A')).start();

    }
}
