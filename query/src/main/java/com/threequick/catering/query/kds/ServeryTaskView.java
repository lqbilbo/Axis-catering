package com.threequick.catering.query.kds;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Entity
public class ServeryTaskView {

    @Id
    @javax.persistence.Id
    private String identifier;
    private String serveryIdentifier;
    private long AmountOfCooking;
    private String cookingTime;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getServeryIdentifier() {
        return serveryIdentifier;
    }

    public void setServeryIdentifier(String serveryIdentifier) {
        this.serveryIdentifier = serveryIdentifier;
    }

    public long getAmountOfCooking() {
        return AmountOfCooking;
    }

    public void setAmountOfCooking(long amountOfCooking) {
        AmountOfCooking = amountOfCooking;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    @Override
    public String toString() {
        return "ServeryTaskView{" +
                "identifier='" + identifier + '\'' +
                ", serveryIdentifier='" + serveryIdentifier + '\'' +
                ", AmountOfCooking=" + AmountOfCooking +
                ", cookingTime='" + cookingTime + '\'' +
                '}';
    }
}
