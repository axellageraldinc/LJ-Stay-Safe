package lj.com.ljstaysafe.contract;

import java.util.List;

import lj.com.ljstaysafe.model.DeveloperLog;

public interface DeveloperLogContract {
    interface View{
        void loadDeveloperLog(List<DeveloperLog> developerLogList);
    }
    interface Presenter{
        void saveLog(DeveloperLog developerLog);
        void loadLogs();
    }
}
