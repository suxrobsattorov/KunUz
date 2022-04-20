package ok.suxrob.service;

import ok.suxrob.dto.AttachDTO;
import ok.suxrob.entity.AttachEntity;
import ok.suxrob.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {

    @Autowired
    private AttachRepository attachRepository;

    @Value("${attach.upload.folder}")
    private String uploadFolder;
    @Value("${attach.open.url}")
    private String openUrl;

    public AttachDTO saveFile(MultipartFile file) {
        String filePath = getYmDString();
        String key = UUID.randomUUID().toString();
        String ectension = getExtension(file.getOriginalFilename());

        File folder = new File(uploadFolder + "/" + filePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFolder + "/" + filePath + "/" + key + "." + ectension);
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setKey(key);
            entity.setOriginName(file.getOriginalFilename());
            entity.setExtension(ectension);
            entity.setSize(file.getSize());
            entity.setFilePath(filePath);
            attachRepository.save(entity);
            AttachDTO dto = toDTO(entity);
            dto.setUrl(openUrl + "/" + key);
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setOriginName(entity.getOriginName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setExtension(entity.getExtension());
        dto.setKey(entity.getKey());
        dto.setFilePath(entity.getFilePath());
        dto.setSize(entity.getSize());
        return dto;
    }

    public byte[] loadAttach(String key) throws IOException {
        byte[] imageByte;
        Optional<AttachEntity> optional = attachRepository.findByKey(key);
        if (!optional.isPresent()) {
            return new byte[0];
        }

        BufferedImage originalImage = null;
        String filePath = optional.get().getFilePath() + "/" + key + "." + optional.get().getExtension();
        try {
            originalImage = ImageIO.read(new File(uploadFolder + "/" + filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String extension = getExtension(key);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, extension, baos);

        baos.flush();
        imageByte = baos.toByteArray();
        baos.close();
        return imageByte;
    }

    public static String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE) + 1;

        return year + "/" + month + "/" + day;
    }

    public String getExtension(String key) {
        int lastIndex = key.lastIndexOf(".");
        return key.substring(lastIndex + 1);
    }

    public void delete(String key) {
        Optional<AttachEntity> optional = attachRepository.findByKey(key);
        if (!optional.isPresent()) {
            return;
        }
        String filePath = optional.get().getFilePath() + "/" + key + "." + optional.get().getExtension();

        File file = new File(uploadFolder + "/" + filePath);
        if (file.exists()) {
            file.delete();
        }
        attachRepository.delete(optional.get());
    }


}
