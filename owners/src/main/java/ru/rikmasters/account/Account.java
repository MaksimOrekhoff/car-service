package ru.rikmasters.account;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "accounts", schema = "public")
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "green_balance")
    private Double greenBalance;
    @Column(name = "red_balance")
    private Double redBalance;
    @Column(name = "blue_balance")
    private Double blueBalance;
    @Column(name = "owner_id")
    private Long ownerId;
}
