package com.rzyc.fulongapi.controller;

import com.common.utils.Arith;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.TypeConversion;
import com.common.utils.model.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rzyc.fulongapi.bean.check.RectifyStatistics;
import com.rzyc.fulongapi.bean.index.*;
import com.rzyc.fulongapi.bean.news.NewsPageDto;
import com.rzyc.fulongapi.enums.DangerLevel;
import com.rzyc.fulongapi.enums.DangerState;
import com.rzyc.fulongapi.enums.IsNot;
import com.rzyc.fulongapi.enums.UserType;
import com.rzyc.fulongapi.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Api(tags = "首页")
@CrossOrigin("*")
@RequestMapping("index")
@Controller
@Validated
public class IndexController extends BaseController {


    @ApiOperation(value = "banner列表", notes = "banner列表")
    @GetMapping("/generateCode")
    @ResponseBody
    public MultiResult<News> bannerList() throws Exception {
        MultiResult<News> result = new MultiResult<>();
        List<News> banners = new ArrayList<>();
        banners = newsMapper.findByBannerState(IsNot.IS.getState());
        if (null == banners || 0 == banners.size()) {
            banners = newsMapper.findByTime();
        }
        if (null != banners && banners.size() > 0) {
            for (News news : banners) {
                handleNewsCover(news);
            }
        }
        result.setData(banners);
        return result;
    }

    @ApiOperation(value = "新闻分页", notes = "新闻分页")
    @GetMapping("/newsPage")
    @ResponseBody
    public SingleResult<Pager<News>> newsPage(@Valid NewsPageDto newsPageDto) throws Exception {
        SingleResult<Pager<News>> result = new SingleResult<>();
        Pager<News> pager = new Pager<>();
        String conditions = TypeConversion.getCondition(newsPageDto.getCondition());
        PageHelper.startPage(newsPageDto.getPage(), newsPageDto.getPageSize());
        Page<News> page = (Page<News>) newsMapper.newsList(conditions);
        getDatePage(pager, page);
        for (News news : pager.getRows()) {
            handleNewsCover(news);
        }
        result.setData(pager);
        return result;
    }

    @ApiOperation(value = "新闻详情", notes = "新闻详情")
    @GetMapping("/newsDetail/{newsId}")
    @ApiImplicitParam(name = "newsId", value = "新闻id", required = true)
    @ResponseBody
    public SingleResult<News> newsDetail(@PathVariable String newsId) throws Exception {
        SingleResult<News> result = new SingleResult<>();

        News news = newsMapper.selectDetail(newsId);
        if (null != news) {

            //新闻内容
            NewsContent newsContent = newsContentMapper.findByNewsId(news.getNewsId());
            if (null != newsContent) {
                news.setContentText(newsContent.getContentText());
            }
            handleNewsCover(news);
            result.setData(news);
        } else {
            result.setCode(Code.NO_DATA.getCode());
            result.setMessage(Message.NO_DATA);
        }
        return result;
    }

    /**
     * 处理新闻封面图
     *
     * @param news
     * @throws Exception
     */
    private void handleNewsCover(News news) throws Exception {
        news.setCover(setServiceFile(news.getCover()));
    }

