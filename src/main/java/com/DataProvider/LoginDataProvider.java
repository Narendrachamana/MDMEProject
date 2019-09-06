package com.DataProvider;

import org.testng.annotations.DataProvider;

import com.Utilities.ReadProperty;
import com.Utilities.TestData;

public class LoginDataProvider {
	

		@DataProvider(name = "LoginTestData")
		public static Object[][] getTestData() {

			ReadProperty config = new ReadProperty("config");
			TestData re = new TestData();
			return re.getTestData("Sheet1");

		}

}
