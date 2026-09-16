# OpenCart Selenium Hybrid Automation Framework

A production-style Selenium Hybrid Test Automation Framework built using **Java, Selenium WebDriver, TestNG, Maven, Page Object Model (POM), Apache POI, Extent Reports, Log4j2, Selenium Grid, and GitHub Actions**.

The framework automates key functional workflows of the OpenCart demo e-commerce application and demonstrates scalable, reusable, maintainable, data-driven, and CI-ready UI automation practices.

---

## Application Under Test

**OpenCart Demo Application**

https://tutorialsninja.com/demo/

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Selenium WebDriver | Web UI automation |
| TestNG | Test execution and assertions |
| Maven | Build and dependency management |
| Page Object Model | Test design pattern |
| Apache POI | Excel test-data handling |
| Extent Reports | HTML test reporting |
| Log4j2 | Execution logging |
| Git & GitHub | Version control |
| GitHub Actions | CI/CD automation |
| Selenium Grid | Remote and cross-browser execution |

---

## Framework Features

- Page Object Model (POM)
- Hybrid automation framework design
- Data-driven testing using Excel
- TestNG DataProvider
- Reusable explicit wait methods
- Reusable Selenium page actions
- Cross-browser support
- Local and Selenium Grid execution
- Maven-based test execution
- Extent HTML reporting
- Automatic screenshots for failed tests
- Log4j2 execution logging
- Positive and negative test scenarios
- Environment-based credential management
- Maven system-property credential support
- TestNG XML suite management
- GitHub Actions CI/CD integration
- Automated Selenium execution in CI
- Headless Chrome execution in GitHub Actions
- Secure credential management using GitHub Actions Secrets

---

## Automated Test Scenarios

The framework currently covers the following functional scenarios:

| Test Case | Scenario |
|---|---|
| TC001 | New user account registration |
| TC002 | Valid user login |
| TC003 | Data-driven login using Excel |
| TC004 | Product search |
| TC005 | Add product to shopping cart |
| TC006 | Shopping cart product validation |
| TC007 | Invalid login validation |
| TC008 | User logout and logout-page validation |

The data-driven login test executes multiple datasets, so the total number of TestNG executions may be greater than the number of test classes.

### Latest Verified CI Execution

```text
Tests run: 9
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

The complete Selenium suite has been successfully executed through GitHub Actions.

---

## Project Structure

```text
OpenCartHybridFramework/
│
├── .github/
│   └── workflows/
│       └── selenium-test.yml
│
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── pageObjects/
│   │   │   │   ├── BasePage.java
│   │   │   │   ├── HomePage.java
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── LogoutPage.java
│   │   │   │   ├── MyAccountPage.java
│   │   │   │   ├── RegistrationPage.java
│   │   │   │   ├── SearchPage.java
│   │   │   │   └── ShoppingCartPage.java
│   │   │   │
│   │   │   ├── testBase/
│   │   │   │   └── BaseClass.java
│   │   │   │
│   │   │   ├── testCases/
│   │   │   │   ├── TC001_RegistrationPage.java
│   │   │   │   ├── TC002_LoginPage.java
│   │   │   │   ├── TC003_DataProviderLogin.java
│   │   │   │   ├── TC004_Search.java
│   │   │   │   ├── TC005_AddToCart.java
│   │   │   │   ├── TC006_CartValidation.java
│   │   │   │   ├── TC007_InvalidLogin.java
│   │   │   │   └── TC008_Logout.java
│   │   │   │
│   │   │   └── utility/
│   │   │       ├── DataProvider1.java
│   │   │       ├── ExcelUtility.java
│   │   │       └── ExtentReportUtility.java
│   │   │
│   │   └── resources/
│   │       ├── config.properties
│   │       └── log4j2.xml
│   │
│   └── testData/
│       └── LoginData.xlsx
│
├── master.xml
├── crossBrowser.xml
├── grouping.xml
├── dockerRun.xml
├── pom.xml
├── run.bat
├── .gitignore
└── README.md
```

---

## Framework Architecture

The framework follows a layered automation design to separate test logic, page interactions, configuration, test data, and utilities.

```text
                    TestNG Test Cases
                           │
                           ▼
                      Page Objects
                           │
                           ▼
                        BasePage
                           │
                           ▼
                  Selenium WebDriver
                           │
                           ▼
                 OpenCart Application

            ┌──────────────┴──────────────┐
            │                             │
      Test Data / Excel             Configuration
       Apache POI                  config.properties
            │
            ▼
       DataProvider

Utilities:
- Extent Reports
- Screenshots
- Log4j2 Logging
- Random Test Data
```

This separation helps improve framework maintainability and reduces duplicate Selenium code.

---

## Page Object Model

The framework implements the **Page Object Model (POM)** design pattern.

Each major application page has a corresponding Page Object class.

For example:

```text
Test Case
   │
   ▼
