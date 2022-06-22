package cn.zjavax.demo.util;

import lombok.Data;

import java.util.List;

@Data
public class ResultPage<T> {
//    int page;
//    int per_page;
    int total_count;
    List<T> results;
}
