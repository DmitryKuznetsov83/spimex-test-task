package com.spimex.test_task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestTaskApplicationTests {

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void initialBalance() throws Exception {
		mvc.perform(post("/api/topUpAccount/500"));
	}

	@Test
	public void mainTest() throws Exception {

		// баланс
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(500)))
				.andExpect(jsonPath("bonus", equalTo(0)));

		// пополнение
		mvc.perform(get("/api/transactions")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));

		// online/100
		mvc.perform(post("/api/payment/online/100"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(400)))
				.andExpect(jsonPath("bonus", equalTo(17)));

		// shop/120
		mvc.perform(post("/api/payment/shop/120"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(280)))
				.andExpect(jsonPath("bonus", equalTo(29)));

		// online/301
		mvc.perform(post("/api/payment/online/301"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(280)))
				.andExpect(jsonPath("bonus", equalTo(29)));

		// online/17
		mvc.perform(post("/api/payment/online/17"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(262)))
				.andExpect(jsonPath("bonus", equalTo(31)));

		// shop/1000
		mvc.perform(post("/api/payment/shop/1000"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(262)))
				.andExpect(jsonPath("bonus", equalTo(31)));

		// online/21
		mvc.perform(post("/api/payment/online/21"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(241)))
				.andExpect(jsonPath("bonus", equalTo(34)));

		// shop/570
		mvc.perform(post("/api/payment/shop/570"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(241)))
				.andExpect(jsonPath("bonus", equalTo(34)));

		// online/700
		mvc.perform(post("/api/payment/online/700"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(241)))
				.andExpect(jsonPath("bonus", equalTo(34)));

		// bonus/10
		mvc.perform(post("/api/payment/bonus/10"));
		mvc.perform(get("/api/money"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("sum", equalTo(241)))
				.andExpect(jsonPath("bonus", equalTo(24)));

	}

}