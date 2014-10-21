package com.hengyi.japp.materiel.web;

import static org.apache.shiro.web.util.WebUtils.getHttpRequest;
import static org.apache.shiro.web.util.WebUtils.getSavedRequest;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.hibernate.validator.constraints.NotBlank;
import org.ocpsoft.rewrite.annotation.Join;

import com.hengyi.japp.data.UserType;
import com.hengyi.japp.event.LoginEvent;
import com.hengyi.japp.exception.LoginErrorException;
import com.hengyi.japp.web.AbstractBean;

@Named
@RequestScoped
@Join(path = "/login", to = "/faces/login.jsf")
public class AuthBean extends AbstractBean {

    private static final long serialVersionUID = 1L;
    @Inject
    private Event<LoginEvent> loginEvent;
    @Inject
    private Subject subject;
    @NotBlank
    private String loginId;
    @NotBlank
    private String loginPassword;

    public void login() {
        try {
            if (subject.isAuthenticated()) {
                subject.logout();
            }
            UsernamePasswordToken token = new UsernamePasswordToken(loginId, loginPassword);
            subject.login(token);
            loginEvent.fire(new LoginEvent(UserType.LOCAL, loginId));

            ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
            String redirectUrl = eContext.getApplicationContextPath();
            SavedRequest savedReq = getSavedRequest(getHttpRequest(subject));
            if (savedReq != null) {
                String originalUrl = savedReq.getRequestUrl();
                if (!originalUrl.contains("login")) {
                    redirectUrl = originalUrl;
                }
            }
            eContext.redirect(redirectUrl);
        } catch (Exception e) {
            errorMessage(new LoginErrorException(), false);
        }
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
