package ru.skprojects.chat.client;

import ru.skprojects.configuration.Config;
import ru.skprojects.configuration.ConfigFactory;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ChatClientImpl implements ChatClient {

    private ChatClientController chatClientController;
    private ChatLoginController chatLoginController;

    private Properties properties = new Properties();

    {
        properties.put("host","localhost");
        properties.put("port","5005");
    }

    private Config<Properties> config = ConfigFactory.getPropertiesConfig(properties);

    public ChatClientImpl(ChatLoginController chatLoginController) {
        this.chatLoginController = chatLoginController;
        config.loadConfig();
    }

    public void setChatClientController(ChatClientController chatClientController) {
        this.chatClientController = chatClientController;
    }

    private Socket connection;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private boolean isLogin = false;

    public Socket getConnection() {
        Socket conn = null;
        try {
            conn = new Socket(properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")));
            inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            outputStream = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void sendMessage(String message) {
        try {
            outputStream.write(message + "\n");
            outputStream.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMessage(String message) {
        chatClientController.chatText.appendText(message);
    }

    @Override
    public void startChat() {
        try {
//            connection = getConnection();
            ExecutorService executors = Executors.newFixedThreadPool(1);
            Future future = executors.submit(new Thread() {
                @Override
                public void run() {
                    String msg = null;
                    while(connection.isConnected()) {
                        try {
                            msg = inputStream.readLine();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                        if (msg != null) {
                            getMessage(msg);
                        }
                    }
                    try {
                            connection.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            });
            while(!future.isDone()) {
                executors.shutdown();
            }
            if (connection.isConnected()) {
                connection.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stopChat() {
        try {
            inputStream.close();
            outputStream.close();
            if (connection.isConnected()) {
                connection.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean logIn(String login, String password, String rPassword) {
        try {
            connection = getConnection();
            String msgFromServer = null;
            while (!isLogin) {
                msgFromServer = inputStream.readLine();
                System.out.println(msgFromServer);
                if (msgFromServer.equals(":#ok")) {
                    isLogin = true;
                } else {
                    switch (msgFromServer) {
                        case ":#login":
                        case ":#reg":
                            sendMessage(login);
                            break;
                        case ":#pass":
                            sendMessage(password);
                            break;
                        case ":#rpass":
                            if (password.equals(rPassword)) sendMessage(password);
                            else sendMessage(":#errpass");
                            break;
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return isLogin;
    }

}
