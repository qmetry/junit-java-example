# junit-java-example
This is sample junit + selenium project in Java. It shows how to upload test result file on JIRA instance using [QMetry for JIRA - Test Management](https://marketplace.atlassian.com/plugins/com.infostretch.QmetryTestManager/cloud/overview).  

### Install chromedriver  

You need to install [chromedriver](https://sites.google.com/a/chromium.org/chromedriver/) to run test on Google Chrome. Then set chromedriver path in `src/main/java/com/qmetry/SampleAutomationCode.java`.

### Run test

If you like to upload test result after completion of test, then provide these details in properties file. `qtm4j.properties`. 

1. API Key - You can get this value by logging into your JIRA instance. Then click on QMetry Menu -> Automation API page. 
2. Base URL - QMetry Automation API URL. This information is also available in Automation API page. 
3. Username/password - This information is required for server/On premise version. (For cloud, you can skip this)
4. JIRA Hosting type - Put `C` for Cloud and `S` for on premise/ Server. 
5. Test Asset Hierarchy - TestCase-TestStep or TestScenario-TestCase.
6. Test Run Key - If you would like to upload test result in any of existing test run then provide test run key.
7. And Other optional params. Refer [QMetry docs](https://qmetrytestdocs.atlassian.net/wiki/) for more details.

After providing these details, you are ready to start test.

```
mvn test
```

It will generate `surefile-reports` test result file. 

Addionally, right after test completion, test result file will be uploaded on your JIRA instance if you have provided correct details in properties file. 
