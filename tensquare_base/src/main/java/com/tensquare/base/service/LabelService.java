package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String labelId) {
        return labelDao.findById(labelId).get();
    }

    public void add(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root  根对象,也就是要把查询条件封装到的那个对象   where 列名 = label.getXxx()
             * @param query  封装的是查询关键字,比如group by、order by等等...
             * @param criteriaBuilder  用于封装条件对象
             * @return Predicate  如果直接返回null,表示不需要任何条件
             */
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");    //相当于where labelname like '%JAVA%'
                    list.add(labelname);
                }

                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate state = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(state);
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        });
    }

    public Page<Label> pageQuery(int page, int size, Label label) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root  根对象,也就是要把查询条件封装到的那个对象   where 列名=label.getXxx()
             * @param query  封装的是查询关键字,比如group by、order by等等...
             * @param criteriaBuilder  用于封装条件对象
             * @return Predicate  如果直接返回null,表示不需要任何条件
             */
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");    //相当于where labelname like '%JAVA%'
                    list.add(labelname);
                }

                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate state = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(state);
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);

                return criteriaBuilder.and(predicates);
            }
        }, pageable);
    }
}
