package locker.server;

import locker.netlib.Server;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings("unused")
public class LockerServer {

    /**
     * The list of computers, in a table model.
     */
    private ComputersListModel model = new ComputersListModel();

    /**
     * Class constructor.
     *
     * @param port the port of the server.
     */
    public LockerServer(int port) {
        System.out.println("Welcome\nSetting up Locker (server)...");
        AdminGUI adminGUI = new AdminGUI(model);
        Server server = new Server(port) {
            @Override
            public void onMessage(String msg) {
                System.out.println("Received: " + msg);
                String[] stuff = msg.split("-");
                try {
                    model.addData(new Object[]{
                            stuff[0], stuff[1], stuff[2],
                    });

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Wrong message from client: " + msg);
                    System.err.println("Could not parse it!");
                }
            }
        };
        adminGUI.addSaveLister(event -> {
            try {
                System.out.println("Exporting data...");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Export list");
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    PrintWriter pw = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()));
                    for (Object[] o : model.getData()) {
                        pw.write(Arrays.toString(o));
                    }
                    pw.close();
                }
                System.out.println("Done.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Done.\nWaing for a connection...");
        server.connect();
    }

    /**
     * Main.
     */
    public static void main(String[] args) {
        new LockerServer(1234);
    }
}