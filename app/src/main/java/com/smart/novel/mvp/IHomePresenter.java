package com.smart.novel.mvp;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：
 */
public interface IHomePresenter {
    void getHeaderData(String requestType);

    void getAdmitLivingData(String requestType);

    void getLivingUserData(String requestType);

    void getAlertData(String requestType);

    void getLivingTrendData(String requestType);

    void getUserNurseData(String requestType);

    void getStaffData(String requestType);

    void getBranchAddress(String requestType);

    void getNurseProgressList(String requestType);
}
