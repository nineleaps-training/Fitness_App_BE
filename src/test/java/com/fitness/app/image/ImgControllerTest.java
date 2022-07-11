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

    ObjectMapper objectMapper=new ObjectMapper();

    @Mock
    ImgRepo imgRepo;

    @Mock
    ImgService imgService;

    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(imgController).build();
        inputStream = imgController.getClass().getClassLoader().getResourceAsStream("/home/nineleaps/Documents/qrcode.png");
    }

    @DisplayName("Downloading Image Test")
    @Test
    void testGetImage() throws Exception {
        String hello="asd";
        Doc docFile=new Doc("id", "fileName", "image/png", hello.getBytes());
        Mockito.when(imgService.getImage("id")).thenReturn(docFile);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/downloadFile/id").contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals("image/png", result.getResponse().getContentType());

    }

    @DisplayName("Testing if image not present in database")
    @Test
    void testGetImageNull() throws Exception {
        Doc docFile=null;
        Mockito.when(imgService.getImage("id")).thenReturn(docFile);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/downloadFile/id").contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(null, result.getResponse().getContentType());

    }

    @DisplayName("Uploading Image Test")
    @Test
    void testSaveFile() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "qrcode.png", "multipart/form-data", inputStream);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadFile/id").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();
        Assertions.assertEquals(201, result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getContentAsString());
    }
}
