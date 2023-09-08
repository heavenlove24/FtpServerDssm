package org.example;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.command.CommandFactoryFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws FtpException {


        FtpServerFactory serverFactory = new FtpServerFactory();

        CommandFactoryFactory commandFactoryFactory = new CommandFactoryFactory();
        commandFactoryFactory.addCommand("ALLO", new Allo());
        serverFactory.setCommandFactory(commandFactoryFactory.createCommandFactory());

        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(21);
        listenerFactory.setServerAddress("0.0.0.0");
        serverFactory.addListener("default", listenerFactory.createListener());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        UserManager um = userManagerFactory.createUserManager();
        BaseUser user = new BaseUser();
        user.setName("admin");
        user.setPassword("admin");
        user.setHomeDirectory("/ftpuser");
        List<Authority> authorities = new ArrayList<>();
        String rights = "pwd|cd|dir|put|get|rename|delete|mkdir|rmdir|append";
        parseAuthorities(authorities, rights);
        authorities.add(new WritePermission());
        authorities.add(new ConcurrentLoginPermission(50, 50));
        user.setAuthorities(authorities);
        um.save(user);
        serverFactory.setUserManager(um);

        FtpServer server = serverFactory.createServer();
        // start the server
        server.start();
    }
    private static void parseAuthorities(List<Authority> authorities, String rights) {
        if (rights.contains("pwd")) {
            authorities.add(new Authorities.PWDPermission());
        }
        if (rights.contains("cd")) {
            authorities.add(new Authorities.CWDPermission());
        }
        if (rights.contains("dir")) {
            authorities.add(new Authorities.ListPermission());
        }
        if (rights.contains("put")) {
            authorities.add(new Authorities.StorePermission());
        }
        if (rights.contains("get")) {
            authorities.add(new Authorities.RetrievePermission());
        }
        if (rights.contains("rename")) {
            authorities.add(new Authorities.RenameToPermission());
        }
        if (rights.contains("delete")) {
            authorities.add(new Authorities.DeletePermission());
        }
        if (rights.contains("rmdir")) {
            authorities.add(new Authorities.RemoveDirPermission());
        }
        if (rights.contains("mkdir")) {
            authorities.add(new Authorities.MakeDirPermission());
        }
        if (rights.contains("append")) {
            authorities.add(new Authorities.AppendPermission());
        }
    }
}