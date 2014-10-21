package com.hengyi.japp.materiel.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.hengyi.japp.materiel.domain.SapT023t;
import com.hengyi.japp.materiel.service.QueryService;

public class SapT023tConverter implements Converter {

    @Inject
    private QueryService queryService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        return StringUtils.isBlank(value) ? null : queryService.find(SapT023t.class, value);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        return (value == null || !(value instanceof SapT023t)) ? null : ((SapT023t) value).getMatkl();
    }

}
