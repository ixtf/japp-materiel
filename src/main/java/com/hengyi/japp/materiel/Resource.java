/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.materiel;

import static com.hengyi.japp.shiro.ShiroUtil.getLoginUser;
import static org.apache.shiro.SecurityUtils.getSubject;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;

import org.apache.shiro.subject.Subject;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengyi.japp.dto.LoginUserDTO;

/**
 *
 * @author jzb
 */
public class Resource {

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember()
                .getDeclaringClass());
    }

    @Produces
    public Mapper produceDozerMapper() {
        return new DozerBeanMapper();
    }

    @Named("subject")
    @Produces
    public Subject produceSubject() {
        return getSubject();
    }

    @Named("loginUser")
    @Produces
    public LoginUserDTO produceLoginUser() {
        return getLoginUser();
    }
}
