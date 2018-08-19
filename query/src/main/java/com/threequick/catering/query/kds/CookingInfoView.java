package com.threequick.catering.query.kds;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class CookingInfoView {

    @javax.persistence.Id
    @GeneratedValue
    private Long jpaId;

    private String identifier;
    private CookingType cookingType;
    private String cookingName;

    /* ----------------------------------setter & getter ------------------------------------*/

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCookingName() {
        return cookingName;
    }

    public void setCookingName(String cookingName) {
        this.cookingName = cookingName;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }

    public CookingType getCookingType() {
        return cookingType;
    }

    public void setCookingType(CookingType cookingType) {
        this.cookingType = cookingType;
    }
}
