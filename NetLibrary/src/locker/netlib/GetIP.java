package locker.netlib;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author Marco Cipriani
 * @version 0.1
 */
@Deprecated
public class GetIP {

    public static void main(String[] args) throws SocketException, UnknownHostException {
        System.out.println("Host address: " + InetAddress.getLocalHost().getHostAddress());
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        for (; n.hasMoreElements(); ) {
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements(); ) {
                InetAddress address = a.nextElement();
                System.out.println("  " + address.getHostAddress());
            }
        }
    }
}