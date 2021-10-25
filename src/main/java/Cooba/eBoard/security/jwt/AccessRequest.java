package Cooba.eBoard.security.jwt;

import lombok.Data;

@Data
public class AccessRequest {
    private String username;
    private String password;
}
