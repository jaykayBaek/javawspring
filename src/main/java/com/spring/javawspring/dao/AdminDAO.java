package com.spring.javawspring.dao;

import org.apache.ibatis.annotations.Param;

public interface AdminDAO {

	public int setMemberLevelUp(
			@Param("idx") int idx,
			@Param("level") int level);

}
