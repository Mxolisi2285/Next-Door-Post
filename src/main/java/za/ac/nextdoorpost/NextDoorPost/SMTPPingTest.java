package za.ac.nextdoorpost.NextDoorPost;

public class SMTPPingTest {

    public static void main(String[] args) {
        String host = "smtp.gmail.com";
        int port = 587; // or 465 for SSL

        try (java.net.Socket socket = new java.net.Socket(host, port)) {
            System.out.println("Connection successful to " + host + " on port " + port);
        } catch (java.io.IOException e) {
            System.err.println("Connection failed to " + host + " on port " + port);
            e.printStackTrace();
        }
    }
}



