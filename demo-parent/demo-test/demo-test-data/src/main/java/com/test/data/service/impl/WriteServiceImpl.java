package com.test.data.service.impl;

import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.data.mybatis.annotation.DataSource;
import com.sfsctech.data.mybatis.datasource.support.DBType;
import com.test.data.repository.mapper.TExamRecordMapper;
import com.test.data.repository.mapper.TExamineesMapper;
import com.test.data.repository.model.domain.TExamRecord;
import com.test.data.repository.model.domain.TExaminees;
import com.test.data.repository.model.example.TExamRecordExample;
import com.test.data.repository.model.example.TExamineesExample;
import com.test.data.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class WriteServiceImpl
 *
 * @author 张麒 2022-6-24.
 * @version Description:
 */
@Transactional
@Service
@DataSource
public class WriteServiceImpl implements WriteService {

    @Autowired
    private TExamineesMapper mapper;

    @Override
    public void read() {
        List<TExaminees> list = mapper.selectByExample(new TExamineesExample());
        list.forEach(r -> System.out.println(FastJson.toJSONString(r)));
    }
}
