package pl.qbala.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(/*cascade = CascadeType.MERGE*/)
    private Company contractor;

//    @ManyToOne
//    private Company buyer;

    private String number;

    private float quantity;

    private float priceNet;

    private String description;

    private LocalDate dateOfIssue;

    private LocalDate deadline;

    private int taxQualification;

    private Integer vat;

    private Boolean control = false;

    public Facture(){}

    public Company getContractor() {
        return contractor;
    }

    public void setContractor(Company contractor) {
        this.contractor = contractor;
    }

    public int getTaxQualification() {
        return taxQualification;
    }

    public Boolean getControl() {
        return control;
    }

    public void setControl(Boolean control) {
        this.control = control;
    }

    public void setTaxQualification(int taxQualification) {
        this.taxQualification = taxQualification;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Company getSeller() {
//        return seller;
//    }
//
//    public void setSeller(Company seller) {
//        this.seller = seller;
//    }
//
//    public Company getBuyer() {
//        return buyer;
//    }
//
//    public void setBuyer(Company buyer) {
//        this.buyer = buyer;
//    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(float priceNet) {
        this.priceNet = priceNet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
