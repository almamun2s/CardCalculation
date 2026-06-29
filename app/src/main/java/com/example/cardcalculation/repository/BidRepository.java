package com.example.cardcalculation.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cardcalculation.data.AppDatabase;
import com.example.cardcalculation.data.dao.BidDao;
import com.example.cardcalculation.data.model.Bid;
import com.example.cardcalculation.data.model.Game;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BidRepository {
    private BidDao bidDao;
    private LiveData<List<Bid>> allBids;
    private ExecutorService executorService;

    public BidRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        bidDao = database.bidDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Bid>> getAllBids(int gameId){
       return bidDao.getAllBids(gameId);
    }
    public LiveData<Bid> getBidById(int bidId){ return bidDao.getBidById(bidId);}

    public void insert(Bid bid){
        executorService.execute(() -> bidDao.insert(bid));
    }

    public void update(Bid bid){
        executorService.execute(() -> bidDao.update(bid));
    }

    public void delete(Bid bid){
        executorService.execute(() -> bidDao.delete(bid));
    }
}
