/**
 * This is the Listener/configuration class for users of QMetry for JIRA to upload results.
 * User can insert his API parameters in this class as variable values and run the 
 * project as maven test.
 * 
 * NOTE: You can change the automation code according to your use case in SampleAutomationCode.java
 * and respective tests in SampleAutomationCodeTest.java. 
 */

package com.qmetry.qaf.automation.integration.qtm4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.http.auth.InvalidCredentialsException;
import org.json.simple.parser.ParseException;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import com.qmetry.UploadToCloud;
import com.qmetry.UploadToServer;

public class JUnitExecutionListener extends RunListener {
	
	private String OUTPUT_DIR = "target/surefire-reports";
	private String ZIP_FILE_NAME = "result.zip";
	private String API_KEY = "integration.param.qtm4j.apikey";
	private String API_TEST_RUN_NAME = "integration.param.qtm4j.testrunname";
	private String API_ENDPOINT = "integration.param.qtm4j.baseurl";
	private String API_USERNAME = "integration.param.qtm4j.username";
	private String API_PASSWORD = "integration.param.qtm4j.password";
	private String API_LABELS = "integration.param.qtm4j.labels";
	private String API_COMPONENTS = "integration.param.qtm4j.components";
	private String API_SPRINT = "integration.param.qtm4j.sprint";
	private String API_VERSION = "integration.param.qtm4j.versions";
	private String API_PLATFORM = "integration.param.qtm4j.platform";
	private String API_COMMENT = "integration.param.qtm4j.comment";
	private String FILE_URL=new File(ZIP_FILE_NAME).getAbsolutePath();
	private static final String INTEGRATION_EANBLED = "integration.param.qtm4j.enabled";
	private static final String API_TYPE = "integration.param.qtm4j.installation.type";
	public static final String KEEP_ZIP_DEBUG = "integration.param.qtm4j.debug";
	private static final String API_TEST_ASSET_HIERARCHY = "integration.param.qtm4j.testassethierarchy";
	private static final String API_TESTRUNKEY = "integration.param.qtm4j.testrunkey";

	Properties p;
	
	@Override
	public void testRunStarted(Description description) {
		try {
			super.testRunStarted(description);

			System.out.println("Test Run Started");
			InputStream is = new FileInputStream("qtm4j.properties");
			p = new Properties();

			p.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testRunFinished(Result result) throws MalformedURLException, UnsupportedEncodingException, ProtocolException, IOException, ParseException {
		System.out.println("Test Run Finished");
		// If integration not enabled then return
		if (!isEnabled()) {
			System.out.println("QTM4J integration is disabled !!!");
			return;
		}

		try {
			System.out.println("Executing zip command");
			ZipUtils.zipDirectory(new File(OUTPUT_DIR).getAbsolutePath(), ZIP_FILE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (p.getProperty(API_TYPE).toUpperCase().contains("S")) {
			//code for server API
			try {
				UploadToServer.uploadToTheServer(p.getProperty(API_KEY)
						,p.getProperty(API_ENDPOINT)
						,p.getProperty(API_PASSWORD)
						,p.getProperty(API_TEST_RUN_NAME)
						,p.getProperty(API_LABELS),
						p.getProperty(API_SPRINT)
						,p.getProperty(API_VERSION)
						,p.getProperty(API_COMPONENTS)
						,p.getProperty(API_USERNAME)
						,FILE_URL
						,p.getProperty(API_PLATFORM)
						,p.getProperty(API_COMMENT)
						,p.getProperty(API_TEST_ASSET_HIERARCHY)
						,p.getProperty(API_TESTRUNKEY));
			} catch (InvalidCredentialsException e) {
				e.printStackTrace();
			}
		} else {
			//code for Cloud API
			System.out.println("Cloud code here.");
			try {
				UploadToCloud.uploadToTheCloud(p.getProperty(API_KEY)
						,p.getProperty(API_ENDPOINT) 
						, FILE_URL
						,p.getProperty(API_TEST_RUN_NAME)
						,p.getProperty(API_LABELS)
						,p.getProperty(API_SPRINT)
						,p.getProperty(API_VERSION)
						,p.getProperty(API_COMPONENTS)
						,p.getProperty(API_PLATFORM)
						,p.getProperty(API_COMMENT)
						,p.getProperty(API_TEST_ASSET_HIERARCHY)
						,p.getProperty(API_TESTRUNKEY));
			} catch (Exception e) {
				e.printStackTrace();
				}
		    }
		
		// logic to upload file
		System.out.println("Exiting QTM4J Result Uploader");
		if (p.getProperty(KEEP_ZIP_DEBUG).equalsIgnoreCase("true")) {
			// keep zip file
			File f = new File(ZIP_FILE_NAME + ".tmp"
					+ new SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(new Date()));
			f.mkdirs();
			try {
				FileUtils.moveFile(new File(ZIP_FILE_NAME), f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
		    	//e.printStackTrace();
			}
		} else {
			// delete zip file
			new File(ZIP_FILE_NAME).delete();
		}
	}

	/**
	 * @return required properties is set or not to enable integration with
	 *         qtm4j
	 */
	public boolean isEnabled() {
		// get configuration values from pom
		return p.getProperty(INTEGRATION_EANBLED).equalsIgnoreCase("true");
	}
}