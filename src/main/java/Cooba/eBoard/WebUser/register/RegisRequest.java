package Cooba.eBoard.WebUser.register;

import lombok.Data;
import org.intellij.lang.annotations.Pattern;

@Data
public class RegisRequest {
    private String username;
    @Pattern("^[a-zA-Z0-9._:$!%-]+@[a-zA-Z0-9.-]+.[a-zA-Z]$")
    private String email;
    @Pattern("[A-Za-z0-9]{6,}")
    private String password;
}
