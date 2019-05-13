package cn.edu.neusoft.common;

/**
 * @author Chen
 * @create 2019-05-08 9:59
 * 参数工具类
 */
public class ParamUtils {

    /**
     * 根据逗号拆分参数，返回参数个数
     * @return
     */
    public static int paramNum(String param){
        if (param != null && param !="") {
            String[] split = param.split(",");
            int num = split.length;
            return num;
        }
        return 0;
    }
}
