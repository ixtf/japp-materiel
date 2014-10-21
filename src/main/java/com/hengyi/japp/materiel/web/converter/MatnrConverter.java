package com.hengyi.japp.materiel.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.hengyi.japp.materiel.service.SapFacade;

public class MatnrConverter implements Converter {

    @Inject
    private SapFacade sapFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        try {
            return StringUtils.isBlank(value) ? null : sapFacade.convertMatnr(value);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        return (value == null || !(value instanceof String)) ? null : test((String) value);
    }

//TODO 自行去掉前导零，最好用sap来
    private String test(String value) {
        int start = value.length();
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) != '0') {
                start = i;
                break;
            }
        }
        return StringUtils.substring(value, start);
    }

}
