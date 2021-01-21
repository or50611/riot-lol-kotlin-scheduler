package com.springboot.riot.data.rune.mapper

import com.springboot.riot.data.rune.dto.RuneDataDto
import com.springboot.riot.data.rune.dto.RuneImageDto
import org.apache.ibatis.annotations.Mapper


@Mapper
interface RuneMapper {

    fun insertRuneInfo(runeDataDto: RuneDataDto): Int

}
