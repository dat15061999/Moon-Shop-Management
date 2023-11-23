package com.cg.api;



import com.cg.model.Image;

import com.cg.service.file.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileRestController {
    private final UploadFileService uploadFileService;

//    @PostMapping
//    public Image upload(@RequestParam("avatar") MultipartFile avatar) throws IOException {
//        return uploadFileService.saveAvatar(avatar);
//    }

    @PostMapping("/images")
    public Image uploadImage(@RequestParam("images") MultipartFile avatar) throws IOException {
        return uploadFileService.saveAvatar(avatar);
    }
    @PostMapping("/posters")
    public Image uploadPost(@RequestParam("poster") MultipartFile avatar) throws IOException {
        return uploadFileService.saveAvatar(avatar);
    }
}