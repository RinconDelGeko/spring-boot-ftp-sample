/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gekosoft.sftpupload.configuration;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

/**
 *
 * @author Miguel Angel
 */
@Configuration
public class SFTPConfiguration {
  
  
    @Value("${spring.sftp.host}")
    private String sftpHost;

    @Value("${spring.sftp.port:22}")
    private int sftpPort;

    @Value("${spring.sftp.user}")
    private String sftpUser;

    @Value("${spring.sftp.password}")
    private String sftpPasword;

    @Value("${spring.sftp.remote.directory:/holaquetal}")
    private String sftpRemoteDirectory;

    
    ExpressionParser expressionParser = new SpelExpressionParser();
    
    @Bean
    public SessionFactory<LsEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(sftpHost);
        factory.setPort(sftpPort);
        factory.setUser(sftpUser);
        factory.setPassword(sftpPasword);
        
        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<LsEntry>(factory);
    }
    

    @Bean
    @ServiceActivator(inputChannel = "sftp-sample")
    public MessageHandler handler() {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
        handler.setAutoCreateDirectory(true);
        handler.setRemoteDirectoryExpression(expressionParser.parseExpression("headers['path']"));
        handler.setFileNameGenerator(new FileNameGenerator() {
            @Override
            public String generateFileName(Message<?> message) {
               
              return  (String) message.getHeaders().get("filename");

            }

        });
        return handler;
    }
    
    @MessagingGateway
    public interface UploadGateway {

        @Gateway(requestChannel = "sftp-sample")
        void upload(@Payload byte[] file, @Header("filename") String filename, @Header("path") String path);

    }

}
