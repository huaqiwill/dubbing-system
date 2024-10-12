package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.tts.domain.MoyinMember;
import com.moyin.tts.service.IMoyinMemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/moyin/member")
public class MoyinMemberController extends BaseController {

    @Resource
    private IMoyinMemberService memberService;

    /**
     * 查询配音员分类列表
     */
    @PreAuthorize("@ss.hasPermi('moyin:member:list')")
    @Log(title = "会员列表", businessType = BusinessType.EXPORT)
    @GetMapping("/list")
    public TableDataInfo list(MoyinMember moyinCategory) {
        startPage();
        List<MoyinMember> list = memberService.selectList(moyinCategory);
        return getDataTable(list);
    }

    /**
     * 导出配音员分类列表
     */
    @PreAuthorize("@ss.hasPermi('moyin:member:export')")
    @Log(title = "导出会员列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinMember moyinCategory) {
        List<MoyinMember> list = memberService.selectList(moyinCategory);
        ExcelUtil<MoyinMember> util = new ExcelUtil<>(MoyinMember.class);
        util.exportExcel(response, list, "配音员分类数据");
    }

    /**
     * 获取配音员分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('moyin:member:query')")
    @Log(title = "会员详情", businessType = BusinessType.EXPORT)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(memberService.selectById(id));
    }

    /**
     * 新增配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:member:add')")
    @Log(title = "新增会员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinMember moyinCategory) {
        return toAjax(memberService.insert(moyinCategory));
    }

    /**
     * 修改配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:member:edit')")
    @Log(title = "修改会员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinMember moyinCategory) {
        return toAjax(memberService.updateById(moyinCategory));
    }

    /**
     * 删除配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:member:remove')")
    @Log(title = "删除会员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(memberService.deleteByIds(ids));
    }
}
