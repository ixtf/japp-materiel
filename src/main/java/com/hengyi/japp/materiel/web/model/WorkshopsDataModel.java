package com.hengyi.japp.materiel.web.model;

import com.hengyi.japp.materiel.domain.Workshop;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.hengyi.japp.materiel.service.QueryService;
import com.hengyi.japp.materiel.web.command.WorkshopQueryCommand;

@SuppressWarnings("unchecked")
public class WorkshopsDataModel extends LazyDataModel<Workshop> {

    private static final long serialVersionUID = 1L;
    @EJB
    private QueryService queryService;
    @Inject
    private WorkshopQueryCommand command;
    private boolean searchClick;

    @PostConstruct
    public void postConstruct() {
        setRowCount(queryService.count(command).intValue());
    }

    @Override
    public Workshop getRowData(String rowKey) {
        if (rowKey == null) {
            return null;
        }

        for (Workshop o : (List<Workshop>) getWrappedData()) {
            if (rowKey.equals(o.getId())) {
                return o;
            }
        }
        return queryService.find(Workshop.class, rowKey);
    }

    @Override
    public List<Workshop> load(int first, int pageSize, String sortField,
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

    public WorkshopQueryCommand getCommand() {
        return command;
    }

    public void setCommand(WorkshopQueryCommand command) {
        this.command = command;
    }
}
