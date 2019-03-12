/**
 * Copyright 2019 覃海林(qinhaisenlin@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.qinhailin.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 字符串工具类
 * @author SAMONHUA
 *
 */
@SuppressWarnings("deprecation")
public class StrUtils {
	/**
	 * 判断字符串是否为空（包括null、""、"null"）
	 * @param s 需检查的字符串
	 * @return
	 */
	public static boolean isNull(String s)
    {
		return s == null || s.equals("") || s.equalsIgnoreCase("null") || s.equalsIgnoreCase("undefined");
    }
	
	public static boolean isNotNull(String s)
    {
		return !isNull(s);
    }
	
	public static String trim(String s)
    {
		if (s == null)
			return s;
		else
			return s.trim();
    }

/*
"^\d+$" //非负整数（正整数 + 0） 
"^[0-9]*[1-9][0-9]*$" //正整数 
"^((-\d+)|(0+))$" //非正整数（负整数 + 0） 
"^-[0-9]*[1-9][0-9]*$" //负整数 
"^-?\d+$" //整数 
"^\d+(\.\d+)?$" //非负浮点数（正浮点数 + 0） 
"^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$" //正浮点数 
"^((-\d+(\.\d+)?)|(0+(\.0+)?))$" //非正浮点数（负浮点数 + 0） 
"^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$" //负浮点数 
"^(-?\d+)(\.\d+)?$" //浮点数 
"^[A-Za-z]+$" //由26个英文字母组成的字符串 
"^[A-Z]+$" //由26个英文字母的大写组成的字符串 
"^[a-z]+$" //由26个英文字母的小写组成的字符串 
"^[A-Za-z0-9]+$" //由数字和26个英文字母组成的字符串 
"^\w+$" //由数字、26个英文字母或者下划线组成的字符串 
"^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$" //email地址 
"^[a-zA-z]+://(\w+(-\w+)*)(\.(\w+(-\w+)*))*(\?\S*)?$" //url 
/^(d{2}|d{4})-((0([1-9]{1}))|(1[1|2]))-(([0-2]([1-9]{1}))|(3[0|1]))$/ // 年-月-日 
/^((0([1-9]{1}))|(1[1|2]))/(([0-2]([1-9]{1}))|(3[0|1]))/(d{2}|d{4})$/ // 月/日/年 
"^([w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$" //Emil 
"(d+-)?(d{4}-?d{7}|d{3}-?d{8}|^d{7,8})(-d+)?" //电话号码 
"^(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5])$" //IP地址 

YYYY-MM-DD基本上把闰年和2月等的情况都考虑进去了 
^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$
 */

