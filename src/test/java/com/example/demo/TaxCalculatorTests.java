package com.example.demo;

import com.example.demo.model.Tax;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest
class TaxCalculatorTests {

	@Autowired
	private TaxController controller;

	//TestCase:1 The response of controller is not empty
	@Test
	void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
	}

	//TestCase:2 200 OK Response with different values
	public static Collection<Object[]> data_OK() {
		return Arrays.asList(new Object[][] {
				{"-10", 0, 0},
				{"0", 0, 0},
				{"100000", 3, 17991.78F}
		});
	}

	@ParameterizedTest
	@MethodSource("data_OK")
	public void TaxCalculator_HTTPResponse_200(String amount,int brackets, float expectedTotalTax)
			throws Exception {

			Tax taxResponse = controller.TaxCalculator(amount);
			assertEquals(HttpStatus.OK.value(), taxResponse.statusCode);
			assertEquals(brackets,taxResponse.bracket.size());
			assertEquals(expectedTotalTax, taxResponse.totalTax);

	}

	//TestCase:3 Bad_Request Response with alphabetical value
	public static Collection<Object[]> data_bad() {
		return Arrays.asList(new Object[][] {
				{"abc"}
		});
	}

	@ParameterizedTest
	@MethodSource("data_bad")
	public void TaxCalculator_HTTPResponse_BADREQUEST(String amount)
			throws Exception {

		Tax taxResponse = controller.TaxCalculator(amount);
		assertEquals(HttpStatus.BAD_REQUEST.value(), taxResponse.statusCode);

	}
}
