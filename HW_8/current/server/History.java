package HW_8.current.server;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class History {
    private final File historyFile = new File("./history.txt");
    private final File historyF = new File("./test.txt");

    public void writeL(String message) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(historyFile, true))) {
            if (!historyFile.exists()) {
                System.out.println("Create new File");
                historyFile.createNewFile();}
            bw.write(message);
            bw.newLine();

        }
    }


    public void write(String message) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(historyFile, true))) {
            if (!historyFile.exists()) {
                System.out.println("Create new File");
                historyFile.createNewFile();}
            bw.write(message);
            bw.newLine();

        }
    }

    public List<String> readM() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {

              br.read();

              return br.lines()
                    .collect(Collectors.toList());
        }
    }

    public List<String> read(int lines) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {

            br.read();

            return br.lines()
                    .sorted(Collections.reverseOrder())
                    .limit(lines)
                    .collect(Collectors.toList());
        }
    }



    public static String history(List<String> h) throws IOException {
        for (int i = h.size(); i > h.size() - 100 ; i--) {
             String historys = h.get(i);
            return historys;
        }
        return " ";
    }

    public String read2(int lines) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(historyFile, "r");
        long length = historyFile.length() - 1;
        int readLines = 0;
        StringBuilder sb = new StringBuilder();

        raf.seek(length);

        for (long i = length; i >= 0; i--) {
            raf.seek(i);
            char ch = raf.readChar();

            if (ch == '\n') {
                readLines++;
                if (readLines == lines) {
                    break;
                }
            }

            sb.append(ch);
        }

        return sb.reverse().toString();
    }

    public String readLine(int lines) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(historyF))) {
            String line;

            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            br.read();
                return line;
//                    .sorted(Collections.reverseOrder())
//                    .limit(lines)
//                    .collect(Collectors.toList());
            }
        }return " ";
    }




}
