package com.gekosoft.sftpupload.controller;

import com.gekosoft.sftpupload.configuration.SFTPConfiguration;
import com.gekosoft.sftpupload.sftpserver.SftpServerEmbedded;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author CamperRdG
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SftpTest {
  
  
  @Value("${spring.sftp.host}")
  private String sftpHost;

  @Value("${spring.sftp.port:22}")
  private int sftpPort;

  @Value("${spring.sftp.user}")
  private String sftpUser;

  @Value("${spring.sftp.password}")
  private String sftpPasword;
  
  @Autowired
  SFTPConfiguration.UploadGateway uploadGateway;
  
  @Test
   public void testUpload() throws IOException{

     Path tempPath = Files.createTempDirectory("SFTP_TEST");

     SftpServerEmbedded sftpServerEmbedded = new SftpServerEmbedded(
     sftpPort, sftpUser, sftpPasword, sftpHost, tempPath
     );

     sftpServerEmbedded.configServer();

     sftpServerEmbedded.getServer().start();

     Path path = Paths.get("src\\main\\resources\\prueba.txt");
     byte[] data = Files.readAllBytes(path);

     String ruta = "/dir/";

     uploadGateway.upload(data, "prueba.txt", ruta);


     sftpServerEmbedded.getServer().stop();

     sftpServerEmbedded = null;

     Path pathFinal = Paths.get(tempPath.toString()+ruta+"prueba.txt");
     byte[] dataFinal = Files.readAllBytes(pathFinal);

     Assert.assertArrayEquals(data, dataFinal);

  }
  

}
