package com.wjl.fcity.welfare.common.util;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author : Fy
 * @date : 2018-04-10 15:01
 * 用户等级：普通用户﹤200分；200分≤铜﹤500分；500分≤银﹤600分；600分≤金
 * <p>
 * 签到贡献值奖励机制：
 * 普通用户获得2个贡献值概率10%,获得1个贡献值概率40%，获得0个贡献值概率50%。
 * 铜级用户获得3个贡献值概率10%,获得2个贡献值概率10%，获得1个贡献值概率50%，获得0个贡献值概率30%。
 * 银级以上用户获得3个贡献值概率20%,获得2个贡献值概率20%，获得1个贡献值概率40%，获得0个贡献值概率20%。
 */
public class SignCreditValue {

    /**
     * 根据用户的贡献值签到增加新的信用值
     *
     * @return 签到产生的信用值
     */
    public static Integer getSignUserCreditValue(Map<String, List<Integer>> userCreditValueMap) {

        //随机产生信用值
        Random random = new Random();
        //生成(1~100)随机数
        Integer addCreditValue = random.nextInt(100) + 1;

        String[] credit = {"0"};

        userCreditValueMap.forEach((String k, List<Integer> v) -> {
            Integer firstScore = v.get(0);
            Integer lastScore = v.get(v.size() - 1);
            if ((firstScore <= addCreditValue) && (addCreditValue <= lastScore)) {
                credit[0] = k;
            }
        });

        return Integer.valueOf(credit[0]);
    }

}
