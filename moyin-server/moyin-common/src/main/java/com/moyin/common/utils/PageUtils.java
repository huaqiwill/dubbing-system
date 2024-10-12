package com.moyin.common.utils;

import com.github.pagehelper.PageHelper;
import com.moyin.common.core.page.PageDomain;
import com.moyin.common.core.page.TableSupport;
import com.moyin.common.utils.sql.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页工具类
 *
 * @author moyin
 */
public class PageUtils extends PageHelper {
    private static final Logger log = LoggerFactory.getLogger(PageUtils.class);

    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        log.warn("页码 {} 页尺寸 {}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage() {
        PageHelper.clearPage();
    }
}
