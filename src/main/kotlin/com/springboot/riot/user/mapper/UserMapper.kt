package com.springboot.riot.user.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {

    fun totalCount(): Int?
}