package com.hengyi.japp.materiel.web.command;

import static org.apache.commons.lang3.StringUtils.replace;

import com.hengyi.japp.materiel.domain.SapT023t;
import com.hengyi.japp.web.command.AbstractQueryCommand;

public class MaraQueryCommand extends AbstractQueryCommand {

    private static final long serialVersionUID = 1L;
    private String maktx;
    private SapT023t sapT023t;

    public String getMaktxQuery() {
        return replace(maktx, "*", "%");
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public SapT023t getSapT023t() {
        return sapT023t;
    }

    public void setSapT023t(SapT023t sapT023t) {
        this.sapT023t = sapT023t;
    }
}
