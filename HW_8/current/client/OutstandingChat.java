package HW_8.current.client;

import HW_8.current.client.communication.ClientCommunicator;
import HW_8.current.client.gui.ChatFrame;

import java.util.function.Consumer;

public class OutstandingChat {/*Основной класс для запуска ChatFrame и ClientCommunicator*/

    private final ChatFrame frame;
    private final ClientCommunicator communicator;

    public OutstandingChat() {
        communicator = new ClientCommunicator();

        Consumer<String> outboundMessageConsumer = communicator::sendMessage;/* Получение сообщений и отправка, через sendMessage(входящее сообщение) */
//        Consumer<String> outboundMessageConsumer = new Consumer<String>() {
//            @Override
//            public void accept(String outboundMessage) {
//            }
//        };


//        Consumer<String> outboundMessageConsumer = outboundMessage -> communicator.sendMessage(outboundMessage);

        frame = new ChatFrame(outboundMessageConsumer);

        new Thread(() -> {
            while (true) {
                String inboundMessage = communicator.receiveMessage();
                frame.getInboundMessageConsumer().accept(inboundMessage);
            }
        })
                .start();
    }


}
