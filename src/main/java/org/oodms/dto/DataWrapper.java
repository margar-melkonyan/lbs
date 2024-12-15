package org.oodms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.oodms.entity.User;

import java.util.List;

@Data
public class DataWrapper {
    private List<User> users;
}


