package tdx;

import java.nio.ByteBuffer;

public class GetSecurityQuotesCmd {

    /**
     * c语言下的小端模式 <HIHHIIHH
     */
    private static short h1 = 0x10c;

    private static int h2 = 0x02006320;

    /**
     * h3 h4 为 pkgdatalen short型
     */
    private static int h5 = 0x5053e;

    private static int h6 = 0;

    private static short h7 = 0;

    /** h8 为 stock_len short型 */

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        for (byte b : getBytes()) {
            System.out.print(TDXMain.byteToHex(b));
        }
    }

    public static byte[] getBytes() throws Exception {
        ByteBuffer bf = ByteBuffer.allocate(1024);

        byte[] h1_bytes = FormatTransfer.toLH(h1);
        bf.put(h1_bytes);

        byte[] h2_bytes = FormatTransfer.toLH(h2);
        bf.put(h2_bytes);

        // pkgdatalen = stock_len * 7 + 12
        byte[] h3_bytes = FormatTransfer.toLH((short) 26);
        bf.put(h3_bytes);
        // h4 与h3相同
        bf.put(h3_bytes);

        byte[] h5_bytes = FormatTransfer.toLH(h5);
        bf.put(h5_bytes);

        byte[] h6_bytes = FormatTransfer.toLH(h6);
        bf.put(h6_bytes);

        byte[] h7_bytes = FormatTransfer.toLH(h7);
        bf.put(h7_bytes);

        // stock_len
        byte[] h8_bytes = FormatTransfer.toLH((short) 2);
        bf.put(h8_bytes);

        // 市场
        bf.put((byte) 0);
        String code1 = "000001";
        byte[] c1_bytes = code1.getBytes("UTF-8");
        bf.put(c1_bytes);

        bf.put((byte) 1);
        String code2 = "600300";
        byte[] c2_bytes = code2.getBytes("UTF-8");
        bf.put(c2_bytes);

        bf.flip();
        byte[] readbytes = new byte[bf.limit()];
        bf.get(readbytes);

        return readbytes;
    }
}
