package com.czt.web.scheduletask;

import com.czt.dao.StatisticMapper;
import com.czt.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private MailUtils mailUtils;
    @Value("${spring.mail.username}")
    private String mailto;

    /**
     * 定时邮件发送任务，每月1日中午12点整发送邮件
     */
//    @Scheduled(cron = "0 0 12 1 * ?")   //    每12点发送
    @Scheduled(cron = "0 */1 * * * ? ")     //每三分钟发送
    public void sendEmail(){
        //  定制邮件内容
        long totalvisit = statisticMapper.getTotalVisit();
        long totalComment = statisticMapper.getTotalComment();
        StringBuffer content = new StringBuffer();
        content.append("博客系统总访问量为："+totalvisit+"人次").append("\n");
        content.append("博客系统总评论量为："+totalComment+"人次").append("\n");
        mailUtils.sendSimpleEmail(mailto,
                "个人博客系统流量统计情况",content.toString());
    }
}

