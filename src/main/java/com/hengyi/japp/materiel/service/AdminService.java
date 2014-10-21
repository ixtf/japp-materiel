package com.hengyi.japp.materiel.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.shiro.authz.annotation.RequiresRoles;

import com.hengyi.japp.shiro.Secured;

@Secured
@RequiresRoles("admin")
@Stateless
public class AdminService {

    @PersistenceContext
    private EntityManager em;
}