	/**
	 * 判断字符串是否是数字(正负整数、浮点数、0)
	 * @param s 需检查的字符串
	 * @return true数字
	 */
	public static boolean isNumber(String s)
	{
		if (StrUtils.isNull(s))
		{
			return false;
		}
		if (s.matches("^(-?\\d+)(\\.\\d+)?$"))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否是正负整数、0
	 * @param s 需检查的字符串
	 * @return true数字
	 */
	public static boolean isInteger(String s)
	{
		 if(isNotNull(s))
			 return s.matches("^-?\\d+$");
		 else
			 return false;
	}
	
	/**
	 * 判断字符串是否是合法日期
	 * @param s 需检查的字符串。格式：yyyy-MM-dd
	 * @return true日期
	 */
	public static boolean isDate(String s)
	{
		 if(isNotNull(s))
			 return s.matches("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");
		 else
			 return false;
	}
	
	/**
	 * 判断字符串是否是合法日期(含时间)
	 * @param s 需检查的字符串。格式：yyyy-MM-dd HH:mm:ss
	 * @return true日期
	 */
	public static boolean isDateTime(String s)
	{
		 if(isNotNull(s))
			 return s.matches("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$");
			 //return s.matches("^(?ni:(?=\\d)((?'year'((1[6-9])|([2-9]\\d))\\d\\d)(?'sep'[/.-])(?'month'0?[1-9]|1[012])\\2(?'day'((?<!(\\2((0?[2469])|11)\\2))31)|(?<!\\2(0?2)\\2)(29|30)|((?<=((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|(16|[2468][048]|[3579][26])00)\\2\\3\\2)29)|((0?[1-9])|(1\\d)|(2[0-8])))(?:(?=\\x20\\d)\\x20|$))?((?<time>((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2}))?)$");
		 else
			 return false;
	}
	
	/**
	 * 判断字符串是否是电话号码(11位手机号码，3-4位区号，7-8位直播号码，1-4位分机号)
	 * @param s 需检查的字符串
	 * @return
	 */
	public static boolean isPhoneNo(String s)
	{
		 if(isNotNull(s))
			 return s.matches("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)");
		 else
			 return false;
	}
	
	/**
	 * 判断字符串是否是手机号码
	 * @param s 需检查的字符串
	 * @return
	 */
	public static boolean isMobilePhoneNo(String s)
	{
		 if(isNotNull(s))
			 return s.matches("^13\\d{9}$");
		 else
			 return false;
	}
	
	/**
	 * 判断字符串是否是邮箱地址
	 * @param s 需检查的字符串
	 * @return
	 */
	public static boolean isEMail(String s)
	{
		 if(isNotNull(s))
			 return s.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		 else
			 return false;
	}
	
	/**
	 * 判断字符串是否是网址
	 * @param s 需检查的字符串
	 * @return
	 */
	public static boolean isURL(String s)
	{
		 if(isNotNull(s))
			 return s.matches("^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$");
		 else
			 return false;
	}
	
	/**
	 * 判断字符串是否是IP地址
	 * @param s 需检查的字符串
	 * @return
	 */
	public static boolean isIP(String s)
	{
		 if(isNotNull(s))
			 return s.matches("^(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5])$");
		 else
			 return false;
	}
	
	/**
	 * 判断字符串是否是有效变量名称
	 * @param s 需检查的字符串
	 * @return
	 */
	public static boolean isVariableName(String s)
	{
		 if(isNotNull(s))
			 return s.matches("^([a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*)");
		 else
			 return false;
	}
	
	/** 
     * 判断两个字符是否相等。 
     * @param c1 字符1 
     * @param c2 字符2 
     * @return 若是英文字母，不区分大小写，相等true，不等返回false；<br/> 
     *         若不是则区分，相等返回true，不等返回false。 
     */  
    private static boolean isEqualsIgnoreCase(char c1, char c2){
    	//字母小写                   字母大写
        if (((97 <= c1 && c1 <= 122) || (65 <= c1 && c1 <= 90))
        		&& ((97 <= c2 && c2 <= 122) || (65 <= c2 && c2 <= 90))
        		&& ((c1-c2 == 32) || (c2-c1 == 32))) {
        	return true;
        } else if (c1 == c2) {
        	return true;
        }
        return false;
    }
	
	/** 
     * 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始，不区分大小。 
     *  
     * @param parent 父字符串。 
     * @param child 要查找的子字符串。 
     * @return 指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。 
     */  
    public static int indexOfIgnoreCase(String parent, String child) {
        return indexOfIgnoreCase(parent, child, -1);
    }
      
    /** 
     * 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始，不区分大小。 
     *  
     * @param parent 被查找字符串。 
     * @param child 要查找的子字符串。 
     * @param fromIndex 开始查找的索引位置。其值没有限制，如果它为负，则与它为 0 的效果同样：将查找整个字符串。 
     *          如果它大于此字符串的长度，则与它等于此字符串长度的效果相同：返回 -1。 
     * @return 指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。 
     */ 
	public static int indexOfIgnoreCase(String parent, String child,  
            int fromIndex) {
        if (parent == null || child == null) {
            return -1;//throw new NullPointerException("输入的字符串为空");
        }
        fromIndex = fromIndex < 0 ? 0 : fromIndex;
        if (child.equals("")) {
            return fromIndex >= parent.length() ? parent.length() : fromIndex;
        }
        int index1 = fromIndex;
        int index2 = 0;
        char c1;
        char c2;
        loop1: while (true) {
            if (index1 < parent.length()) {
                c1 = parent.charAt(index1);
                c2 = child.charAt(index2);
            } else {
                break loop1;
            }
            while (true) {
                if (isEqualsIgnoreCase(c1, c2)) {
                    if (index1 < parent.length() - 1  
                            && index2 < child.length() - 1) {
                        c1 = parent.charAt(++index1);
                        c2 = child.charAt(++index2);
                    } else if (index2 == child.length() - 1) {
                        return fromIndex;
                    } else {
                        break loop1;
                    }
                } else {
                    index2 = 0;
                    break;
                }
            }
            //重新查找子字符串的位置  
            index1 = ++fromIndex;
        }
        return -1;
    }
      
    /** 
     * 返回指定子字符串在此字符串中最右边出现处的索引。 
     *  
     * @param parent 父字符串。  
     * @param child 要查找的子字符。 
     * @return 在此对象表示的字符序列中最后一次出现该字符的索引；如果在该点之前未出现该字符，则返回 -1 
     */  
    public static int lastIndexOfIgnoreCase(String parent, String child){
        if(parent == null){
        	return -1;//throw new NullPointerException("输入的参数为空");
        } else {
            return lastIndexOfIgnoreCase(parent, child, parent.length());
        }
    }
      
    /** 
     * 返回指定字符在此字符串中最后一次出现处的索引，从指定的索引处开始进行反向查找。 
     * @param parent 被查找字符串 。 
     * @param child 要查找的子字符串。 
     * @param fromIndex 开始查找的索引。fromIndex 的值没有限制。如果它大于等于此字符串的长度，则与它小于此字符串长度减 1 的效果相同：将查找整个字符串。 
     *          如果它为负，则与它为 -1 的效果相同：返回 -1。  
     * @return 在此对象表示的字符序列（小于等于 fromIndex）中最后一次出现该字符的索引； 
     *          如果在该点之前未出现该字符，则返回 -1 
     */  
    public static int lastIndexOfIgnoreCase(String parent, String child,  
            int fromIndex) {
        //当被查找字符串或查找子字符串为空时，抛出空指针异常。  
        if (parent == null || child == null) {
            throw new NullPointerException("输入的参数为空");
        }
        if (child.equals("")) {
            return fromIndex >= parent.length() ? parent.length() : fromIndex;
        }
        fromIndex = fromIndex >= parent.length() ? parent.length() - 1 : fromIndex;
  
        int index1 = fromIndex;
        int index2 = 0;
  
        char c1;
        char c2;
  
        loop1: while (true) {
            if (index1 >= 0) {
                c1 = parent.charAt(index1);
                c2 = child.charAt(index2);
            } else {
                break loop1;
            }
  
            while (true) {
                //判断两个字符是否相等  
                if (isEqualsIgnoreCase(c1, c2)) {
                    if (index1 < parent.length() - 1  
                            && index2 < child.length() - 1) {
                        c1 = parent.charAt(++index1);
                        c2 = child.charAt(++index2);
                    } else if (index2 == child.length() - 1) {
                        return fromIndex;
                    } else {
                        break loop1;
                    }
                } else {
                    //在比较时，发现查找子字符串中某个字符不匹配，则重新开始查找子字符串  
                    index2 = 0;
                    break;
                }
            }
            //重新查找子字符串的位置  
            index1 = --fromIndex;
        }
        return -1;
    }

	/**
	 * 转换父字符串中的参数名、参数值键值对对象 
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param ignoreCase true参数名忽略大小写，忽略大小写后返回的参数名都为小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @param split 键值对之间的分隔符，传空则使用“;”。分隔符大小写必须一致，即不受ignoreCase参数影响
	 * @return 键值对MAP
	 */
	public static Map<String, String> getParams(String parent, boolean ignoreCase, String equals, String split) {
		if (isNull(parent)) {
	    	return null;
	    }
	    String strEquals = isNull(equals) ? "=" : equals;
	    String strSplit = isNull(split) ? "=" : split;
	    Map<String, String> params = new HashMap<String, String>();
	    /*
	    //split方法使用的是正则表达式，故部分特殊字符不能正常工作，必须使用通配符
	    String[] aryParams = parent.split(strSplit);
	    for(String strParam: aryParams) {
	        if (ignoreCase)
	        	params.put(strParam.substring(0, strParam.indexOf(strEquals)).toLowerCase(), strParam.substring(strParam.indexOf(strEquals) + strEquals.length(), strParam.length()));
	        else
	        	params.put(strParam.substring(0, strParam.indexOf(strEquals)), strParam.substring(strParam.indexOf(strEquals) + strEquals.length(), strParam.length()));
	    }
	    */
	    //aaa###1$$$$cCc###22$$$$ddd###333$$$$eee###4444$$$$
	    String strParent = parent + strSplit;
	    int intPos;
	    int intStart = 0;
	    do {
	    	intPos = strParent.indexOf(strSplit, intStart);
	    	if (intPos >= 0) {
	    		String strParam = strParent.substring(intStart, intPos);
	    		if (!isNull(strParam)) {
	    			int intEqualsPos = strParam.indexOf(strEquals);
	    			String strParamName = intEqualsPos >= 0 ? strParam.substring(0, intEqualsPos) : strParam;
	    			String strParamValue = intEqualsPos >= 0 ? strParam.substring(intEqualsPos + strEquals.length(), strParam.length()) : "";
	    			params.put(ignoreCase ? strParamName.toLowerCase() : strParamName, strParamValue);
	    		}
	    	}
	    	intStart = intPos + strSplit.length();
	    } while(intPos >= 0);
	    return params;
	}
	
	/**
	 * 转换父字符串中的参数名、参数值键值对对象 ，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param ignoreCase true参数名忽略大小写，忽略大小写后返回的参数名都为小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @return 键值对MAP
	 */
	public static Map<String, String> getParams(String parent, boolean ignoreCase, String equals) {
		return getParams(parent, ignoreCase, equals, ";");
	}
	
	/**
	 * 转换父字符串中的参数名、参数值键值对对象 ，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param ignoreCase true参数名忽略大小写，忽略大小写后返回的参数名都为小写
	 * @return 键值对MAP
	 */
	public static Map<String, String> getParams(String parent, boolean ignoreCase) {
		return getParams(parent, ignoreCase, "=");
	}
	
	/**
	 * 转换父字符串中的参数名、参数值键值对对象，参数名区分大小写 ，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @return 键值对MAP
	 */
	public static Map<String, String> getParams(String parent) {
		return getParams(parent, false);
	}
	
	/**
	 * 检查父字符串中某参数是否存在
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名
	 * @param ignoreCase 是否忽略大小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @param split 键值对之间的分隔符，传空则使用“;”。分隔符大小写必须一致，即不受ignoreCase参数影响
	 * @return 存在返回true
	 */
	public static boolean paramExists(String parent, String paramName, boolean ignoreCase, String equals, String split) {
		if (isNull(parent) || isNull(paramName)) {
	    	return false;
	    }
	    Map<String, String> params = getParams(parent, ignoreCase, equals, split);
		return ignoreCase ? params.containsKey(paramName.toLowerCase()) : params.containsKey(paramName);
	}
	
	/**
	 * 检查父字符串中某参数是否存在，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串，
	 * @param paramName 参数名
	 * @param ignoreCase 是否忽略大小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @return 存在返回true
	 */
	public static boolean paramExists(String parent, String paramName, boolean ignoreCase, String equals) {
		return paramExists(parent, paramName, ignoreCase, equals, ";");
	}
	
	/**
	 * 检查父字符串中某参数是否存在，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串，
	 * @param paramName 参数名
	 * @param ignoreCase 是否忽略大小写
	 * @return 存在返回true
	 */
	public static boolean paramExists(String parent, String paramName, boolean ignoreCase) {
		return paramExists(parent, paramName, ignoreCase, "=");
	}
	
	/**
	 * 检查父字符串中某参数是否存在，参数名区分大小写，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串，
	 * @param paramName 参数名
	 * @return 存在返回true
	 */
	public static boolean paramExists(String parent, String paramName) {
		return paramExists(parent, paramName, false);
	}

	/**
	 * 获取父字符串中的指定参数名的参数值
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名。上例传入paramName="xyz"返回"666"。不存在返回""，非null
	 * @param ignoreCase 是否忽略大小写。注意如果忽略大小写(true)将把所有的key转换为小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @param split 键值对之间的分隔符，传空则使用“;”。分隔符大小写必须一致，即不受ignoreCase参数影响
	 * @return 参数值
	 */
	public static String getParamValue(String parent, String paramName, boolean ignoreCase, String equals, String split) {
		if (isNull(parent) || isNull(paramName)) {
	    	return "";
	    }
	    Map<String, String> params = getParams(parent, ignoreCase, equals, split);
	    String result = "";
	    if (ignoreCase) {
	    	if (params.containsKey(paramName.toLowerCase()))
	    		result = String.valueOf(params.get(paramName.toLowerCase()));
	    } else {
	    	if (params.containsKey(paramName))
	    		result = String.valueOf(params.get(paramName));
	    }
		return result;
	}
	
	/**
	 * 获取父字符串中的指定参数名的参数值，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名。上例传入paramName="xyz"返回"666"。不存在返回""，非null
	 * @param ignoreCase 是否忽略大小写。注意如果忽略大小写(true)将把所有的key转换为小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @return 参数值
	 */
	public static String getParamValue(String parent, String paramName, boolean ignoreCase, String equals) {
		return getParamValue(parent, paramName, ignoreCase, equals, ";");
	}
	
	/**
	 * 获取父字符串中的指定参数名的参数值，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名。上例传入paramName="xyz"返回"666"。不存在返回""，非null
	 * @param ignoreCase 是否忽略大小写。注意如果忽略大小写(true)将把所有的key转换为小写
	 * @return 参数值
	 */
	public static String getParamValue(String parent, String paramName, boolean ignoreCase) {
		return getParamValue(parent, paramName, ignoreCase, "=");
	}
	
	/**
	 * 获取父字符串中的指定参数名的参数值，参数名区分大小写，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名。上例传入paramName="xyz"返回"666"。不存在返回""，非null
	 * @return 参数值
	 */
	public static String getParamValue(String parent, String paramName) {
		return getParamValue(parent, paramName, false);
	}

	/**
	 * 写入父字符串中的指定参数名的参数值
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名，不存在将添加
	 * @param paramValue 参数值
	 * @param ignoreCase 是否忽略大小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @param split 键值对之间的分隔符，传空则使用“;”。分隔符大小写必须一致，即不受ignoreCase参数影响
	 * @return 返回写入参数的新串，原串不修改。如写入"abc=123;xyz=666;mnq=888"字符串中的某项的值,如paramName="xyz",paramValue="XXXX"返回"abc=123;xyz=XXXX;mnq=888"
	 */
	public static String setParamValue(String parent, String paramName, String paramValue, boolean ignoreCase, String equals, String split) {
		if (isNull(paramName)) {
	    	return parent;
	    }
		String strEquals = isNull(equals) ? "=" : equals;
	    String strSplit = isNull(split) ? "=" : split;
	    if (isNull(parent))
	    	return paramName + strEquals + paramValue;
	    String strParent = strSplit + parent + strSplit;
	    int intPos = ignoreCase ? indexOfIgnoreCase(strParent, strSplit + paramName + strEquals) : strParent.indexOf(strSplit + paramName + strEquals);
	    String result;
	    if (intPos >= 0) {
	    	String leftStr = parent.substring(0, intPos + paramName.length() + strEquals.length());
	    	String rightStr = parent.substring(intPos + paramName.length() + strEquals.length(), parent.length());
	    	if (rightStr.indexOf(strSplit) >= 0)
	    		rightStr = rightStr.substring(rightStr.indexOf(strSplit), rightStr.length());
	    	else
	    		rightStr = "";
	        result = leftStr + paramValue + rightStr;
	    } else {
	    	result = parent + strSplit + paramName + strEquals + paramValue;
	    }
	    return result;
	}
	
	/**
	 * 写入父字符串中的指定参数名的参数值，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名，不存在将添加
	 * @param paramValue 参数值
	 * @param ignoreCase 是否忽略大小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @return 返回写入参数的新串，原串不修改。如写入"abc=123;xyz=666;mnq=888"字符串中的某项的值,如paramName="xyz",paramValue="XXXX"返回"abc=123;xyz=XXXX;mnq=888"
	 */
	public static String setParamValue(String parent, String paramName, String paramValue, boolean ignoreCase, String equals) {
		return setParamValue(parent, paramName, paramValue, ignoreCase, equals, ";");
	}
	
	/**
	 * 写入父字符串中的指定参数名的参数值，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名，不存在将添加
	 * @param paramValue 参数值
	 * @param ignoreCase 是否忽略大小写
	 * @return 返回写入参数的新串，原串不修改。如写入"abc=123;xyz=666;mnq=888"字符串中的某项的值,如paramName="xyz",paramValue="XXXX"返回"abc=123;xyz=XXXX;mnq=888"
	 */
	public static String setParamValue(String parent, String paramName, String paramValue, boolean ignoreCase) {
		return setParamValue(parent, paramName, paramValue, ignoreCase, "=");
	}
	
	/**
	 * 写入父字符串中的指定参数名的参数值，参数名区分大小写，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父字符串。如"abc=123;xyz=666;mnq=888"字符串
	 * @param paramName 参数名，不存在将添加
	 * @param paramValue 参数值
	 * @return 返回写入参数的新串，原串不修改。如写入"abc=123;xyz=666;mnq=888"字符串中的某项的值,如paramName="xyz",paramValue="XXXX"返回"abc=123;xyz=XXXX;mnq=888"
	 */
	public static String setParamValue(String parent, String paramName, String paramValue) {
		return setParamValue(parent, paramName, paramValue, false);
	}
	
	/**
	 * 在父串中删除参数
	 * @param parent 父串
	 * @param paramName 参数名称
	 * @param ignoreCase true参数名忽略大小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @param split 键值对之间的分隔符，传空则使用“;”。分隔符大小写必须一致，即不受ignoreCase参数影响
	 * @return 返回删除参数后的串，不会修改原串
	 */
	public static String deleteParam(String parent, String paramName, boolean ignoreCase, String equals, String split) {
		if (isNull(parent) || isNull(paramName)) {
	    	return parent;
	    }
		String strEquals = isNull(equals) ? "=" : equals;
	    String strSplit = isNull(split) ? "=" : split;
	    String strParent = strSplit + parent + strSplit;
	    int intPos = ignoreCase ? indexOfIgnoreCase(strParent, strSplit + paramName + strEquals) : strParent.indexOf(strSplit + paramName + strEquals);
	    if (intPos >= 0) {
	    	String leftStr = parent.substring(0, intPos);
	    	String rightStr = parent.substring(intPos + paramName.length() + strEquals.length(), parent.length());
	    	rightStr = rightStr.indexOf(strSplit) >= 0 ? rightStr.substring(rightStr.indexOf(strSplit) + strSplit.length(), rightStr.length()) : "";
	        return leftStr + rightStr;
	    } else {
	    	return parent;
	    }
	}
	
	/**
	 * 在父串中删除参数，参数名区分大小写，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父串
	 * @param paramName 参数名称
	 * @param ignoreCase true参数名忽略大小写
	 * @param equals 键与值之间的等于字符串，传空则使用“=”。等于符大小写必须一致，即不受ignoreCase参数影响
	 * @return 返回删除参数后的串，不会修改原串
	 */
	public static String deleteParam(String parent, String paramName, boolean ignoreCase, String equals) {
		return deleteParam(parent, paramName, ignoreCase, equals, ";");
	}
	
	/**
	 * 在父串中删除参数，参数名区分大小写，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父串
	 * @param paramName 参数名称
	 * @param ignoreCase true参数名忽略大小写
	 * @return 返回删除参数后的串，不会修改原串
	 */
	public static String deleteParam(String parent, String paramName, boolean ignoreCase) {
		return deleteParam(parent, paramName, ignoreCase, "=");
	}
	
	/**
	 * 在父串中删除参数，参数名区分大小写，参数值等于符为“=”，分隔符为“;”
	 * @param parent 父串
	 * @param paramName 参数名称
	 * @return 返回删除参数后的串，不会修改原串
	 */
	public static String deleteParam(String parent, String paramName) {
		return deleteParam(parent, paramName, false);
	}
	
	/**
	 * 得到左边的字符串
	 * @param parent 父串
	 * @param leftLength 左侧子串长度
	 * @return 返回左起子字符串
	 */
	public static String getLeftString(String parent, int leftLength){
		if(isNull(parent)){
			return "";
		}else{
			if(parent.length() <= leftLength){
				return parent;
			}else{
				return parent.substring(0, leftLength);
			}
		}
	}
	
	/**
	 * 得到右边的字符串
	 * @param parent 父串
	 * @param rightLength 右侧子串长度
	 * @return 返回右起子字符串
	 */
	public static String getRightString(String parent, int rightLength){
		if(isNull(parent)){
			return "";
		}else{
			if(parent.length() <= rightLength){
				return parent;
			}else{
				return parent.substring(parent.length() - rightLength, parent.length());
			}
		}
	}
	
	/**
	 * 字符串转换为整数
	 * @param s 字符串
	 * @param defaultValue 非法字符时缺省值
	 * @return 转换后的整数
	 */
	public static int strToInt(String s, int defaultValue) {
		if (isInteger(s))
			return Integer.parseInt(s);
		else
			return defaultValue;
	}
	
	/**
	 * 字符串转换为浮点数
	 * @param s 字符串
	 * @param defaultValue 非法字符时缺省值
	 * @return 转换后的浮点数
	 */
	public static float strToFloat(String s, float defaultValue) {
		if (isNumber(s))
			return Float.parseFloat(s);
		else
			return defaultValue;
	}
	
	/**
	 * 字符串转换为日期
	 * @param s 字符串
	 * @param defaultValue 非法字符时缺省值
	 * @return 转换后的日期
	 */
	public static Date strToDate(String s, Date defaultValue) {
		if (isDate(s))
			return new Date(s);
		else
			return defaultValue;
	}
	
	/**
	 * 子字符串出现次数
	 * @param parent 父串
	 * @param child 子串
	 * @param ignoreCase true子串(child)忽略大小写
	 * @return 子串出现次数
	 */
	public static int getSubStrCount(String parent, String child, boolean ignoreCase) {
		int count = 0;
		int start = 0;
		if (ignoreCase) {
			while (indexOfIgnoreCase(parent, child, start) >= 0 && start < parent.length()) {
				count++;
				start = indexOfIgnoreCase(parent, child, start) + child.length();
			}
		} else {
			while (parent.indexOf(child, start) >= 0 && start < parent.length()) {
				count++;
				start = parent.indexOf(child, start) + child.length();
			}
		}
		return count;
	}
	
	/**
	 * 子字符串出现次数，子串区分大小写
	 * @param parent 父串
	 * @param child 子串
	 * @return 子串出现次数
	 */
	public static int getSubStrCount(String parent, String child) {
		return getSubStrCount(parent, child, false);
	}
	
	/**
	 * 获取子字符串List
	 * @param parent 父串
	 * @param split 分隔字符串
	 * @param ignoreCase true分隔字符串(split)忽略大小写
	 * @param distinct true返回唯一的列表，去除重复的项
	 * @return 子字符串List
	 */
	public static List<String> getSubStrs(String parent, String split, boolean ignoreCase, boolean distinct) {
		if (isNull(parent)) {
	    	return null;
	    }
		//split方法使用的是正则表达式，故部分特殊字符不能正常工作，必须使用通配符
		List<String> result = new ArrayList<String>();
		if (isNull(split)) {
			result.add(parent);
	    	return result;
	    }
	    //aaa###bbb###ccc###ddd###eee
	    String strParent = parent + split;
	    int intPos;
	    int intStart = 0;
	    do {
	    	intPos = ignoreCase ? indexOfIgnoreCase(strParent, split, intStart) : strParent.indexOf(split, intStart);
	    	if (intPos >= 0) {
	    		String strSub = strParent.substring(intStart, intPos);
	    		if (!distinct || result.indexOf(strSub) == -1)
	    			result.add(strSub);
	    	}
	    	intStart = intPos + split.length();
	    } while(intPos >= 0);
	    return result;
	}
	
	/**
	 * 获取子字符串List
	 * @param parent 父串
	 * @param split 分隔字符串
	 * @param ignoreCase true分隔字符串(split)忽略大小写
	 * @return 子字符串List
	 */
	public static List<String> getSubStrs(String parent, String split, boolean ignoreCase) {
		return getSubStrs(parent, split, ignoreCase, false);
	}
	
	/**
	 * 获取子字符串List，分隔符区分大小写
	 * @param parent 父串
	 * @param split 分隔字符串
	 * @return 子字符串List
	 */
	public static List<String> getSubStrs(String parent, String split) {
		return getSubStrs(parent, split, false);
	}
	
	/**
	 * 获取指定索引位置子字符串
	 * @param parent 父串
	 * @param split 分隔字符串
	 * @param splitIndex 取第几个分隔符位置的子串，第一个子串为0
	 * @param ignoreCase true分隔字符串(split)忽略大小写
	 * @return 子字符串
	 */
	public static String getSubStr(String parent, String split, int splitIndex, boolean ignoreCase) {
		if (isNull(parent) || splitIndex < 0) {
	    	return "";
	    }
		List<String> subStrList = getSubStrs(parent, split, ignoreCase);
		return subStrList.size() > splitIndex ? subStrList.get(splitIndex) : "";
	}
	
	/**
	 * 获取指定索引位置子字符串，分隔符区分大小写
	 * @param parent 父串
	 * @param split 分隔字符串
	 * @param splitIndex 取第几个分隔符位置的子串，第一个子串为0
	 * @return 子字符串
	 */
	public static String getSubStr(String parent, String split, int splitIndex) {
		return getSubStr(parent, split, splitIndex, false);
	}
	
	/**
	 * 生成新的GUID
	 * @param secure true表示返回安全的GUID，如“479A9159DA2A4A8EBD660B188BB07F5D”<br/>
	 *   否则返回如“479A9159-DA2A-4A8E-BD66-0B188BB07F5D”
	 * @return 返回GUID
	 */
	public static String newGUID(boolean secure){
    	UUID uuid = UUID.randomUUID();
    	String result = uuid.toString();
    	if (secure)
    		return result.toUpperCase().replaceAll("-", "");
    	else
    		return result;
    }
	
	/**
	 * 生成新的安全的GUID
	 * @return 返回GUID，如：479A9159DA2A4A8EBD660B188BB07F5D
	 */
	public static String newGUID(){
		return newGUID(true);
	}
	
	/**
	 * 日期转字符串
	 * @param date 日期
	 * @param dateFormat 转换格式，yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
	
	/**
	 * 字符串转日期
	 * @param date 字符串格式日期
	 * @param dateFormat 转换格式，yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date stringToDate(String date, String dateFormat){
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		Date convertDate = null;
		try {
			convertDate = sdf.parse(date);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return convertDate;
	}
	
	/**
	 * 字符串日期是否符合指定日期格式
	 * @param date 字符串格式日期
	 * @param dateFormat 判断是否符合的日期格式，yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static boolean isDateFormat(String date, String dateFormat){
		return (stringToDate(date, dateFormat) != null);
	}
	
	public static void main(String[] args) {
		String url = "https://192.168.5.241:8443/app/data?module=sysman&service=ExchangeData&u=0001&method=receiveData";
		String port = "80";
		if (url.indexOf(":", 6) >= 0) {
			port = url.substring(url.indexOf(":", 6) + 1);
			if (port.indexOf("/") >= 0) {
				port = port.substring(0, port.indexOf("/"));
			}
		}
		System.out.println(port);
	}
}