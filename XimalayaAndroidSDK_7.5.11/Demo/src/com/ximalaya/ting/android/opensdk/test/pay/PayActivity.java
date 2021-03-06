package com.ximalaya.ting.android.opensdk.test.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.test.android.R;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.history.PlayHistoryList;
import com.ximalaya.ting.android.opensdk.model.track.BatchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;
import com.ximalaya.ting.android.opensdk.test.XMAuthDemoActivity;
import com.ximalaya.ting.android.opensdk.test.download.BatchDownloadPayActivity;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;
import com.ximalaya.ting.android.sdkdownloader.downloadutil.IDoSomethingProgress;
import com.ximalaya.ting.android.sdkdownloader.exception.AddDownloadException;
import com.ximalaya.ting.android.xmpayordersdk.IXmPayOrderListener;
import com.ximalaya.ting.android.xmpayordersdk.PayFinishModel;
import com.ximalaya.ting.android.xmpayordersdk.PayOrderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by le.xin on 2017/5/10.
 */

public class PayActivity extends Activity implements IXmPlayerStatusListener {
    public static final String TAG = "PayActivity";

    private ListView mListView;

    private int page = 0;
    private AlbumList mAlbumList;
    private long albumId;

    private long mLastTrackId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_pay);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this ,android.R.layout.simple_expandable_list_item_1 ,android.R.id.text1 ,new ArrayList<String>() {
            {
                add("0 ????????????");
                add("1 ?????????????????????");
                add("2 ?????????????????????(????????????????????????)");
                add("3 ?????????????????????");
                add("4 ????????????(???????????????,??????????????????????????????)");
                add("5 ??????????????????");
                add("6 ????????????(???????????????,????????????????????????)");
                add("7 ????????????(???????????????,?????????????????????)");
                add("8 ???????????????????????????");
                add("9 ??????????????????");
                add("10 ????????????????????????");
                add("11 ??????");
                add("12 ???????????????????????????");
                add("13 ????????????????????????");
            }
        }));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // ????????????
                        Map<String ,String> maps = new HashMap<String, String>();
                        maps.put("page" ,++page + "");
                        maps.put("count" ,"200");
                        CommonRequest.getAllPaidAlbums(maps, new IDataCallBack<AlbumList>() {
                            @Override
                            public void onSuccess(AlbumList object) {
                                mAlbumList = object;
                                System.out.println("object = [" + object + "]");
                            }

                            @Override
                            public void onError(int code, String message) {
                                System.out.println("code = [" + code + "], message = [" + message + "]");
                            }
                        });
                        break;
                    case 1: // ?????????????????????
                        if(mAlbumList == null) {
                            Toast.makeText(PayActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        maps = new HashMap<String, String>();
                         albumId = mAlbumList.getAlbums().get(new Random().nextInt(mAlbumList.getAlbums().size()))
                                .getId();
                        maps.put("album_id" , albumId +"");
                        CommonRequest.getPaidTrackByAlbum(maps, new IDataCallBack<TrackList>() {
                            @Override
                            public void onSuccess(TrackList object) {
                                System.out.println("object = [" + object + "]");
                            }

                            @Override
                            public void onError(int code, String message) {
                                System.out.println("code = [" + code + "], message = [" + message + "]");
                            }
                        });
                        break;
                    case 2: // ?????????????????????(????????????????????????)
                        maps = new HashMap<>();
//                        maps.put("album_id" ,"5203860");
                        maps.put("album_id" ,"13394295");
                        maps.put("page" ,"6");
                        CommonRequest.getPaidTrackByAlbum(maps, new IDataCallBack<TrackList>() {
                            @Override
                            public void onSuccess(TrackList trackList) {
                                Track track = trackList.getTracks().get(6);
                                mLastTrackId = track.getDataId();
                                XmPlayerManager.getInstance(PayActivity.this).playList(trackList ,10);
                            }

                            @Override
                            public void onError(int code, String message) {
                            }
                        });
                        break;
                    case 3: // ?????????????????????
                        PayOrderManager.clientPlaceOrderTracks(13394295, new ArrayList<Long>() {
                            {
                                add(mLastTrackId);
                            }
                        }, PayActivity.this, new IXmPayOrderListener() {
                            @Override
                            public void onFinish(PayFinishModel payFinishModel) {
                                System.out.println("PayActivity.onFinish ???????????? --  " + payFinishModel);
                                Toast.makeText(PayActivity.this, "???????????? -- " + payFinishModel, Toast.LENGTH_SHORT).show();
                                if(payFinishModel.getCode() == CODE_PAY_H5_SUCCESS) {
                                    PlayableModel currSound =
                                            XmPlayerManager.getInstance(PayActivity.this).getCurrSound();
                                    if(currSound.getDataId() == mLastTrackId) {
                                        XmPlayerManager.getInstance(PayActivity.this).resetPlayer();
                                        XmPlayerManager.getInstance(PayActivity.this).play(
                                                XmPlayerManager.getInstance(PayActivity.this).getCurrentIndex());
                                    }
                                }
                            }
                        });


                        break;
                    case 4: // ????????????(???????????????,??????????????????????????????)
                        Map map = new HashMap<String, String>();
                        map.put("ids" ,"74158861");
                        CommonRequest.batchPaidTracks(map, new IDataCallBack<BatchTrackList>() {
                            @Override
                            public void onSuccess(BatchTrackList object) {
                                System.out.println("object = [" + object + "]");
                                XmPlayerManager.getInstance(PayActivity.this).playList(object.getTracks() ,0);
                            }

                            @Override
                            public void onError(int code, String message) {
                                System.out.println("code = [" + code + "], message = [" + message + "]");
                            }
                        });
                        break;
                    case 5: // ??????????????????
                        PayOrderManager.clientPlaceOrderAlbum(418783, PayActivity.this, new IXmPayOrderListener() {
                            @Override
                            public void onFinish(PayFinishModel payFinishModel) {
                                Toast.makeText(PayActivity.this, "???????????? -- " + payFinishModel, Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case 6: // ????????????(???????????????,????????????????????????)
                        maps = new HashMap<>();
                        maps.put("album_id" ,"15444721");
                        CommonRequest.getPaidTrackByAlbum(maps, new IDataCallBack<TrackList>() {
                            @Override
                            public void onSuccess(TrackList trackList) {
                                XmPlayerManager.getInstance(PayActivity.this).playList(trackList ,0);
                            }

                            @Override
                            public void onError(int code, String message) {
                                ;
                            }
                        });
                        break;
                    case 7: // ????????????(???????????????,?????????????????????)
                        XmDownloadManager.getInstance().downloadPayTracks(new ArrayList<Long>() {
                            {
                                add(21077011L);
                            }
                        }, true, new IDoSomethingProgress<AddDownloadException>() {
                            @Override
                            public void begin() {
                                Toast.makeText(PayActivity.this, "begin", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void success() {
                                Toast.makeText(PayActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void fail(AddDownloadException ex) {
                                Toast.makeText(PayActivity.this, "fail", Toast.LENGTH_SHORT).show();
                                System.out.println("ex = [" + ex + "]");
                            }
                        });
                        break;
                    case 8: // ???????????????????????????
                        final Track downloadInfo = XmDownloadManager.getInstance().getDownloadInfo(21077011L, true);
                        if (downloadInfo != null) {
                            XmPlayerManager.getInstance(PayActivity.this).playList(new ArrayList<Track>() {
                                {
                                    add(downloadInfo);
                                }
                            }, 0);
                        }
                        break;
                    case 9: // ??????????????????
                        map = new HashMap<String, String>();
                        CommonRequest.getBoughtAlbums(map, new IDataCallBack<AlbumList>() {
                            @Override
                            public void onSuccess(AlbumList object) {
                                System.out.println("object = [" + object + "]");
                            }

                            @Override
                            public void onError(int code, String message) {
                                System.out.println("code = [" + code + "], message = [" + message + "]");
                            }
                        });
                        break;
                    case 10:
                        PayOrderManager.clientPlaceOrderByOrderNum("20170804001702020000000284487932", PayActivity.this, new IXmPayOrderListener() {
                            @Override
                            public void onFinish(PayFinishModel payFinishModel) {
                                System.out.println("?????????????????? === " + payFinishModel);
                            }
                        });
                        break;
                    case 11:
                        startActivity(new Intent(PayActivity.this ,XMAuthDemoActivity.class));
                        break;
                    case 12 :
//                        Intent intent = new Intent(PayActivity.this, PayedAlbumActivity.class);
//                        intent.putExtra("albumId" ,4345263L);
//                        startActivity(intent);
                        Map<String ,String> maps1 = new HashMap<>();
                        CommonRequest.getPlayHistoryByUid(maps1, new IDataCallBack<PlayHistoryList>() {
                            @Override
                            public void onSuccess(@Nullable PlayHistoryList object) {

                            }

                            @Override
                            public void onError(int code, String message) {

                            }
                        });
                    case 13 :
                        Intent intent = new Intent(PayActivity.this, BatchDownloadPayActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        XmPlayerManager.getInstance(this).removePlayerStatusListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        XmPlayerManager.getInstance(this).addPlayerStatusListener(this);
    }

    @Override
    public void onPlayStart() {

    }

    @Override
    public void onPlayPause() {

    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onSoundPlayComplete() {

    }

    @Override
    public void onSoundPrepared() {

    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel curModel) {
        if (curModel == null && lastModel instanceof Track) {
            // isAudition ?????????????????????????????????
            if (((Track) lastModel).isAudition() &&
                    XmPlayerManager.getInstance(getApplicationContext()).getPlayerStatus() == PlayerConstants.STATE_IDLE) {
                // ???????????????????????????????????????,?????????????????????????????????????????????
                new AlertDialog.Builder(PayActivity.this).setMessage("????????????").setNeutralButton("??????", null).create().show();
            }
        }
    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingStop() {

    }

    @Override
    public void onBufferProgress(int percent) {

    }

    @Override
    public void onPlayProgress(int currPos, int duration) {

    }

    @Override
    public boolean onError(XmPlayerException exception) {
        return false;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonRequest.getInstanse().setITokenStateChange(null);
    }
}
