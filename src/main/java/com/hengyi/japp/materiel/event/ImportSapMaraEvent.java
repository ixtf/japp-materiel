package com.hengyi.japp.materiel.event;

public class ImportSapMaraEvent {

    private final Integer I_FIRST;
    private final Integer I_PAGESIZE;
    private final Iterable<String> matnrs;

    public ImportSapMaraEvent(Integer I_FIRST, Integer I_PAGESIZE, Iterable<String> matnrs) {
        super();
        this.I_FIRST = I_FIRST;
        this.I_PAGESIZE = I_PAGESIZE;
        this.matnrs = matnrs;
    }

    public ImportSapMaraEvent(Integer I_FIRST, Integer I_PAGESIZE) {
        this(I_FIRST, I_PAGESIZE, null);
    }

    public ImportSapMaraEvent(Iterable<String> matnrs) {
        this(null, null, matnrs);
    }

    public ImportSapMaraEvent() {
        this(null, null, null);
    }

    public Iterable<String> getMatnrs() {
        return matnrs;
    }

    public Integer getI_FIRST() {
        return I_FIRST;
    }

    public Integer getI_PAGESIZE() {
        return I_PAGESIZE;
    }
}
