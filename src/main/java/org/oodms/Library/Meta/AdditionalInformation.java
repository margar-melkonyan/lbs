package org.oodms.Library.Meta;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
public class AdditionalInformation {
    Timestamp CreatedAt;
    Timestamp UpdatedAt;

    public AdditionalInformation() {}

    public void setCreatedAt(Timestamp createdAt) {
        CreatedAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        UpdatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return String.format("AdditionalInformation [CreatedAt=%s, %s]",CreatedAt.toString(), UpdatedAt.toString());
    }
}
