package squareboot.locker.server;

import squareboot.locker.netlib.Server;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author SquareBoot
 * @version 0.1
 */
@SuppressWarnings("unused")
public class LockerServer {

    /**
     * The list of computers, in a table model.
     */
    private ComputersListModel model = new ComputersListModel();
    /**
     * Where to save.
     */
    private File logFile;

    /**
     * Class constructor.
     *
     * @param port the port of the server.
     */
    public LockerServer(int port) {
        this(port, null);
    }

    /**
     * Class constructor.
     *
     * @param port    the port of the server.
     * @param outFile where to save the list of clients.
     */
    public LockerServer(int port, String outFile) {
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
                    if (logFile != null) {
                        save();
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Wrong message from client: " + msg);
                    System.err.println("Could not parse it!");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        adminGUI.addSaveLister(event -> {
            try {
                System.out.println("Exporting data...");
                if (logFile == null) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Export list");
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        logFile = fileChooser.getSelectedFile();

                    } else {
                        System.out.println("Aborted.");
                        return;
                    }
                }
                save();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if (outFile == null) {
            System.out.println("Asking for a log file...");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Export list");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                logFile = fileChooser.getSelectedFile();
                System.out.println("Autosave enabled.");
            }

        } else {
            logFile = new File(outFile);
            System.out.println("Autosave enabled.");
        }
        System.out.println("Waiting for clients...");
        server.connect();
    }

    /**
     * Main.
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (args.length == 1) {
            new LockerServer(Integer.parseInt(args[0]));

        } else if (args.length == 2) {
            new LockerServer(Integer.parseInt(args[0]), args[1]);

        } else {
            System.err.println("Invalid arguments!");
        }
    }

    /**
     * Saves the list of clients on the disk.
     */
    private void save() throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(logFile));
        for (Object[] o : model.getData()) {
            pw.write(Arrays.toString(o).replace("[", "").replace("]", ""));
        }
        pw.close();
        System.out.println("Done.");
    }
}