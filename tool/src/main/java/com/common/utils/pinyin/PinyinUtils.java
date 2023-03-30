package com.common.utils.pinyin;

/**
 * 处理拼音的工具类
 * @author 李熠
 *
 */
public class PinyinUtils {

	//简体中文的编码范围从B0A1(45217)一直到F7FE(63486)
	private static int BEGIN = 0xB0A1;
	private static int END = 0xF7FE;

	// 按照声 母表示，这个表是在GB2312中的出现的第一个汉字，也就是说“啊”是代表首字母a的第一个汉字。
    // i, u, v都不做声母, 自定规则跟随前面的字母
    private static char chartable[] = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', };

    //26个字母区间对应27个端点
    //GB2312汉字区间十进制表示
    private static int table[] = new int[27];

 // 对应首字母区间表
    private static char[] initialtable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'T', 'T', 'W', 'X', 'Y', 'Z', };

    static{
    	for (int i = 0; i < 26; i++) {
			table[i] = gbValue(chartable[i]);
		}
    	table[26] = END;//区间表结尾
    }

    /**
     * 将汉字转换为拼音首字母
     * @param str
     * @return
     */
    public static String cn2py(String str){
    	String result = "";
    	int length = str.length();
    	try {
			for (int i = 0; i < length; i++) {
				result += char2Initial(str.charAt(i));
			}
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
    	return result;
    }

    /**
     * 输入字符，得到他的声母，英文字母返回对应的大写字母，其他
     * 非简体汉字返回'0'
     * @param ch
     * @return
     */
    private static char char2Initial(char ch){
    	if(ch >= 'a' && ch <= 'z'){
    		return (char)(ch - 32);
    	}
    	if(ch >= 'A' && ch <= 'Z'){
    		return ch;
    	}
    	int gb = gbValue(ch);
    	if((gb < BEGIN) || (gb > END)){
    		return ch;
    	}
    	int i;
    	for (i = 0; i < 26; i++) {
			if((gb >= table[i]) && (gb < table[i+1])){
				break;
			}
		}
    	if(gb == END){
    		i = 25;
    	}
    	return initialtable[i];
    }

    /**
     * 取出汉字的编码
     * @param ch
     * @return
     */
    private static int gbValue(char ch){
    	String str = new String();
    	str += ch;
    	try {
			byte bytes[] = str.getBytes("gb2312");
			if(bytes.length < 2){
				return 0;
			}
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }

    public static void main(String[] args) {
		System.out.println(cn2py("眉山"));
	}
}
