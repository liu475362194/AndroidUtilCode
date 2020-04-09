package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.blankj.utilcode.constant.MemoryConstants;
import com.blankj.utilcode.constant.TimeConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/13
 *     desc  : utils about convert
 * </pre>
 */
public final class ConvertUtils {

    private static final int    BUFFER_SIZE = 8192;
    private static final char[] hexDigits   =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Bytes to bits.
     *
     * @param bytes The bytes.
     * @return bits
     */
    public static String bytes2Bits(final byte[] bytes) {
        if (bytes == null || bytes.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * Bits to bytes.
     *
     * @param bits The bits.
     * @return bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // add "0" until length to 8 times
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * Bytes to chars.
     *
     * @param bytes The bytes.
     * @return chars
     */
    public static char[] bytes2Chars(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * Chars to bytes.
     *
     * @param chars The chars.
     * @return bytes
     */
    public static byte[] chars2Bytes(final char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     * 可自定义长度
     */
    public static byte[] int2Bytes(int value, int size) {
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            bytes[i] = (byte) ((value >> ((size - i - 1) * 8)) & 0xFF);
        }
        return bytes;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。
     * 可自定义长度
     */
    public static byte[] int2Bytes2(int value, int size) {
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            bytes[size - i - 1] = (byte) ((value >> ((size - i - 1) * 8)) & 0xFF);
        }
        return bytes;
    }

    /**
     * byte数组中取int数值，本方法适用于(高位在前，低位在后)的顺序。
     * 可自定义获取的长度
     */
    public static int bytes2Int(byte[] bytes, int offset, int size) {
        int value = 0;
        for (int i = 0; i < size; i++) {
            value = value | ((bytes[offset + i] & 0xFF) << ((size - i - 1) * 8));
        }
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序。
     * 可自定义获取的长度
     */
    public static int bytes2Int2(byte[] bytes, int offset, int size) {
        int value = 0;
        for (int i = 0; i < size; i++) {
            value = value | ((bytes[offset + i] & 0xFF) << (i * 8));
        }
        return value;
    }

    /**
     * int 转 16进制字符串
     *
     * @param value 内容，int
     * @param size  需要转换的大小
     */
    public static String int2HexString(int value, int size) {
        String hexString = bytes2HexString(int2Bytes(value, size));
        if (size > 1)
            hexString = hexString.replace(" ", "");

        return hexString;
    }

    /**
     * 十进制转换成二进制
     *
     * @param value 十进制整数
     * @return String 二进制字符串
     */
    public static String int2Binary(int value) {
        BigInteger bi = new BigInteger(String.valueOf(value));    //转换成BigInteger类型
        return bi.toString(2);    //参数2指定的是转化成X进制，默认10进制
    }

    /**
     * 二进制转换成十进制
     *
     * @param value 二进制字符串，如“10101100”
     * @return int 十进制整数
     */
    public static int binary2Int(String value) {
        BigInteger bi = new BigInteger(value, 2);    //转换为BigInteger类型
        return Integer.parseInt(bi.toString());        //转换成十进制
    }

    /**
     * Bytes to hex string.
     * <p>e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns "00A8"</p>
     *
     * @param bytes The bytes.
     * @return hex string
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * Hex string to bytes.
     * <p>e.g. hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }</p>
     *
     * @param hexString The hex string.
     * @return the bytes
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (UtilsBridge.isSpace(hexString)) return new byte[0];
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Bytes to string.
     */
    public static String bytes2String(final byte[] bytes) {
        return bytes2String(bytes, "");
    }

    /**
     * Bytes to string.
     */
    public static String bytes2String(final byte[] bytes, final String charsetName) {
        if (bytes == null) return null;
        try {
            return new String(bytes, getSafeCharset(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(bytes);
        }
    }

    /**
     * String to bytes.
     */
    public static byte[] string2Bytes(final String string) {
        return string2Bytes(string, "");
    }

    /**
     * String to bytes.
     */
    public static byte[] string2Bytes(final String string, final String charsetName) {
        if (string == null) return null;
        try {
            return string.getBytes(getSafeCharset(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return string.getBytes();
        }
    }

    /**
     * Bytes to JSONObject.
     */
    public static JSONObject bytes2JSONObject(final byte[] bytes) {
        if (bytes == null) return null;
        try {
            return new JSONObject(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * JSONObject to bytes.
     */
    public static byte[] jsonObject2Bytes(final JSONObject jsonObject) {
        if (jsonObject == null) return null;
        return jsonObject.toString().getBytes();
    }

    /**
     * Bytes to JSONArray.
     */
    public static JSONArray bytes2JSONArray(final byte[] bytes) {
        if (bytes == null) return null;
        try {
            return new JSONArray(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * JSONArray to bytes.
     */
    public static byte[] jsonArray2Bytes(final JSONArray jsonArray) {
        if (jsonArray == null) return null;
        return jsonArray.toString().getBytes();
    }

    /**
     * Bytes to Parcelable
     */
    public static <T> T bytes2Parcelable(final byte[] bytes,
                                         final Parcelable.Creator<T> creator) {
        if (bytes == null) return null;
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }

    /**
     * Parcelable to bytes.
     */
    public static byte[] parcelable2Bytes(final Parcelable parcelable) {
        if (parcelable == null) return null;
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    /**
     * Bytes to Serializable.
     */
    public static Object bytes2Object(final byte[] bytes) {
        if (bytes == null) return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Serializable to bytes.
     */
    public static byte[] serializable2Bytes(final Serializable serializable) {
        if (serializable == null) return null;
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
            oos.writeObject(serializable);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Bytes to bitmap.
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return UtilsBridge.bytes2Bitmap(bytes);
    }

    /**
     * Bitmap to bytes.
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap) {
        return UtilsBridge.bitmap2Bytes(bitmap);
    }

    /**
     * Bitmap to bytes.
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format, int quality) {
        return UtilsBridge.bitmap2Bytes(bitmap, format, quality);
    }

    /**
     * Bytes to drawable.
     */
    public static Drawable bytes2Drawable(final byte[] bytes) {
        return UtilsBridge.bytes2Drawable(bytes);
    }

    /**
     * Drawable to bytes.
     */
    public static byte[] drawable2Bytes(final Drawable drawable) {
        return UtilsBridge.drawable2Bytes(drawable);
    }

    /**
     * Drawable to bytes.
     */
    public static byte[] drawable2Bytes(final Drawable drawable, final Bitmap.CompressFormat format, int quality) {
        return UtilsBridge.drawable2Bytes(drawable, format, quality);
    }

    /**
     * Size of memory in unit to size of byte.
     *
     * @param memorySize Size of memory.
     * @param unit       The unit of memory size.
     *                   <ul>
     *                   <li>{@link MemoryConstants#BYTE}</li>
     *                   <li>{@link MemoryConstants#KB}</li>
     *                   <li>{@link MemoryConstants#MB}</li>
     *                   <li>{@link MemoryConstants#GB}</li>
     *                   </ul>
     * @return size of byte
     */
    public static long memorySize2Byte(final long memorySize,
                                       @MemoryConstants.Unit final int unit) {
        if (memorySize < 0) return -1;
        return memorySize * unit;
    }

    /**
     * Size of byte to size of memory in unit.
     *
     * @param byteSize Size of byte.
     * @param unit     The unit of memory size.
     *                 <ul>
     *                 <li>{@link MemoryConstants#BYTE}</li>
     *                 <li>{@link MemoryConstants#KB}</li>
     *                 <li>{@link MemoryConstants#MB}</li>
     *                 <li>{@link MemoryConstants#GB}</li>
     *                 </ul>
     * @return size of memory in unit
     */
    public static double byte2MemorySize(final long byteSize,
                                         @MemoryConstants.Unit final int unit) {
        if (byteSize < 0) return -1;
        return (double) byteSize / unit;
    }

    /**
     * Size of byte to fit size of memory.
     * <p>to three decimal places</p>
     *
     * @param byteSize Size of byte.
     * @return fit size of memory
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize) {
        if (byteSize < 0) {
            return "shouldn't be less than zero!";
        } else if (byteSize < MemoryConstants.KB) {
            return String.format("%.3fB", (double) byteSize);
        } else if (byteSize < MemoryConstants.MB) {
            return String.format("%.3fKB", (double) byteSize / MemoryConstants.KB);
        } else if (byteSize < MemoryConstants.GB) {
            return String.format("%.3fMB", (double) byteSize / MemoryConstants.MB);
        } else {
            return String.format("%.3fGB", (double) byteSize / MemoryConstants.GB);
        }
    }

    /**
     * Time span in unit to milliseconds.
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return milliseconds
     */
    public static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    /**
     * Milliseconds to time span in unit.
     *
     * @param millis The milliseconds.
     * @param unit   The unit of time span.
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return time span in unit
     */
    public static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    /**
     * Milliseconds to fit time span.
     *
     * @param millis    The milliseconds.
     *                  <p>millis &lt;= 0, return null</p>
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return fit time span
     */
    public static String millis2FitTimeSpan(long millis, int precision) {
        return UtilsBridge.millis2FitTimeSpan(millis, precision);
    }

    /**
     * Input stream to output stream.
     */
    public static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Output stream to input stream.
     */
    public ByteArrayInputStream output2InputStream(final OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * Input stream to bytes.
     */
    public static byte[] inputStream2Bytes(final InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    /**
     * Bytes to input stream.
     */
    public static InputStream bytes2InputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Output stream to bytes.
     */
    public static byte[] outputStream2Bytes(final OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * Bytes to output stream.
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Input stream to string.
     */
    public static String inputStream2String(final InputStream is, final String charsetName) {
        if (is == null) return "";
        try {
            ByteArrayOutputStream baos = input2OutputStream(is);
            if (baos == null) return "";
            return baos.toString(getSafeCharset(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * String to input stream.
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(getSafeCharset(charsetName)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Output stream to string.
     */
    public static String outputStream2String(final OutputStream out, final String charsetName) {
        if (out == null) return "";
        try {
            return new String(outputStream2Bytes(out), getSafeCharset(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * String to output stream.
     */
    public static OutputStream string2OutputStream(final String string, final String charsetName) {
        if (string == null) return null;
        try {
            return bytes2OutputStream(string.getBytes(getSafeCharset(charsetName)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> inputStream2Lines(final InputStream is) {
        return inputStream2Lines(is, "");
    }

    public static List<String> inputStream2Lines(final InputStream is,
                                                 final String charsetName) {
        BufferedReader reader = null;
        try {
            List<String> list = new ArrayList<>();
            reader = new BufferedReader(new InputStreamReader(is, getSafeCharset(charsetName)));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Drawable to bitmap.
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        return UtilsBridge.drawable2Bitmap(drawable);
    }

    /**
     * Bitmap to drawable.
     */
    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
        return UtilsBridge.bitmap2Drawable(bitmap);
    }

    /**
     * View to bitmap.
     */
    public static Bitmap view2Bitmap(final View view) {
        return UtilsBridge.view2Bitmap(view);
    }

    /**
     * Value of dp to value of px.
     */
    public static int dp2px(final float dpValue) {
        return UtilsBridge.dp2px(dpValue);
    }

    /**
     * Value of px to value of dp.
     */
    public static int px2dp(final float pxValue) {
        return UtilsBridge.px2dp(pxValue);
    }

    /**
     * Value of sp to value of px.
     */
    public static int sp2px(final float spValue) {
        return UtilsBridge.sp2px(spValue);
    }

    /**
     * Value of px to value of sp.
     */
    public static int px2sp(final float pxValue) {
        return UtilsBridge.px2sp(pxValue);
    }

    private static String getSafeCharset(String charsetName) {
        String cn = charsetName;
        if (UtilsBridge.isSpace(charsetName) || !Charset.isSupported(charsetName)) {
            cn = "UTF-8";
        }
        return cn;
    }
}
