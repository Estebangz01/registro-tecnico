package main.java.com.registro.tecnico.config;

public final class Credentials {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "ecomoda_registro";
    private static final String USER = "root";
    private static final String PASSWORD = "Alex_bwu1";

    private Credentials() {
    }

    public static String getUrl() {
        return "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    public static String getUser() {
        return USER;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
