# OpenCart Selenium Hybrid Automation Framework

A Selenium-based Hybrid Test Automation Framework developed using **Java, TestNG, Maven, Page Object Model (POM), Apache POI, and Extent Reports**.

The framework automates key functional workflows of the OpenCart demo e-commerce application and demonstrates reusable, maintainable, and data-driven UI automation practices.

## Application Under Test

**OpenCart Demo Application**  
https://tutorialsninja.com/demo/

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
| Log4j2 | Logging |
| Git & GitHub | Version control |
| Selenium Grid | Remote/cross-browser execution |

## Framework Features

- Page Object Model (POM)
- Hybrid automation framework design
- Data-driven testing using Excel
- TestNG DataProvider
- Reusable explicit wait methods
- Cross-browser support
- Local and Selenium Grid execution
- Maven-based test execution
- Extent HTML reports
- Automatic screenshots for failed tests
- Log4j2 logging
- Positive and negative test scenarios
- Environment-based credential management
- Reusable page actions
- TestNG XML suite management

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

## Project Structure

```text
OpenCartHybridFramework/
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

## Page Object Model

The framework separates page interactions from test logic.

For example:

```text
Test Case
   ↓
Page Object
   ↓
Reusable BasePage methods
   ↓
Selenium WebDriver
   ↓
Web Application
```

Page elements are encapsulated inside their respective Page Object classes, while reusable operations such as clicking, typing, visibility checks, and explicit waits are maintained centrally.

## Data-Driven Testing

Login test data is maintained in:

```text
src/testData/LoginData.xlsx
```

Example structure:

```text
Email | Password | ExpectedResult
```

Apache POI reads the Excel data and TestNG `DataProvider` supplies each dataset to the login test.

The framework supports both positive and negative login scenarios.

## Configuration

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

Sensitive credentials are intentionally not stored in the configuration file or source code.

## Credential Management

Valid login credentials can be supplied through environment variables:

```text
OPENCART_USERNAME
OPENCART_PASSWORD
```

Alternatively, Maven system properties can be used.

Example:

```bash
mvn clean test -Dopencart.username="YOUR_EMAIL" -Dopencart.password="YOUR_PASSWORD"
```

> Do not commit real credentials to the repository.

## Running Tests

### Run the complete Maven suite

```bash
mvn clean test -Dopencart.username="YOUR_EMAIL" -Dopencart.password="YOUR_PASSWORD"
```

### Run using TestNG

The primary TestNG suite is:

```text
master.xml
```

It can also be executed directly from an IDE with TestNG support.

### Cross-Browser Execution

Cross-browser configuration is maintained in:

```text
crossBrowser.xml
```

The framework currently supports browsers including:

- Chrome
- Edge
- Firefox

## Selenium Grid Support

The framework supports remote WebDriver execution through Selenium Grid.

Configure the Grid URL in:

```properties
gridUrl=http://localhost:4444/wd/hub
```

and set the execution environment accordingly.

This allows the same framework to be extended for distributed and containerized browser execution.

## Explicit Wait Strategy

The framework uses reusable explicit waits instead of depending on fixed delays.

Common operations are centralized in `BasePage`, including:

```text
click()
type()
getText()
isDisplayed()
```

This improves synchronization and reduces duplicated Selenium code across Page Objects.

## Test Reports

Extent Reports are generated after suite execution.

Reports are stored under:

```text
reports/
```

The HTML report provides test execution status including:

- Passed tests
- Failed tests
- Skipped tests
- Failure details
- Execution information
- Screenshots for failed tests

Generated reports are excluded from source control.

## Failure Screenshots

When a test fails, the framework captures a browser screenshot automatically.

Screenshots are generated under:

```text
screenshots/
```

These runtime artifacts are excluded from Git using `.gitignore`.

## Logging

Log4j2 is used for execution logging.

Logging configuration is maintained in:

```text
src/test/resources/log4j2.xml
```

Logs help track test execution steps and simplify debugging when failures occur.

## Test Design Principles

The framework follows several maintainability practices:

- Page elements are encapsulated in Page Objects.
- Test classes focus primarily on test workflow and assertions.
- Reusable Selenium actions are maintained centrally.
- Tests are designed to execute independently.
- Test data is separated from test logic.
- Sensitive credentials are externalized.
- Generated reports, logs and screenshots are not committed to source control.

## CI/CD

The project is structured for CI/CD execution using Maven.

GitHub Actions integration will allow the automation suite to execute automatically on repository events such as pushes and pull requests.

## Future Enhancements

Planned improvements include:

- GitHub Actions CI pipeline
- Parallel execution using `ThreadLocal<WebDriver>`
- Thread-safe Extent Reports
- Dockerized Selenium Grid execution
- Additional end-to-end e-commerce scenarios
- Enhanced test-data management

## Author

**Alfaj Godad**

QA Automation Engineer  
Selenium WebDriver | Java | TestNG | API Testing | Maven | CI/CD