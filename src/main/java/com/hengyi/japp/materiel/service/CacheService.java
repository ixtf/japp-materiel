package com.hengyi.japp.materiel.service;

import com.google.common.collect.ImmutableList;

import static com.hengyi.japp.Constant.SESSION_OPERATOR;
import static com.hengyi.japp.shiro.ShiroUtil.ADMIN_PRINCIPAL;
import static com.hengyi.japp.shiro.ShiroUtil.getLoginUser;
import static com.hengyi.japp.shiro.ShiroUtil.getSession;
import static com.hengyi.japp.shiro.ShiroUtil.isAdmin;
import static com.hengyi.japp.shiro.ShiroUtil.setSession;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hengyi.japp.materiel.domain.Operator;
import com.hengyi.japp.materiel.domain.Workshop;

import java.util.List;
import javax.annotation.PostConstruct;

@Named("cacheService")
@Startup
@Singleton
@Lock(LockType.READ)
@AccessTimeout(value = -1)
public class CacheService {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private QueryService queryService;
    private List<Workshop> allWorkshop;

    public List<Workshop> getAllWorkshop() {
        return allWorkshop;
    }

    @PostConstruct
    void postConstruct() {
//        if (em.createNamedQuery("Workshop.count", Long.class).getSingleResult() <= 0) {
//            Workshop workShop = new Workshop();
//            em.merge(workShop);
//        }
        initAllWorkshop();
    }

    public void initAllWorkshop() {
        allWorkshop = ImmutableList.copyOf(em.createNamedQuery("Workshop.find", Workshop.class).getResultList());
    }

    public Operator getCurrentOperator() {
        Operator operator = getSession(SESSION_OPERATOR);
        if (operator != null) {
            return operator;
        }
        if (isAdmin()) {
            operator = new Operator(null, ADMIN_PRINCIPAL);
        } else {
            operator = queryService.find(Operator.class, getLoginUser().getId());
        }
        if (operator == null) {
            operator = new Operator(getLoginUser().getId(), getLoginUser().getName());
            em.merge(operator);
        }
        setSession(SESSION_OPERATOR, operator);
        return operator;
    }

    public String getTheme() {
        try {
            return isAdmin() ? "bootstrap" : getCurrentOperator().getTheme();
        } catch (Exception e) {
            return "bootstrap";
        }
    }

    public void updateTheme(String theme) {
        Operator operator = em.find(Operator.class, getCurrentOperator().getId());
        operator.setTheme(theme);
        em.merge(operator);
        setSession(SESSION_OPERATOR, operator);
    }

}
