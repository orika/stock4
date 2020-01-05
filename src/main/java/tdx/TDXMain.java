package tdx;

import java.io.InputStream;
import java.net.Socket;

public class TDXMain {

    private static Socket socket;

    public static void main(String[] args) throws Exception {

        String cmd1 = "0c 02 18 93 00 01 03 00 03 00 0d 00 01";

        String cmd2 = "0c 02 18 94 00 01 03 00 03 00 0d 00 02";

        int[] cmd3 =
                new int[]{
                        0x0c, 0x03, 0x18, 0x99, 0x00, 0x01, 0x20, 0x00, 0x20, 0x00, 0xdb, 0x0f, 0xd5, 0xd0, 0xc9,
                        0xcc, 0xd6, 0xa4, 0xa8, 0xaf, 0x00, 0x00, 0x00, 0x8f, 0xc2, 0x25, 0x40, 0x13, 0x00, 0x00,
                        0xd5, 0x00, 0xc9, 0xcc, 0xbd, 0xf0, 0xd7, 0xea, 0x00, 0x00, 0x00, 0x02
                };

        byte[] cmd3_bytes = new byte[cmd3.length];
        for (int i = 0; i < cmd3.length; i++) {
            cmd3_bytes[i] = (byte) cmd3[i];
        }

        byte[] cmd1_bytes = hexsToBytes(cmd1.split(" "));
        byte[] cmd2_bytes = hexsToBytes(cmd2.split(" "));

        socket = new Socket("119.147.212.81", 7709);

        System.out.println("\r\ncmd1");
        sendAndGet(cmd2_bytes);

        System.out.println("\r\ncmd2");
        sendAndGet(cmd2_bytes);

        System.out.println("\r\ncm3");
        sendAndGet(cmd3_bytes);

        System.out.println("\r\nquote");
        sendAndGet(GetSecurityQuotesCmd.getBytes());
    }

    private static void sendAndGet(byte[] cmd_bytes) throws Exception {
        socket.getOutputStream().write(cmd_bytes);
        socket.getOutputStream().flush();
        InputStream in = socket.getInputStream();
        byte[] readBytes = new byte[1024];
        do {
            int len = in.read(readBytes);
            for (int j = 0; j < len; j++) {
                System.out.print(byteToHex(readBytes[j]));
            }
        } while (in.available() > 0);
    }

    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static byte hexToByte(String hex) {
        return (byte) Integer.parseInt(hex, 16);
    }

    public static byte[] hexsToBytes(String[] hexs) {
        byte[] bytes = new byte[hexs.length];
        int i = 0;
        for (String hex : hexs) {
            bytes[i] = hexToByte(hex);
            i++;
        }
        return bytes;
    }

    public static String[] bytesToHexs(byte[] bytes) {
        String[] hexs = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            hexs[i] = byteToHex(bytes[i]);
        }
        return hexs;
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }
}
