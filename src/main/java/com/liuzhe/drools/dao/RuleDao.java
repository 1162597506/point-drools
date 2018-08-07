package com.liuzhe.drools.dao;

import com.liuzhe.drools.entity.Rule;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Alan on 2018/8/6.
 */
@Mapper
public interface RuleDao{
    @Select("SELECT * FROM rule where id = #{id}")
    Rule getById(@Param("id") Integer id);

    @Insert("INSERT INTO rule(name,rule) VALUE(#{name},#{rule})")
    Integer setRule(@Param("name") String name,@Param("rule") String rule);

    @Select("SELECT * FROM rule ")
    List<Rule> getRuleList();

    @Update("delete from rule WHERE id = #{id}")
    Integer deleteRule(@Param("id") Integer id);

    @Update("UPDATE rule SET rule= #{rule} AND name = #{name} WHERE id = #{id}")
    Integer updateRule(@Param("id") Integer id,@Param("name") String name,@Param("rule") String rule);
}
