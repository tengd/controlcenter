
package com.evisible.os.business.util;

import com.evisible.os.controlcenter.util.StringConvert;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * <p>汉字转拼音工具类</p>
 * @author JiangWanDong
 * @Date   2018年4月2日
 */
public class PinyinUtil {

    private static HanyuPinyinOutputFormat format = null;
    private static String[]pinyin;
    
    static{
    	format = new HanyuPinyinOutputFormat();
        /*
         * 设置需要转换的拼音格式
         * 以天为例
         * HanyuPinyinToneType.WITHOUT_TONE 转换为tian
         * HanyuPinyinToneType.WITH_TONE_MARK 转换为tian1
         * HanyuPinyinVCharType.WITH_U_UNICODE 转换为tiān
         * 
         */
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pinyin = null;
    }

    /**
     * 对单个字进行转换
     */
    public static String getCharPinYin(char pinYinStr){
        try 
        {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(pinYinStr, format);
        } catch (BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
            return "";
        }
        if(pinyin == null || pinyin.length == 0){
            return pinYinStr+"";
        }
        //多音字会返回一个多音字拼音的数组，pinyiin4j并不能有效判断该字的读音
        return pinyin[0];
    }

    /**
     * 对字符串进行转换
     * @param pinYinStr
     * @return
     */
    public static String getStringPinYin(String pinYinStr){
    	pinYinStr = pinYinStr.trim();
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        //循环字符串
        for(int i = 0; i<pinYinStr.length(); i++)
        {

            tempStr = getCharPinYin(pinYinStr.charAt(i));
            if(tempStr == null)
            {
                //非汉字直接拼接
                sb.append(pinYinStr.charAt(i));
            }
            else
            {
                sb.append(tempStr);
            }
        }

        return sb.toString();
    }
    
    /**
     * 
     * <p>获取字符串的首字母缩写形式</p>
     */
    public static String getCaptitalChars(String str){
    	str = str.trim();
    	StringBuilder builder = new StringBuilder();
    	for(int i = 0 ; i < str.length() ; i++){
    		String pinyin = getCharPinYin(str.charAt(i));
    		if(StringConvert.isEmpty(pinyin) || pinyin.equals(" ")){
    			continue;
    		}
    		builder.append(pinyin.substring(0, 1).toUpperCase());
    	}
    	return builder.toString();
    }
    public static void main(String[] args){
    	System.out.println(getCaptitalChars("asd易见供应链  adA是的123123 ad"));
    }
}
