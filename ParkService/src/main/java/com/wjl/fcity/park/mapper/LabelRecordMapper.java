package com.wjl.fcity.park.mapper;

import com.wjl.fcity.park.dto.LabelDTO;
import com.wjl.fcity.park.dto.MyLabelDTO;
import com.wjl.fcity.park.mapper.provider.LabelRecordProvider;
import com.wjl.fcity.park.model.LabelRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 杨洋
 * @date 2018/3/27
 */
@Repository
public interface LabelRecordMapper {

    /**
     * 新增标签记录
     *
     * @param userId     用户id
     * @param mobile     手机号码
     * @param nickName   称呼(通讯录)
     * @param gmtCreated 创建时间
     * @param label      标签
     */
    @Insert("INSERT INTO label_record (`user_id`,mobile,nick_name,gmt_created,label_id,`status`) VALUES (#{userId},#{mobile},#{nickName},#{gmtCreated},#{labelId},0)")
    void addLabel(@Param("userId") Long userId, @Param("mobile") String mobile, @Param("nickName") String nickName, @Param("gmtCreated") Date gmtCreated, @Param("labelId") Long label);

    /**
     * 批量插入新标签
     *
     * @param userId     用户id
     * @param mobile     手机号码
     * @param nickName   称呼(通讯录)
     * @param gmtCreated 创建时间
     * @param labels     标签list
     */
    @InsertProvider(type = LabelRecordProvider.class, method = "addLabelList")
    void addLabelList(@Param("userId") Long userId, @Param("mobile") String mobile, @Param("nickName") String nickName, @Param("gmtCreated") Date gmtCreated, List<String> labels);

    /**
     * 获取用户已标好友列表
     *
     * @param userId 用户id
     * @return 已标好友列表
     */
    @Select("SELECT * FROM label_record WHERE user_id = #{userId} AND status = 0")
    List<LabelRecord> getLabeledList(Long userId);

    /**
     * 查询该用户被打了多少标签
     *
     * @param mobile 用户手机号
     * @return 被打标签数量
     */
    @Select("SELECT count(*) FROM label_record WHERE mobile = #{mobile} AND status = 0")
    Integer countMyLabels(String mobile);

    /**
     * 查询该用户被打的标签
     *
     * @param mobile 用户手机号
     * @return 标签list
     */
    @Select("SELECT T1.label_id AS labelId,count(T1.id) AS labelCount,T2.content AS content,T2.category AS category FROM label_record AS T1 " +
            "LEFT JOIN label AS T2 ON T2.id = T1.label_id " +
            "WHERE T1.mobile = #{mobile} AND T1.`status` = 0 " +
            "GROUP BY T1.label_id ORDER BY labelCount DESC")
    List<MyLabelDTO> getMyLabels(String mobile);

    /**
     * 获取用户已标签好友列表
     *
     * @param userId 用户Id
     * @return list
     */
    @Select(value = "SELECT mobile ,CONCAT (GROUP_CONCAT(label_id),\",\") AS labels, nick_name AS nickName ,gmt_created FROM `label_record` WHERE status = 0 AND user_id = #{userId} GROUP BY mobile ORDER BY gmt_created DESC")
    List<LabelDTO> getAllLabel(Long userId);
}
