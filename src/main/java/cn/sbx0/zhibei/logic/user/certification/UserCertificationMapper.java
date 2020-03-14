package cn.sbx0.zhibei.logic.user.certification;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户认证 数据层
 */
@Mapper
public interface UserCertificationMapper {

    /**
     * 查询所有的认证信息
     *
     * @param userId
     * @param kind
     * @param status
     * @param start
     * @param end
     * @return
     */
    List<UserCertification> findAllByUserAndKindAndStatusAndPage(Integer userId, String kind, String status, int start, int end);

    /**
     * 查询所有的认证信息的条数
     *
     * @param userId
     * @param kind
     * @param status
     * @return
     */
    Integer countAllByUserAndKindAndStatusAndPage(Integer userId, String kind, String status);
}