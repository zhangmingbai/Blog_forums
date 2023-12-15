package sw.com.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sw.com.entity.RestBean;
import sw.com.utils.AliOssUtil;

import java.util.UUID;

/**
 * 文件接口
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @PostMapping("/upload")
    public RestBean<String> upload(MultipartFile file) throws Exception {
        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的,从而防止文件覆盖
        String filename = null;
        if (originalFilename != null) {
            filename = UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String url = AliOssUtil.uploadFile(filename,file.getInputStream());
        return RestBean.success(url);
    }

}

