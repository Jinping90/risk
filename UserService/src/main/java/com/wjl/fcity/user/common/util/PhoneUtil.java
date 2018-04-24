package com.wjl.fcity.user.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author czl
 */
public class PhoneUtil {

    public static boolean isMobileNO(String mobiles) {
        // 所有手机号
        String mobile = "^1[3|4578][0-9]\\d{8}$";
        Pattern isMobile = Pattern.compile(mobile);
        Matcher matcher = isMobile.matcher(mobiles);

        if (matcher.matches()) {
            return true;
        }

        //Add 178 移动方面最新答复
        String chinaMobile = "^134[0-8]\\d{7}$|^(?:13[5-9]|147|15[0-27-9]|178|18[2-478])\\d{8}$";
        Pattern isChinaMobile = Pattern.compile(chinaMobile);
        Matcher matcher1 = isChinaMobile.matcher(mobiles);

        if (matcher1.matches()) {
            return true;
        }

        //联通
        String chinaUnion = "^(?:13[0-2]|145|15[56]|176|18[56])\\d{8}$";
        Pattern isChinaUnion = Pattern.compile(chinaUnion);
        Matcher matcher2 = isChinaUnion.matcher(mobiles);

        if (matcher2.matches()) {
            return true;
        }

        //^1349\d{7}$ 1349号段 电信方面没给出答复，视作不存在
        String chinaTelcom = "^(?:133|153|177|18[019])\\d{8}$";
        Pattern isChinaTelcom = Pattern.compile(chinaTelcom);
        Matcher matcher3 = isChinaTelcom.matcher(mobiles);

        if (matcher3.matches()) {
            return true;
        }

        //其他
        String otherTelphone = "^(?:170|171)\\d{8}$";
        Pattern isOtherTelphone = Pattern.compile(otherTelphone);
        Matcher matcher4 = isOtherTelphone.matcher(mobiles);

        return matcher4.matches();

    }

}
