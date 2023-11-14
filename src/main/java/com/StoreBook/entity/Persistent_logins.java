package com.StoreBook.entity;


import lombok.*;


import java.util.Date;

import javax.persistence.*;



@Table(name = "persistent_logins")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Persistent_logins {
    private String username;

    @Id
    private String series;

    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_used;
}
