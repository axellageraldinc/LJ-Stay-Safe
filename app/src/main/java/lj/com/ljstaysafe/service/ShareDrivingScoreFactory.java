package lj.com.ljstaysafe.service;

import android.content.Context;

import lj.com.ljstaysafe.contract.SeeDrivingScoreContract;
import lj.com.ljstaysafe.model.ShareDrivingScoreDestination;

public class ShareDrivingScoreFactory {
    public static ShareDrivingScoreService getShareDrivingScoreService(SeeDrivingScoreContract.View view,
                                                                       Context context,
                                                                       ShareDrivingScoreDestination shareDrivingScoreDestination){
        if(shareDrivingScoreDestination == ShareDrivingScoreDestination.STAY_SAFE){
            return new ShareDrivingScoreOnLjStaySafe(view, context);
        }
        return null;
    }
}
