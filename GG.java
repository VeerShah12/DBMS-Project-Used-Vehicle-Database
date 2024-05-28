package proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class GG {
  private static final String DB_NAME = "GearGarage";
  private static final String USER = "root";
  private static final String PASS = "1264";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
  private static Connection conn = null;
  private static Statement stmt = null;

  public static void main(String[] args) {
    try {
      connect();
      System.out.println("Welcome to the Gear Garage!");
      Scanner scanner = new Scanner(System.in);
      while (true) {
        System.out.println("\nPlease choose an option:");
        System.out.println("1. Search by vehicle / manufacturer name");
        System.out.println("2. Exit");
        int choice = scanner.nextInt();
        if (choice == 1) {
          System.out.println("Enter the name of the vehicle / manufacturer:\n");
          System.out.println("(press enter to search by filter)\n");
          scanner.nextLine();
          String name = scanner.nextLine();
          search(name, scanner);
        } else if (choice == 2) {
          break;
        } else {
          System.out.println("Invalid input. Please try again.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private static void search(String carName, Scanner scanner) throws SQLException {
    String query= "SELECT * FROM Vehicles v " +
        "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
        "WHERE v.vehicle_name LIKE ?";

    PreparedStatement pstmt = conn.prepareStatement(query);
    pstmt.setString(1, "%" + carName + "%");
    ResultSet rs = pstmt.executeQuery();
    System.out.println("\nPlease choose an option to filter the search results:");
    System.out.println("1. By age");
    System.out.println("2. By mileage");
    System.out.println("3. By body type");
    System.out.println("4. By fuel type");
    System.out.println("5. By transmission");
    System.out.println("6. By engine capacity");
    System.out.println("7. By running status");
    System.out.println("8. Display all results");
    int filterChoice = scanner.nextInt();
    if (rs.next()) {
      switch (filterChoice) {
        case 1:
          System.out.println("Enter the vehicle age:");
          int age = scanner.nextInt();
          query = "SELECT * FROM Vehicles v " +
              "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
              "WHERE v.vehicle_name LIKE ? AND vd.make_year >= ?";
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, "%" + carName + "%");
          pstmt.setInt(2, age);
          rs = pstmt.executeQuery();
          printResultSet(rs);
          break;
        case 2:
          System.out.println("Enter the mileage:");
          int mileage = scanner.nextInt();
          query = "SELECT * FROM Vehicles v " +
              "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
              "WHERE v.vehicle_name LIKE ? AND vd.mileage >= ?";
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, "%" + carName + "%");
          pstmt.setInt(2, mileage);
          rs = pstmt.executeQuery();
          printResultSet(rs);
          break;
        case 3:
          System.out.println("Enter the vehicle body type:");
          scanner.nextLine();
          String bodyType = scanner.nextLine();
          query = "SELECT * FROM Vehicles v " +
              "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
              "WHERE v.vehicle_name LIKE ? AND vd.body_type = ?";
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, "%" + carName + "%");
          pstmt.setString(2, bodyType);
          rs = pstmt.executeQuery();
          printResultSet(rs);
          break;
        case 4:
          System.out.println("Enter the vehicle fuel type:");
          String fuelType = scanner.nextLine();
          query = "SELECT * FROM Vehicles v " +
              "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
              "WHERE v.vehicle_name LIKE ? AND vd.fuel_type = ?";
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, "%" + carName + "%");
          pstmt.setString(2, fuelType);
          rs = pstmt.executeQuery();
          printResultSet(rs);
          break;
        case 5:
          System.out.println("Enter the vehicle transmission:");
          String transmission = scanner.nextLine();
          query = "SELECT * FROM Vehicles v " +
              "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
              "WHERE v.vehicle_name LIKE ? AND vd.transmission = ?";
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, "%" + carName + "%");
          pstmt.setString(2, transmission);
          rs = pstmt.executeQuery();
          printResultSet(rs);
          break;
        case 6:
          System.out.println("Enter the minimum engine capacity:");
          int minEngineCapacity = scanner.nextInt();
          System.out.println("Enter the maximum engine capacity:");
          int maxEngineCapacity = scanner.nextInt();
          query = "SELECT * FROM Vehicles v " +
              "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
              "WHERE v.vehicle_name LIKE ? AND vd.engine_capacity BETWEEN ? AND ?";
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, "%" + carName + "%");
          pstmt.setInt(2, minEngineCapacity);
          pstmt.setInt(3, maxEngineCapacity);
          rs = pstmt.executeQuery();
          printResultSet(rs);
          break;
        case 7:
          System.out.println("Enter the minimum running status:");
          int minRunning = scanner.nextInt();
          System.out.println("Enter the maximum running status:");
          int maxRunning = scanner.nextInt();
          query = "SELECT * FROM Vehicles v " +
              "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
              "WHERE v.vehicle_name LIKE ? AND CAST(REPLACE(vd.running, ',', '') AS UNSIGNED) BETWEEN ? AND ?";
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, "%" + carName + "%");
          pstmt.setInt(2, minRunning);
          pstmt.setInt(3, maxRunning);
          rs = pstmt.executeQuery();
          printResultSet(rs);
          break;
        case 8:
          printResultSet(rs);
          break;
        default:
          System.out.println("Invalid input. Please try again.");
          break;
      }
    } else {
      System.out.println("No results found for " + carName);
    }
  }

  private static void printResultSet(ResultSet rs) throws SQLException {
    ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
    int columnCount = rsmd.getColumnCount();

    System.out.println("+-----------------+------------+------+-------+----------+------------+---------+");
    System.out.printf("| %-15s | %-10s | %-5s | %-6s | %-9s | %-10s | %-7s |%n",
            "Vehicle ID", "Vehicle Name", "Year", "Mileage", "Body", "Fuel", "Transmission", "Price");
    System.out.println("+-----------------+------------+------+-------+----------+------------+---------+");

    while (rs.next()) {
      String vehicleId = rs.getString("vehicle_id");
      String vehicleName = rs.getString("vehicle_name");
      int makeYear = rs.getInt("make_year");
      double mileage = rs.getDouble("mileage");
      String bodyType = rs.getString("body_type");
      String fuelType = rs.getString("fuel_type");
      String transmission = rs.getString("transmission");
      String price = rs.getString("price");

      System.out.printf("| %-15s | %-15s | %-10d | %-5.1f | %-6s | %-9s | %-10s | %7s |%n",
              vehicleId, vehicleName, makeYear, mileage, bodyType, fuelType, transmission, price);
    }

    System.out.println("+-----------------+------------+------+-------+----------+------------+---------+");

    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter vehicle ID for quotation");
    System.out.print("Enter vehicle ID: ");
   String selectedId = scanner.nextLine();
    printQuotation(selectedId);
  }

  private static void printQuotation(String vehicleId) throws SQLException {
    String query = "SELECT * FROM Vehicles v " +
        "JOIN VehicleDetails vd ON v.vehicle_id = vd.vehicle_id " +
        "WHERE v.vehicle_id = ?";

    PreparedStatement pstmt = conn.prepareStatement(query);
    pstmt.setString(1, String.valueOf(vehicleId));
    ResultSet rs = pstmt.executeQuery();

    if (rs.next()) {
      String vehicleIdResult = rs.getString("vehicle_id");
      String vehicleName = rs.getString("vehicle_name");
      int makeYear = rs.getInt("make_year");
      int mileage = rs.getInt("mileage");
      String bodyType = rs.getString("body_type");
      String fuelType = rs.getString("fuel_type");
      String transmission = rs.getString("transmission");
      String price = rs.getString("price");

      double tax;
      if (bodyType.equalsIgnoreCase("Hatchback")) {
        tax = Double.parseDouble(price.replaceAll(",", "")) * 0.12;
      } else if (bodyType.equalsIgnoreCase("SUV")) {
        tax = Double.parseDouble(price.replaceAll(",", "")) * 0.28;
      } else {
        tax = Double.parseDouble(price.replaceAll(",", "")) * 0.18;
      }

      System.out.println("\n=================== Quotation ===================");
      System.out.println("Vehicle ID: " + vehicleIdResult);
      System.out.println("Vehicle Name: " + vehicleName);
      System.out.println("Year: " + makeYear);
      System.out.println("Mileage: " + mileage);
      System.out.println("Body Type: " + bodyType);
      System.out.println("Fuel Type: " + fuelType);
      System.out.println("Transmission: " + transmission);
      System.out.println("Price: Rs" + price);
      System.out.println("Tax: Rs." + String.format("%.2f", tax));
      System.out.println("------------------------------------------------");
      System.out.println("Total Price: Rs" + String.format("%.2f", Double.parseDouble(price.replaceAll(",", "")) + tax));
      System.out.println("=================================================");
    } else {
      System.out.println("Vehicle with ID " + vehicleId + " not found.");
    }
  }

  private static void connect()
      throws SQLException {
    if (conn == null) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
      } catch (ClassNotFoundException e) {
        System.out.println("Error loading driver: " + e.getMessage());
      }
    }
  }
}