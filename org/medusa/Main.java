package org.medusa;

import com.anti_captcha.Api.NoCaptchaProxyless;
import com.anti_captcha.Helper.DebugHelper;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main {

	public static String emailDomain = "";
	public static String emailPrefix = "";
	public static String passwd = "";
	
	public static double version = 0.1;
	public static String v = "Alpha";
	
	public static int currentProgressive = 0;
	public static int currentNumber = 0;
	public static int accountsWanted = 1;
	public static boolean started = false;
	public static String antiCaptchaKey = "";
	public static int accountsCreated = 0;
	public static int completeNumber = 0;
	
	static String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    static File logFile = new File(timeLog + randomAlphaNumeric(2) + ".txt");
	
    public static void main(String[] args) throws InterruptedException, MalformedURLException, JSONException {
    	System.out.println("Welcome to Medusa's Account Creator (v" + version + "-" + v + ")");
    	System.out.println("Please note that this might not work 100% of the time");
    	MainGUI gui = new MainGUI();
    	gui.setVisible(true);
    }

    public static void createAccount() throws MalformedURLException, InterruptedException {
    	System.out.println("Waiting for captcha code... This might take a while...");
        DebugHelper.setVerboseMode(false);
        NoCaptchaProxyless api = new NoCaptchaProxyless();
        api.setClientKey(antiCaptchaKey);
        api.setWebsiteUrl(new URL("https://secure.runescape.com/m=account-creation/create_account"));
        api.setWebsiteKey("6LccFA0TAAAAAHEwUJx_c1TfTBWMTAOIphwTtd1b");
        
        if (!api.createTask()) {
        	System.out.println(api.getErrorMessage());
        } else if (!api.waitForResult()) {
        	System.out.println("-----------------------");
            System.out.println("Failed to solve captcha");
	        completeNumber++;
        } else {
        	currentProgressive++;
            createPost(api.getTaskSolution().getGRecaptchaResponse());
        }
    }
    
    public static void createPost(String string) {
    	try {
    	HttpClient httpclient = HttpClients.createDefault();
    	HttpPost httppost = new HttpPost("https://secure.runescape.com/m=account-creation/create_account");

    	String email = emailPrefix + "+" + currentProgressive + "@" + emailDomain;
    	String password = passwd;
    	String username = randomAlphaNumeric(12);
    	
    	// Request parameters and other properties.
    	List<NameValuePair> params = new ArrayList<NameValuePair>(2);
    	params.add(new BasicNameValuePair("email1", email));
    	params.add(new BasicNameValuePair("onlyOneEmail", "1"));
    	params.add(new BasicNameValuePair("password1", password));
    	params.add(new BasicNameValuePair("onlyOnePassword", "1"));
    	params.add(new BasicNameValuePair("displayname", username));
    	params.add(new BasicNameValuePair("day", "1"));
    	params.add(new BasicNameValuePair("month", "2"));
    	params.add(new BasicNameValuePair("year", "1999"));
    	params.add(new BasicNameValuePair("g-recaptcha-response", string));
    	params.add(new BasicNameValuePair("submit", "Play Now"));
    	httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

    	//Set headers
    	httppost.setHeader("Host", "secure.runescape.com");
    	httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT x.y; rv:10.0) Gecko/20100101 Firefox/10.0");
    	httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    	httppost.setHeader("Accept-Language", "en-US,en);q=0.5");
    	httppost.setHeader("Accept-Encoding", "gzip, deflate, br");
    	httppost.setHeader("Referer", "http://oldschool.runescape.com/");
    	
    	//Execute and get the response.
    	HttpResponse response = httpclient.execute(httppost);
    	HttpEntity entity = response.getEntity();

    	if (entity != null) {
    	    InputStream instream = entity.getContent();
    	    String getResponseString = readStream(instream);

	        completeNumber++;
    	    try {
            	System.out.println("-----------------------");
            	System.out.println(email + ":" + password + ":" + username);
    	        if (getResponseString.contains("Account Created") || getResponseString.length() < 2){
    	        currentNumber++;
    	        System.out.println(currentNumber + "/" + accountsWanted + " accounts made.");
    	        writeFile(email + ":" + password + ":" + username);
    	        
    	        if (completeNumber >= accountsWanted) {
                	System.out.println("-----------------------");
    	        	System.out.println("Task done");
    	        	NotificationGUI gui = new NotificationGUI("complete");
    	        	gui.setAlwaysOnTop(true);
    	        	gui.setVisible(true);
    	        	MainGUI.running = false;
    	        }
    	        } else {
    	        System.out.println("Creation failed...");
    	        }
    	    } finally {
    	        instream.close();
    	    }
    	}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    
    static String readStream(InputStream stream) throws IOException {
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = stream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }
    }

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		public static String randomAlphaNumeric(int count) {
			StringBuilder builder = new StringBuilder();
			while (count-- != 0) {
				int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
				builder.append(ALPHA_NUMERIC_STRING.charAt(character));
			}
			return builder.toString();
	}

	public static void writeFile(String account) {
		BufferedWriter writer = null;
        if (logFile.exists()) {
        	try(FileWriter fw = new FileWriter(logFile, true);
            	    BufferedWriter bw = new BufferedWriter(fw);
            	    PrintWriter out = new PrintWriter(bw))
            	{
            	    out.println(account);
            	} catch (IOException e) {
            	}
        } else {
        try {

            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(account + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
        }
	}
    
}
