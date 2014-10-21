package com.hengyi.japp.materiel.web.command;

import static org.apache.commons.lang3.StringUtils.replace;

import com.hengyi.japp.web.command.AbstractQueryCommand;

public class OperatorQueryCommand extends AbstractQueryCommand {

    private static final long serialVersionUID = 1L;
    private String name;

    public String getNameQuery() {
        return replace(getName(), "*", "%");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
