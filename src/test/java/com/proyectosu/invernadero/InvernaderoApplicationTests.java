package com.proyectosu.invernadero;

import com.proyectosu.invernadero.config.MqttTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
@Import(MqttTestConfig.class)
class InvernaderoApplicationTests {

	@Test
	void contextLoads() {
	}

}
