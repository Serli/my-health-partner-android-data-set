package com.serli.myhealthpartner.model;

import java.util.Date;

/**
 * Contains the data of the user profile
 */
public class ProfileData {

    private int weight;
    private int size;
    private int sex;
    private Date birthday;

    /**
     * @return The profile's birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday The profile's birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return The profile's height
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size The profile's height
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return The profile's gender
     */
    public int getSex() {
        return sex;
    }

    /**
     * @param sex The profile's gender
     */
    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * @return The profile's weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight The profile's weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
