package com.tensquare.qa.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {
    /*有nativeQuery = true时可以执行原生sql语句, false时则执行的是jpql语句*/
    @Query(value = "select * from tb_problem where id in (select problemid from tb_pl where labelid = ? order by replyTime desc)", nativeQuery = true)
    public List<Problem> findNewlist(String label, Pageable pageable);

    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid = ? order by reply desc", nativeQuery = true)
    public List<Problem> findHotlist(String label);

    @Query(value = "", nativeQuery = true)
    public List<Problem> findWaitlist();
}