    @ApiOperation(value = "整改统计&隐患统计", notes = "整改统计&隐患统计")
    @GetMapping("/rectifyStatistics")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    @ResponseBody
    public SingleResult<RectifyStatistics> rectifyStatistics(String userId) throws Exception {
        SingleResult<RectifyStatistics> result = new SingleResult<>();

        RectifyStatistics statistics = new RectifyStatistics();

        System.out.println("userId -> " + userId);
        List<String> buildIds = new ArrayList<>();
        List<String> unitIds = new ArrayList<>();


        SysUser sysUser = sysUserMapper.selectById(userId);

        //判断企业类型
        if (StringUtils.isNotBlank(userId)) {

            if (null != sysUser) {
                if (UserType.BUILD.getType() == sysUser.getUserType()) {
                    buildIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                } else if (UserType.UNIT.getType() == sysUser.getUserType()) {
                    unitIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                }
            }
        }

        //隐患总数
        Integer totalNum = 0;
        //已整改
        Integer rectifyNum = 0;
        //整改中数量
        Integer rectifyingNum = 0;
        //未整改数量
        Integer notRectifyNum = 0;
        //一般隐患数量
        Integer commonlyNum = 0;

        if(UserType.ENT.getType() == sysUser.getUserType()){

            String enterpriseId = sysUser.getEnterpriseId();

            //企业账号
            //隐患总数
            totalNum = checkDangerMapper.countEntByState(null, enterpriseId);
            statistics.setTotalNum(totalNum);

            //已整改
            rectifyNum = checkDangerMapper.countEntByState(DangerState.RECTIFYED.getState(), enterpriseId);
            statistics.setRectifyNum(rectifyNum);

            //整改中数量
            rectifyingNum = checkDangerMapper.countEntByState(DangerState.RECTIFYING.getState(), enterpriseId);
            statistics.setRectifyingNum(rectifyingNum);

            //未整改数量
            notRectifyNum = checkDangerMapper.countEntByState(DangerState.NOT_RECTIFY.getState(), enterpriseId);
            statistics.setNotRectifyNum(notRectifyNum);

            //一般隐患数量
            commonlyNum = checkDangerMapper.countEntByLevel(DangerLevel.COMMONLY.getState(), enterpriseId);
            statistics.setCommonlyNum(commonlyNum);

        }else{
            //政府账号

            //隐患总数
            totalNum = checkDangerMapper.countByState(null, buildIds, unitIds);
            statistics.setTotalNum(totalNum);

            //已整改
            rectifyNum = checkDangerMapper.countByState(DangerState.RECTIFYED.getState(), buildIds, unitIds);
            statistics.setRectifyNum(rectifyNum);

            //整改中数量
            rectifyingNum = checkDangerMapper.countByState(DangerState.RECTIFYING.getState(), buildIds, unitIds);
            statistics.setRectifyingNum(rectifyingNum);

            //未整改数量
            notRectifyNum = checkDangerMapper.countByState(DangerState.NOT_RECTIFY.getState(), buildIds, unitIds);
            statistics.setNotRectifyNum(notRectifyNum);

            //一般隐患数量
            commonlyNum = checkDangerMapper.countByLevel(DangerLevel.COMMONLY.getState(), buildIds, unitIds);
            statistics.setCommonlyNum(commonlyNum);
        }



        //不能整改隐患
        Integer unableRectify = notRectifyNum;
        statistics.setUnableRectify(unableRectify);

        //重大隐患数量
        Integer majorNum = totalNum - commonlyNum;
        statistics.setMajorNum(majorNum);

        //待整改隐患
        Integer stayRectifyNum = rectifyingNum + notRectifyNum;
        statistics.setStayRectifyNum(stayRectifyNum);

        //整改完成率
        Double rate = 0.0;
        if (totalNum > 0) {
            rate = Arith.div(rectifyNum, totalNum, 2);
            rate = rate * 100;
            rate = TypeConversion.decimalFormat(rate, 2);
        }
        statistics.setRectifyRate(rate);

        result.setData(statistics);

        return result;
    }

    @ApiOperation(value = "社区基本数据", notes = "社区基本数据")
    @GetMapping("/communityInfo")
    @ResponseBody
    public SingleResult<CommunityInfo> communityInfo() throws Exception {
        SingleResult<CommunityInfo> result = new SingleResult<>();

        CommunityInfo communityInfo = new CommunityInfo();

        //面积
        Double areaNum = 826000.0;
        communityInfo.setAreaNum(areaNum);

        //栋数
        List<Building> buildings = buildingMapper.findAll();
        Integer buildNum = buildings.size();
        communityInfo.setBuildNum(buildNum);

        //单元数
        List<BuildUnit> units = buildUnitMapper.findAll();
        Integer unitNum = units.size();
        communityInfo.setUnitNum(unitNum);

        //户数
        Integer houseNum = buildUnitMapper.sumFloor();
        houseNum = 11500;
        communityInfo.setHouseNum(houseNum);

        result.setData(communityInfo);
        return result;
    }

    @ApiOperation(value = "商户统计", notes = "商户统计")
    @GetMapping("/entNumInfo")
    @ResponseBody
    public MultiResult<EntNumInfo> entNumInfo() throws Exception {
        MultiResult<EntNumInfo> result = new MultiResult<>();

        List<EntNumInfo> entNums = new ArrayList<>();

        //统计分类数量
        Integer index = 7;
        List<EntNumInfo> entNumInfos = enterpriseMapper.entNumInfo();
        if (entNumInfos.size() > index) {
            entNums = entNumInfos.subList(0, index);

            String typeName = "其他分类";
            Integer entNum = 0;
            for (Integer i = index; i < entNumInfos.size(); i++) {
                entNum += entNumInfos.get(i).getEntNum();
            }

            EntNumInfo otherEnt = new EntNumInfo();
            otherEnt.setEntNum(entNum);
            otherEnt.setTypeName(typeName);
            entNums.add(otherEnt);

        } else {
            entNums = entNumInfos;
        }
        Integer count = enterpriseMapper.countAll();
        result.setData(entNums);
        result.setCount(count);
        return result;
    }

