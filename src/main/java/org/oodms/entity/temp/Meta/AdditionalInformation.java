package org.oodms.entity.temp.Meta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class AdditionalInformation {
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Override
    public String toString() {
        return String.format("AdditionalInformation [CreatedAt=%s, %s]", createdAt.toString(), updatedAt.toString());
    }
}
