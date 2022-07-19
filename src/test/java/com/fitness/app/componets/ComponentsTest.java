package com.fitness.app.componets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitness.app.components.Components;

@ExtendWith(MockitoExtension.class)
class ComponentsTest {

    @InjectMocks
    Components components;

    @Test
    @DisplayName("Testing OTP Builder")
    void testOtpBuilder() {

        Assertions.assertEquals(String.class, components.otpBuilder().getClass());

    }

    @Test
    @DisplayName("Testing if the OTP is sent")
    void testSendOtpMessageException() {

        int actual = components.sendOtpMessage("message", "1234", "8469492322");
        Assertions.assertEquals(0, actual);
    }
}
