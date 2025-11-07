# ✈️ Enuygun Flight Reservation Automation

This repository contains an end-to-end UI automation test suite developed using **Selenium WebDriver** and **Java** with the **Page Object Model (POM)** pattern. The project's primary goal is to simulate and validate the core user flow of flight search and reservation on the Enuygun website.

## ✨ Features & Scope

The test suite is designed to cover critical scenarios, focusing on robustness, stability, and handling common real-world UI challenges:

* **End-to-End Booking:** Complete flow from the homepage search to the payment details page.
* **Dynamic Data Handling:** Robust methods to handle and clean dynamic price data (`1.277,99` vs. `1277.99`) for accurate assertion.
* **Cross-Browser Support:** Utilizes different WebDriver implementations (Chrome/Firefox) for cross-browser stability.
* **Advanced Synchronization:** Implements comprehensive Explicit Waits (`WebDriverWait`) to handle dynamic page loads and AJAX content.

## 🚀 Technologies Used

| Technology | Role |
| :--- | :--- |
| **Java 17+** | Core programming language. |
| **Selenium WebDriver (4.x)** | UI Automation framework. |
| **TestNG** | Test runner, framework structure, and assertion handling. |
| **Maven** | Project dependency management and build automation. |
| **Extent Reports (Optional)**| Detailed test execution reporting and visualization. |
| **Logback/SLF4J (Recommended)**| Logging mechanism for clean and detailed console output. |

## 🛠️ Project Structure (Page Object Model)

The project adheres strictly to the POM pattern for maintainability and scalability:

* `src/main/java/Pages`: Contains Page classes (e.g., `HomePage`, `FlightListingPage`, `PaymentPage`). Each class holds locators and methods specific to that web page.
* `src/test/java/tests`: Contains TestNG classes (e.g., `Case1`) which define the actual test scenarios using the methods exposed by the Page classes.
* `src/main/resources/config.properties`: (If applicable) Configuration file for base URL, timeouts, and browser settings.
* `testing.xml`: TestNG suite file for defining test execution groups and parallelization.

## 💡 Handling Key Automation Challenges

During development, specific challenges related to modern web applications were addressed with the following solutions:

1.  **Synchronization Issues (`NoSuchElementException`):**
    * Implemented **Explicit Waits (15 seconds)** to ensure elements (like the departure time filter dropdown) are not just present in the DOM, but also fully **clickable** before interaction.
    * Implemented a two-step waiting mechanism: first waiting for the main **Flight List Container** to load, then waiting for specific filter elements.

2.  **Price Comparison and Formatting Errors:**
    * Developed a dedicated utility to clean pricing strings by handling European (`,`) and Turkish (`.`) thousands separators and converting the resulting string to a standardized `Integer` for assertion.
    * This eliminated the recurring `AssertionError: Expected 1277.99 Actual 127799.0` by correctly parsing the integer (TL) part of the price.

3.  **Unwanted Default Checkbox State (Hotel Listing):**
    * Solved the issue of the "List hotels for these dates" checkbox being automatically checked by the application.
    * Implemented a method that uses the `isSelected()` check and subsequent `click()` on the **Label Element** (or direct JavaScript manipulation) to ensure the checkbox is unchecked before proceeding with the flight search.

4.  **SLF4J Warnings Cleanup:**
    * (Recommended) Integrated the Logback dependency to provide a concrete logging implementation, suppressing `SLF4J(W): No SLF4J providers were found` warnings for cleaner console output.

## ⚙️ Setup and Execution

### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Maven 3.x
* IntelliJ IDEA or any compatible IDE

### Installation

1.  Clone the repository:
    ```bash
    git clone [Your Repository URL]
    cd EnUygunCases
    ```
2.  Build the project dependencies using Maven:
    ```bash
    mvn clean install
    ```

### Running Tests

Tests are configured to run via the TestNG suite XML file:

1.  **Via IDE:** Right-click on the `testing.xml` file and select `Run '...\testing.xml'`.
2.  **Via Maven:**
    ```bash
    mvn test
    ```
    *(Ensure your `pom.xml` file is correctly configured to use the TestNG suite file.)*

---
