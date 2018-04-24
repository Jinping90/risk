package com.wjl.fcity.mall.mapper.provider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangjunyi on 2017/12/14.
 */
public class BannerProviderTest {

    public static void main(String[] args) {
       /* BannerProvider bp =new BannerProvider();
        BannerDto banner=new BannerDto();
        banner.setSize(5);
        banner.setNid("首页Banner");
        System.out.println(bp.getBanner(banner));

        Integer integer=new Integer(1);
        System.out.println(integer>0);*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date= null;
        try {
            date = sdf.parse("2017-10-30 12:05:36");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date.getTime());

    }

}