Page Object
   │
   ▼
Reusable BasePage Methods
   │
   ▼
Selenium WebDriver
   │
   ▼
Web Application
```

Web elements and page-specific operations are encapsulated inside their respective Page Object classes.

Test classes primarily contain:

- Test workflow
- Test data
- Assertions
- Validation logic

This keeps test cases readable and easier to maintain.

---

## Data-Driven Testing

The framework supports data-driven testing using **Apache POI and TestNG DataProvider**.

Login test data is maintained in:

```text
src/testData/LoginData.xlsx
```

Example Excel structure:

```text
Email | Password | ExpectedResult
```

Apache POI reads the Excel workbook and TestNG `DataProvider` supplies each dataset to the test method.

This allows the same Selenium test to execute against multiple combinations of:

- Valid credentials
- Invalid email addresses
- Invalid passwords
- Expected login results

The test data remains separated from the test logic.

---

## Configuration Management

Application and execution configuration is maintained in:

```text
src/test/resources/config.properties
```

Example:

```properties
executionEnv=local
appUrl=https://tutorialsninja.com/demo/
gridUrl=http://localhost:4444/wd/hub
```

The `executionEnv` property determines whether tests execute using a local browser or Selenium Grid.

Sensitive credentials are intentionally not stored in the configuration file or source code.

---

## Secure Credential Management

Valid OpenCart credentials are externalized instead of being hardcoded into the framework.

### Environment Variables

The framework supports:

```text
OPENCART_USERNAME
OPENCART_PASSWORD
```

### Maven System Properties

Credentials can also be supplied during Maven execution:

```bash
mvn clean test -Dopencart.username="YOUR_EMAIL" -Dopencart.password="YOUR_PASSWORD"
```

### GitHub Actions Secrets

For CI execution, credentials are stored securely as GitHub repository secrets:

```text
OPENCART_USERNAME
OPENCART_PASSWORD
```

The GitHub Actions workflow passes these secrets to the test execution environment.

> Never commit real usernames, passwords, tokens, or other sensitive credentials to the repository.

---

## Running Tests Locally

### Prerequisites

Ensure the following are installed:

- Java JDK
- Maven
- Git
- Chrome, Edge, or Firefox
- IDE such as Eclipse or IntelliJ IDEA

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

---

### Run the Complete Maven Suite

```bash
mvn clean test -Dopencart.username="YOUR_EMAIL" -Dopencart.password="YOUR_PASSWORD"
```

Maven executes the configured TestNG test suite and generates the test results.

---

## TestNG Suite Execution

The primary TestNG suite is:

```text
master.xml
```

It can also be executed directly from an IDE with TestNG support.

Additional suite configuration files include:

```text
crossBrowser.xml
grouping.xml
dockerRun.xml
```

These XML files provide different execution configurations for the framework.

---

## Cross-Browser Testing

The framework supports multiple browsers.

Currently supported browsers include:

- Google Chrome
- Microsoft Edge
- Mozilla Firefox

Browser selection can be controlled through TestNG parameters.

Browser-specific WebDriver options are configured dynamically inside the framework.

---

## Selenium Grid Support

The framework supports remote WebDriver execution through **Selenium Grid**.

Configure the Grid URL in:

```properties
gridUrl=http://localhost:4444/wd/hub
```

and configure the execution environment for remote execution.

The framework initializes `RemoteWebDriver` when remote execution is selected.

Supported remote platforms include:

```text
Windows
Linux
macOS
```

Supported browsers include:

```text
Chrome
Firefox
Edge
```

This design allows the framework to be extended for distributed and containerized browser execution.

---

## Explicit Wait Strategy

The framework uses Selenium explicit waits to improve synchronization between Selenium WebDriver and dynamic web elements.

Reusable operations are maintained centrally.

Common operations include:

```text
click()
type()
getText()
isDisplayed()
```

Explicit waits are used before interacting with elements whenever necessary.

This approach helps reduce synchronization failures and avoids unnecessary fixed delays such as `Thread.sleep()`.

---

## Test Reports

The framework uses **Extent Reports** for HTML test reporting.

Reports are generated under:

```text
reports/
```

Example generated report:

```text
TestReport-YYYY.MM.DD.HH.MM.SS.html
```

The report provides information such as:

- Test execution status
- Passed tests
- Failed tests
- Skipped tests
- Failure details
- Execution information
- Screenshots for failed tests

Generated reports are runtime artifacts and are excluded from source control.

---

## Failure Screenshots

When a test fails, the framework automatically captures a browser screenshot.

Screenshots are generated under:

```text
screenshots/
```

Screenshot filenames include the test name and timestamp, making failures easier to investigate.

Example:

```text
TC002_LoginPage_20260916_113831.png
```

Generated screenshots are excluded from Git using `.gitignore`.

---

## Logging

The framework uses **Log4j2** for execution logging.

Logging configuration is maintained in:

```text
src/test/resources/log4j2.xml
```

Logging is used to capture important framework activities such as:

- Browser initialization
- Execution environment
- Application launch
- Test execution
- Browser shutdown
- Framework events

This simplifies debugging and provides better visibility into automation execution.

---

## CI/CD Integration

The framework is integrated with **GitHub Actions** for continuous integration.

The workflow configuration is maintained in:

```text
.github/workflows/selenium-test.yml
```

The CI pipeline automatically executes the Selenium automation suite when configured repository events occur.

### CI Pipeline Flow

```text
Developer Push
      │
      ▼
