package club.tacbin.ggen.web;

import club.tacbin.ggen.dto.GoCodeGenDTO;
import club.tacbin.ggen.util.EntityGenUtil;
import club.tacbin.ggen.util.ZipUtil;
import org.springframework.http.server.reactive.HttpHeadResponseDecorator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @description: go generate
 * @author: tacbin
 * @date: 2021-11-09
 **/
@RestController
@RequestMapping("go")
public class GoGenerateController {

    @PostMapping("genfile")
    public void generator(HttpServletResponse response,   GoCodeGenDTO goCodeGenDTO) {
        String uuid = UUID.randomUUID().toString();
        goCodeGenDTO.setFilePath(uuid);
        EntityGenUtil.doGen(goCodeGenDTO);
        ZipUtil.downloadZip(uuid, response);
    }
}
