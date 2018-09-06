package com.anti_captcha;

import java.net.MalformedURLException;

import org.medusa.Main;

public class AccountCreationThread extends Thread {

	@Override
	public void run() {
		try {
			Main.createAccount();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
}
