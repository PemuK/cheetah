package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.DemoDo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoDao {

    @Insert("""
            INSERT INTO demo(name, address, age)
            VALUES(#{name}, #{address}, #{age})
            """)
    int insert(DemoDo demoDo);
}
