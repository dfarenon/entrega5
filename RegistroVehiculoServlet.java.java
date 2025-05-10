import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculoDAO { // Data Access Object para la entidad Vehiculo

    private static final String URL = "jdbc:tu_base_de_datos://localhost:tu_puerto/tu_esquema_de_base_de_datos";
    private static final String USUARIO = "tu_usuario";
    private static final String CONTRASENA = "tu_contrasena";

    public void insertarVehiculo(String placa, String marca, String modelo, int anio, String color, String numeroVIN) {
        String sql = "INSERT INTO Vehiculos (Placa, Marca, Modelo, Año, Color, NumeroVIN) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, placa);
            sentencia.setString(2, marca);
            sentencia.setString(3, modelo);
            sentencia.setInt(4, anio);
            sentencia.setString(5, color);
            sentencia.setString(6, numeroVIN); // Si NumeroVIN es null, se insertará como NULL en la base de datos

            int filasInsertadas = sentencia.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Vehículo insertado correctamente.");
            } else {
                System.out.println("No se pudo insertar el vehículo.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buscarVehiculoPorPlaca(String placa) {
        String sql = "SELECT Placa, Marca, Modelo, Año, Color, NumeroVIN FROM Vehiculos WHERE Placa = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, placa);

            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                System.out.println("Placa: " + resultado.getString("Placa"));
                System.out.println("Marca: " + resultado.getString("Marca"));
                System.out.println("Modelo: " + resultado.getString("Modelo"));
                System.out.println("Año: " + resultado.getInt("Año"));
                System.out.println("Color: " + resultado.getString("Color"));
                System.out.println("Número de VIN: " + resultado.getString("NumeroVIN"));
            } else {
                System.out.println("No se encontró ningún vehículo con la placa: " + placa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarVehiculo(String placa, String marca, String modelo, int anio, String color, String numeroVIN) {
        String sql = "UPDATE Vehiculos SET Marca = ?, Modelo = ?, Año = ?, Color = ?, NumeroVIN = ? WHERE Placa = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, marca);
            sentencia.setString(2, modelo);
            sentencia.setInt(3, anio);
            sentencia.setString(4, color);
            sentencia.setString(5, numeroVIN);
            sentencia.setString(6, placa); // Cláusula WHERE para identificar el vehículo a actualizar

            int filasActualizadas = sentencia.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Vehículo con placa " + placa + " actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún vehículo con la placa: " + placa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarVehiculo(String placa) {
        String sql = "DELETE FROM Vehiculos WHERE Placa = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, placa);

            int filasEliminadas = sentencia.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Vehículo con placa " + placa + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún vehículo con la placa: " + placa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
