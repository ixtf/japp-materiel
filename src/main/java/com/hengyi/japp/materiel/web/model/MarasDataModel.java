package com.hengyi.japp.materiel.web.model;

import com.hengyi.japp.materiel.domain.Mara;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.hengyi.japp.materiel.service.QueryService;
import com.hengyi.japp.materiel.web.command.MaraQueryCommand;

@SuppressWarnings("unchecked")
public class MarasDataModel extends LazyDataModel<Mara> {

    private static final long serialVersionUID = 1L;
    @EJB
    private QueryService queryService;
    @Inject
    private MaraQueryCommand command;
    private boolean searchClick;

    @PostConstruct
    public void postConstruct() {
        setRowCount(queryService.count(command).intValue());
    }

    @Override
    public Mara getRowData(String rowKey) {
        if (rowKey == null) {
            return null;
        }

        for (Mara o : (List<Mara>) getWrappedData()) {
            if (rowKey.equals(o.getMatnr())) {
                return o;
            }
        }
        return queryService.find(Mara.class, rowKey);
    }

    @Override
    public List<Mara> load(int first, int pageSize, String sortField,
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

    public MaraQueryCommand getCommand() {
        return command;
    }

    public void setCommand(MaraQueryCommand command) {
        this.command = command;
    }
}
