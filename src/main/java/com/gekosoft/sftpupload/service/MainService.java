package com.gekosoft.sftpupload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CamperRdG
 */
public interface MainService {
  
  void uploadFile(MultipartFile file, String dir);
  
  
}
