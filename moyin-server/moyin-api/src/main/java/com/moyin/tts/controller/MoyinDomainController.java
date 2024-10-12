package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.tts.domain.MoyinDomain;
import com.moyin.tts.service.IMoyinDomainService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/moyin/domain")
public class MoyinDomainController extends BaseController {

    @Resource
    private IMoyinDomainService domainService;

    @PreAuthorize("@ss.hasPermi('moyin:domain:list')")
    @Log(title = "领域列表", businessType = BusinessType.EXPORT)
    @GetMapping("/list")
    public TableDataInfo list(MoyinDomain domain) {
        startPage();
        List<MoyinDomain> list = domainService.selectList(domain);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:domain:export')")
    @Log(title = "领域导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinDomain domain) {
        List<MoyinDomain> list = domainService.selectList(domain);
        ExcelUtil<MoyinDomain> util = new ExcelUtil<>(MoyinDomain.class);
        util.exportExcel(response, list, "配音员分类数据");
    }

    @PreAuthorize("@ss.hasPermi('moyin:domain:query')")
    @Log(title = "领域详情", businessType = BusinessType.EXPORT)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(domainService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:domain:add')")
    @Log(title = "新增领域", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinDomain domain) {
        return toAjax(domainService.insert(domain));
    }

    @PreAuthorize("@ss.hasPermi('moyin:domain:edit')")
    @Log(title = "修改领域", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinDomain domain) {
        return toAjax(domainService.updateById(domain));
    }

    @PreAuthorize("@ss.hasPermi('moyin:domain:remove')")
    @Log(title = "删除领域", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(domainService.deleteByIds(ids));
    }
}
