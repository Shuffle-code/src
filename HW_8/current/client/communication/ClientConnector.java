package HW_8.current.client.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientConnector {/*Класс для создания соединения, хранения сообщений getIn и getOut */
    private Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
//    private boolean Timeout = true;

    public ClientConnector() {
        try {
            socket = new Socket("127.0.0.1", 8888);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException("Chat was not started.", e);
        }
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }
}
