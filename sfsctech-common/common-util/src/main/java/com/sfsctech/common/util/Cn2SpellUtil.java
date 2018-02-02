package com.sfsctech.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Class Cn2SpellUtil
 *
 * @author 张麒 2018-2-2.
 * @version Description:
 */
public class Cn2SpellUtil {

    public static String converterToFirstSpell(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aNameChar : nameChar) {
            if (aNameChar > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(aNameChar, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(aNameChar);
            }
        }
        return pinyinName.toString();
    }

    public static String converterToSpell(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aNameChar : nameChar) {
            if (aNameChar > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(aNameChar, defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(aNameChar);
            }
        }
        return pinyinName.toString();
    }
}
