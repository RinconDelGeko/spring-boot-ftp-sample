package com.gekosoft.sftpupload.sftpserver;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;

/**
 *
 * @author CamperRdG
 */
public class SftpServerEmbedded {

  private SshServer server;
  
  private Path sftpPath;
  
  private int port;

  private String user;
  
  private String pass;
  
  private String host;
  

  public SftpServerEmbedded(int port, String user, String pass, String host, Path path) {
    this.server = SshServer.setUpDefaultServer();
    this.port = port;
    this.user = user;
    this.pass = pass;
    this.host = host;
    this.sftpPath = path;
  }
  
  public void configServer(){
 
    server.setPort(port);
    server.setHost(host);
    server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("key.ser")));
    server.setPasswordAuthenticator(new PasswordAuthenticator() {
      @Override
      public boolean authenticate(String userInput, String passInput, ServerSession ss) throws PasswordChangeRequiredException {
        return user.equals(userInput) && pass.equals(passInput);
      }
    });
    server.setSubsystemFactories(Collections.<NamedFactory<Command>>singletonList(new SftpSubsystemFactory()));
    server.setFileSystemFactory(new VirtualFileSystemFactory(sftpPath));
  }

  public SshServer getServer() {
    return server;
  }

  public void setServer(SshServer server) {
    this.server = server;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }
  
  
  
}