GitHub Repository
      │
      ▼
GitHub Actions
      │
      ▼
Setup Java Environment
      │
      ▼
Resolve Maven Dependencies
      │
      ▼
Configure Browser Environment
      │
      ▼
Run Selenium in Headless Chrome
      │
      ▼
Execute TestNG Suite
      │
      ▼
Generate Extent Report
      │
      ▼
Test Results
```

### Headless Browser Execution

The framework detects the GitHub Actions CI environment and configures Chrome for headless execution.

Typical CI browser arguments include:

```text
--headless=new
--no-sandbox
--disable-dev-shm-usage
--window-size=1920,1080
```

This allows Selenium tests to execute on the GitHub-hosted Linux runner without requiring a visible browser window.

---

## GitHub Actions Credential Security

The CI workflow does not contain hardcoded OpenCart credentials.

Instead, GitHub Actions repository secrets are used:

```text
OPENCART_USERNAME
OPENCART_PASSWORD
```

The workflow securely provides these credentials to the test execution environment.

This keeps sensitive information outside:

- Java source code
- `config.properties`
- TestNG XML files
- Git history
- Public repository files

---

## CI Execution Result

The framework has been successfully executed using GitHub Actions.

Latest verified execution:

```text
Tests run: 9
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

Extent Report generation was also successfully completed during CI execution.

This verifies that the framework can execute both locally and in a CI environment.

---

## Test Design Principles

The framework follows maintainable automation practices:

- Page elements are encapsulated in Page Objects
- Test classes focus primarily on workflow and assertions
- Selenium actions are reusable
- Explicit waits are centralized
- Test data is separated from test logic
- Configuration is externalized
- Credentials are not hardcoded
- Tests are designed to execute independently
- Runtime reports are separated from source code
- Failure screenshots are generated automatically
- Logging is centralized
- Maven manages dependencies and execution
- GitHub Actions provides automated CI execution

---

## Version Control

Git and GitHub are used for source-code management.

The repository excludes runtime and local-development artifacts through `.gitignore`.

Examples include:

```text
target/
reports/
screenshots/
logs/
.classpath
.project
.settings/
```

This keeps the repository clean and focused on framework source code and configuration.

---

## Current Framework Capabilities

The project currently demonstrates:

**UI Automation**
- Selenium WebDriver
- Page Object Model
- Explicit waits
- Functional UI validation

**Framework Design**
- Hybrid framework architecture
- Reusable components
- TestNG test management
- Maven dependency management

**Data-Driven Testing**
- Apache POI
- Excel test data
- TestNG DataProvider

**Execution**
- Local browser execution
- Cross-browser support
- Selenium Grid support
- Headless CI execution

**Reporting & Debugging**
- Extent Reports
- Failure screenshots
- Log4j2 logging

**DevOps**
- Git
- GitHub
- GitHub Actions
- CI test execution
- Secure repository secrets

---

## Future Enhancements

Planned improvements include:

- Parallel execution using `ThreadLocal<WebDriver>`
- Thread-safe Extent Reports
- Dockerized Selenium Grid execution
- Additional end-to-end e-commerce scenarios
- REST Assured API automation integration
- UI and API combined automation scenarios
- Enhanced test-data management
- Improved CI artifact management

---

## Project Highlights

This project demonstrates practical experience with:

- Designing a Selenium automation framework from scratch
- Implementing Page Object Model
- Building reusable Selenium components
- Creating data-driven tests with Excel
- Managing tests using TestNG
- Managing dependencies and execution using Maven
- Implementing positive and negative functional scenarios
- Configuring cross-browser automation
- Supporting Selenium Grid execution
- Generating professional HTML automation reports
- Capturing screenshots automatically on failures
- Implementing execution logging
- Externalizing sensitive test credentials
- Integrating Selenium automation with GitHub Actions
- Running browser automation in a headless Linux CI environment
- Troubleshooting local vs CI execution differences

---

## Author

**Alfaj Godad**

**QA Automation Engineer**

Selenium WebDriver | Java | TestNG | Maven | API Testing | Git | GitHub | CI/CD