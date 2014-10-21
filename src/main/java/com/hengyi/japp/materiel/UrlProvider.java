/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.materiel;

import static com.hengyi.japp.shiro.ShiroUtil.getLoginUser;
import static org.apache.shiro.SecurityUtils.getSubject;
import static org.apache.shiro.web.util.WebUtils.getHttpRequest;
import static org.ocpsoft.rewrite.config.Direction.isInbound;
import static org.ocpsoft.rewrite.servlet.config.Path.matches;
import static org.ocpsoft.rewrite.servlet.config.Redirect.temporary;
import static org.ocpsoft.rewrite.servlet.config.rule.Join.path;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Condition;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Operation;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.Rewrite;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;

import com.hengyi.japp.dto.LoginUserDTO;
import com.hengyi.japp.event.LogoutEvent;
import static com.hengyi.japp.shiro.ShiroUtil.isAdmin;
import com.hengyi.japp.web.UAgentInfo;

/**
 *
 * @author jzb
 */
@RewriteConfiguration
public class UrlProvider extends HttpConfigurationProvider {

    @Inject
    private Event<LogoutEvent> logoutEvent;

    Condition adminCondition = new Condition() {

        @Override
        public boolean evaluate(Rewrite event, EvaluationContext context) {
            return isAdmin();
        }
    };
    Condition authenticatedCondition = new Condition() {

        @Override
        public boolean evaluate(Rewrite event, EvaluationContext context) {
            return getSubject().isAuthenticated();
        }
    };
    Condition mobilePhoneCondition = new Condition() {

        @Override
        public boolean evaluate(Rewrite event, EvaluationContext context) {
            HttpServletRequest req = getHttpRequest(getSubject());
            String userAgent = req.getHeader("user-agent");
            String accept = req.getHeader("Accept");
            UAgentInfo agent = new UAgentInfo(userAgent, accept);
            return agent.isMobilePhone;
        }
    };
    Operation logoutOperation = new Operation() {

        @Override
        public void perform(Rewrite event, EvaluationContext context) {
            LoginUserDTO loginUser = getLoginUser();
            logoutEvent.fire(new LogoutEvent(loginUser.getUserType(), loginUser.getBindId()));
            getSubject().logout();
        }
    };

    @Override
    public Configuration getConfiguration(ServletContext context) {
        ConfigurationBuilder builder = ConfigurationBuilder.begin()
                // login
                .addRule().when(isInbound().and(matches("/login")).and(authenticatedCondition)).perform(temporary(context.getContextPath() + "/"))
                // logout
                .addRule().when(isInbound().and(matches("/logout")).and(authenticatedCondition)).perform(temporary(context.getContextPath() + "/").and(logoutOperation))
                .addRule().when(isInbound().and(matches("/logout")).andNot(authenticatedCondition)).perform(temporary(context.getContextPath() + "/login"))
                // 管理员首页
                .addRule().when(isInbound().and(matches("/")).and(adminCondition)).perform(temporary(context.getContextPath() + "/admin"))
                // 权限不足
                .addRule(path("/unauthorized").to("/faces/unauthorized.jsf"));
        return builder;
    }

    @Override
    public int priority() {
        return 10;
    }

}
