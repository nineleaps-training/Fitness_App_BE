package com.fitness.app.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("Image Controller Test")
@ExtendWith(MockitoExtension.class)
class ImgControllerTest {

    @InjectMocks
    ImgController imgController;

    MockMvc mockMvc;

    java.io.InputStream inputStream;

    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    ImgRepo imgRepo;

    MockMultipartFile mockMultipartFile;

    String png = "qrcode.png";
    String jpg = "qrcode.jpg";
    String jpeg = "qrcode.jpeg";

    String typePNG = "image/png";
    String typeJPG = "image/jpg";
    String typeJPEG = "image/jpeg";

    @Mock
    ImgService imgService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(imgController).build();
        inputStream = imgController.getClass().getClassLoader()
                .getResourceAsStream("/home/nineleaps/Documents/qrcode.png");
    }

    @DisplayName("Downloading Image Test")
    @Test
    void testGetImage() throws Exception {
        String hello = "asd";
        Doc docFile = new Doc("id", "fileName", "image/png", hello.getBytes());
        Mockito.when(imgService.getImage("id")).thenReturn(docFile);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/image/downloadFile/id")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals("image/png", result.getResponse().getContentType());

    }

    @DisplayName("Testing if image not present in database")
    @Test
    void testGetImageNull() throws Exception {
        Doc docFile = null;
        Mockito.when(imgService.getImage("id")).thenReturn(docFile);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/image/downloadFile/id")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(null, result.getResponse().getContentType());

    }

    @DisplayName("Uploading Image Test")
    @Test
    void testSaveFile() throws Exception {

        mockMultipartFile = new MockMultipartFile("file", png, typePNG, inputStream);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.multipart("/v1/image/uploadFile/id").file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();
        Assertions.assertEquals(201, result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getContentAsString());
    }

    @DisplayName("Uploading JPG Image Test")
    @Test
    void testSaveFileJpg() throws Exception {

        mockMultipartFile = new MockMultipartFile("file", jpg, typeJPG, inputStream);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.multipart("/v1/image/uploadFile/id").file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();
        Assertions.assertEquals(201, result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getContentAsString());
    }

    @DisplayName("Uploading JPEG Image Test")
    @Test
    void testSaveFileJpeg() throws Exception {

        mockMultipartFile = new MockMultipartFile("file", jpeg, typeJPEG, inputStream);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.multipart("/v1/image/uploadFile/id").file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();
        Assertions.assertEquals(201, result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getContentAsString());
    }

    @DisplayName("Uploading file other than jpg|jpeg|png")
    @Test
    void testSaveImageFalse() throws Exception {

        mockMultipartFile = new MockMultipartFile("file", "qrcode.pdf", "application/pdf", inputStream);
        try {
            mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/image/uploadFile/id").file(mockMultipartFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(MockMvcResultMatchers.status().is(404)).andReturn();
        } catch (Exception e) {
            Assertions.assertEquals("Allowed file types: jpg, jpeg or png", e.getMessage());
        }
    }
}
