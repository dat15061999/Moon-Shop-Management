package com.cg.service.file;

import com.cg.model.Image;
import com.cg.repository.ImageRepository;
import com.cg.until.UploadUtils;
import com.cloudinary.Cloudinary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class UploadFileService {

    private final Cloudinary cloudinary;

    private final ImageRepository fileRepository;

    private final UploadUtils uploadUtils;

    public Image saveAvatar(MultipartFile avatar) throws IOException {
        var file = new Image();
        fileRepository.save(file);

        var uploadResult = cloudinary.uploader().upload(avatar.getBytes(), uploadUtils.buildImageUploadParams(file));

        String fileUrl = (String) uploadResult.get("secure_url");
        String fileFormat = (String) uploadResult.get("format");

        file.setFileName(file.getId() + "." + fileFormat);
        file.setUrl(fileUrl);
        file.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
        file.setCloudId(file.getFileFolder() + "/" + file.getId());
        fileRepository.save(file);
        return file;
    }

}