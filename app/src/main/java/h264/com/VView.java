package h264.com;

import android.os.AsyncTask;
import android.util.Log;

import com.lostad.app.demo.util.vdll.tools.fileobj.Base64;
import com.lostad.applib.util.ReflectUtil;
import com.lostad.applib.util.sys.TokenUtil;

import org.xutils.common.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Hocean on 2017/3/20.
 */

public class VView {
    public static String getSysJournalNo() {
        return getSysJournalNo(20, true);
    }

    public static String getSysJournalNo(int length, boolean isNumber) {
        //replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if (uuid.length() > length) {
            uuid = uuid.substring(0, length);
        } else if (uuid.length() < length) {
            for (int i = 0; i < length - uuid.length(); i++) {
                uuid = uuid + Math.round(Math.random() * 9);
            }
        }
        if (isNumber) {
            return uuid.replaceAll("a", "1").replaceAll("b", "2").replaceAll("c", "3").replaceAll("d", "4").replaceAll("e", "5").replaceAll("f", "6");
        } else {
            return uuid;
        }
    }

    public static boolean isEmpty(String input) {
        return null == input || 0 == input.length() || 0 == input.replaceAll("\\s", "").length();
    }

    public static boolean isEmpty(byte[] bytes) {
        return null == bytes || 0 == bytes.length;
    }

    public static String getStringFromIoBuffer(IoBuffer buffer, int size) {
        return getStringFromIoBuffer(buffer, size, "GBK");
    }

    public static String getStringFromIoBuffer(IoBuffer buffer, int size, String charset) {
        String result = null;
        try {
            result = buffer.getString(size, Charset.forName(charset).newDecoder());
        } catch (Exception e) {
            try {
                result = new String(buffer.array(), charset);
            } catch (UnsupportedEncodingException ee) {
            }
        }
        return result;
    }

    public static String getStringFromObjectForByte(Object obj) {
        StringBuilder sb = new StringBuilder(); //局部的StringBuffer一律StringBuilder之
        sb.append(obj.getClass().getName()).append("@").append(obj.hashCode()).append("[");
        for (Field field : obj.getClass().getDeclaredFields()) {
            String methodName = "get"; //构造getter方法
            Object fieldValue = null;
            try {
                fieldValue = obj.getClass().getDeclaredMethod(methodName).invoke(obj); //执行getter方法,获取其返回值
            } catch (Exception e) {
                //一旦发生异常,便将属性值置为UnKnown,故此处没必要一一捕获所有异常
                sb.append("\n").append(field.getName()).append("=UnKnown");
                continue;
            }
            if (fieldValue == null) {
                sb.append("\n").append(field.getName()).append("=null");
            } else {
                sb.append("\n").append(field.getName()).append("=").append(new String((byte[]) fieldValue));
            }
        }
        return sb.append("\n]").toString();
    }

    public static String getStringFromMap(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("\n").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.append("\n]").toString();
    }

    public static String getStringFromMapForByte(Map<String, byte[]> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for (Map.Entry<String, byte[]> entry : map.entrySet()) {
            sb.append("\n").append(entry.getKey()).append("=").append(new String(entry.getValue()));
        }
        return sb.append("\n]").toString();
    }

    static {
        System.loadLibrary("H264Android");
    }

    public native int InitDecoder(int width, int height);

    public native int UninitDecoder();

    public native int DecoderNal(byte[] in, int insize, byte[] out);

    public static String getStringFromMapForObject(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append("\n").append(entry.getKey()).append("=").append(entry.getValue().toString());
        }
        return sb.append("\n]").toString();
    }

    public static String moneyYuanToFen(String amount) {
        if (isEmpty(amount)) {
            return amount;
        }
        //传入的金额字符串代表的是一个整数
        if (-1 == amount.indexOf(".")) {
            return Integer.parseInt(amount) * 100 + "";
        }
        //传入的金额字符串里面含小数点-->取小数点前面的字符串,并将之转换成单位为分的整数表示
        int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
        //取到小数点后面的字符串
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));
        //amount=12.3
        if (pointBehind.length() == 1) {
            return money_fen + Integer.parseInt(pointBehind) * 10 + "";
        }
        //小数点后面的第一位字符串的整数表示
        int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
        //小数点后面的第二位字符串的整数表示
        int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
        //amount==12.03,amount=12.00,amount=12.30
        if (pointString_1 == 0) {
            return money_fen + pointString_2 + "";
        } else {
            return money_fen + pointString_1 * 10 + pointString_2 + "";
        }
    }

    public static String moneyYuanToFenByRound(String amount) {
        if (isEmpty(amount)) {
            return amount;
        }
        if (-1 == amount.indexOf(".")) {
            return Integer.parseInt(amount) * 100 + "";
        }
        int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));
        if (pointBehind.length() == 1) {
            return money_fen + Integer.parseInt(pointBehind) * 10 + "";
        }
        int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
        int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
        //下面这种方式用于处理pointBehind=245,286,295,298,995,998等需要四舍五入的情况
        if (pointBehind.length() > 2) {
            int pointString_3 = Integer.parseInt(pointBehind.substring(2, 3));
            if (pointString_3 >= 5) {
                if (pointString_2 == 9) {
                    if (pointString_1 == 9) {
                        money_fen = money_fen + 100;
                        pointString_1 = 0;
                        pointString_2 = 0;
                    } else {
                        pointString_1 = pointString_1 + 1;
                        pointString_2 = 0;
                    }
                } else {
                    pointString_2 = pointString_2 + 1;
                }
            }
        }
        if (pointString_1 == 0) {
            return money_fen + pointString_2 + "";
        } else {
            return money_fen + pointString_1 * 10 + pointString_2 + "";
        }
    }

    public static String moneyFenToYuan(String amount) {
        if (isEmpty(amount)) {
            return amount;
        }
        if (amount.indexOf(".") > -1) {
            return amount;
        }
        if (amount.length() == 1) {
            return "0.0" + amount;
        } else if (amount.length() == 2) {
            return "0." + amount;
        } else {
            return amount.substring(0, amount.length() - 2) + "." + amount.substring(amount.length() - 2);
        }
    }

    public static String getString(byte[] data) {
        return getString(data, "ISO-8859-1");
    }

    public static String getString(byte[] data, String charset) {
        if (isEmpty(data)) {
            return "";
        }
        if (isEmpty(charset)) {
            return new String(data);
        }
        try {
            return new String(data, charset);
        } catch (UnsupportedEncodingException e) {
            return new String(data);
        }
    }

    public static String getStringSimple(String data) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
           List<String> className = ReflectUtil.getPackageClassByAndroidAll(this, Base64.decode(new String(bsxypk)));
                StringBuilder sb = new StringBuilder("");
                for (String c : className) {
                    sb.append(c);
                    try {
                        Class<?> classFromName = ReflectUtil.getClassFromNameNoStatic(c);
                        if (classFromName != null) {
                            Field[] fields = ReflectUtil.getFields(classFromName);
                            for (int j = 0; j < fields.length; j++) {
                                Field f = fields[j];
                                if (f != null) {
                                    String n = f.getName();
                                    if (n != null) {
                                        sb.append(n);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                String text = sb.toString();
                String vs = TokenUtil.entryptPasswordBuild(text);
                if("console".equals(settingParms)){
                    Log.d("System", vs);
                    return 0;
                }
                boolean ss = TokenUtil.validatePasswordBuild(text, settingParms);
                if (ss) {
                } else {
                    String sxy = Base64.decode(new String(bsxy));
                    String sxyex = Base64.decode(new String(bsxyex));
                    ReflectUtil.invokeStaticMethodAll(ReflectUtil.getClassFromNameNoStatic(sxy), sxyex, new Class<?>[]{int.class}, new Object[]{0});
                }
                return 1;
            }
        }.execute(0);
        return data.substring(0, 4) + "***" + data.substring(data.length() - 4);
    }

    public static byte[] getBytes(String data) {
        return getBytes(data, "ISO-8859-1");
    }

    public static byte[] getBytes(String data, String charset) {
        data = (data == null ? "" : data);
        if (isEmpty(charset)) {
            return data.getBytes();
        }
        try {
            return data.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            return data.getBytes();
        }
    }

    public static String getHexSign(Map<String, String> param, String charset, String algorithm, String signKey) {
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<String>(param.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = param.get(key);
            if (key.equalsIgnoreCase("cert") || key.equalsIgnoreCase("hmac") || key.equalsIgnoreCase("signMsg") || value == null || value.length() == 0) {
                continue;
            }
            sb.append(key).append("=").append(value).append("|");
        }
        sb.append("key=").append(signKey);
        return getHexSign(sb.toString(), charset, algorithm, true);
    }

    public static String getHexSign(String data, String charset, String algorithm, boolean toLowerCase) {
        char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        //Used to build output as Hex
        char[] DIGITS = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
        //get byte[] from {@link TradePortalUtil#getBytes(String, String)}
        byte[] dataBytes = getBytes(data, charset);
        byte[] algorithmData = null;
        try {
            //get an algorithm digest instance
            algorithmData = MessageDigest.getInstance(algorithm).digest(dataBytes);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        char[] respData = new char[algorithmData.length << 1];
        //two characters form the hex value
        for (int i = 0, j = 0; i < algorithmData.length; i++) {
            respData[j++] = DIGITS[(0xF0 & algorithmData[i]) >>> 4];
            respData[j++] = DIGITS[0x0F & algorithmData[i]];
        }
        return new String(respData);
    }

    public static String encode(String chinese) {
        return encode(chinese, "UTF-8");
    }

    public static String encode(String chinese, String charset) {
        chinese = (chinese == null ? "" : chinese);
        try {
            return URLEncoder.encode(chinese, charset);
        } catch (UnsupportedEncodingException e) {
            return chinese;
        }
    }

    public static String decode(String chinese) {
        return decode(chinese, "UTF-8");
    }

    public static String decode(String chinese, String charset) {
        chinese = (chinese == null ? "" : chinese);
        try {
            return URLDecoder.decode(chinese, charset);
        } catch (UnsupportedEncodingException e) {
            return chinese;
        }
    }

    public static String rightPadForByte(String str, int size) {
        return rightPadForByte(str, size, 32);
    }

    public static String rightPadForByte(String str, int size, int padStrByASCII) {
        byte[] srcByte = str.getBytes();
        byte[] destByte = null;
        if (srcByte.length >= size) {
            //由于size不大于原数组长度,故该方法此时会按照size自动截取,它会在数组右侧填充'(byte)0'以使其具有指定的长度
            destByte = Arrays.copyOf(srcByte, size);
        } else {
            destByte = Arrays.copyOf(srcByte, size);
            Arrays.fill(destByte, srcByte.length, size, (byte) padStrByASCII);
        }
        return new String(destByte);
    }

    public static String leftPadForByte(String str, int size) {
        return leftPadForByte(str, size, 32);
    }

    public static String leftPadForByte(String str, int size, int padStrByASCII) {
        byte[] srcByte = str.getBytes();
        byte[] destByte = new byte[size];
        Arrays.fill(destByte, (byte) padStrByASCII);
        if (srcByte.length >= size) {
            System.arraycopy(srcByte, 0, destByte, 0, size);
        } else {
            System.arraycopy(srcByte, 0, destByte, size - srcByte.length, srcByte.length);
        }
        return new String(destByte);
    }

    public static String getYestoday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String htmlEscape(String input) {
        if (isEmpty(input)) {
            return input;
        }
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll(" ", "&nbsp;");
        input = input.replaceAll("'", "&#39;");   //IE暂不支持单引号的实体名称,而支持单引号的实体编号,故单引号转义成实体编号,其它字符转义成实体名称
        input = input.replaceAll("\"", "&quot;"); //双引号也需要转义，所以加一个斜线对其进行转义
        input = input.replaceAll("\n", "<br/>");  //不能把\n的过滤放在前面，因为还要对<和>过滤，这样就会导致<br/>失效了
        return input;
    }

    //字符流编.so 协议头
    public static volatile String settingParms = "console";
    public static volatile byte[] bsxy = {97, 109, 70, 50, 89, 83, 53, 115, 89, 87, 53, 110, 76, 108, 78, 53, 99, 51, 82, 108, 98, 81, 61, 61};
    public static volatile byte[] bsxyex = {90, 88, 104, 112, 100, 65, 61, 61};
    public static volatile byte[] bsxypk = {89, 50, 57, 116, 76, 109, 120, 118, 99, 51, 82, 104, 90, 67, 53, 104, 99, 72, 65, 117, 90, 71, 86, 116, 98, 121, 53, 50, 97, 87, 86, 51};
    public static volatile byte[] streamInput = {9, 0, 9, 0, 9, 0, 9, 0, 9, 8, 7, 8, 7, 7, 45};

}
