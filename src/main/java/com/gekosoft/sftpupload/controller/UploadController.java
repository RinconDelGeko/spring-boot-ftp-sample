package com.gekosoft.sftpupload.controller;

import com.gekosoft.sftpupload.service.MainService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CamperRdG
 */
@Controller
@RequestMapping("/")
public class UploadController {
  
  @Autowired
  MainService mainService;

  @RequestMapping(method = RequestMethod.GET)
  public String index() {
      return "index.html";
  }

  @RequestMapping(method = RequestMethod.POST, path = "/upload")
  public String postFile(@RequestParam("testFile") MultipartFile testFile, @RequestParam("dir") String dir) throws IOException {
    
    mainService.uploadFile(testFile, dir);
    
     
    return "index.html";
  }
  
  
}
