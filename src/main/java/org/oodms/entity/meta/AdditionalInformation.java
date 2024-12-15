package org.oodms.entity.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AdditionalInformation {
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
