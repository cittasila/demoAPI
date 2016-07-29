package com.langying.models;

import java.io.Serializable;

public class RBookLexile implements Serializable {
    private Integer id;

    private String lexilelevel;

    private Integer minlexile;

    private Integer maxlexile;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLexilelevel() {
        return lexilelevel;
    }

    public void setLexilelevel(String lexilelevel) {
        this.lexilelevel = lexilelevel;
    }

    public Integer getMinlexile() {
        return minlexile;
    }

    public void setMinlexile(Integer minlexile) {
        this.minlexile = minlexile;
    }

    public Integer getMaxlexile() {
        return maxlexile;
    }

    public void setMaxlexile(Integer maxlexile) {
        this.maxlexile = maxlexile;
    }
}