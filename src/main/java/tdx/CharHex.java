package tdx;

import com.alibaba.fastjson.JSON;

public class CharHex {

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 16; i++) {

            System.out.println(
                    TDXMain.byteToHex((byte) i)
                            + " "
                            + JSON.toJSON(new String(i + "").getBytes()).toString());
        }

        System.out.println(JSON.toJSON("我".getBytes("UTF-8")).toString());
    }
}
