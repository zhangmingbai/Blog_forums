package sw.com.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import sw.com.entity.RestBean;
import sw.com.entity.dto.Notice;
import sw.com.service.NoticeService;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;


    //新增
    @PostMapping("/add")
    public RestBean<Void> add(@RequestBody Notice notice) {
        notice.setTime(DateUtil.today());
        notice.setUser("admin");
        noticeService.save(notice);
        return RestBean.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public RestBean<Void> deleteById(@PathVariable Integer id) {
        noticeService.removeById(id);
        return RestBean.success();
    }

    //批量删除
    @DeleteMapping("/delete/batch")
    public RestBean<Void> deleteBatch(@RequestBody List<Integer> ids) {
        noticeService.removeBatchByIds(ids);
        return RestBean.success();
    }

    //修改
    @PutMapping("/update")
    public RestBean<Void> updateById(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return RestBean.success();
    }

    //根据ID查询
    @GetMapping("/selectById/{id}")
    public RestBean<Notice> selectById(@PathVariable Integer id) {
        Notice notice = noticeService.getById(id);
        return RestBean.success(notice);
    }

    //查询所有
    @GetMapping("/selectAll")
    public RestBean<List<Notice>> selectAll() {
        List<Notice> notices = noticeService.list();
        return RestBean.success(notices);
    }

    //分页查询
    @GetMapping("/selectPage")
    public RestBean<PageInfo<Notice>> selectByPage(Notice notice,
                                                   @RequestParam Integer pageNum,
                                               @RequestParam Integer pageSize) {
        PageInfo<Notice> page = noticeService.selectPage(notice,pageNum, pageSize);
        return RestBean.success(page);
    }
}
