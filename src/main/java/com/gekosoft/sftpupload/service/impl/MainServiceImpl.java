/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gekosoft.sftpupload.service.impl;

import com.gekosoft.sftpupload.configuration.SFTPConfiguration.UploadGateway;
import com.gekosoft.sftpupload.service.MainService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Miguel Angel
 */
@Controller
public class MainServiceImpl implements  MainService{

  @Autowired
  UploadGateway uploadGateway;
  
  @Autowired
  SftpMessageHandler handler;
  
  @Override
  public void uploadFile(MultipartFile file, String dir) {
    try {
      uploadGateway.upload(file.getBytes(), file.getOriginalFilename(), dir);
    } catch (IOException ex) {
      Logger.getLogger(MainServiceImpl.class.getName()).log(Level.SEVERE,
          null,
          ex);
    }
  }
  
  
  
}
