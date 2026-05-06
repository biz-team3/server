package com.bizteam3.server.author.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bizteam3.server.author.entity.Author;

@Mapper
public interface AuthorDao {
	Author findById(@Param("authorId") Integer authorId);
}
