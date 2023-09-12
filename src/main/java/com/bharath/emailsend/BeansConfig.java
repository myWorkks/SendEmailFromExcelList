package com.bharath.emailsend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeansConfig {
	@Bean
	@Scope("singleton")
	@Lazy
	public WebDriver webDriver() {
		System.out.println("web driver bean called");
		System.setProperty("webdriver.chrome.driver",
				"G:\\MyWorks\\Extracts\\ChromeDriver\\chromedriver-win32\\chromedriver.exe");
		return new ChromeDriver();
	}
}
