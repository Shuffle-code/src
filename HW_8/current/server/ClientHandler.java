package HW_8.current.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class ClientHandler {

    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private final Socket socket;

    History history = new History();

    public ClientHandler(Server server, Socket socket) {
        this.socket = socket;
        try {
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    doAuthentication();
                    listenMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            })
                    .start();
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during client establishing...", e);
        }
    }

    private void closeConnection() {
        server.unsubscribe(this);
        server.broadcastMessage(String.format("User[%s] is out.", name));

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

//    private void timeOutClose() {
//        long timeIsNow = System.currentTimeMillis();
//            while (true) {
//                if ((System.currentTimeMillis() - timeIsNow) >= 120000) {
//                    sendMessage("Auth timeout. Connection closed...");
//                    closeConnection();
//                }
//            }
//    }

    private void doAuthentication() throws IOException {
        sendMessage("Greeting you in the Outstanding Chat.");
        sendMessage("Please do authentication. Template is: -auth [login] [password]");

        while (true) {
            String maybeCredentials = in.readUTF();
            /** sample: -auth login1 password1 */
            if (maybeCredentials.startsWith("-auth")) {

                String[] credentials = maybeCredentials.split("\\s");

                Optional<AuthService.Entry> maybeUser = server.getAuthService()
                        .getNickByLoginAndPass(credentials[1], credentials[2]); // replaced search with database

                if (maybeUser.isPresent()) {
                    AuthService.Entry user = maybeUser.get();
                    if (server.isNotUserOccupied(user.getName())) {
                        name = user.getName();
                        sendMessage("AUTH OK.");
                        sendMessage("Welcome.");
                        int size = history.readM().size();
                        if (size < 100){
                            for (int j = 0; j < size; j++) {
                                sendMessage(history.readM().get(j));
                            }
                        } else for (int i = size; i > 0  ; i--) {
                            sendMessage(history.readM().get(i - 1));
                        }
                        server.broadcastMessage(String.format("User[%s] entered chat.", name));
                        server.subscribe(this);
                        return;
                    } else {
                        sendMessage("Current user is already logged in");
                    }
                } else {
                    sendMessage("Invalid credentials.");
                }
            } else {
//                sendMessage("Invalid auth operation");
                socket.setSoTimeout(12000);
                break;
            }
        }
    }

    public void sendMessage(String outboundMessage) {
        try {
            out.writeUTF(outboundMessage);
//            history.write(outboundMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageArr(byte[] b) {
        try {
            out.write(b);
//            history.write(outboundMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenMessages() throws IOException {
        while (true) {
            String inboundMessage = in.readUTF();
            if (inboundMessage.equals("-exit")) {
                break;
            }
            server.broadcastMessage(inboundMessage);
            history.writeL(inboundMessage);

        }
    }

}
