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
     * @param id     id
     * @param kind   kind
     * @param status status
     * @return UserCertification
     */
    List<UserCertification> selectAll(Integer id, String kind, String status);
}