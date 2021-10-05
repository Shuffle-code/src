package HW_8.current.client.communication;

import java.io.IOException;

public class ClientCommunicator {/*Класс для отправки и получения сообщений*/

    private final ClientConnector connector;

    public ClientCommunicator() {
        connector = new ClientConnector();
    }

    public void sendMessage(String outboundMessage) {
        try {
            connector.getOut().writeUTF(outboundMessage);
//            connector.getOut().writeUTF("");
        } catch (IOException e) {
            throw new RuntimeException("Issue occurred while sending a message.", e);
        }
    }

    public String receiveMessage() {
        try {
            return connector.getIn().readUTF();
        } catch (IOException e) {
            throw new RuntimeException("Issue occurred while receiving a message.", e);
        }
    }

}
