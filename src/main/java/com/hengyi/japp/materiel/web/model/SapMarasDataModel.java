package com.hengyi.japp.materiel.web.model;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.hengyi.japp.materiel.domain.SapMara;
import com.hengyi.japp.materiel.service.QueryService;
import com.hengyi.japp.materiel.web.command.SapMaraQueryCommand;

@SuppressWarnings("unchecked")
public class SapMarasDataModel extends LazyDataModel<SapMara> {

    private static final long serialVersionUID = 1L;
    @EJB
    private QueryService queryService;
    @Inject
    private SapMaraQueryCommand command;
    private boolean searchClick;

    @PostConstruct
    public void postConstruct() {
        setRowCount(queryService.count(command).intValue());
    }

    @Override
    public SapMara getRowData(String rowKey) {
        if (rowKey == null) {
            return null;
        }

        for (SapMara o : (List<SapMara>) getWrappedData()) {
            if (rowKey.equals(o.getMatnr())) {
                return o;
            }
        }
        return queryService.find(SapMara.class, rowKey);
    }

    @Override
    public List<SapMara> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        if (searchClick) {
            command.setFirst(0);
        } else {
            command.setFirst(first);
        }
        command.setPageSize(pageSize);
        return queryService.query(command);
    }

    public void search() throws Exception {
        postConstruct();
        searchClick = true;
    }

    public SapMaraQueryCommand getCommand() {
        return command;
    }

    public void setCommand(SapMaraQueryCommand command) {
        this.command = command;
    }
}
