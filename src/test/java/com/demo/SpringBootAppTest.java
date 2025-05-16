package com.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringBootAppTest {

    private MockedStatic<SpringApplication> springApplicationMock;

    @BeforeEach
    void setUp() {
        // Mock SpringApplication static method
        springApplicationMock = mockStatic(SpringApplication.class);
    }

    @AfterEach
    void tearDown() {
        springApplicationMock.close();
    }

    @Test
    void main_WhenSuccessfulStartup_CreatesApplicationContext() {
        // Arrange
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        springApplicationMock.when(() -> SpringApplication.run(eq(SpringBootApp.class), any(String[].class)))
                .thenReturn(context);

        // Act
        SpringBootApp.main(new String[]{});

        // Assert
        springApplicationMock.verify(() -> SpringApplication.run(eq(SpringBootApp.class), any(String[].class)),
                times(1));
        verifyNoMoreInteractions(context);
    }

    @Test
    void main_WhenStartupFails_ThrowsException() {
        // Arrange
        RuntimeException exception = new RuntimeException("Startup failure");
        springApplicationMock.when(() -> SpringApplication.run(eq(SpringBootApp.class), any(String[].class)))
                .thenThrow(exception);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> SpringBootApp.main(new String[]{}),
                "Expected RuntimeException to be thrown");
        assertSame(exception, thrown, "Thrown exception should be the same as the mocked exception");

        springApplicationMock.verify(() -> SpringApplication.run(eq(SpringBootApp.class), any(String[].class)),
                times(1));
    }
}
