package com.fitness.app.componets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComponentsTest {

    @InjectMocks
    Components components;

    @Test
    void testOtpBuilder() {

        Assertions.assertEquals(String.class, components.otpBuilder().getClass());

    }

    @Test
    void testSendOtpMessage() {

        int actual = components.sendOtpMessage("message", "1234", "8469492322");
        Assertions.assertEquals(0, actual);
    }
}
