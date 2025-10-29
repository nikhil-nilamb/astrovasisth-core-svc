package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.constants.FileUploadType;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    void uploadFile(MultipartFile file, FileUploadType fileUploadType,String id) throws Exception;
}
