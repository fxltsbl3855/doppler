package com.sinoservices.util;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liujinye on 14-7-29.
 */
public class StringUtil {
    /**
     * trim字符串中所有的非可见字符，包括字符串中间
     *
     * @param str
     * @return
     * @author hexiufeng
     * @created 2012-03-16
     */
    public static final String alltrimWithWideSpace(String str) {
        if (str == null)
            return "";
        // 转换为字符数组循环判断左边后者右边的字符是否是space
        char[] chArray = str.toCharArray();

        int first = -1, last = -1;
        for (int i = 0; i < chArray.length; i++) {
            char ch = chArray[i];
            if (!Character.isWhitespace(ch) && !Character.isSpaceChar(ch)) {
                first = i;
                break;
            }
        }

        for (int i = chArray.length - 1; i >= 0; i--) {
            char ch = chArray[i];
            if (!Character.isWhitespace(ch) && !Character.isSpaceChar(ch)) {
                last = i;
                break;
            }
        }

        if (first == -1 || last == -1 || last < first)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = first; i <= last; i++) {
            char ch = chArray[i];
            if (!Character.isWhitespace(ch) && !Character.isSpaceChar(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * 去除字符串中的左边的非可见字符，包括半角/全角空格、回车、换行符号
     *
     * @param str
     * @return
     * @author hexiufeng
     * @created 2012-03-16
     */
    public static final String ltrimWithWideSpace(String str) {
        return rawTrimWithWideSpace(str, true, false);
    }

    /**
     * 去除字符串中的右边的非可见字符，包括半角/全角空格、回车、换行符号
     *
     * @param str
     * @return
     * @author hexiufeng
     * @created 2012-03-16
     */
    public static final String rtrimWithWideSpace(String str) {
        return rawTrimWithWideSpace(str, false, true);
    }

    /**
     * 去除字符串中左边和右边的非可见字符，包括半角/全角空格、回车、换行符号
     *
     * @param str
     * @return
     * @author hexiufeng
     * @created 2012-03-16
     */
    public static final String trimWithWideSpace(String str) {
        return rawTrimWithWideSpace(str, true, true);
    }

    /**
     * 去除字符串中的非可见字符，包括半角/全角空格、回车、换行符号
     *
     * @param str
     * @param left，消除左边空格
     * @param right，消除右边空格
     * @return
     * @author hexiufeng
     * @created 2012-03-16
     */
    public static final String rawTrimWithWideSpace(String str, boolean left, boolean right) {
        if (str == null)
            return "";
        // 转换为字符数组循环判断左边后者右边的字符是否是space
        char[] chArray = str.toCharArray();

        int first = -1, last = -1;
        if (!left) {
            first = 0;
        } else {
            for (int i = 0; i < chArray.length; i++) {
                char ch = chArray[i];
                if (!Character.isWhitespace(ch) && !Character.isSpaceChar(ch)) {
                    first = i;
                    break;
                }
            }
        }

        if (!right) {
            last = chArray.length - 1;
        } else {
            for (int i = chArray.length - 1; i >= 0; i--) {
                char ch = chArray[i];
                if (!Character.isWhitespace(ch) && !Character.isSpaceChar(ch)) {
                    last = i;
                    break;
                }
            }
        }

        if (first == -1 || last == -1 || last < first)
            return "";

        return String.valueOf(chArray, first, last - first + 1);
    }

    /**
     * 如果字符串为null,则返回"",否则,返回字符串.trim()
     *
     * @param str
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final String null2Trim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * 字符串转成InputStream
     *
     * @param str
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final InputStream str2InputStream(String str) {
        if (!null2Trim(str).equals("")) {
            ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
            return stream;
        }
        return null;
    }

    /**
     * 冒泡法排序字符串
     *
     * @param strArray
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final String[] sort(String[] strArray) {
        if (strArray == null)
            return null;
        String tmp = "";
        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < strArray.length - i - 1; j++) {
                if (strArray[j].compareTo(strArray[j + 1]) < 0) {
                    tmp = strArray[j];
                    strArray[j] = strArray[j + 1];
                    strArray[j + 1] = tmp;
                }
            }
        }
        return strArray;
    }

    /**
     * ISO转换成GBK
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final String iso2gbk(String str) throws UnsupportedEncodingException {
        return new String(str.getBytes("iso-8859-1"), "GBK");
    }

    /**
     * GBK转换成ISO
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final String gbk2Iso(String str) throws UnsupportedEncodingException {
        return new String(str.getBytes("GBK"), "iso-8859-1");
    }

    /**
     * GBK转换成UTF-8
     *
     * @param str
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final String gbk2Utf8(String str) {
        try {
            return new String(str.getBytes("GBK"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * UTF-8转换成GBK
     *
     * @param str
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final String utf82Gbk(String str) {
        try {
            return new String(str.getBytes("UTF-8"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 正则表达式验证,查询(大小写敏感)
     *
     * @param str
     * @param regx 正则表达式
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final boolean regexMatch(String str, String regx) {
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 正则表达式验证,查询(大小写不敏感)
     *
     * @param str
     * @param regx 正则表达式
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final boolean regexMatchInsensitive(String str, String regx) {
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 全部替换字符串内的匹配字符串.大小写敏感
     *
     * @param str
     * @param regx       正则表达式
     * @param replaceStr 要替换的结果
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final String regexReplaceAll(String str, String regx, String replaceStr) {
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(replaceStr);
    }

    /**
     * 从开始截取字符串.汉字算两个字节.不把汉字从中间截开
     *
     * @param symbol
     * @param len    长度
     * @return
     * @throws UnsupportedEncodingException
     * @author zhaolei
     * @created 2011-4-20
     */
    public static final String subStringFromStartByByteNum(String symbol, int len)
            throws UnsupportedEncodingException {
        int counterOfDoubleByte;
        byte b[];
        counterOfDoubleByte = 0;
        b = symbol.getBytes("GBK");
        if (b.length <= len)
            return symbol;
        for (int i = 0; i < len; i++) {
            if (b[i] < 0)
                counterOfDoubleByte++;
        }
        if (counterOfDoubleByte % 2 == 0)
            return new String(b, 0, len, "GBK");
        else
            return new String(b, 0, len - 1, "GBK");

    }

    /**
     * 判断传入字符串是否为数字
     *
     * @param str
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 过滤掉特殊HTML标签
     *
     * @param str
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static String subNormString(String str) {
        if (str == null)
            return "";
        return str.replaceAll("<[.[^<]]*>", "").replace("<", "");
    }

    /**
     * 扩展String的substring方法。过滤掉特殊HTML标签
     *
     * @param str
     * @param beginIndex 开始位置
     * @param endIndex   结束位置
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static String subNormString(String str, int beginIndex, int endIndex) {
        if (str == null)
            return "";
        str = str.replaceAll("<[.[^<]]*>", "");
        int length = str.length();
        endIndex = (endIndex < 0) ? 0 : endIndex;
        endIndex = (endIndex > length) ? length : endIndex;
        beginIndex = (beginIndex < 0) ? 0 : beginIndex;
        beginIndex = (beginIndex > endIndex) ? endIndex : beginIndex;
        str = str.substring(beginIndex, endIndex);
        return str.replace("<", "");
    }

    /**
     * 过滤null
     *
     * @param str
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static String replaceNull(String str) {
        return (str == null) ? "" : str;
    }

    public static String replaceNullByDefault(String str,String defaultValue) {
        return (str == null || "".equals(str.trim())) ? defaultValue : str;
    }

    /**
     * 将null替换成想要的值
     *
     * @param str
     * @param defaultStr
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static String replaceNull(String str, String defaultStr) {
        return (str == null) ? defaultStr : str;
    }

    /**
     * 截断字符串
     *
     * @param str
     * @param beginIndex 开始位置
     * @param endIndex   结束位置
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static String substring(String str, int beginIndex, int endIndex) {
        if (str == null)
            return "";
        int length = str.length();
        endIndex = (endIndex < 0) ? 0 : endIndex;
        endIndex = (endIndex > length) ? length : endIndex;
        beginIndex = (beginIndex < 0) ? 0 : beginIndex;
        beginIndex = (beginIndex > endIndex) ? endIndex : beginIndex;
        str = str.substring(beginIndex, endIndex);
        return str;
    }

    /**
     * 返回转换的int值。
     *
     * @param o
     * @param defaultint
     * @return
     * @author zhaolei
     * @created 2011-4-20
     */
    public static int parseInt(Object o, int defaultint) {
        try {
            return Integer.parseInt(String.valueOf(o));
        } catch (NumberFormatException e) {
            return defaultint;
        }
    }

    /**
     * 检查字符串是否有内容。
     *
     * @param obj
     * @return
     * @author lichengwu@sankuai.com
     * @created 2011-5-5
     */
    public static boolean isBlank(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof String) {
            String str = (String) obj;
            return str == null ? true : "".equals(str.trim());
        }
        try {
            String str = String.valueOf(obj);
            return str == null ? true : "".equals(str.trim());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 检查字符串是否有内容。
     *
     * @param obj
     * @return
     * @author lichengwu@sankuai.com
     * @created 2011-5-5
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 检查参数是否有效字符串是否有内容，数字是否为非0
     *
     * @param obj
     * @return
     * @author liuhujun@meituan.com
     * @created 2012-3-2
     */
    public static boolean isValid(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof String) {
            String str = (String) obj;
            return !"".equals(str.trim());
        }
        if (obj instanceof Integer) {
            Integer i = (Integer) obj;
            return !i.equals(0);
        }
        try {
            String str = String.valueOf(obj);
            return !"".equals(str.trim());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查参数是否有效字符串是否有内容，数字是否为非0
     *
     * @param obj
     * @return
     * @author liuhujun@meituan.com
     * @created 2012-3-2
     */
    public static boolean isInvalid(Object obj) {
        return !isValid(obj);
    }

    /**
     * 将空白字符串替换成指的字符串
     *
     * @param dest
     * @param str
     * @return
     * @author lichengwu
     * @created 2011-7-11
     */
    public static String changeNull(String dest, String str) {
        if (!isBlank(dest)) {
            return dest;
        }
        return str;
    }

    /**
     * 将空白字符串替换成指的字符串
     *
     * @param dest
     * @param str
     * @return
     * @author lichengwu
     * @created 2011-9-6
     */
    public static String changeNull(Object dest, String str) {
        if (!isBlank(dest)) {
            return String.valueOf(dest);
        }
        return str;
    }


    /**
     * 对特殊字符转移处理。
     * 处理字符：{code}\s、\n、‘、“、<、>、&{code}
     *
     * @param in
     * @return in 为null时直接返回null
     */
    public static String escapeString(String in) {
        if (in == null) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; in != null && i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '\n') {
                out.append("<br/>");
            } else if (c == '\'') {
                out.append("&#039;");
            } else if (c == '\"') {
                out.append("&#034;");
            } else if (c == '<') {
                out.append("&lt;");
            } else if (c == '>') {
                out.append("&gt;");
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    /**
     * 对已转移的字符串进行还原。
     * 处理字符：{code}\s、\n、‘、“、<、>、&{code}
     *
     * @param in
     * @return in 为null时直接返回null
     */
    public static String unescapeString(String in) {
        if (in == null) {
            return null;
        }
        return in.replace("<br/>", "\n")
                .replace("&nbsp;", " ")
                .replace("&#039;", "\'")
                .replace("&#034;", "\"")
                .replace("&lt;", "<")
                .replace("&gt;", ">");
    }

    /**
     * 把List组装为sql in语法的String
     *
     * @param list
     * @return
     */
    public static String list2SqlString(List<?> list) {
        if (list.size() == 0) {
            return "";
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0, n = list.size(); i < n; i++) {
            Object obj = list.get(i);
            if (obj instanceof Integer) {
                out.append(obj + ",");
            } else if (obj instanceof String) {
                out.append("'" + obj + "',");
            } else {
                out.append("'" + obj.toString() + "',");
            }
        }
        return out.substring(0, out.length() - 1);
    }

    /**
     * 把list转为delimit分隔的字符串
     *
     * @param list
     * @param delimit
     * @return
     */
    public static String list2String(List<?> list, String delimit) {
        if (list.size() == 0) {
            return "";
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0, n = list.size(); i < n; i++) {
            Object obj = list.get(i);

            out.append(obj.toString() + delimit);
        }
        return out.substring(0, out.length() - delimit.length());
    }

    /**
     * <pre>
     * 将obj转化成String
     * String.valueOf 方法会将null 转换成 "null"
     * 如果对象为null时希望返回null,可以用这个方法
     * </pre>
     *
     * @param obj
     * @return
     * @author lichengwu
     * @created 2011-11-10
     */
    public static String valueOf(Object obj) {
        return obj == null ? null : String.valueOf(obj);
    }


    private static final Pattern PATTERN = Pattern.compile("^([a-zA-Z0-9_-]|\\.)+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$") ;

}
