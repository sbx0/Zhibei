package cn.sbx0.zhibei.logic.message;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageBaseMapper {
    List<MessageBase> findYourMsg(Integer yourId, String attribute, String direction, int start, int end);

    Integer countYourMsg(Integer yourId, String attribute, String direction);
}
