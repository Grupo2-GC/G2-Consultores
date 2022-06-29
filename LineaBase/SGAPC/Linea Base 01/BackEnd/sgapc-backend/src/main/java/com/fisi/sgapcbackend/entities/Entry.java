package com.fisi.sgapcbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entries")
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@JsonIgnoreProperties(value={"entries", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    //@ManyToOne(fetch = FetchType.LAZY)
    //private Supplier supplier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler"}, allowSetters=true)
    private Supplier supplier;

    @Column(length = 60)
    private String type_receipt;

    private Float total;
    private LocalDateTime date_entry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler"}, allowSetters=true)
    private User user;

    private Float igv;

    @Column(length = 60)
    private String num_receipt;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "entry_id")
    private List<EntryDetail> details;
}
