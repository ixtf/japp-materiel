package com.hengyi.japp.materiel.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.hengyi.japp.materiel.Util;
import com.hengyi.japp.materiel.domain.Mara;
import com.hengyi.japp.materiel.domain.SapMara;
import com.hengyi.japp.materiel.domain.Operator;
import com.hengyi.japp.materiel.domain.SapT023t;
import com.hengyi.japp.materiel.domain.Workshop;
import com.hengyi.japp.materiel.web.command.MaraQueryCommand;
import com.hengyi.japp.materiel.web.command.OperatorQueryCommand;
import com.hengyi.japp.materiel.web.command.SapMaraQueryCommand;
import com.hengyi.japp.materiel.web.command.WorkshopQueryCommand;

@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class QueryService {

    @PersistenceContext
    private EntityManager em;

    public <T> T find(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }

    public List<Operator> autoCompleteOperator(String query) {
        return em.createNamedQuery("Operator.queryByName", Operator.class)
                .setParameter("name", "%" + query + "%").setMaxResults(10)
                .getResultList();
    }

    public List<SapT023t> autoCompleteSapT023t(String query) {
        return em.createNamedQuery("SapT023t.queryByWgbez", SapT023t.class)
                .setParameter("wgbez", "%" + query + "%").setMaxResults(10)
                .getResultList();
    }

    public Long count(OperatorQueryCommand command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Operator> rOperator = cq.from(Operator.class);
        Util.queryCommand(cb, cq, rOperator, command);
        cq.select(cb.countDistinct(rOperator));
        return em.createQuery(cq).getSingleResult();
    }

    public List<Operator> query(OperatorQueryCommand command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Operator> cq = cb.createQuery(Operator.class).distinct(true);
        Root<Operator> rOperator = cq.from(Operator.class);
        Util.queryCommand(cb, cq, rOperator, command);

        TypedQuery<Operator> query = em.createQuery(cq);
        if (command.getFirst() != null) {
            query.setFirstResult(command.getFirst());
        }
        if (command.getPageSize() != null) {
            query.setMaxResults(command.getPageSize());
        }
        return query.getResultList();
    }

    public Long count(WorkshopQueryCommand command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Workshop> rWorkshop = cq.from(Workshop.class);
        Util.queryCommand(cb, cq, rWorkshop, command);
        cq.select(cb.countDistinct(rWorkshop));
        return em.createQuery(cq).getSingleResult();
    }

    public List<Workshop> query(WorkshopQueryCommand command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Workshop> cq = cb.createQuery(Workshop.class).distinct(true);
        Root<Workshop> rWorkshop = cq.from(Workshop.class);
        Util.queryCommand(cb, cq, rWorkshop, command);

        TypedQuery<Workshop> query = em.createQuery(cq);
        if (command.getFirst() != null) {
            query.setFirstResult(command.getFirst());
        }
        if (command.getPageSize() != null) {
            query.setMaxResults(command.getPageSize());
        }
        return query.getResultList();
    }

    public Long count(SapMaraQueryCommand command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<SapMara> rSapMara = cq.from(SapMara.class);
        Util.queryCommand(cb, cq, rSapMara, command);
        cq.select(cb.countDistinct(rSapMara));
        return em.createQuery(cq).getSingleResult();
    }

    public List<SapMara> query(SapMaraQueryCommand command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SapMara> cq = cb.createQuery(SapMara.class).distinct(true);
        Root<SapMara> rSapMara = cq.from(SapMara.class);
        Util.queryCommand(cb, cq, rSapMara, command);

        TypedQuery<SapMara> query = em.createQuery(cq);
        if (command.getFirst() != null) {
            query.setFirstResult(command.getFirst());
        }
        if (command.getPageSize() != null) {
            query.setMaxResults(command.getPageSize());
        }
        return query.getResultList();
    }

    public Long count(MaraQueryCommand command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Mara> rMara = cq.from(Mara.class);
        Util.queryCommand(cb, cq, rMara, command);
        cq.select(cb.countDistinct(rMara));
        return em.createQuery(cq).getSingleResult();
    }

    public List<Mara> query(MaraQueryCommand command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Mara> cq = cb.createQuery(Mara.class).distinct(true);
        Root<Mara> rMara = cq.from(Mara.class);
        Util.queryCommand(cb, cq, rMara, command);

        TypedQuery<Mara> query = em.createQuery(cq);
        if (command.getFirst() != null) {
            query.setFirstResult(command.getFirst());
        }
        if (command.getPageSize() != null) {
            query.setMaxResults(command.getPageSize());
        }
        return query.getResultList();
    }
}
