package com.javaproject;

import com.javaproject.database.DatabaseAccess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class DatabaseServiceProjectApplicationTests {

    @Autowired
    private DatabaseAccess databaseAccess;

    @Test
    void contextLoads() {
        assertThat(databaseAccess).isNotNull();
    }
}
