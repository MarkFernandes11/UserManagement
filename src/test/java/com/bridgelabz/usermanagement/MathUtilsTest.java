package com.bridgelabz.usermanagement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MathUtilsTest {
	
	MathUtils mathUtils;
	
	@BeforeEach
	void init() {
		mathUtils = new MathUtils();
	}
	
	@BeforeAll
	void BeforeAllInit() {
		System.out.println("before all called");
	}
	
	@AfterEach
	void cleanup() {
		System.out.println("cleaning up");
	}
	
//	@Mock
//	private MathUtils mathUtils;
	
	@Test
	@DisplayName("Test add method")
	void testAdd() {

		int expected = -20;
		int actual = mathUtils.sum(-40, 20);
		
		assertEquals(expected, actual,"The addition of 2 numbers are");
	}
	
	
	@Test
	void testDivide() {
		assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0), "Divide by 0 should throw ");
	}
	
	@Test
	void testCircleArea() {
		assertEquals(314.1592653589793, mathUtils.circleArea(10), "Should return circle area ");
	}

	@Test
	@Disabled
	@DisplayName("TDD method, should not run")
	void testDisable() {
		fail("This test should be disabled");
	}
}
