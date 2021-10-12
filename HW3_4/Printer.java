package HW3_4;

public class Printer {

    //Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
    // (порядок – ABСABСABС). Используйте wait/notify/notifyAll.


    private char currentLetterNew = 'A';

    public synchronized void print(char inboundLetter, char nextLetter) { // wait(), notify() и notifyAll() могут быть вызваны только из синхронизированного контекста
        try {
            for (int i = 0; i < 5; i++) {
                while (currentLetterNew != inboundLetter) {
                    wait(); //вынуждает вызывающий поток исполнения уступить монитор и перейти в состояние ожидания до тех пор, пока какой-нибудь другой поток не войдет в тот же монитор и не вызовет метод notify().

                }
                Thread.sleep(100);
                System.out.print(inboundLetter);
                currentLetterNew = nextLetter;
                notifyAll(); //возобновляет исполнение всех потоков, из которых был вызван метод wait() для того же самого монитора. Одному из этих потоков предоставляется доступ.
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}









