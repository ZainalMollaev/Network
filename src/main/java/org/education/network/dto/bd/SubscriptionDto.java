package org.education.network.dto.bd;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SubscriptionDto {

    private String name;
    private String company;
    private String title;
    private String location;
    private List<byte[]> imgs;

}
