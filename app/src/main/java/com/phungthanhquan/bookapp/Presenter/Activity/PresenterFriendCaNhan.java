package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.FriendCaNhanModel;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityFriendCaNhan;

import java.util.List;

public class PresenterFriendCaNhan implements InPresenterFriendCaNhan {
    InterfaceViewActivityFriendCaNhan interfaceViewActivityFriendCaNhan;
    FriendCaNhanModel friendCaNhanModel;

    public PresenterFriendCaNhan(InterfaceViewActivityFriendCaNhan interfaceViewActivityFriendCaNhan) {
        this.interfaceViewActivityFriendCaNhan = interfaceViewActivityFriendCaNhan;
        friendCaNhanModel = new FriendCaNhanModel();
    }

    @Override
    public void layListFriend() {
        friendCaNhanModel.getFriendList(new FriendCaNhanModel.callBaclFriendCanhan() {
            @Override
            public void myCallBack(List<Friend> friendList) {
                interfaceViewActivityFriendCaNhan.laydanhsachban(friendList);
            }
        });
    }

    @Override
    public void layListFriendTheoDoi() {
        friendCaNhanModel.getFriendTheoDoiList(new FriendCaNhanModel.callBaclFriendCanhan() {
            @Override
            public void myCallBack(List<Friend> friendList) {
                interfaceViewActivityFriendCaNhan.hienthilist(friendList);
            }
        });
    }
}
