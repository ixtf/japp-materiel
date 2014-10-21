package com.hengyi.japp.materiel.web.model;

import com.hengyi.japp.materiel.domain.Operator;
import com.hengyi.japp.materiel.service.QueryService;
import com.hengyi.japp.materiel.web.command.OperatorQueryCommand;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@SuppressWarnings("unchecked")
public class OperatorsDataModel extends LazyDataModel<Operator> {

    private static final long serialVersionUID = 1L;
    @EJB
    private QueryService queryService;
    @Inject
    private OperatorQueryCommand command;
    private boolean searchClick;

    @PostConstruct
    public void postConstruct() {
        setRowCount(queryService.count(command).intValue());
    }

    @Override
    public Operator getRowData(String rowKey) {
        if (rowKey == null) {
            return null;
        }

        for (Operator o : (List<Operator>) getWrappedData()) {
            if (rowKey.equals(o.getId())) {
                return o;
            }
        }
        return queryService.find(Operator.class, rowKey);
    }

    @Override
    public List<Operator> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        if (searchClick) {
            command.setFirst(0);
        } else {
            command.setFirst(first);
        }
        command.setPageSize(pageSize);
        searchClick = false;
        return queryService.query(command);
    }

    @Override
    public List<Operator> load(int first, int pageSize,
            List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        if (searchClick) {
            command.setFirst(0);
        } else {
            command.setFirst(first);
        }
        command.setPageSize(pageSize);
        return queryService.query(command);
    }

    public void search() {
        postConstruct();
        searchClick = true;
    }

    public OperatorQueryCommand getCommand() {
        return command;
    }
}
