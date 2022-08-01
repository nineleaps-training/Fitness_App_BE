package com.fitness.app.image;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@DisplayName("Image Service Test")
@ExtendWith(MockitoExtension.class)
class ImgServiceTest {

    @Mock
    ImgRepo imgRepo;

    @InjectMocks
    ImgService imgService;

    File file;
    FileInputStream fileInputStream;
    MultipartFile multipartFile;

    String path = "/home/nineleaps/Documents/";
    String path2 = "/home/nineleaps/Downloads/";
    String name = "qrCode.png";

    @BeforeEach
    public void initcase() {
        imgService = new ImgService(imgRepo);
    }

    @DisplayName("Downloading Image Test")
    @Test
    void testGetImage() throws IOException {
        File file = new File("/home/nineleaps/Documents/qrCode.png");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "image/png",
                IOUtils.toByteArray(input));
        String docName = multipartFile.getOriginalFilename();
        String id = "GM6";
        Doc docFile = new Doc(id, docName, multipartFile.getContentType(), multipartFile.getBytes());
        Optional<Doc> optional = Optional
                .of(new Doc(id, docName, multipartFile.getContentType(), multipartFile.getBytes()));
        when(imgRepo.findById(id)).thenReturn(optional);
        Doc doc = imgService.getImage(id);
        Assertions.assertEquals(docFile, doc);
    }

    @DisplayName("Downloading Image Test when Optional not present")
    @Test
    void testGetImageNoOptional() throws IOException {
        String id = "GM6";
        Optional<Doc> optional = Optional.empty();
        when(imgRepo.findById(id)).thenReturn(optional);
        try {
            imgService.getImage(id);
        } catch (Exception e) {
            Assertions.assertEquals("File Not Found", e.getMessage());
        }
    }

    @DisplayName("Uploading PNG Test")
    @Test
    void testSaveImage() throws IOException {

        file = new File(path + name);
        fileInputStream = new FileInputStream(file);
        multipartFile = new MockMultipartFile(name,
                file.getName(), "image/png", IOUtils.toByteArray(fileInputStream));
        String docName = multipartFile.getOriginalFilename();
        String id = "GM6";
        Doc docFile = new Doc(id, docName, multipartFile.getContentType(), multipartFile.getBytes());
        Doc doc = imgService.saveImage(multipartFile, id);
        Assertions.assertEquals(docFile, doc);
    }

    @DisplayName("Uploading JPEG Test")
    @Test
    void testImageJpeg() throws IOException {

        file = new File(path2 + "logo.jpg");
        fileInputStream = new FileInputStream(file);
        multipartFile = new MockMultipartFile("logo.jpg",
                file.getName(), "image/jpeg", IOUtils.toByteArray(fileInputStream));
        String docName = multipartFile.getOriginalFilename();
        String id = "GM6";
        Doc docFile = new Doc(id, docName, multipartFile.getContentType(), multipartFile.getBytes());
        Doc doc = imgService.saveImage(multipartFile, id);
        Assertions.assertEquals(docFile, doc);
    }

    @DisplayName("Uploading JPG Test")
    @Test
    void testImgJpg() throws IOException {

        file = new File(path2 + "logo.jpg");
        fileInputStream = new FileInputStream(file);
        multipartFile = new MockMultipartFile("logo.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(fileInputStream));
        String docName = multipartFile.getOriginalFilename();
        String id = "GM6";
        Doc docFile = new Doc(id, docName, multipartFile.getContentType(), multipartFile.getBytes());
        Doc doc = imgService.saveImage(multipartFile, id);
        Assertions.assertEquals(docFile, doc);
    }

}
