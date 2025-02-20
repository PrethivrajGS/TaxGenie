## TaxGenie - A Simple Tax Calculator  

### Overview  
TaxGenie is a Java-based GUI application that helps users calculate their tax based on their gross pay. It also retrieves the best investment bank based on interest rates from a MySQL database.  

### Features  
- Takes user input for **Gross Pay, Expenses, and Basic Pay**  
- Calculates **tax amount** based on predefined slabs  
- Computes **net pay** after expenses  
- Fetches the **best bank** for investment from MySQL database  
- Displays the **investment amount** based on the highest interest rate  
- User-friendly **GUI using Swing**  

### Technologies Used  
- **Java (Swing & AWT)** for GUI  
- **JDBC (MySQL Connector)** for database interaction  
- **MySQL** for storing bank interest rates  

### Prerequisites  
- **JDK 8+** installed  
- **MySQL Server** running with a database named `tax`  
- A table named `interest` with columns:  
  - `bank` (VARCHAR)  
  - `rate` (DOUBLE)  

### Setup Instructions  
1. Clone the repository:  
   ```sh
   git clone https://github.com/yourusername/TaxGenie.git
   cd TaxGenie
   ```  
2. Configure MySQL:  
   - Create the `tax` database  
   - Create the `interest` table with `bank` and `rate` columns  
   - Insert sample data  
3. Compile & Run:  
   ```sh
   javac TaxGenie.java  
   java TaxGenie  
   ```  

