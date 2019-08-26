package ru.skprojects.chat.server;

import java.io.*;
import java.net.Socket;

public class ClientService extends Thread {

    private Socket client;
    private ChatServerController chatServerController;

    private BufferedReader inputStream;
    private BufferedWriter outputStream;

    public ClientService(ChatServerController chatServerController, Socket client) {
        super();
        this.client = client;
        this.chatServerController = chatServerController;
    }

    @Override
    public void run() {
        try {
            inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outputStream = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            String login = null;
            if ((login = chatServerController.login(outputStream,inputStream)) != null) {
                outputStream.write(":#ok");
                chatServerController.restoreMessages(this);
                while (client.isConnected()) {
                    String msg = inputStream.readLine();
                    if (msg.equals(":#exit")) {
                        chatServerController.removeClient(this);
                        break;
                    } else {
                        chatServerController.sendMessage(login + ": " + msg);
                    }
                }
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedWriter getOutputStream() {
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

}
