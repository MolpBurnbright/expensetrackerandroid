package com.kapirawan.financial_tracker.preference;

import android.content.SharedPreferences;

public class Preference {
    private static final String USERID = "com.kapirawan.financial_tracker.preference.userId";
    private static final String DATASOURCEID = "com.kapirawan.financial_tracker.preference.userId";
    private static final String ACCOUNTID = "com.kapirawan.financial_tracker.preference.acountId";
    private static final String ACCOUNTDATASOURCEID =
            "com.kapirawan.financial_tracker.preference.acountDatashourceId";

    private static SharedPreferences sharedPreferences;
    private static long userId;
    private static long datasourceId;
    private static long accountId;
    private static long accountDatasourceId;

    public static void init (SharedPreferences pref){
        sharedPreferences = pref;
        userId = pref.getLong(USERID,  0);
        datasourceId = pref.getLong(DATASOURCEID, 0);
        accountId = pref.getLong(ACCOUNTID, 1);
        accountDatasourceId = pref.getLong(ACCOUNTDATASOURCEID, 0);
    }

    public static long getUserId(){
        return userId;
    }

    public static long getDatasourceId(){
        return datasourceId;
    }

    public static long getAccountId(){
        return accountId;
    }

    public static long getAccountDatasourceId(){
        return accountDatasourceId;
    }

    public static void setDatasourceId(long id){
        userId = id;
        sharedPreferences.edit().putLong(USERID, id);
    }

    public static void setUserId(long id){
        userId = id;
        sharedPreferences.edit().putLong(DATASOURCEID, id);
    }

    public static void setAccountId(long id){
        accountId = id;
        sharedPreferences.edit().putLong(ACCOUNTID, id);
    }

    public static void setAccountDatasourceId(long id){
        accountDatasourceId = id;
        sharedPreferences.edit().putLong(ACCOUNTDATASOURCEID, id);
    }
}