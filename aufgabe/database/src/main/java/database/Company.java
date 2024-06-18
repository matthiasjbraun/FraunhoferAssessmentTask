package database;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Company {
    @Column(name = "companyName")
    private String name;
    private String catchPhrase;
    private String bs;
    public Company(){}

    public String getName() {
        return name;
    }

    public void setName(String companyName) {
        this.name = companyName;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }
}
