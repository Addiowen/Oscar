package com.compulynx.compas.models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailObject {
    private String customerCode;
    private String productType;
    private String userId;
    private String customerAccount;
}