    @ApiOperation(value = "商户统计", notes = "商户统计")
    @GetMapping("/entNumInfoAll")
    @ResponseBody
    public MultiResult<EntNumInfo> entNumInfoAll() throws Exception {
        MultiResult<EntNumInfo> result = new MultiResult<>();

        List<EntNumInfo> entNums = new ArrayList<>();

        //统计分类数量
        Integer index = 100;
        List<EntNumInfo> entNumInfos = enterpriseMapper.entNumInfo();
        if (entNumInfos.size() > index) {
            entNums = entNumInfos.subList(0, index);

            String typeName = "其他分类";
            Integer entNum = 0;
            for (Integer i = index; i < entNumInfos.size(); i++) {
                entNum += entNumInfos.get(i).getEntNum();
            }

            EntNumInfo otherEnt = new EntNumInfo();
            otherEnt.setEntNum(entNum);
            otherEnt.setTypeName(typeName);
            entNums.add(otherEnt);

        } else {
            entNums = entNumInfos;
        }
        Integer count = enterpriseMapper.countAll();
        result.setData(entNums);
        result.setCount(count);
        return result;
    }

    @ApiOperation(value = "燃气设备隐患统计", notes = "燃气设备隐患统计")
    @GetMapping("/buildDangerNum")
    @ResponseBody
    public SingleResult<DangerNum> buildDangerNum() throws Exception {
        SingleResult<DangerNum> result = new SingleResult<>();

        DangerNum dangerNum = new DangerNum();


        //下标
        List<Building> buildings = buildingMapper.findAll();
        List<String> buildNams = new LinkedList<>();
        for (Building building : buildings) {
            buildNams.add(building.getBuildName());
        }
        dangerNum.setTitles(buildNams);

        //数据
        Integer index = 3;
        List<BrokenLine> brokenLines = new ArrayList<>();
        List<DangerType> dangerTypes = dangerTypeMapper.findByIndex(index);
        for (DangerType dangerType : dangerTypes) {
            BrokenLine brokenLine = new BrokenLine();
            brokenLine.setName(dangerType.getDangerTypeName());
            List<Integer> datas = new LinkedList<>();
            List<BuildDangerNum> buildDangerNums = buildingMapper.buildDangerNum(dangerType.getDangerTypeId());
            for (BuildDangerNum buildDangerNum : buildDangerNums) {
                datas.add(buildDangerNum.getDangerNum());
            }
            brokenLine.setData(datas);
            brokenLines.add(brokenLine);
        }
        dangerNum.setBrokenLines(brokenLines);

        result.setData(dangerNum);
        return result;
    }

    @ApiOperation(value = "新闻新增或修改", notes = "新闻新增或修改")
    @PostMapping("/newsChange")
    @ResponseBody
    public SingleResult<String> newsChange(@Valid @RequestBody NewsChangeDto newsChangeDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();

        News news = new News();
        BeanUtils.copyProperties(news, newsChangeDto);

        String userId = getUserId();
        news.setModifyBy(userId);
        news.setModifyTime(new Date());

        News oldNews = newsMapper.selectById(news.getNewsId());
        if (null != oldNews) {
            newsMapper.updateById(news);
        } else {
            news.setCreateTime(new Date());
            news.setCreateBy(userId);
            newsMapper.insert(news);
        }

        //先删除动态内容，在新增内容
        newsContentMapper.delByNewsId(news.getNewsId());

        //新增动态内容
        NewsContent newsContent = new NewsContent();
        newsContent.setContentId(RandomNumber.getUUid());
        newsContent.setNewsId(news.getNewsId());
        newsContent.setContentText(newsChangeDto.getContentText());
        newsContent.setModifyBy(userId);
        newsContent.setModifyTime(new Date());
        newsContent.setCreateTime(new Date());
        newsContent.setCreateBy(userId);
        newsContentMapper.insert(newsContent);

        return result;
    }


}
