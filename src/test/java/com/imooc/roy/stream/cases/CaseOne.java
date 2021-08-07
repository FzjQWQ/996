package com.imooc.roy.stream.cases;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查找有缺考情况的学生姓名
 * @author roy f
 */
public class CaseOne {

    @Data
    @AllArgsConstructor
    class ExamStudentScore {
        /**
         * 学生姓名
         */
        private String studentName;
        /**
         * 成绩
         */
        private Integer scoreValue;
        /**
         * 科目
         */
        private String subject;
    }


    /**
     * 学生考试成绩
     */
    Map<String, List<ExamStudentScore>> studentMap;

    @Before
    public void init() {
        studentMap = new HashMap<>();

        List<ExamStudentScore> zsScoreList = new ArrayList<>();
        zsScoreList.add(
                new ExamStudentScore(
                        "张三",
                        30,
                        "CHINESE"));
        zsScoreList.add(
                new ExamStudentScore(
                        "张三",
                        40,
                        "ENGLISH"));
        zsScoreList.add(
                new ExamStudentScore(
                        "张三",
                        50,
                        "MATHS"));
        studentMap.put("张三", zsScoreList);

        List<ExamStudentScore> lsScoreList = new ArrayList<>();
        lsScoreList.add(
                new ExamStudentScore(
                        "李四",
                        80,
                        "CHINESE"));
        lsScoreList.add(
                new ExamStudentScore(
                        "李四",
                        null,
                        "ENGLISH"));
        lsScoreList.add(
                new ExamStudentScore(
                        "李四",
                        100,
                        "MATHS"));
        studentMap.put("李四", lsScoreList);

        List<ExamStudentScore> wwScoreList = new ArrayList<>();
        wwScoreList.add(
                new ExamStudentScore(
                        "王五",
                        null,
                        "CHINESE"));
        wwScoreList.add(
                new ExamStudentScore(
                        "王五",
                        null,
                        "ENGLISH"));
        wwScoreList.add(
                new ExamStudentScore(
                        "王五",
                        70,
                        "MATHS"));
        studentMap.put("王五", wwScoreList);
    }

    @Test
    public void findStudentByRoy() {
        List<String> studentList = new ArrayList<>();
        studentMap.forEach((key,value)->{
            List<String> collect = value.stream()
                    .filter(examStudentScore -> examStudentScore.getScoreValue() == null)
                    .map(ExamStudentScore::getStudentName)
                    .collect(Collectors.toList());
            studentList.addAll(collect);
        });
        System.out.println(JSON.toJSONString(studentList, true));
    }

    @Test
    public void findStudentByMooc() {
        studentMap.forEach((studentName,scoreList)->{
            boolean bool = scoreList
                    .stream()
                    .anyMatch(score -> score.getScoreValue() == null);
            if (bool) {
                System.out.println("此学生[" + studentName + "]有缺考情况");
            }
        });
    }
}
