package club.tacbin.ggen.dto;

import lombok.Data;

/**
 * @description:
 * @author: tacbin
 * @date: 2021-11-09
 **/
@Data
public class GoCodeGenDTO {
    private String filePath;
    private String url;
    private String userName;
    private String password;
    private String tables;
}
