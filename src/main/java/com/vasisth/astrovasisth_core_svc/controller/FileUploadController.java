package com.vasisth.astrovasisth_core_svc.controller;

import com.vasisth.astrovasisth_core_svc.constants.FileUploadType;
import com.vasisth.astrovasisth_core_svc.service.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                            @PathVariable String id,
                                             @RequestParam("type") String type) {
        try {
            fileUploadService.uploadFile(file, FileUploadType.valueOf(type),id);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}