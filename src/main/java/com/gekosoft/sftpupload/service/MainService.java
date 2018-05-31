/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gekosoft.sftpupload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Miguel Angel
 */
public interface MainService {
  
  void uploadFile(MultipartFile file, String dir);
  
  
}
