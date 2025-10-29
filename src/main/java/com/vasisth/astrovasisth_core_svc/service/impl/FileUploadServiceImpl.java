package com.vasisth.astrovasisth_core_svc.service.impl;


import com.vasisth.astrovasisth_core_svc.config.S3Component;
import com.vasisth.astrovasisth_core_svc.constants.FileUploadType;
import com.vasisth.astrovasisth_core_svc.service.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private S3Component s3Component;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public void uploadFile(MultipartFile file, FileUploadType fileUploadType, String id) throws Exception {
        byte[] bytes = file.getBytes();
        String key ="";
        switch(fileUploadType){
                case PROFILE_PICTURE:
                key =  id +  "/profile-pictures/profilePicture";
                break;
            case DOCUMENT:
                key =  id + "/document/"  + file.getOriginalFilename();
                break;
            case OTHER:
                key =  id + "/others/"  + file.getOriginalFilename();
                break;
            default:
                throw new IllegalArgumentException("Unsupported file upload type: " + fileUploadType);
        }
        s3Component.uploadFile(
                bucketName,
                key,
                new java.io.ByteArrayInputStream(bytes),
                bytes.length,
                file.getContentType()
        );
    }
}