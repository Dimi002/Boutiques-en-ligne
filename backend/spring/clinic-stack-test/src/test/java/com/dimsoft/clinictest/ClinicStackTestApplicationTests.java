package com.dimsoft.clinictest;

import com.dimsoft.clinicStackProd.beans.User;
import com.dimsoft.clinicStackProd.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dimsoft.clinicStackProd.ClinicStackProdApplication;
import com.dimsoft.clinicStackProd.service.TestService;

import java.util.List;

@SpringBootTest(classes = ClinicStackProdApplication.class)
@ActiveProfiles("test")
class ClinicStackTestApplicationTests {

	@Autowired
	private TestService testService;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void testServiceGetstringDoesNotReturnNull() {
		Assert.assertNotNull(testService.getString());
	}

	@Test
	void testServiceGetstringReturnsHello() {
		Assert.assertEquals("Hello", testService.getString());
	}

	void getUserWithEmptyArray() {
		List<User> users = userService.getAllUser();
		Assert.assertTrue(users.isEmpty());
	}
}
