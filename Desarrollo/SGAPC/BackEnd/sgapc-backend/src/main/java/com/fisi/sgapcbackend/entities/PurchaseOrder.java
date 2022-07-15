package com.fisi.sgapcbackend.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
	private Supplier supplier;
    
    
    @Column(name = "date_order", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateOrder;
    
    
    @Column(name = "date_delivery", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateDelivery;
    
    @JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private User user;
    
    
    @JsonIgnoreProperties(value= {"hibernateLazyInitializer", "handler" })
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseorder")
    private Set<PurchaseOrderDetail> purchaseroderdetail;

    
    private String num_receipt;
    
    private Float total;
	
	
}
