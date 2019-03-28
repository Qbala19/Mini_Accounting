package pl.qbala.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.pl.NIP;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String password;

    //@NIP
    private String nip;

    @Email
    private String email;

    private String type;

    @OneToMany(mappedBy = "contractor", /*cascade = CascadeType.MERGE,*/ fetch = FetchType.EAGER)
    private List<Facture> factures = new ArrayList<>();

//    @OneToMany(mappedBy = "seller", cascade = CascadeType.MERGE)
//    private List<Facture> facturesSell = new ArrayList<>();

    public Company(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
  }
//
//    public List<Facture> getFacturesBuy() {
//        return facturesBuy;
//    }
//
//    public void setFacturesBuy(List<Facture> facturesBuy) {
//        this.facturesBuy = facturesBuy;
//    }
//
//    public List<Facture> getFacturesSell() {
//        return facturesSell;
//    }
//
//    public void setFacturesSell(List<Facture> facturesSell) {
//        this.facturesSell = facturesSell;
//    }
}
