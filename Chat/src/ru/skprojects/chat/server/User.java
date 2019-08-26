package ru.skprojects.chat.server;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    public boolean isLogin;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.isLogin = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (obj instanceof User) {
            User tmp = (User) obj;
            return tmp.name.equals(this.name) &&
                    tmp.password.equals(this.password);
        } else	return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.password.hashCode();
    }

}
