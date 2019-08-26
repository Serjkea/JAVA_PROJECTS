package ru.skprojects.chat.server;

import ru.skprojects.chat.backup.Backup;
import ru.skprojects.chat.backup.MessagesBackup;
import ru.skprojects.chat.backup.UsersBackup;
import ru.skprojects.configuration.Config;
import ru.skprojects.configuration.ConfigFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatServerControllerImpl implements ChatServerController {

    public ChatServerControllerImpl() {
        config.loadConfig();
    }

    public ChatServerControllerImpl(int port) {
        properties.put("port", port);
    }

    private static final String PATH = System.getProperty("user.dir") + "/";
    private static final String USERS_STORE_NAME = "users.dat";
    private static final String MESSAGES_STORE_NAME = "messages.dat";

    private Properties properties = new Properties();

    {
        properties.put("port", "5005");
        properties.put("backup", "false");
    }

    private Config<Properties> config = ConfigFactory.getPropertiesConfig(properties);
    private Backup<ArrayList<User>> usersBackup = new UsersBackup(PATH + USERS_STORE_NAME);
    private Backup<ArrayList<String>> messagesBackup = new MessagesBackup(PATH + MESSAGES_STORE_NAME);

    private ServerSocket serverSocket;
    private Socket client;

    private ArrayList<ClientService> clients = new ArrayList<ClientService>();
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<String> messages = new ArrayList<String>();

    @Override
    public void startServer() {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("port")));
            if (Boolean.parseBoolean(properties.getProperty("backup"))) {
                users = usersBackup.load();
                messages = messagesBackup.load();
            }
            System.out.println("Server was started!");
            while(true) {
                client = serverSocket.accept();
                ClientService clientService = new ClientService(this, client);
                clients.add(clientService);
                clientService.start();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopServer();
        }
    }

    @Override
    public void stopServer() {
        try {
            serverSocket.close();
            usersBackup.save(users);
            messagesBackup.save(messages);
            properties.setProperty("backup","true");
            config.saveConfig();
            System.out.println("Server was stopped!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        Lock lock = new ReentrantLock();
        lock.lock();
        messages.add(message);
        BufferedWriter outputStream = null;
        for(ClientService client: clients) {
            try {
                outputStream = client.getOutputStream();
                outputStream.write(message + "/n");
                outputStream.flush();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    @Override
    public void restoreMessages(ClientService client) {
        Lock lock = new ReentrantLock();
        lock.lock();
        BufferedWriter outputClientStream = client.getOutputStream();
        for (String msg: messages) {
            try {
                outputClientStream.write(msg + "/n");
                outputClientStream.flush();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    @Override
    public void removeClient(ClientService client) {
        Lock lock = new ReentrantLock();
        lock.lock();
        clients.remove(client);
        clients.trimToSize();
        lock.unlock();
    }

    @Override
    public String login(BufferedWriter ocs, BufferedReader ics) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            ocs.write(":#login" + "/n");
            String login = ics.readLine();
            for (User u: users) {
                if(u.getName().equals(login)) {
                    ocs.write(":#pass");
                    String password = ics.readLine();
                    if (u.getPassword().equals(password)) {
                        u.isLogin = true;
                        lock.unlock();
                        return login;
                    }
                    lock.unlock();
                    return null;
                }
            }
            ocs.write(":#pass");
            String password = ics.readLine();
            ocs.write(":#rpass");
            if (password.equals(ics.readLine())) {
                users.add(new User(login,password));
                lock.unlock();
                return login;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        lock.unlock();
        return null;
    }

}
