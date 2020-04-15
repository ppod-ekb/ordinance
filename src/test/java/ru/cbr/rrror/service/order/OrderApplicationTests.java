package ru.cbr.rrror.service.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cbr.rrror.service.order.repository.OrdinanceRepository;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderApplicationTests {

	@Autowired
	private OrdinanceRepository repository;

	@Test
	void contextLoads() {
		log.debug(">>> context load test");

		repository.findById(1L).ifPresent(o -> log.debug(o.toString()));
	}

}
