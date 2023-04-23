package com.efesco.mailsvc.inf;

import com.efesco.mailsvc.model.EmailBatchModel;
import com.efesco.mailsvc.model.EmailBatchStatus;
import com.efesco.mailsvc.model.EmailBlackList;

import java.util.List;

public interface MailService {
    
    EmailBatchStatus sendSalaryEmailBatch(String servicePassword,
                                          EmailBatchModel emailModel);

    long sendEmailBatch(String servicePassword,
                        EmailBatchModel emailModel) throws Exception;

    EmailBatchStatus sendSynEmailBatch(String servicePassword,
                                       EmailBatchModel emailModel, String mailcode) throws Exception;

    void dealconfigDbEmail(String servicePassword, String dbName)
            throws Exception;

    EmailBatchStatus queryBatchStatus(String servicePassword,
                                      long batchNo) throws Exception;

    List<EmailBlackList> getBlackMaps(String bizType, String servicePassword) throws Exception;
}
